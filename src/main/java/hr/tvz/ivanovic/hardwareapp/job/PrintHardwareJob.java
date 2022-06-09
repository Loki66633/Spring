package hr.tvz.ivanovic.hardwareapp.job;

import hr.tvz.ivanovic.hardwareapp.Hardware;
import hr.tvz.ivanovic.hardwareapp.HardwareRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PrintHardwareJob extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HardwareRepository hardwareRepository;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Hardware> hardwareList = new ArrayList<>();
        hardwareList.addAll(hardwareRepository.findAll());

        logger.info("----------------------------------");
        logger.info("Ovo su trenutno dostupni hardveri:");
        logger.info("----------------------------------");
        for(int i=0;i<hardwareList.size();i++){
            if(hardwareList.get(i).getStock()>0)
                logger.info(hardwareList.get(i).getName() +  " - " + hardwareList.get(i).getStock());
        }
        logger.info("----------------------------------\n");
    }



}
