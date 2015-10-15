package sample.account.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the main spring-boot application class
 * Scans Spring component annotations and provides main()
 * to startup service (Default: Tomcat)
 * 
 * @author vpotry
 *
 */
@ComponentScan("sample.account") // Search for @component annotated classes (here: BankAccountService)
@SpringBootApplication
public class Application {

	/**
	 * Main method used to boot-up the Spring-boot application
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}