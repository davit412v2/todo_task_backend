package com.todo.task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requires full application context with database")
class TodoTaskApplicationTests {

	@Test
	void contextLoads() {
	}

}
