package com.whh.common.utils.email;

/**
 * Created by huahui.wu on 2017/7/27.
 */

import com.alibaba.dubbo.common.utils.NamedThreadFactory;

import java.util.concurrent.*;


/**
 * 邮件发送异步线程池
 */
public class SMTPThreadPoolExecutor {
    private static int corePoolSize = 10;
    private static int maximumPoolSize = -1;
    private static long keepAliveTime = 100;
    private static int workQueueSize = 0;
    private static TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private static BlockingQueue<Runnable> workQueue = null;
    private static ExecutorService executorService = null;
    private static String name = "smtp";
    private static RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

    static {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        executorService = create();
    }

    private SMTPThreadPoolExecutor() {
    }

    public static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }


    public static ExecutorService create() {
        ExecutorService executorService = null;
        if (maximumPoolSize <= 0) {
            maximumPoolSize = Integer.MAX_VALUE;
        }
        if (workQueue == null) {
            if (workQueueSize <= 0) {
                workQueue = new LinkedBlockingQueue<Runnable>();
                workQueueSize = Integer.MAX_VALUE;
            } else {
                workQueue = new LinkedBlockingQueue<Runnable>(workQueueSize);
            }
        }
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue,
                new NamedThreadFactory(name), handler);
        return executorService;
    }

}