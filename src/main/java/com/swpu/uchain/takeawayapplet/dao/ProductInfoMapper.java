package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductInfo record);

    ProductInfo selectByPrimaryKey(Long id);

    List<ProductInfo> selectAll();

    int updateByPrimaryKey(ProductInfo record);

    ProductInfo selectByProductName(String productName);

    List<ProductInfo> selectProductByCategoryType(Integer categoryType);
}