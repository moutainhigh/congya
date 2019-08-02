package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.mapper.IBaseMapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
public interface OmOrderMapper extends IBaseMapper<OmOrderPo> {

   /* SELECT o.id,s.id,s.`name`,u.`name`,u.phone,o.total_number,o.sum_money,a.ship_name,a.detailed_address,a.mobile,o.activity_type,o.`status`,o.create_time
    from om_order o,sm_store s,um_user u,um_area_shipping a
    where o.store_id=s.id and o.um_user_id=u.id and o.area_shipping_id=a.id
    and o.del_flag=0 and s.del_flag=0 and u.del_flag=0 and a.del_flag=0
    and o.id='' and u.id='' and u.phone LIKE '%%' AND o.activity_type=''
    AND s.id='' and s.name LIKE '%%' AND o.create_time>=   and o.create_time<=
    AND o.`status`=''  AND o.sum_money>=   AND o.sum_money<=*/






}
