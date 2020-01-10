<<<<<<< Updated upstream
package com.project.sightseeing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SightseeingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SightseeingApplication.class, args);
	}

}
=======
package com.project.sightseeing;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.sightseeing.User.User;

@SpringBootApplication
public class SightseeingApplication {
	
	public static Map<String , User> loggedInUsers = new HashMap<String, User>();
	
	public static void main(String[] args) {
		SpringApplication.run(SightseeingApplication.class, args);
	}

}
>>>>>>> Stashed changes
