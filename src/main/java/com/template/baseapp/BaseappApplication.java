package com.template.baseapp;

import com.plugin.logging.annotations.EnableLogPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
@EnableLogPlugin
@DependsOn({"loggingUtils"})
public class BaseappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseappApplication.class, args);
	}

}
