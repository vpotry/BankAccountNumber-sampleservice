package sample.account.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import sample.account.domain.BankAccountNumber;
import sample.account.service.BankAccountService;

/**
 * Restful webservice controller for serving 
 * client requests.
 *  
 * @author vpotry
 *
 */
@RestController
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;

	/**
	 * Creates new random account number for demo-purposes: 
	 * does not handle for example bank office number information etc.
	 * 
	 * Example request from same host: 
	 * http://localhost:8080/newaccount
	 * 
	 * @return BankAccountNumber domain object JSON presentation
	 */
    @RequestMapping("/newaccount")
    public BankAccountNumber getNewAccount() {
        return bankAccountService.getRandomAccount();
    }
    
   /**
    * Validates given (PathVariable) accountNumber
    * Validity information is included in
    * BankAccountNumber domain object.
    * 
    * Example request from same host: 
    * http://localhost:8080/validate/123456-987654
    * 
    * @param  accountNumber PathVariable in text or long format
    * @return BankAccountNumber  domain object JSON presentation
    */
    @RequestMapping("/validate/{accountNumber}")
    public BankAccountNumber validate(@PathVariable String accountNumber) {
        return bankAccountService.validate(accountNumber);
    }
}