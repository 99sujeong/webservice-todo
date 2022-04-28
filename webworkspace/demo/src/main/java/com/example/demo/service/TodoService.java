package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity id : {} is saved.", entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	public List<TodoEntity> retrieve (final String userId) {
		return repository.findByUserId(userId);
	}
	
	private void validate(final TodoEntity entity) {
		// Validations
		if (entity == null) {
			log.warn("Entity cannnot be unll.");
			throw new RuntimeException("Entity cannot be null.");
		}
			
		if (entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		// (1) 저장할 엔터티가 유요한지 확인 
		validate(entity);
		
		// (2) 넘겨받은 엔터티 id를 이용해 TodoEntity를 가져온다 / 존재하지 않은 엔터티는 업데이트할 수 없기 때문
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			// (3) 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌움
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			// (4) DB에 새 값을 저장
			repository.save(todo);
		});
		
		// Retrieve Todo에서 만든 메서드를 이욯해 사용자의 모든 Todo 리스트를 리턴함
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete (final TodoEntity entity) {
		// (1) 저장할 엔터티가 유효한지 확인
		validate(entity);
		
		try {
			// (2) 엔터티 삭제
			repository.delete(entity);
		} catch (Exception e) {
			// (3) exception 발생 시 id와 exception을 로깅함
			log.error("error deleting entity ", entity.getId(), e);
			
			// (4) 컨트롤러로 exception을 보냄 / DB 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새 exception 오브젝트를 리턴
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		
		// (5) 새 Todo 리스트를 가져와 리턴함
		return retrieve(entity.getUserId());
	}
}
