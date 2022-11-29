package com.imooc.spring.escape.scheduled_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Scheduled 中的常见参数
 * fixedDelay：上次任务结束与下次任务开始的间隔时间（毫秒）
 * fixedRate：两次任务开始的间隔时间，不管上次任务是否执行完成
 * initialDelay：一般与上面两个组合使用，表示回等待设置的时间之后才开始第一次执行（第一次执行的延迟时间）
 * cron：上次任务结束与下次任务开始间隔时间（秒）
 *
 * @author zlp
 * @date 2022/11/29
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class ScheduledTask {

    @Scheduled(fixedRate = 1000)
    public void task01() throws Exception {
        log.info("Schedule Task process task01.");
        while (true) {
            Thread.sleep(2000);
            log.info("Schedule Task process something!");
        }
    }

    @Scheduled(fixedRate = 1000)
    public void tesk02() {
        log.info("Schedule Task process task02.");
    }
}
