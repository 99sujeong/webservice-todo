package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// main method�� ������ �ִ� main class
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// static method�� run() ȣ�� -> ���ڷ� DemoApplication.class�� �־� ���α׷� ����
		SpringApplication.run(DemoApplication.class, args);
	}

}
