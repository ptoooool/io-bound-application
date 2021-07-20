package class101.foo.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 이게 있어야지만 cron 표현식을 이용해서 스케줄링을 사용할 수 있다.
@SpringBootApplication
public class IoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoApplication.class, args);
	}

}
