package quartez.config;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import quartez.jobs.MemberClassStatsJob;
import quartez.jobs.MemberStatsJob;

/**
 * created by Atiye Mousavi
 * Date: 8/9/2021
 * Time: 9:22 AM
 */
@Configuration
public class QuartzSubmitJobs {
    //این یک cron experssion است
    //علامت / به معنی این است که هر 5 دقیقه تکرار شود
    //از چپ به راست ثانبه-دقیقه-ساعت -روز ماه-ماه-روز هفته-سال
    //Seconds
    //Minutes
    //Hours
    //Day-of-Month
    //Month
    //Day-of-Week
    //Year (optional field)
    //علامت " به معنی تکرار است-مثلا هر روز هفته-هر ماه
    //علامت ؟ برای فیلد های Day-of-Month و Day-of-Week مجاز است
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";

    @Bean(name = "memberStats")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(MemberStatsJob.class, "Member Statistics Job");
    }

    @Bean(name = "memberStatsTrigger")
    public SimpleTriggerFactoryBean simpleTriggerFactoryMemberStats(@Qualifier("memberStats") JobDetail jobDetail) {
        return QuartzConfig.createSimpleTriggerFactory(jobDetail, 60000, "Member Statistics Trigger");
    }

    @Bean(name = "memberClassStats")
    public JobDetailFactoryBean jobMemberClassStats() {
        return QuartzConfig.createJobDetail(MemberClassStatsJob.class, "Class Statistics Job");
    }

    @Bean(name = "memberClassStatsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("memberClassStats") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Class Statistics Trigger");
    }
}