package sample.account.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import sample.account.service.BankAccountService;

/**
 * Spring bean configurations
 *
 * @author vpotry
 */
@Configuration
public class AppConfiguration {

	/**
	 * Register BankAccountService bean
	 * 
	 * @return BankAccountService
	 */
	@Bean
	public BankAccountService bankAccountService() {
		return new BankAccountService();
	}

}