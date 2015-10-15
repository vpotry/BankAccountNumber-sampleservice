package sample.account.util;

import java.util.Random;

import sample.account.domain.*;

/**
 * Bank account generation class
 * - Just for demo purposes
 *
 * @author vpotry
 */
public class BankAccountGenerator {
    
    /**
     * Generates (somewhat) random BBAN account number
     * 
     * @return BankAccountNumber  format: 123456-[12...123456]
     * 
     */
    public static BankAccountNumber getRandomBankAccountNumber() {
    	BankGroup bankGroup = getRandomBankGroup();
    	String bankId = bankGroup.getIds()[0];
    	
    	// Account number 1st part (6 digits)
    	int[] partOneNumbers = getRandomNumbers(6 - bankId.length());
    	
    	// Second part (after '-'), let's use 3...4 numbers for that
    	int[] partTwoNumbers = getRandomNumbers(getRandomNumber(3,4));
    	
    	// Put the parts together
    	String accountNumber = bankId;
    	
    	for(int i=0; i<partOneNumbers.length; i++) {
    		accountNumber += partOneNumbers[i];
    	}
    	
    	accountNumber += "-";
    	
    	for(int i=0; i<partTwoNumbers.length; i++) {
    		accountNumber += partTwoNumbers[i];
    	}
    	
    	String longFormat = "";
    	try {
    		longFormat = BankAccountUtils.getInLongFormat(accountNumber, bankGroup);
    	} catch(Exception e) {
    		// TODO: Exception handling
    		e.printStackTrace();
    	}
    	
    	longFormat = longFormat.substring(0, longFormat.length()-1);
    	
    	int chksum = BankAccountUtils.getChecksum(longFormat);
    	longFormat += chksum;
    			
    	return new BankAccountNumber(longFormat);
    }
    
    /**
     * Random numbers for rest of the bank account
     * 
     * @param int amount of numbers to be generated
     * @return int[] array of generated numbers
     */
    private static int[] getRandomNumbers(int amount) {
    	int[] vals = new int[amount];
    	int minVal = 1;
    	int maxVal = 9;
    	
    	for(int i=0; i<amount; i++) {
    		vals[i] = getRandomNumber(minVal, maxVal);
    	}
    	
    	return vals;
    }
    
    /**
     * Gets the random BankGroup
     * @return BankGroup 
     */
    private static BankGroup getRandomBankGroup() {
    	BankGroup bgrp = BankGroup.Unknown;
		
    	while(bgrp == BankGroup.Unknown) {
    		bgrp = BankGroup.values()[getRandomNumber(0, BankGroup.values().length-1)];
    	}
    	
    	return bgrp;
    }
    
    /**
     * Somewhat randon Random number generator
     * for Demo-purposes
     * 
     * @param int min number wanted
     * @param int max number wanted
     * @return int Random number between minVal ... maxVal
     */
    private static int getRandomNumber(int minVal, int maxVal) {
    	Random rnd = new Random();
		rnd.setSeed(System.nanoTime());
		return rnd.nextInt(maxVal-minVal) + minVal;
    }
   
}