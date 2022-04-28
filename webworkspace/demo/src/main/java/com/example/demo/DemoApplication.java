package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// main method를 가지고 있는 main class
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// static method의 run() 호출 -> 인자로 DemoApplication.class을 넣어 프로그램 실행
		SpringApplication.run(DemoApplication.class, args);
	}

}
