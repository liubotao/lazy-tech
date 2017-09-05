package com.whh.common.utils.email;

/**
 * Created by huahui.wu on 2017/7/27.
 */

import com.alibaba.dubbo.common.utils.NamedThreadFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


/**
 *
 *
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

    public static void main(String[] args) {
        System.out.println("------------------------------发送开始---------");
        SMTPThreadPoolExecutor.execute(new Runnable() {
            public void run() {
                String userName = "admin@edu-ing.ml"; // 发件人邮箱
                String password = "WHH88913233123"; // 发件人密码
                String smtpHost = "smtp.yandex.com"; // 邮件服务器

                String to = "rece@edu-ing.ml"; // 收件人，多个收件人以半角逗号分隔
                String cc = "rece1faf@edu-ing.ml"; // 抄送，多个抄送以半角逗号分隔
                String subject = "这是邮件的主题"; // 主题
                String body = "<h2 style='color:red'>这是邮件的正文</h2>"; // 正文，可以用html格式的哟
                List<String> attachments = Arrays.asList("D:\\test.txt"); // 附件的路径，多个附件也不怕
                EmailUtil email = EmailUtil.entity(smtpHost, userName, password, to, cc, subject, body, attachments);
                try {
                    email.send(); // 发送！
                } catch (Exception e) {

                }


            }
        });
        System.out.println("------------------------------发送结束---------");
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