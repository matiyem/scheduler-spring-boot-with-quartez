package quartez.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quartez.service.MemberClassService;

/**
 * created by Atiye Mousavi
 * Date: 8/8/2021
 * Time: 3:46 PM
 */
@Slf4j
@Component
@DisallowConcurrentExecution
public class MemberClassStatsJob implements Job {

    @Autowired
    MemberClassService memberClassService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        memberClassService.classStats();
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
