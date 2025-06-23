package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
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


	public String plaintextPassword() {Add commentMore actions


		String password = "mySuperSecret"; // Plain-text storage


		return "Password in memory: " + password;


	}
}
