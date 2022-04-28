package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")	// 리소스
public class TestController {
	
	@GetMapping
	public String testController() {
		return "Hello World!";
	}
	
	@GetMapping("/{id}")
	public String testControllerWithPathVariable(@PathVariable(required=false) int id) {
		return ("Hello World! ID " + id);
	}
	
	@GetMapping("/testRequestParam")
	public String testControllerWithRequestParam(@RequestParam(required=false) int id) {
		return ("Hello World! ID " + id);
	}
	
	@GetMapping("/testRequestBody")
	public String testControllerWithRequestBody(@RequestBody(required=false) TestRequestBodyDTO dto) {
		return "Hello World! ID " + dto.getId() + " Message : " + dto.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("msj");
		list.add("sj");
		
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).error(null).build();
		
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// http status를 400으로 설정
		return ResponseEntity.badRequest().body(response);
		// ok()를 이용해 status를 200으로 리턴
		// return ResponseEntity.ok().body(response);
	}
}
 