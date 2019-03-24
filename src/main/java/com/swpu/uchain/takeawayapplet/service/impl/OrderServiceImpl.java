package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.dao.OrderDetailMapper;
import com.swpu.uchain.takeawayapplet.dao.OrderMasterMapper;
import com.swpu.uchain.takeawayapplet.dao.ProductInfoMapper;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import com.swpu.uchain.takeawayapplet.entity.OrderMaster;
import com.swpu.uchain.takeawayapplet.entity.ProductInfo;
import com.swpu.uchain.takeawayapplet.enums.OrderStatusEnum;
import com.swpu.uchain.takeawayapplet.enums.PayStatusEnum;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.redis.key.OrderKey;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import com.swpu.uchain.takeawayapplet.service.PayService;
import com.swpu.uchain.takeawayapplet.util.OrderMasterConversionDTOUtil;
import com.swpu.uchain.takeawayapplet.util.RandomUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import com.swpu.uchain.takeawayapplet.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName OrderServiceImpl
 * @Author hobo
 * @Date 19-3-4 下午12:33
 * @Description
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private PayService payService;

    @Override
    public boolean insert(OrderDetail orderDetail) {
        if (orderDetailMapper.insert(orderDetail) == 1) {
            redisService.set(OrderKey.orderKey, orderDetail.getDetailId() + "", orderDetail);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(OrderMaster orderMaster) {
        TimeUtil timeUtil = new TimeUtil();
        orderMaster.setUpdateTime(timeUtil.getNowTime());
        if (orderMasterMapper.updateByPrimaryKey(orderMaster) == 1) {
            redisService.set(OrderKey.orderKey, orderMaster.getId() + "", OrderMaster.class);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        redisService.delete(OrderKey.orderKey, id + "");
        return (orderDetailMapper.deleteByPrimaryKey(id) == 1);
    }


    @Override
    public List<OrderDetail> selectDetailByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = redisService.getArraylist(OrderKey.orderKey, orderId + "", OrderDetail.class);
        if (orderDetails == null || orderDetails.size() == 0) {
            orderDetails = orderDetailMapper.selectByOrderId(orderId);
            if (orderDetails != null && orderDetails.size() > 0) {
                redisService.set(OrderKey.orderKey, orderId + "", orderDetails);
            }
        }
        return orderDetails;
    }

    @Override
    public OrderDTO findOrder(Long orderId) {
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetails);
        return orderDTO;
    }


    @Override
    public ResultVO findOrderByOrderId(Long orderId) {
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (orderMaster == null) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
        }
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
        }
        OrderDTO orderDto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return ResultUtil.success(orderDto);
    }

    @Override
    public ResultVO findListByOpenId(String openId) {
        List<OrderMaster> orderMasters = redisService.getArraylist(OrderKey.orderKerByOpenId, openId, OrderMaster.class);
        if (orderMasters == null || orderMasters.size() == 0) {
            orderMasters = orderMasterMapper.findListByOpenId(openId);
            if (orderMasters == null || orderMasters.size() == 0) {
                return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
            }
            redisService.set(OrderKey.orderKerByOpenId, openId, orderMasters);
        }
        List<OrderDTO> orderDTOList = OrderMasterConversionDTOUtil.convert(orderMasters);
        return ResultUtil.success(orderDTOList);
    }


    @Override
    public ResultVO creatOrder(OrderDTO orderDTO) {
        Long orderId = RandomUtil.creatRandom();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        TimeUtil timeUtil = new TimeUtil();
        String creatTime = timeUtil.getNowTime();
        //查询商品(数量，单价)
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(orderDetail.getProductId());
            if (productInfo == null) {
                return ResultUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情存入数据库
            orderDetail.setDetailId(RandomUtil.creatRandom());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductId(productInfo.getId());
            orderDetail.setCreatTime(creatTime);
            insert(orderDetail);
        }
        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setId(orderId);
        orderDTO.setId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreatTime(creatTime);
        orderMasterMapper.insert(orderMaster);
        log.info("创建订单 orderMaster={}", orderMaster);
        return ResultUtil.success(orderDTO);
    }

    @Override
    public ResultVO cancelOrder(OrderDTO orderDTO) {

        //判断订单状态 (只有完成完成状态下的订单才能取消)
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
            log.error("【取消订单】 此订单状态状态不正确,id={},orderStatus={}", orderDTO.getId(), orderDTO.getOrderStatus());
            return ResultUtil.error(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = OrderMasterConversionDTOUtil.convert(orderDTO);
        if (update(orderMaster)) {
            return ResultUtil.success();
        }

        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO finishOrder(OrderDTO orderDTO) {

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【完结订单】修改订单状态失败 orderId={},orderStatus={}", orderDTO.getId(), orderDTO.getOrderStatus());
            return ResultUtil.error(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        //TODO 退款
        OrderMaster orderMaster = OrderMasterConversionDTOUtil.convert(orderDTO);
        if (update(orderMaster)) {
            return ResultUtil.success(orderMaster);
        }

        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO paidOrder(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【支付订单】修改订单状态失败 orderId={},orderStatus={}", orderDTO.getId(), orderDTO.getOrderStatus());
            return ResultUtil.error(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.info("【支付订单】修改订单状态失败  orderId={},payStatus={}", orderDTO.getId(), orderDTO.getPayStatus());
            return ResultUtil.error(ResultEnum.PAY_STATUS_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = OrderMasterConversionDTOUtil.convert(orderDTO);
        if (update(orderMaster)) {
            return ResultUtil.success(orderDTO);
        }
        return ResultUtil.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO findAllList() {
        List<OrderMaster> orderMasters = orderMasterMapper.selectAll();
        List<OrderDTO> orderDTOList = OrderMasterConversionDTOUtil.convert(orderMasters);
        return ResultUtil.success(orderDTOList);
    }
}
