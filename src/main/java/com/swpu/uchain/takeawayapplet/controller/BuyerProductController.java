package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.VO.ProductVO;
import com.swpu.uchain.takeawayapplet.entity.ProductCategory;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.service.CategoryService;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BuyerProductController
 * @Author hobo
 * @Date 19-3-25 上午10:30
 * @Description 买家商品接口类
 **/
@RestController
@RequestMapping("/buyer/product")
@Api(tags = "买家商品接口")
public class BuyerProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list", name = "获取商品信息")
    public Object list() {

        List<ProductCategory> categories = categoryService.selectAll();

        List<ProductVO> productVOS = new ArrayList<>();
        for (ProductCategory productCategory : categories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfo> infoList = productService.selectByCategoryType(productCategory.getCategoryType());

            productVO.setInfoList(infoList);
            productVOS.add(productVO);
        }
        return ResultUtil.success(productVOS);
    }

}
