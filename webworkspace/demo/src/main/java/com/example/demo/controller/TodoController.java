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
		
		TodoDTO response = TodoDTO.builder().id("123").title("������").done(true).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			// temporary uesr id
			String temporaryUserId = "temporary-user";
			
			// (1) TodoEntity�� ��ȯ
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// (2) id�� null�� �ʱ�ȭ / ���� �ٽÿ��� id�� ������ϱ� ����
			entity.setId(null);
			
			// (3) �ӽ� ����� ���̵� ���� 
			entity.setUserId(temporaryUserId);
			
			// (4) ���񽺸� �̿��� Todo ����Ƽ�� ����
			List<TodoEntity> entities = service.create(entity);
			
			// (5) �ڹ� ��Ʈ���� �̿��� ���ϵ� ����Ƽ ����Ʈ�� TodoDTO ����Ʈ�� ��ȯ
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// (6) ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// (7) ResponseDTO ����
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			// (8) Ȥ�� ���ܰ� �ִ� ��� dto ��� error�� �޽����� �־� ����
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		// temporary uesr id
		String temporaryUserId = "temporary-user";
		
		// (1) ���� �޼����� retrieve() �޼��带 ����� Todo ����Ʈ�� ������
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ����Ƽ ����Ʈ�� TodoDTO ����Ʈ�� ��ȯ
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// (3) ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// (4) ResponseDTO ����
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
		
		// (4) �ڹ� ��Ʈ���� �̿��� ���ϵ� ����Ƽ ����Ʈ�� TodoDTO ����Ʈ�� ��ȯ
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
				
		// (5) ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
				
		// (6) ResponseDTO ����
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
			
			// (4) �ڹ� ��Ʈ���� �̿��� ���ϵ� ����Ƽ ����Ʈ�� TodoDTO ����Ʈ�� ��ȯ
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// (5) ��ȯ�� TodoDTO ����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// (6) ResponseDTO ����
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (7) Ȥ�� ���ܰ� �ִ� ��� dto ��� error�� �޽����� �־� ����
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
