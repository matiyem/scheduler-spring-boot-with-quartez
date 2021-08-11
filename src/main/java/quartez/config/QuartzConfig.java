package quartez.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 10:04 AM
 */
@Slf4j
@Configuration
public class QuartzConfig {


    //در مرحله 2 این کلاس نوشته میشود و بعد از آن برنامه حاضر است که جابی را اجرا کند
    private ApplicationContext applicationContext;
    private DataSource dataSource;


    public QuartzConfig(ApplicationContext applicationContext, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger...triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "MyInstanceName");
        properties.setProperty("org.quartz.scheduler.instanceId", "Instance1");

        schedulerFactory.setOverwriteExistingJobs(true);//جاب هایی که قبلا ساخته شده اند توسط configuration و register شده اند بصورت پیشفرض دوباره overWrite نمیشوند برای اینکه دوباره overWrite ود باید مقدار آن را true قرار دهیم
        schedulerFactory.setAutoStartup(true);//برای اینکه بعد از initialize شدن خودش run شود که بصورت پیش فرض true در نظر گرفته میشود
        schedulerFactory.setQuartzProperties(properties);
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);

        if (ArrayUtils.isNotEmpty(triggers)) {
            schedulerFactory.setTriggers(triggers);
        }
        return schedulerFactory;
    }

    static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs, String triggerName) {
        Map map = new HashMap();
        map.put("jobDetail", jobDetail);
        map.put("pollFrequencyMs", pollFrequencyMs);
        map.put("triggerName", triggerName);
        log.debug("createTrigger(jobDetail={}, pollFrequencyMs={}, triggerName={})", map);

        SimpleTriggerFactoryBean factoryBean=new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setName(triggerName);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return factoryBean;
    }
    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail ,String cronExpression, String triggerName){
        Map map = new HashMap();
        map.put("jobDetail", jobDetail);
        map.put("cronExpression", cronExpression);
        map.put("triggerName", triggerName);
        log.debug("createTrigger(jobDetail={}, pollFrequencyMs={}, triggerName={})", map);

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

        return factoryBean;
    }

    static JobDetailFactoryBean createJobDetail(Class jobClass,String jobName){
        //این متد در اصل یه جاب میسازد و آن را به executor مورد نظر وصل میکند

        log.debug("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);

        return factoryBean;
    }

}

