package hr.tvz.ivanovic.hardwareapp.job;

import hr.tvz.ivanovic.hardwareapp.Hardware;
import hr.tvz.ivanovic.hardwareapp.HardwareDTO;
import hr.tvz.ivanovic.hardwareapp.HardwareRepository;
import hr.tvz.ivanovic.hardwareapp.HardwareService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class Job extends QuartzJobBean {



    @Autowired
    private HardwareRepository hardwareRepository;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Hardware> hardveri= hardwareRepository.findAll();

        System.out.println("-------------------------");
        System.out.println("Ovo su dostupni hardveri:");
        System.out.println("-------------------------");



        for(int i=0;i<hardveri.size();i++){
            if(hardveri.get(0).getStock()>0){
                System.out.println(hardveri.get(i).getName() + " - " + hardveri.get(i).getStock());
            }
        }

        System.out.println("-------------------------\n");

    }
}
