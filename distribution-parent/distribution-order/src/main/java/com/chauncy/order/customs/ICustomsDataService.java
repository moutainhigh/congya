package com.chauncy.order.customs;

import com.chauncy.data.domain.po.order.CustomsDataPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.haiguan.vo.CustomsDataWithMyId;
import com.chauncy.data.haiguan.vo.HgCheckVO;

import java.util.Map;

/**
 * <p>
 * 海关字段 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-20
 */
public interface ICustomsDataService extends Service<CustomsDataPo> {


    /**
     * @return com.chauncy.data.haiguan.vo.HgCheckVO
     * @Author zhangrt
     * @Date 2019/9/21 23:41
     * @Description
     * @Update
     * @Param []
     **/

    CustomsDataWithMyId getHgCheckVo(String customsDataId);

}
