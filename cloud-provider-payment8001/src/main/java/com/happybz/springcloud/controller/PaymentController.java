package com.happybz.springcloud.controller;

import com.happybz.springcloud.entities.CommonResult;
import com.happybz.springcloud.entities.Payment;
import com.happybz.springcloud.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@Api(value = "PaymentController", description = "HelloController")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("***** element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @PostMapping(value = "payment/create")
    @ApiOperation(notes = "create", value = "create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****insert:" + result);
        if(result>0){
            return new CommonResult(200,"success,serverPort:"+serverPort,result);
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
            return new CommonResult(200,"success,serverPort:"+serverPort,payment);
        }else{
            return new CommonResult(444,"failed",null);
        }
    }
}
