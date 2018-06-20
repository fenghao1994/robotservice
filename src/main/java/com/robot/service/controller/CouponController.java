package com.robot.service.controller;

import com.robot.service.model.CouponTicket;
import com.robot.service.model.Result;
import com.robot.service.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/create/coupon", method = RequestMethod.POST)
    public ResponseEntity<Result> createCoupon() {
        couponService.createCoupon();
        Result result = new Result();
        result.setCode(1);
        result.setData("生成成功");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/coupon", method = RequestMethod.GET)
    public ResponseEntity<List<CouponTicket>> getCoupon() {
        List<CouponTicket> noUseCoupon = couponService.getNoUseCoupon();
        return new ResponseEntity(noUseCoupon, HttpStatus.OK);
    }
}
