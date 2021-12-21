package com.high.currency11plus.service;

import com.high.currency11plus.domain.Vegetable;
import org.springframework.stereotype.Service;

@Service
public class VegetableServiceImpl {
    public Vegetable call() throws Exception {
        System.out.println("开始买菜、洗菜");
        Thread.sleep(1000);  // 模拟买菜，洗菜时间
        System.out.println("买菜、洗菜已完成");
        return new Vegetable();
    }
}
