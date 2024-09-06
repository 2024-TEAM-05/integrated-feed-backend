package team05.integrated_feed_backend.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
@EnableFeignClients(basePackages = "team05.integrated_feed_backend.infra.sns.api")
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/x-www-form-urlencoded");
		};
	}
}
