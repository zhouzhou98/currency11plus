package com.high.currency11plus.service;

import com.high.currency11plus.domain.Cook;
import org.springframework.stereotype.Service;

@Service
public class CookServiceImpl {
    public Cook call() throws Exception {
        System.out.println("开始炒菜");
        // 模拟炒菜时间
        Thread.sleep(8000);
        Cook cook = new Cook();
        System.out.println("炒菜完毕");
        return cook;
    }
}
