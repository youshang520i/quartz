package top.youshang520i.demo;

import org.quartz.*;

import java.util.Date;

/**
 * @actuor youshang520i
 * @create 2018-11-10 18:43
 * @desc 测试
 */
public class MyJob implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务..........."+new Date().toLocaleString());

        JobDetail jobDetail = jobExecutionContext.getJobDetail();   //从定时任务的上下文中获取Job数据
        JobDataMap jobDataMap = jobDetail.getJobDataMap();      //从数据中获取Map数据
        Object orderId = jobDataMap.get("orderId");             //冲map中获取key为orderId的value
        System.out.println(orderId);                    //输出

    }
}
