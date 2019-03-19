package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.entity.ProductCategory;
import com.swpu.uchain.takeawayapplet.form.CategoryForm;

import java.util.List;

public interface CategoryService {

    boolean insert(ProductCategory productCategory);

    boolean update(ProductCategory productCategory);

    boolean delete(Integer id);

    ProductCategory selectByCategoryName(String categoryName);

    ResultVO findAll();

    ResultVO insertCategory(CategoryForm categoryForm);

    ResultVO updateCategory(ProductCategory productCategory);

    ResultVO deleteCategory(Integer id);

}
