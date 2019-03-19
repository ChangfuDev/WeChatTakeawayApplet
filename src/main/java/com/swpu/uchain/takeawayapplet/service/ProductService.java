package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.form.ProductInfoForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    boolean insert(ProductInfo productInfo);

    boolean update(ProductInfo productInfo);

    boolean delete(Long id);

    ProductInfo selectByProductName(String productName);

    ResultVO productInsert(ProductInfoForm productInfoForm, MultipartFile file);

    ResultVO productUpdate(ProductInfo productInfo);

    ResultVO productDelete(Long id);

    ResultVO selectAll();

    List<ProductInfo> selectByCategoryType(Integer categoryType);
}
