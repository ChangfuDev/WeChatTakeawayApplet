package com.swpu.uchain.takeawayapplet.VO;

import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ProductVO
 * @Author hobo
 * @Date 19-3-25 上午10:39
 * @Description
 **/
@Data
public class ProductVO {

    private String categoryName;

    private Integer categoryType;

    private List<ProductInfo> infoList;


}
