package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.ProductCategory;
import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    ProductCategory selectByCategoryName(String categoryName);

    List<ProductCategory> selectAll();

    int updateByPrimaryKey(ProductCategory record);
}