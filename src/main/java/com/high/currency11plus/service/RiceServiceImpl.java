package com.high.currency11plus.service;

import com.high.currency11plus.domain.Rice;
import org.springframework.stereotype.Service;

@Service
public class RiceServiceImpl {
    public Rice call() throws Exception {
        System.out.println("开始煮饭");
        Thread.sleep(5000);  // 模拟煮饭时间
        System.out.println("煮饭完成");
        return new Rice();
    }
}
