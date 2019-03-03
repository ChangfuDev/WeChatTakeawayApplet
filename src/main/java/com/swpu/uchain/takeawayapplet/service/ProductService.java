package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;

import java.util.List;

public interface ProductService {


    boolean insert(ProductInfo productInfo);

    boolean update(ProductInfo productInfo);

    boolean delete(Long id);

    ProductInfo selectByProductName(String productName);

    ResultVO productInsert(ProductInfo productInfo);

    ResultVO productUpdate(ProductInfo productInfo);

    ResultVO productDelete(Long id);

    List<ProductInfo> selectAll();

    List<ProductInfo> selectByCategoryType(Integer categoryType);
}
