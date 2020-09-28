package net.lbenzzine.backend;

import net.lbenzzine.backend.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing //  Enabling JPA Auditing
@EnableSwagger2
public class BackendApplication implements CommandLineRunner {

    @Autowired
    RewardService rewardService;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Call the service to initilize DB

        rewardService.addTransactions();


    }


}
