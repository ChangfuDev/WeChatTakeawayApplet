package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.form.CategoryForm;
import com.swpu.uchain.takeawayapplet.service.CategoryService;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SellerCategoryController
 * @Author hobo
 * @Date 19-3-19 下午12:47
 * @Description 商家类目接口
 **/

@RestController
@RequestMapping("/seller/category")
@Api(tags = "商家类目接口")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ApiOperation("获取类目列表")
    @GetMapping(value = "/list", name = "获取类目列表")
    public Object list() {
        return categoryService.findAll();
    }


    @ApiOperation("获取此类目下的所有商品")
    @GetMapping(value = "/findOne", name = "获取此类目下的所有商品")
    public Object findOne(Integer categoryType) {
        return productService.selectByCategoryType(categoryType);
    }

    @ApiOperation("添加类目")
    @PostMapping(value = "/insert", name = "添加类目")
    public Object insert(CategoryForm categoryForm) {
        return categoryService.insertCategory(categoryForm);
    }

    @ApiOperation("修改类目")
    @PostMapping(value = "/update", name = "修改类目")
    public Object update(CategoryForm categoryForm) {
        return categoryService.updateCategory(categoryForm);
    }

    @ApiOperation("删除类目")
    @GetMapping(value = "/delete", name = "删除类目")
    public Object delete(Integer id) {
        return categoryService.deleteCategory(id);
    }


}
