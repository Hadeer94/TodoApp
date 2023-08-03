package org.example.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
     Todo findByTitle(String title);
     List<Todo> findByUserId(String id);
}
