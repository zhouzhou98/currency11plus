package com.high.currency11plus.controller;

import com.high.currency11plus.domain.Rice;
import com.high.currency11plus.service.CookServiceImpl;
import com.high.currency11plus.service.RiceServiceImpl;
import com.high.currency11plus.service.VegetableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class TestController {
    @Autowired
    private CookServiceImpl cookService;

    @Autowired
    private RiceServiceImpl riceService;

    @Autowired
    private VegetableServiceImpl vegetableService;
    @GetMapping("/test")
    public void test() throws ExecutionException, InterruptedException {
        Integer threads = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                threads << 1,
                50,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        long startTime = System.currentTimeMillis();
        CompletableFuture<Rice> riceFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return riceService.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, executor);

        CompletableFuture vegetableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return vegetableService.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).thenApplyAsync(v -> {
            try {
                return cookService.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, executor);
        CompletableFuture.allOf(riceFuture, vegetableFuture).get();
        System.out.println("整个过程，共耗时" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
