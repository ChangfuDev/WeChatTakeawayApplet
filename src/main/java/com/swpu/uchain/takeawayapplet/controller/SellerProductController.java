package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.form.ProductInfoForm;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName SellerProductController
 * @Author hobo
 * @Date 19-3-18 下午6:47
 * @Description 卖家端商品
 **/

@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("商家获取商品列表")
    @GetMapping(value = "/list", name = "商家获取商品列表")
    public Object getList() {
        return productService.selectAll();
    }


    @ApiOperation("商家添加商品")
    @PostMapping(value = "/insert", name = "商家添加商品")
    public Object insertProduct(ProductInfoForm productInfoForm, MultipartFile file) {
        return productService.productInsert(productInfoForm, file);
    }

    @ApiOperation("商家删除商品")
    @GetMapping(value = "/delete", name = "商家删除商品")
    public Object deleteProduct(Long id) {
        return productService.productDelete(id);
    }

}
