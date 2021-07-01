package com.wenruo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mysql.cj.util.TimeUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @projectName: WenRuo
 * @package: com.wenruo
 * @className: ThreadPoolTest
 * @author: muyao
 * @description: 1.0.0
 * @date: 2020/12/30 2:43 下午
 * @version: 1.0
 */
public class ThreadPoolTest {

    /*
        定义一个全局的simpleDateformat
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*
        使用threadFactorBuilder创建线程池
     */
    private static ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5,
            200,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024),
            nameThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    /*
        定义一个CountDownLatch，保证所有子线程完成之后主线程在执行
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {
        /* 定义一个线程安全的HashSet */
        Set<String> dates = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < 100 ; i++ ) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            pool.execute(() -> {
                /* 时间增加 */
                calendar.add(Calendar.DATE, finalI);
                /* 通过simpleDateFormat把时间转成字符串 */
                String dateString = simpleDateFormat.format(calendar.getTime());
                /* 把字符串放进set中 */
                dates.add(dateString);
                /*countDown*/
                countDownLatch.countDown();
            });
        }
        /* 阻塞，直到countDown为0 */
        countDownLatch.await();
        System.out.println(dates.size());
    }
}
