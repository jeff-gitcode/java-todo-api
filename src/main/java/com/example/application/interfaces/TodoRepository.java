package com.example.application.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}