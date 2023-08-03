package com.springTutorial;

import org.example.error.NotFoundException;
import org.example.todos.Todo;
import org.example.todos.TodoRepository;
import org.example.todos.TodoService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
@RunWith(SpringRunner.class)
public class TodoServiceTest {
    @MockBean
    private TodoRepository todoRepository;
 //I do not understand this line , added because Autowired give error without it
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TodoService todoService;
    @TestConfiguration
    static class TodoServiceContextConfiguration{
        @Bean
        public TodoService todoService(){
            return new TodoService();
        }
    }
    @Test
    public void whenFindAll_returnTodoList(){
        Todo todo1 = new Todo("1","first","hi todo 1");
        Todo todo2 = new Todo("2","second","hi todo 1");
        Todo todo3 = new Todo("3","third","hi todo 1");
        List<Todo> data = Arrays.asList(todo1,todo2,todo3);
        given(todoRepository.findAll()).willReturn(data);
        //
        assertThat(todoService.findAll())
                .hasSize(3)
                .contains(todo1,todo2,todo3);

    }
    @Test
    public void whenGetById_TodoShouldBeFound(){
        Todo todo = new Todo("1","todo 1","hi todo");
        given(todoRepository.findById(anyString())).willReturn(Optional.ofNullable(todo));
        Todo result = todoService.getById("1");
        assertThat(result.getTitle()).containsIgnoringCase("todo");
    }
    @Test(expected = NotFoundException.class)
    public void whenInvalidId_TodoShouldNotBeFound(){
        given(todoRepository.findById(anyString())).willReturn(Optional.empty());
        todoService.getById("1");
    }
}
