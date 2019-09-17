package com.chauncy.message.sms;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chauncy.common.enums.message.PushObjectEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.GuavaUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.third.SendSms;
import com.chauncy.data.domain.po.message.MmSMSMessagePo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.manage.message.interact.add.AddSmsMessageDto;
import com.chauncy.data.mapper.message.MmSMSMessageMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.message.sms.impl.IMmSMSMessageService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.user.service.IPmMemberLevelService;
import com.chauncy.user.service.IUmUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 平台短信信息 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmSMSMessageServiceImpl extends AbstractService<MmSMSMessageMapper, MmSMSMessagePo> implements IMmSMSMessageService {

    @Autowired
    private MmSMSMessageMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Override
    public void saveSmsMessage(AddSmsMessageDto addSmsMessageDto) {

        //保存发送短信信息到数据库
        MmSMSMessagePo saveSms=new MmSMSMessagePo();
        BeanUtils.copyProperties(addSmsMessageDto,saveSms);
        UmUserPo appCurrUser = securityUtil.getAppCurrUser();
        saveSms.setCreateBy(appCurrUser.getId()+"");
        mapper.insert(saveSms);

        //获取用户手机号，逗号隔开
        int loopSize;
        PushObjectEnum pushObjectEnum=PushObjectEnum.getPushObjectEnumById(addSmsMessageDto.getPushObject());
        switch (pushObjectEnum){
            case ALLUSER:
                //阿里批量发送短信一次只能发送1000条
                  loopSize=userMapper.selectCount(Wrappers.emptyWrapper());
                 for (int i=0;i<loopSize;i++){
                     PageInfo<String> pageUserPhones = PageHelper.startPage(i + 1, 1000).doSelectPageInfo(() -> userMapper.getAllPhones());
                     if (!ListUtil.isListNullAndEmpty(pageUserPhones.getList())){
                         //阿里云发送短信
                         SendSms.sendContent(GuavaUtil.ListToString(pageUserPhones.getList(),","),addSmsMessageDto.getTemplateCode());
                     }
                 }
                 break;
            case SPECIFYUSER:
                if (ListUtil.isListNullAndEmpty(addSmsMessageDto.getObjectIds())){
                    throw new ServiceException(ResultCode.PARAM_ERROR,"选择指定用户时，用户的手机号码不能为空");
                }
                //每1000个用户手机分组
                List<List<String>> specifyUserPhones = Lists.partition(addSmsMessageDto.getObjectIds(), 1000);
                specifyUserPhones.forEach(x->{
                    if (!ListUtil.isListNullAndEmpty(x)){
                        //阿里云发送短信
                        SendSms.sendContent(GuavaUtil.ListToString(x,","),addSmsMessageDto.getTemplateCode());
                    }
                });
                break;
            case SPECIFYMEMBERLEVEL:
                if (ListUtil.isListNullAndEmpty(addSmsMessageDto.getObjectIds())){
                    throw new ServiceException(ResultCode.NO_EXISTS,"选择指定用户时，会员等级id不能为空");
                }
                //通过会员ID获取对应的用户ID
                String memberLevelId = addSmsMessageDto.getObjectIds().get(0);
                if (memberLevelMapper.selectById(memberLevelId)==null){
                    throw new ServiceException(ResultCode.NO_EXISTS,"数据库不存在该会员等级，请检查");
                }
                break;
        }

    }
}
