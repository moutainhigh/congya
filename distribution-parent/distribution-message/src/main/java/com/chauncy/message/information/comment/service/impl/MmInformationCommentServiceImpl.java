package com.chauncy.message.information.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.ServiceConstant;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.RelativeDateFormatUtil;
import com.chauncy.data.domain.po.message.information.MmInformationPo;
import com.chauncy.data.domain.po.message.information.comment.MmCommentLikedPo;
import com.chauncy.data.domain.po.message.information.comment.MmInformationCommentPo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.message.information.add.AddInformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.InformationCommentDto;
import com.chauncy.data.dto.manage.message.information.select.SearchInfoCommentDto;
import com.chauncy.data.mapper.message.information.MmInformationMapper;
import com.chauncy.data.mapper.message.information.comment.MmCommentLikedMapper;
import com.chauncy.data.mapper.message.information.comment.MmInformationCommentMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationMainCommentVo;
import com.chauncy.data.vo.manage.message.information.comment.InformationViceCommentVo;
import com.chauncy.message.information.comment.service.IMmInformationCommentService;
import com.chauncy.message.information.service.IMmInformationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 资讯评论信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MmInformationCommentServiceImpl extends AbstractService<MmInformationCommentMapper, MmInformationCommentPo> implements IMmInformationCommentService {

    @Autowired
    private MmInformationCommentMapper mmInformationCommentMapper;
    @Autowired
    private MmInformationMapper mmInformationMapper;
    @Autowired
    private MmCommentLikedMapper mmCommentLikedMapper;
    @Autowired
    private IMmInformationService mmInformationService;

    /**
     * @Author yeJH
     * @Date 2019/10/21 20:34
     * @Description 查询资讯所有评论
     *
     * @Update yeJH
     *
     * @param  searchInfoCommentDto
     * @return com.github.pagehelper.PageInfo<com.chauncy.data.vo.manage.message.information.comment.InformationCommentVo>
     **/
    @Override
    public PageInfo<InformationCommentVo> searchInfoComment(SearchInfoCommentDto searchInfoCommentDto) {

        Integer pageNo = searchInfoCommentDto.getPageNo()==null ? defaultPageNo : searchInfoCommentDto.getPageNo();
        Integer pageSize = searchInfoCommentDto.getPageSize()==null ? defaultPageSize : searchInfoCommentDto.getPageSize();

        PageInfo<InformationCommentVo> informationCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchInfoComment(searchInfoCommentDto));
        return informationCommentVoPageInfo ;

    }

    /**
     * 后台分条件页查询评论
     *
     * @param informationCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationViceCommentVo> searchPaging(InformationCommentDto informationCommentDto) {

        Integer pageNo = informationCommentDto.getPageNo()==null ? defaultPageNo : informationCommentDto.getPageNo();
        Integer pageSize = informationCommentDto.getPageSize()==null ? defaultPageSize : informationCommentDto.getPageSize();

        PageInfo<InformationViceCommentVo> informationViceCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchInfoViceComment(informationCommentDto.getId()));
        return informationViceCommentVoPageInfo;
    }

    /**
     * 根据主评论id查询副评论
     *
     * @param mainId
     * @return
     */
    @Override
    public List<InformationViceCommentVo> searchViceCommentByMainId(Long mainId, Long userId) {

        MmInformationCommentPo mmInformationCommentPo = mmInformationCommentMapper.selectById(mainId);
        if (null == mmInformationCommentPo){
            throw new ServiceException(ResultCode.NO_EXISTS,"评论不存在");
        }
        List<InformationViceCommentVo> informationViceCommentVoList =
                mmInformationCommentMapper.searchViceCommentByMainId(mainId,  userId, null);
        informationViceCommentVoList.stream().forEach(viceComment -> {
            //评论发布时间规则
            viceComment.setReleaseTime(RelativeDateFormatUtil.format(viceComment.getCreateTime()));
        });
        return informationViceCommentVoList;
    }

    /**
     * app分页查询评论
     *
     * @param informationCommentDto
     * @return
     */
    @Override
    public PageInfo<InformationMainCommentVo> searchInfoCommentById(InformationCommentDto informationCommentDto) {

        Integer pageNo = informationCommentDto.getPageNo()==null ? defaultPageNo : informationCommentDto.getPageNo();
        Integer pageSize = informationCommentDto.getPageSize()==null ? defaultPageSize : informationCommentDto.getPageSize();

        PageInfo<InformationMainCommentVo> informationMainCommentVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mmInformationCommentMapper.searchInfoMainComment(informationCommentDto));

        //获取发布时间，用户标签，剩余评论条数等
        editCommentVo(informationMainCommentVoPageInfo.getList());
        return informationMainCommentVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/9/18 11:54
     * @Description 查询资讯评论  评论的用户标签，主评论副评论发布时间格式，剩余评论条数等获取
     *
     * @Update yeJH
     *
     * @Param [informationMainCommentVoList]
     * @return void
     **/
    private void editCommentVo(List<InformationMainCommentVo> informationMainCommentVoList) {
        informationMainCommentVoList.forEach(informationMainCommentVo -> {
            //用户标签
            if (null != informationMainCommentVo.getLabels()){
                informationMainCommentVo.setLabelList(Splitter.on(",")
                        .omitEmptyStrings().splitToList(informationMainCommentVo.getLabels()));
            }
            //剩余评论条数
            informationMainCommentVo.setViceCommentCount(
                    informationMainCommentVo.getViceCommentCount() > ServiceConstant.VICE_COMMENT_NUM
                            ? informationMainCommentVo.getViceCommentCount() - ServiceConstant.VICE_COMMENT_NUM : 0);
            //主评论发布时间规则
            informationMainCommentVo.setReleaseTime(RelativeDateFormatUtil.format(informationMainCommentVo.getCreateTime()));
            //副评论发布时间规则
            List<InformationViceCommentVo> informationViceCommentVoList =
                    informationMainCommentVo.getInformationViceCommentVoList();
            if(null != informationViceCommentVoList) {
                informationViceCommentVoList.forEach(informationViceCommentVo -> {
                    informationViceCommentVo.setReleaseTime(
                            RelativeDateFormatUtil.format(informationViceCommentVo.getCreateTime()));
                });
            }
        });
    }

    /**
     * 隐藏显示评论
     *
     * @param baseUpdateStatusDto
     */
    @Override
    public void editStatusBatch(BaseUpdateStatusDto baseUpdateStatusDto) {
        this.editEnabledBatch(baseUpdateStatusDto);
    }


    /**
     * 删除评论
     * @param id
     */
    @Override
    public void delInfoCommentById(Long id) {
        //批量删除
        mmInformationCommentMapper.deleteById(id);
    }

    /**
     * 保存评论
     */
    @Override
    public InformationMainCommentVo saveInfoComment(AddInformationCommentDto addInformationCommentDto, Long userId) {

        MmInformationCommentPo mmInformationCommentPo = new MmInformationCommentPo();
        mmInformationCommentPo.setInfoId(addInformationCommentDto.getInfoId());
        mmInformationCommentPo.setUserId(userId);
        mmInformationCommentPo.setContent(addInformationCommentDto.getContent());
        if(null != addInformationCommentDto.getParentId() && addInformationCommentDto.getParentId() != 0L) {
            //用户评论的评论
            MmInformationCommentPo parentComment = mmInformationCommentMapper.selectById(addInformationCommentDto.getParentId());
            if(null != parentComment.getParentId()) {
                //用户评论的评论不是主评论
                mmInformationCommentPo.setParentId(parentComment.getParentId());
                mmInformationCommentPo.setParentUserId(parentComment.getUserId());
            } else {
                //用户评论的评论是主评论  parentId是评论本身的id
                mmInformationCommentPo.setParentId(parentComment.getId());
            }
        }
        mmInformationCommentMapper.insert(mmInformationCommentPo);
        //资讯评论量+1
        MmInformationPo mmInformationPo = mmInformationMapper.selectById(addInformationCommentDto.getInfoId());
        UpdateWrapper<MmInformationPo> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().eq(MmInformationPo::getId, mmInformationPo.getId())
                .set(MmInformationPo::getCommentNum, mmInformationPo.getCommentNum() + 1);
        mmInformationService.update(updateWrapper);

        //获取主评论 查询该主评论以及下面的副评论，返回给前端
        InformationCommentDto informationCommentDto = new InformationCommentDto();
        informationCommentDto.setUserId(userId);
        informationCommentDto.setCommentId(mmInformationCommentPo.getParentId());
        List<InformationMainCommentVo> commentVoList = mmInformationCommentMapper.searchInfoMainComment(informationCommentDto);
        if(null != commentVoList && commentVoList.size() > 0) {
            editCommentVo(commentVoList);
            return commentVoList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 用户评论点赞、取消点赞
     *
     * @param commentId  评论id
     * @param userId 用户id
     */
    @Override
    public void likeComment(Long commentId, Long userId) {
        MmInformationCommentPo mmInformationCommentPo = mmInformationCommentMapper.selectById(commentId);
        if(null == mmInformationCommentPo) {
            throw new ServiceException(ResultCode.NO_EXISTS,"评论不存在或已删除");
        }

        //评论点赞数
        Integer infoLikedNum = mmInformationCommentPo.getLikedNum();

        //查询是否点赞过
        MmCommentLikedPo mmCommentLikedPo = mmCommentLikedMapper.selectForUpdate(commentId, userId);
        if(null == mmCommentLikedPo) {
            //未点赞过
            mmCommentLikedPo = new MmCommentLikedPo();
            mmCommentLikedPo.setCommentId(commentId);
            mmCommentLikedPo.setCreateBy(userId.toString());
            mmCommentLikedPo.setUserId(userId);
            //新增点赞记录
            mmCommentLikedMapper.insert(mmCommentLikedPo);
            infoLikedNum += 1;
        } else if(!mmCommentLikedPo.getDelFlag()) {
            //取消点赞
            mmCommentLikedPo.setDelFlag(true);
            mmCommentLikedMapper.updateById(mmCommentLikedPo);
            infoLikedNum -= 1;
        } else {
            //点赞
            mmCommentLikedPo.setDelFlag(false);
            mmCommentLikedMapper.updateById(mmCommentLikedPo);
            infoLikedNum += 1;
        }

        //评论修改点赞数
        UpdateWrapper<MmInformationCommentPo> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().eq(MmInformationCommentPo::getId, commentId)
                .set(MmInformationCommentPo::getLikedNum, infoLikedNum > 0 ? infoLikedNum : 0);
        this.update(updateWrapper);
    }
}
