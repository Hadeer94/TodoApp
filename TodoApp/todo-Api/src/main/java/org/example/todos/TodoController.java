package org.example.todos;

import org.example.BaseController;
import org.example.error.NotFoundException;
import org.example.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value="/api/v1/todos")
public class TodoController extends BaseController {
    @Autowired
    private TodoService  todoService;

@GetMapping(value = {"/",""})
    public ResponseEntity<List<Todo>> getAllTodos(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AppUser user = (AppUser) authentication.getPrincipal();
        List<Todo> result= todoService.findByUser(getCurrentUser().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id){
        try{
            Todo result= todoService.getById(id);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch(NoSuchElementException ex){
            throw new NotFoundException(String.format("No record with the id %s found",id));
        }
    }
    @PostMapping(value = {"","/"})
    public ResponseEntity<Todo> createNewTodo(@RequestBody Todo todo){
        todo.setUserId(getCurrentUser().getId());
        Todo result = todoService.save(todo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
