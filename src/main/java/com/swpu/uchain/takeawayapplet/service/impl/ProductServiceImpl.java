package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.dao.ProductInfoMapper;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.redis.key.ProductKey;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Author hobo
 * @Date 19-3-3 下午4:53
 * @Description
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean insert(ProductInfo productInfo) {
        if (productInfoMapper.insert(productInfo) == 1) {
            redisService.set(ProductKey.productKey, productInfo.getProductName(), productInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(ProductInfo productInfo) {
        if (productInfoMapper.updateByPrimaryKey(productInfo) == 1) {
            redisService.set(ProductKey.productKey, productInfo.getProductName(), productInfo);
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(Long id) {
        redisService.delete(ProductKey.productKey, id + "");
        return (productInfoMapper.deleteByPrimaryKey(id) == 1);
    }

    @Override
    public ProductInfo selectByProductName(String productName) {
        return productInfoMapper.selectByProductName(productName);
    }

    @Override
    public ResultVO productInsert(ProductInfo productInfo) {
        if (productInfoMapper.selectByProductName(productInfo.getProductName()) != null) {
            return ResultUtil.error(ResultEnum.PRODUCT_EXIST);
        }
        if (insert(productInfo)) {
            return ResultUtil.success(productInfo);
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO productUpdate(ProductInfo productInfo) {
        if (productInfoMapper.selectByProductName(productInfo.getProductName()) == null) {
            return ResultUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (update(productInfo)) {
            return ResultUtil.success(productInfo);
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    /**
     * 商家删除商品
     */
    @Override
    public ResultVO productDelete(Long id) {
        if (productInfoMapper.selectByPrimaryKey(id) == null) {
            return ResultUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (delete(id)) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public List<ProductInfo> selectAll() {
        return productInfoMapper.selectAll();
    }

    @Override
    public List<ProductInfo> selectByCategoryType(Integer categoryType) {
        List<ProductInfo> productInfos = redisService.getArraylist(ProductKey.productKey, categoryType + "", ProductInfo.class);
        if (productInfos == null || productInfos.size() == 0) {
            productInfos = productInfoMapper.selectProductByCategoryType(categoryType);
            if (productInfos != null && productInfos.size() != 0) {
                redisService.set(ProductKey.productKey, categoryType + "", productInfos);
            }
        }
        return productInfos;
    }
}
