package ru.davyd.NauJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.davyd.NauJava.console.ConsoleUI;

@SpringBootApplication
public class NauJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run( NauJavaApplication.class, args);

		ConsoleUI consoleUI = context.getBean(ConsoleUI.class);
		consoleUI.start();

		context.close();
	}
}
