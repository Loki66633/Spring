package hr.tvz.ivanovic.hardwareapp.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(PrintHardwareJob.class)
                .withIdentity("printJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger JobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();

        return TriggerBuilder.newTrigger().forJob(jobDetail())
                .withIdentity("printTrigger").withSchedule(scheduleBuilder).build();
    }


}
