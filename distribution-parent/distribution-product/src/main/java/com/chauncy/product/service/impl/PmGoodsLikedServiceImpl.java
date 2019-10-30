package com.chauncy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.domain.po.product.PmGoodsLikedPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.product.PmGoodsLikedMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.product.service.IPmGoodsLikedService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品点赞表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsLikedServiceImpl extends AbstractService<PmGoodsLikedMapper, PmGoodsLikedPo> implements IPmGoodsLikedService {

    @Autowired
    private PmGoodsLikedMapper mapper;

    @Autowired
    private PmGoodsMapper goodsMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author chauncy
     * @Date 2019-10-30 13:39
     * @Description //对商品点赞/取消点赞
     *
     * @Update chauncy
     *
     * @param  goodsId
     * @return java.lang.Integer
     **/
    @Override
    public Integer updateGoodsLiked(Long goodsId) {

        UmUserPo userPo = securityUtil.getAppCurrUser();
        if (userPo == null ){
            throw new ServiceException(ResultCode.FAIL, "您不是app用户！");
        }

        //查询是否点赞过,并发情况，锁住行
        PmGoodsLikedPo goodsLikedPo = mapper.selectOne(new QueryWrapper<PmGoodsLikedPo>().lambda()
                .eq(PmGoodsLikedPo::getGoodsId, goodsId)
                .eq(PmGoodsLikedPo::getUserId, userPo.getId())
                .last("for update"));

        //从未点赞
        /*if (goodsLikedPo == null){
            goodsLikedPo = new PmGoodsLikedPo();
            goodsLikedPo.setId(null).setCreateBy(userPo.getId()).setGoodsId(goodsId).setUserId(userPo.getId())
                    .setIsLiked(true).setDelFlag(false);
            mapper.insert(goodsLikedPo);

            goodsMapper.addLikedNum(evaluateId);

        }else if (evaluateLikedPo.getIsLiked()) { //取消点赞

            evaluateLikedPo.setIsLiked(false);
            evaluateLikedMapper.updateById(evaluateLikedPo);

            mapper.delLikedNum(evaluateId);
        }else if (!evaluateLikedPo.getIsLiked()){ //点赞过且取消点赞再次点赞
            evaluateLikedPo.setIsLiked(true);
            evaluateLikedMapper.updateById(evaluateLikedPo);

            mapper.addLikedNum(evaluateId);

        }
        Integer num = mapper.selectById(evaluateId).getLikedNum();
        if (num < 0){
            num = 0;
        }*/

        return null;
    }
}
