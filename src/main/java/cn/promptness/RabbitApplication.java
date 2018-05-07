package cn.promptness;

import cn.promptness.config.EnableHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author : Lynn
 * @Date : 2018-04-21 19:00
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableRabbit
@EnableHttpClient
public class RabbitApplication {

    private static Log logger = LogFactory.getLog(RabbitApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(RabbitApplication.class, args);
        logger.info("RabbitApplication is success!");

    }


}
