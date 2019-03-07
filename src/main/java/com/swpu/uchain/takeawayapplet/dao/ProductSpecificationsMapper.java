package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.ProductSpecifications;
import java.util.List;

public interface ProductSpecificationsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductSpecifications record);

    ProductSpecifications selectByPrimaryKey(Integer id);

    List<ProductSpecifications> selectAll();

    int updateByPrimaryKey(ProductSpecifications record);
}