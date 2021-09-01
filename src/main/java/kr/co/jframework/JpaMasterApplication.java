package kr.co.jframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMasterApplication.class, args);
	}
}


//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan

//위 3개는 	@SpringBootApplication 를 모두담고있는 어노테이션이다.