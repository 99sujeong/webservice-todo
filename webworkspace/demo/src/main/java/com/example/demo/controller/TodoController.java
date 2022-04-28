package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	@GetMapping("/testTodo")
	public ResponseEntity<?> testTodo2() {
		
		TodoDTO response = TodoDTO.builder().id("123").title("문수정").done(true).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			// temporary uesr id
			String temporaryUserId = "temporary-user";
			
			// (1) TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// (2) id를 null로 초기화 / 생성 다시에는 id가 없어야하기 떄문
			entity.setId(null);
			
			// (3) 임시 사용자 아이디 설정 
			entity.setUserId(temporaryUserId);
			
			// (4) 서비스를 이용해 Todo 엔터티를 생성
			List<TodoEntity> entities = service.create(entity);
			
			// (5) 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// (7) ResponseDTO 리턴
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			// (8) 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		// temporary uesr id
		String temporaryUserId = "temporary-user";
		
		// (1) 서비스 메서드의 retrieve() 메서드를 사용해 Todo 리스트를 가져옴
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		// (2) 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// (4) ResponseDTO 리턴
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		// temporary uesr id
		String temporaryUserId = "temporary-user";

		// (1)
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		// (2)
		entity.setUserId(temporaryUserId);
		
		// (3)
		List<TodoEntity> entities = service.update(entity);
		
		// (4) 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
				
		// (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
				
		// (6) ResponseDTO 리턴
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
		try {
			// temporary uesr id
			String temporaryUserId = "temporary-user";
			
			// (1) 
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// (2)
			entity.setUserId(temporaryUserId);
			
			// (3)
			List<TodoEntity> entities = service.update(entity);
			
			// (4) 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// (6) ResponseDTO 리턴
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (7) 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
