package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.productInfo;
import java.util.List;

public interface productInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(productInfo record);

    productInfo selectByPrimaryKey(Long id);

    List<productInfo> selectAll();

    int updateByPrimaryKey(productInfo record);
}