package hr.tvz.ivanovic.hardwareapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class HardwareappApplication {

    public static void main(String[] args) {
        SpringApplication.run(HardwareappApplication.class, args);
    }

}
