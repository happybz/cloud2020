package com.happybz.springcloud.controller;

import com.happybz.springcloud.entities.CommonResult;
import com.happybz.springcloud.entities.Payment;
import com.happybz.springcloud.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(value = "PaymentController", description = "HelloController")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "payment/create")
    @ApiOperation(notes = "create", value = "create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****insert:" + result);
        if(result>0){
            return new CommonResult(200,"success",result);
        }else{
            return new CommonResult(444,"failed",null);
        }
    }

    @GetMapping(value = "payment/get/{id}")
    @ApiOperation(notes = "getPaymentById", value = "getPaymentById")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****get:" + payment);
        if(payment != null){
            return new CommonResult(200,"success",payment);
        }else{
            return new CommonResult(444,"failed",null);
        }
    }
}
