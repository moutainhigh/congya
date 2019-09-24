package com.chauncy.data.haiguan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/9/23 13:47
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomsDataWithMyId {

    private String customsDataId;

    private HgCheckVO hgCheckVO;
}
