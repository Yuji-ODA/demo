package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		CompletableFuture<String> myFuture = CompletableFuture.supplyAsync(() -> "メッセージ")
				.thenApply(x -> x + ": 俺の塩");

		myFuture.thenCompose();

		myFuture.thenAccept(x -> {
			System.out.println(x);
		});


//		SpringApplication.run(DemoApplication.class, args);
	}
}
