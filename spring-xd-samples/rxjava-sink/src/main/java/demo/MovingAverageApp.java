package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.bus.runner.EnableMessageBus;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;

import config.ModuleDefinition;

@SpringBootApplication
@EnableMessageBus
@EnableIntegration
@ComponentScan(basePackageClasses=ModuleDefinition.class)
public class MovingAverageApp {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MovingAverageApp.class, args);
	}

}
