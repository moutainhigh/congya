package com.chauncy.message.interact.service.impl;

import com.chauncy.common.enums.message.PushObjectEnum;
import com.chauncy.common.enums.message.PushTypeEnum;
import com.chauncy.common.util.ListUtil;
import com.chauncy.common.util.third.JpushClientUtil;
import com.chauncy.data.domain.po.message.interact.MmInteractPushPo;
import com.chauncy.data.domain.po.message.interact.MmInteractRelMessageObjectPo;
import com.chauncy.data.dto.manage.message.interact.add.AddPushMessageDto;
import com.chauncy.data.dto.manage.user.select.SearchUserListDto;
import com.chauncy.data.mapper.message.interact.MmInteractPushMapper;
import com.chauncy.data.mapper.message.interact.MmInteractRelMessageObjectMapper;
import com.chauncy.data.mapper.user.UmUserMapper;
import com.chauncy.data.vo.manage.message.interact.push.UmUsersVo;
import com.chauncy.data.vo.manage.user.list.UmUserListVo;
import com.chauncy.message.interact.service.IMmInteractPushService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        PushTypeEnum pushType = PushTypeEnum.getPushTypeEnumById(addPushMessageDto.getPushType());
        PushObjectEnum pushObject = PushObjectEnum.getPushObjectEnumById(addPushMessageDto.getObjectType());
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
                        //通过会员ID获取对应的用户ID
                        Long memberLevelId = addPushMessageDto.getObjectIds().get(0);
                        MmInteractRelMessageObjectPo relMessageObjectPo = new MmInteractRelMessageObjectPo();
                        relMessageObjectPo.setPushId(interactPushPo.getId());
                        relMessageObjectPo.setObjectId(memberLevelId);
                        relMessageObjectPo.setId(null);
                        relMessageObjectPo.setCreateBy(securityUtil.getCurrUser().getUsername());
                        relMessageObjectMapper.insert(relMessageObjectPo);

                        Map<String, Object> query = Maps.newHashMap();
                        query.put("member_level_id", memberLevelId);
                        List<Long> userIds = userMapper.selectByMap(query).stream().map(b -> b.getId()).collect(Collectors.toList());
                        //List<long> 转List<String>
                        List<String> alia = ListUtil.transferLongToString(userIds);
                        List<List<String>> aliaLists = Lists.partition(alia, 1000);
                        aliaLists.forEach(c -> JpushClientUtil.sendToBieMing(c, notificationTitle, addPushMessageDto.getTitle(), addPushMessageDto.getDetailHtml(), extras));
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
}
