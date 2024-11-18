package portfolio.eams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

// 캐싱, 스케줄링...
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class EamsApplication {
	// English academy managemnt system.

	public static void main(String[] args) {
		SpringApplication.run(EamsApplication.class, args);
	}

}
