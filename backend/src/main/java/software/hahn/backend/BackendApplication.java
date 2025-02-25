package software.hahn.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
      /*  String password = "1234"; // Password to hash
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Hash the password
        String hashedPassword = encoder.encode(password);

        // Print the bcrypt hash
        System.out.println("BCrypt hashed password: " + hashedPassword);*/
        SpringApplication.run(BackendApplication.class, args);
    }

}
