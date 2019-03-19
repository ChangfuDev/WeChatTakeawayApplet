package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.config.UploadIconConfig;
import com.swpu.uchain.takeawayapplet.dao.ProductInfoMapper;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.ProductInfoForm;
import com.swpu.uchain.takeawayapplet.redis.key.ProductKey;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.service.ProductService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import com.swpu.uchain.takeawayapplet.util.TimeUtil;
import com.swpu.uchain.takeawayapplet.util.UploadIconUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private UploadIconUtil uploadIconUtil;

    @Autowired
    private UploadIconConfig uploadIconConfig;


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
    public ResultVO productInsert(ProductInfoForm productInfoForm, MultipartFile file) {
        if (productInfoMapper.selectByProductName(productInfoForm.getProductName()) != null) {
            return ResultUtil.error(ResultEnum.PRODUCT_EXIST);
        }

        ProductInfo productInfo = new ProductInfo();
        String iconPath = uploadIconUtil.getUploadFilePath(file);
        productInfo.setProductIcon(uploadIconConfig.getUploadDir() + "/" + iconPath);
        TimeUtil timeUtil = new TimeUtil();
        productInfo.setCreatTime(timeUtil.getNowTime());
        BeanUtils.copyProperties(productInfoForm, productInfo);

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
        TimeUtil timeUtil = new TimeUtil();
        productInfo.setUpdateTime(timeUtil.getNowTime());
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
    public ResultVO selectAll() {
        return ResultUtil.success(productInfoMapper.selectAll());
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
