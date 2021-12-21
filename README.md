问题：请用多线程基本知识完成两种情况下的煮饭、准备 菜(买菜、洗菜）、炒菜、开始吃饭的功能。  
1、准备菜(买菜、洗菜)：  1秒， 炒菜：2秒，煮饭：5秒   
2、准备菜(买菜、洗菜)：1秒，炒菜：8秒，煮饭： 5秒。说明：只有煮饭完成、准备菜、炒菜完成，才能开始吃饭，两种情况 下，在尽量短的时间内完成所有动作。

方法：通过CompleteFuture以实现  
```java
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
        System.out.println("整个过程，共耗时" + (System.currentTimeMillis() - startTime) + "ms")
```
