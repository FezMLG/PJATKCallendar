package pl.pijok.PJATKCallendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.PatternSyntaxException;

@SpringBootApplication
@RestController
public class PjatkCallendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjatkCallendarApplication.class, args);
	}
}
