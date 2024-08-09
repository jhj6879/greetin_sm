package com.example.greeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GreetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreetingApplication.class, args);

		// 현재 시간을 가져옵니다.
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

		// 원하는 포맷으로 변환합니다.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
		String formattedDate = now.format(formatter);

		// 결과 출력
		System.out.println("현재 시간 (한국식 포맷): " + formattedDate);
	}

}
