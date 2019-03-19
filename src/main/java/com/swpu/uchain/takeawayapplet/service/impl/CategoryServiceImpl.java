package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.dao.ProductCategoryMapper;
import com.swpu.uchain.takeawayapplet.entity.ProductCategory;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.CategoryForm;
import com.swpu.uchain.takeawayapplet.redis.key.CategoryKey;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.service.CategoryService;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import com.swpu.uchain.takeawayapplet.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Author hobo
 * @Date 19-3-3 下午2:21
 * @Description 类目service层
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean insert(ProductCategory productCategory) {
        if (productCategoryMapper.insert(productCategory) == 1) {
            redisService.set(CategoryKey.categoryKey, productCategory.getCategoryType() + "", productCategory);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(ProductCategory productCategory) {
        if (productCategoryMapper.updateByPrimaryKey(productCategory) == 1) {
            redisService.set(CategoryKey.categoryKey, productCategory.getCategoryType() + "", productCategory);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        redisService.delete(CategoryKey.categoryKey, id + "");
        return (productCategoryMapper.deleteByPrimaryKey(id) == 1);
    }

    @Override
    public ProductCategory selectByCategoryName(String categoryName) {
        return productCategoryMapper.selectByCategoryName(categoryName);
    }


    @Override
    public ResultVO findAll() {
        return ResultUtil.success(productCategoryMapper.selectAll());
    }

    @Override
    public ResultVO insertCategory(CategoryForm categoryForm) {
        if (selectByCategoryName(categoryForm.getCategoryName()) != null) {
            return ResultUtil.error(ResultEnum.CATEGORY_EXIST);
        }
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, productCategory);
        TimeUtil timeUtil = new TimeUtil();
        productCategory.setCreatTime(timeUtil.getNowTime());

        if (insert(productCategory)) {
            return ResultUtil.success(productCategory);
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updateCategory(ProductCategory productCategory) {
        if (productCategoryMapper.selectByPrimaryKey(productCategory.getId()) == null) {
            return ResultUtil.error(ResultEnum.CATEGORY_NOT_EXIST);
        }
        TimeUtil timeUtil = new TimeUtil();
        productCategory.setUpdateTime(timeUtil.getNowTime());
        if (update(productCategory)) {
            return ResultUtil.success(productCategory);
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteCategory(Integer id) {
        if (productCategoryMapper.selectByPrimaryKey(id) == null) {
            return ResultUtil.error(ResultEnum.CATEGORY_NOT_EXIST);
        }

        //判断类目下是否还有商品存在
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(id);
        List<ProductInfo> infos = productService.selectByCategoryType(productCategory.getCategoryType());
        if (infos != null && infos.size() > 0) {
            return ResultUtil.error(ResultEnum.PRODUCT_OF_CATEGORY_NOT_NULL);
        }
        if (delete(id)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }
}
