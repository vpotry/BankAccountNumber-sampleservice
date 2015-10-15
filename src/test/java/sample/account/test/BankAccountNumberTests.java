package sample.account.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sample.account.domain.BankAccountNumber;
import sample.account.util.BankAccountGenerator;

/**
 * Unit test cases for BankAccountNumber service
 * 
 * @author vpotry
 *
 */
public class BankAccountNumberTests {

	@Test
	public void testRandonGeneration() {
		BankAccountNumber ban = BankAccountGenerator.getRandomBankAccountNumber();
		
		assertEquals(ban.isValid(), true);
		assertEquals(ban.getElectronicNumber().length(), 14);
	}
	
	@Test
	public void testStaticValidBankAccountNumber() {
		BankAccountNumber ban = new BankAccountNumber("575001-223545");
		
		assertEquals(ban.isValid(), true);
		assertEquals(ban.getElectronicNumber().length(), 14);
	}
	
	@Test
	public void testStaticInvalidBankAccountNumber() {
		BankAccountNumber ban = new BankAccountNumber("123456-121");
		
		assertEquals(ban.isValid(), false);
	}
	
}
