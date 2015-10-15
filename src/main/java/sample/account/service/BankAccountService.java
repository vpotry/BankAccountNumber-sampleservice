package sample.account.service;

import org.springframework.stereotype.Service;

import sample.account.util.BankAccountGenerator;
import sample.account.domain.BankAccountNumber;

/**
 * Service for BankAccountController
 * Provides access for BankAccountNumber operations
 * 
 * @author vpotry
 *
 */
@Service("bankAccountService")
public class BankAccountService {
	
	/**
	 * @return BankAccountNumber from random generator
	 */
    public BankAccountNumber getRandomAccount() {
        return BankAccountGenerator.getRandomBankAccountNumber();
    }
    
    /**
     * @param accountNumber to be validated
     * @return BankAccountNumber with validity information
     */
	public BankAccountNumber validate(String accountNumber) {
		return new BankAccountNumber(accountNumber);
	}
	
    
}