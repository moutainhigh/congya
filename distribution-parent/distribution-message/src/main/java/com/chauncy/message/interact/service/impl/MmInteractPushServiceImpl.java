package com.chauncy.message.interact.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chauncy.common.enums.message.PushObjectEnum;
import com.chauncy.common.enums.message.PushTypeEnum;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.GuavaUtil;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.third.JpushClientUtil;
import com.chauncy.common.util.third.SendSms;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.message.interact.MmInteractPushPo;
import com.chauncy.data.domain.po.message.interact.MmInteractRelMessageObjectPo;
import com.chauncy.data.domain.po.user.PmMemberLevelPo;
import com.chauncy.data.dto.manage.message.interact.add.AddPushMessageDto;
import com.chauncy.data.dto.manage.message.interact.select.SearchPushDto;
import com.chauncy.data.dto.manage.message.interact.select.SearchSmsDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.mapper.message.MmSMSMessageMapper;
import com.chauncy.data.mapper.message.interact.MmInteractPushMapper;
import com.chauncy.data.mapper.message.interact.MmInteractRelMessageObjectMapper;
import com.chauncy.data.mapper.user.PmMemberLevelMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.manage.message.interact.push.InteractPushVo;
import com.chauncy.data.vo.manage.message.interact.push.SmsPushVo;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.chauncy.message.interact.service.IMmInteractPushService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台信息管理 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInteractPushServiceImpl extends AbstractService<MmInteractPushMapper, MmInteractPushPo> implements IMmInteractPushService {

    @Autowired
    private MmInteractPushMapper mapper;

    @Autowired
    private UmUserMapper userMapper;

    @Autowired
    private MmInteractRelMessageObjectMapper relMessageObjectMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PmMemberLevelMapper memberLevelMapper;

    @Autowired
    private MmSMSMessageMapper smsMessageMapper;

    /**
     * 用户列表
     *
     * @param searchUserListDto
     * @return
     */
    @Override
    public PageInfo<UmUsersVo> searchUserList(SearchUserListDto searchUserListDto) {

        Integer pageNo = searchUserListDto.getPageNo() == null ? defaultPageNo : searchUserListDto.getPageNo();
        Integer pageSize = searchUserListDto.getPageSize() == null ? defaultPageSize : searchUserListDto.getPageSize();
        PageInfo<UmUsersVo> umUsersVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> userMapper.loadSearchUsers(searchUserListDto));

        return umUsersVoPageInfo;
    }


    /**
     * 添加推送信息
     *
     * @param addPushMessageDto
     * @return
     */
    @Override
    public void addPushMessage(AddPushMessageDto addPushMessageDto) {
        //获取推送方式
        PushTypeEnum pushType = PushTypeEnum.fromName(addPushMessageDto.getPushType());
        PushObjectEnum pushObject = PushObjectEnum.fromName(addPushMessageDto.getObjectType());
        MmInteractPushPo interactPushPo = new MmInteractPushPo();
        BeanUtils.copyProperties(addPushMessageDto, interactPushPo);
        interactPushPo.setId(null);
        interactPushPo.setCreateBy(securityUtil.getCurrUser().getUsername());
        mapper.insert(interactPushPo);

        Map<String, String> extras = Maps.newHashMap();
        extras.put("param", "额外的字段");
        //通知栏标题
        String notificationTitle = "葱鸭百货";
        switch (pushType) {
            //通知栏
            case NOTIFICATIONBAR:
                //获取并处理推送对象
                switch (pushObject) {
                    //所有用户
                    case ALLUSER:
                        JpushClientUtil.sendToAll(notificationTitle, addPushMessageDto.getTitle(), addPushMessageDto.getDetailHtml(), extras);
                        break;
                    //指定用户
                    case SPECIFYUSER:
                        if (addPushMessageDto.getObjectIds() == null || addPushMessageDto.getObjectIds().size() == 0) {
                            throw new ServiceException(ResultCode.NO_EXISTS, "请选择指定用户");
                        }
                        addPushMessageDto.getObjectIds().forEach(a -> {
                            MmInteractRelMessageObjectPo relMessageObjectPo = new MmInteractRelMessageObjectPo();
                            relMessageObjectPo.setPushId(interactPushPo.getId());
                            relMessageObjectPo.setObjectId(a);
                            relMessageObjectPo.setId(null);
                            relMessageObjectPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                            relMessageObjectMapper.insert(relMessageObjectPo);
                        });
                        //List<Long> 转List<String>
                        List<String> alias = ListUtil.transferLongToString(addPushMessageDto.getObjectIds());
                        List<List<String>> aliasList = Lists.partition(alias, 1000);
                        //极光推送的额外信息
                        aliasList.forEach(x -> {
                            JpushClientUtil.sendToBieMing(x, notificationTitle, addPushMessageDto.getTitle(), addPushMessageDto.getDetailHtml(), extras);

                        });
                        break;
                    //指定会员
                    case SPECIFYMEMBERLEVEL:
                        if (addPushMessageDto.getObjectIds() == null || addPushMessageDto.getObjectIds().size() == 0) {
                            throw new ServiceException(ResultCode.NO_EXISTS, "请选择指定会员等级");
                        }
                        //通过会员ID获取对应的用户ID
                        Long memberLevelId = addPushMessageDto.getObjectIds().get(0);
                        PmMemberLevelPo queryMemberLevel = memberLevelMapper.selectById(memberLevelId);
                        if (queryMemberLevel == null) {
                            throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该会员等级，请检查");
                        }
                        MmInteractRelMessageObjectPo relMessageObjectPo = new MmInteractRelMessageObjectPo();
                        relMessageObjectPo.setPushId(interactPushPo.getId());
                        relMessageObjectPo.setObjectId(memberLevelId);
                        relMessageObjectPo.setId(null);
                        relMessageObjectPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                        relMessageObjectMapper.insert(relMessageObjectPo);

                        int loopSize=userMapper.countLtOrEqLevel(queryMemberLevel.getLevel())/1000+1;
                        for (int i=0;i<loopSize;i++){
                            PageInfo<String> pageUserId = PageHelper.startPage(i + 1, 1000).doSelectPageInfo(() -> userMapper.getIdsLtOrEqLevel(queryMemberLevel.getLevel()));
                            if (!ListUtil.isListNullAndEmpty(pageUserId.getList())){
                                //极光推送
                                JpushClientUtil.sendToBieMing(pageUserId.getList(), notificationTitle, addPushMessageDto.getTitle(), addPushMessageDto.getDetailHtml(), extras)           ;
                            }
                        }
                        break;
                }
                break;
            //app内消息中心
            case APPMESSAGE:
                switch (pushObject) {
                    //所有用户
                    case ALLUSER:
                        break;
                    //指定会员
                    case SPECIFYMEMBERLEVEL:
                        //通过会员ID获取对应的用户ID
                        Long memberLevelId = addPushMessageDto.getObjectIds().get(0);
                        MmInteractRelMessageObjectPo relMessageObjectPo = new MmInteractRelMessageObjectPo();
                        relMessageObjectPo.setPushId(interactPushPo.getId());
                        relMessageObjectPo.setObjectId(memberLevelId);
                        relMessageObjectPo.setId(null);
                        relMessageObjectPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                        relMessageObjectMapper.insert(relMessageObjectPo);
                        break;
                    //指定用户
                    case SPECIFYUSER:
                        addPushMessageDto.getObjectIds().forEach(a -> {
                            MmInteractRelMessageObjectPo messageObjectPo = new MmInteractRelMessageObjectPo();
                            messageObjectPo.setPushId(interactPushPo.getId());
                            messageObjectPo.setObjectId(a);
                            messageObjectPo.setId(null);
                            messageObjectPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                            relMessageObjectMapper.insert(messageObjectPo);
                        });
                        break;
                }
                break;
        }
    }

    /**
     * 条件查询推送信息
     *
     * @param searchPushDto
     * @return
     */
    @Override
    public PageInfo<InteractPushVo> search(SearchPushDto searchPushDto) {
        Integer pageNo = searchPushDto.getPageNo() == null ? defaultPageNo : searchPushDto.getPageNo();
        Integer pageSize = searchPushDto.getPageSize() == null ? defaultPageSize : searchPushDto.getPageSize();
        PageInfo<InteractPushVo> interactPushVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.search(searchPushDto));
        //根据不同推送对象获取对应的信息
        interactPushVoPageInfo.getList().forEach(a -> {
            PushObjectEnum pushObject = PushObjectEnum.fromName(a.getObjectType());
            switch (pushObject) {
                case ALLUSER:
                    a.setSpecifiedObject(pushObject.getName());
                    break;
                case SPECIFYUSER:
                    QueryWrapper<MmInteractRelMessageObjectPo> query1 = new QueryWrapper<>();
                    query1.eq("push_id", a.getId());
                    List<MmInteractRelMessageObjectPo> relMessageObjectPos = relMessageObjectMapper.selectList(query1);
                    if (ListUtil.isListNullAndEmpty(relMessageObjectPos)) {
                        break;
                    }
                    List<Long> userIds = relMessageObjectPos.stream().map(d -> d.getObjectId()).collect(Collectors.toList());
                    List<UmUsersVo> usersVos = userMapper.getUsersByIds(userIds);
                    a.setUserList(usersVos);
                    a.setSpecifiedObject(pushObject.getName());
                    break;
                case SPECIFYMEMBERLEVEL:
                    QueryWrapper<MmInteractRelMessageObjectPo> query = new QueryWrapper<>();
                    query.eq("push_id", a.getId());
                    MmInteractRelMessageObjectPo relMessageObjectPos1 = relMessageObjectMapper.selectOne(query);
                    if (relMessageObjectPos1 == null) {
                        break;
                    }
                    Long memberLevelId = relMessageObjectPos1.getObjectId();
                    a.setMemberLevelId(memberLevelId);
                    String memberName = memberLevelMapper.selectById(memberLevelId).getLevelName();
                    a.setSpecifiedObject(memberName);
                    break;
            }
        });

        return interactPushVoPageInfo;
    }



    /**
     * 根据推送信息ID批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public void delPushByIds(Long[] ids) {

        List<Long> idList = Arrays.asList(ids);
        idList.forEach(a -> {
            if (mapper.selectById(a) == null) {
                throw new ServiceException(ResultCode.NO_EXISTS, "数据库不存在该数据");
            }
            Map<String, Object> query = Maps.newHashMap();
            query.put("push_id", a);
            List<Long> relIds = relMessageObjectMapper.selectByMap(query).stream().map(b -> b.getId()).collect(Collectors.toList());
            if (!ListUtil.isListNullAndEmpty(relIds)) {
                relMessageObjectMapper.deleteBatchIds(relIds);
            }
            mapper.deleteById(a);
        });

    }



    /**
     * 查找所有会员等级id和名称
     *
     * @return
     */
    @Override
    public List<BaseVo> searchMemberLevel() {

        return memberLevelMapper.searchMemberLevel();
    }
}
