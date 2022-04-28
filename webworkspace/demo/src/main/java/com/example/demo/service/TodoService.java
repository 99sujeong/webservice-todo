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
		// (1) ������ ����Ƽ�� �������� Ȯ�� 
		validate(entity);
		
		// (2) �Ѱܹ��� ����Ƽ id�� �̿��� TodoEntity�� �����´� / �������� ���� ����Ƽ�� ������Ʈ�� �� ���� ����
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			// (3) ��ȯ�� TodoEntity�� �����ϸ� ���� �� entity ������ ���� ����
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			// (4) DB�� �� ���� ����
			repository.save(todo);
		});
		
		// Retrieve Todo���� ���� �޼��带 �̟G�� ������� ��� Todo ����Ʈ�� ������
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete (final TodoEntity entity) {
		// (1) ������ ����Ƽ�� ��ȿ���� Ȯ��
		validate(entity);
		
		try {
			// (2) ����Ƽ ����
			repository.delete(entity);
		} catch (Exception e) {
			// (3) exception �߻� �� id�� exception�� �α���
			log.error("error deleting entity ", entity.getId(), e);
			
			// (4) ��Ʈ�ѷ��� exception�� ���� / DB ���� ������ ĸ��ȭ�Ϸ��� e�� �������� �ʰ� �� exception ������Ʈ�� ����
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		
		// (5) �� Todo ����Ʈ�� ������ ������
		return retrieve(entity.getUserId());
	}
}
