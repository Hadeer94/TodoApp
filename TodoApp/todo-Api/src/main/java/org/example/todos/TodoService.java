package org.example.todos;

import org.example.error.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
    public List<Todo> findByUser(String id) {
        return todoRepository.findByUserId(id);
    }

    public Todo getById(String id) {
        return todoRepository.findById(id).get();
    }
    public Todo save(Todo todo){
        System.out.println("userid "+todo.getUserId());
        if(todoRepository.findByTitle(todo.getTitle())!=null){
            throw new ConflictException("there is a todo with same title");
        }
        return todoRepository.save(todo);
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
