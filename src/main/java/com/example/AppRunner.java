// package com.example;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.example.application.TodoService;
// import com.example.model.Todo;

// @Component
// public class AppRunner implements CommandLineRunner {
	
// 	@Autowired
// 	TodoService todoService;

// 	@Override
//     public void run(String... args) throws Exception {
		
//         Todo todo = new Todo();
//         todo.setTitle("Learn Spring Boot");
        
// 		todoService.createTodo(todo);
		
// 		List<Todo> todos = todoService.getAllTodos();
// 		todos.forEach((t) -> System.out.println(t.getTitle()));
// 	}

// }
