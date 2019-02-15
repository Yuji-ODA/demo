package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "メッセージ")
				.thenApply(x -> x + ": 俺の塩")
				.thenAccept(x -> {
			System.out.println(x);
		});


		SpringApplication.run(DemoApplication.class, args);
	}
}
