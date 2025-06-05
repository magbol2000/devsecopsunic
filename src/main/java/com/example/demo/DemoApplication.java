package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
		return "Hello world! This web application created using Java Spring Boot framework and deployed in Kubernetes.";
	}

	// 🚨 Hardcoded credential (S2068)
	@RequestMapping("/credentials")
	public String credentials() {
		String dbPassword = "password123"; // Hardcoded credential
		return "DB password is: " + dbPassword;
	}

	// 🔓 Predictable SecureRandom seed (S2245)
	@RequestMapping("/token")
	public String token() {
		SecureRandom sr = new SecureRandom();
		sr.setSeed(1234L); // Predictable seed
		byte[] bytes = new byte[16];
		sr.nextBytes(bytes);
		return "Generated token: " + bytes.toString();
	}

	// 🧨 Usage of getRequestedSessionId() (S2254)
	@RequestMapping("/session-id")
	public String sessionId(HttpServletRequest request) {
		String sessionId = request.getRequestedSessionId(); // Vulnerability
		return "Session ID: " + sessionId;
	}

	// 📛 Session fixation risk - no regeneration (S2080)
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		request.getSession(); // Session created but not invalidated/recreated
		return "Logged in!";
	}

	// 🛑 Plain-text password in memory (S5542)
	@RequestMapping("/plaintext-password")
	public String plaintextPassword() {
		String password = "mySuperSecret"; // Plain-text storage
		return "Password in memory: " + password;
	}
}
