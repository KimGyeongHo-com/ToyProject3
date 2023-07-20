package team7.module;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "team7.module")
@EnableScheduling
public class ToyProject3Application {

	public static void main(String[] args) {
		SpringApplication.run(ToyProject3Application.class, args);
	}

}
