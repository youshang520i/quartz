package top.youshang520i.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;

import java.util.Date;

/**
 * @actuor youshang520i
 * @create 2018-11-10 18:45
 * @desc 测试
 */
public class Demo1 {

    public static void main(String[] args) throws SchedulerException {
        //1.创建Schedeler工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //2.从工厂中获取调度器实例
        Scheduler scheduler = schedulerFactory.getScheduler();

        //3.创建JobDataail
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withDescription("this is a ram job")               //任务调度器是根据jobName+GroupName（组的名字）来调用的
                .withIdentity("ramJob","ramGroup")//计划任务调度器的key等于JobName+GroupName如果产生重复的key那么会报错提示,一个key只允许存在一个
                .build();

        //向任务传递数据
       // jobDetail.getJobDataMap().put("orderId",1001);
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("orderId",10001);

        //任务运行的时间，SimpleSchedel类型触发器有效
        long l = System.currentTimeMillis()+ 3 * 1000l;//3秒后启动任务
        Date date = new Date(l);

        //4.创建Trigger（触发器）
        //使用SimpleScheduleBuilder(简单触发器i)或者CrouScheduleBuilder(规则触发器)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("ramTrigger","ramTriggerGroup")
                .startAt(date)//默认当前启动时间
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(3))//间隔三秒执行三次
                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))//规则触发器两秒执行一次
                .build();

        //5.注册任务和定时器
        scheduler.scheduleJob(jobDetail,trigger);

        //6.启动调度器
        scheduler.start();

    }
}
