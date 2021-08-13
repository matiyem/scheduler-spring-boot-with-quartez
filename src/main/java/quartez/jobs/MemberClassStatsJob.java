package quartez.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quartez.service.MemberClassService;

import java.io.IOException;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 3:46 PM
 */
@Slf4j
@Component
@DisallowConcurrentExecution//به qurtez میگوید چندین نمونه از جاب معین در یک زمان را اجرا نکند این محدودیت در اصل برای jobDetail در نظر گرفته شده نه برای job class
public class MemberClassStatsJob implements Job {
    //Job یک اینترفیس است که توسط comoponent پیاده سازی میشود برای اجرای عملیاتی که میخواهیم توسط schudler انجام شود

    @Autowired
    MemberClassService memberClassService;

    @Override
    public void execute(JobExecutionContext context) {//  رتنها ارور JobExecutionException امکان اضافه شدن به signature را دارد بقیه ارور ها باید توسط try-catch هندل شود
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        memberClassService.classStats();
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
