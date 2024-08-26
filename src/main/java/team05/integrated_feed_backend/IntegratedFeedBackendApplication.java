package team05.integrated_feed_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableFeignClients(basePackages = "team05.integrated_feed_backend.infra.sns.api")
public class IntegratedFeedBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegratedFeedBackendApplication.class, args);
	}

}
