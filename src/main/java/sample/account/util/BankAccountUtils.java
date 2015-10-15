package sample.account.util;

import sample.account.domain.*;

/**
 * Provides static methods for bank account check / creation
 *
 * @author vpotry
 */
public class BankAccountUtils {
	
	/**
	 * Validates given account number
	 * 
	 * @param accountNumber String
	 * @return result code (here: demo-like information about validity), "" if ok
	 */
	public static String validateAccountNumber(String accountNumber) {
		String res = "";
		
		if(isLengthValid(accountNumber)) {
			// Check Bank group
			BankGroup bankGrp = BankGroup.findByAccountNumber(accountNumber);
			
			if(bankGrp != BankGroup.Unknown) {
				try {
					// Transfer to electronic format if needed
					String longAccountNumber = getInLongFormat(accountNumber, bankGrp);
					// Calculate checksum
					int checksum = validateChecksum(longAccountNumber);
					
					if(checksum != Character.getNumericValue(longAccountNumber.charAt(BankAccountNumber.MAX_LENGTH-1))) {
						res = "Checksum is invalid!";
					}
				} catch(NumberFormatException ex) {
					// TODO: Logging required
					res = "Account number is malformed / contains illegal characters.";
				} catch(Exception ex) {
					// TODO: Logging required
					res = "Invalid other reason.";
				}
			}
		}
		return res;
	}
	
	/**
	 * Basic validation: Min / Max length & null check
	 * 
	 * @param accountNumber
	 * @return true if valid length
	 */
	private static boolean isLengthValid(String accountNumber) {
		return (accountNumber != null 
				&& accountNumber.length() >= BankAccountNumber.MIN_LENGTH
				&& accountNumber.length() <= BankAccountNumber.MAX_LENGTH);		
	}
	
	/**
	 * Converts long/electrical bank account number to text format
	 * 
	 * Note: Account number must be validated before conversion
	 * validation is not performed here, and if '-' char exists 
	 * the same number is returned.
	 * 
	 * @param accountNumber
	 * @param isValid account validation result
	 * 
	 * @return String  Account number format: 123456-987654
	 */
	public static String getInTextFormat(String accountNumber, boolean isValid) throws Exception {
		
		if(accountNumber == null || accountNumber.indexOf("-") > 0 || !isValid) {
			return accountNumber;
		}
		
		BankGroup bankGrp = BankGroup.findByAccountNumber(accountNumber);
		
		switch(bankGrp) {
			/* Nordea, SHB, SEB, Danske Bank, Tapiola, DnB NOR, Swedbank, S-Bank, AAB and
			Sampo */
			case Nordea:
			case Handelsbanken:
			case SEB:
			case DanskeBank: 
			case Tapiola:
			case DnBNOR:
			case Swedbank:
			case SBank:
			case Sampo:
			case Alandsbanken:
				accountNumber = removeZeros(accountNumber, 6);
				break;
			/* Savings banks, Aktia and local cooperative banks, cooperative banks, OKO Bank and
				Okobank */
			case SP:
			case OP:
				accountNumber = removeZeros(accountNumber, 7);
				break;
			default:
				// Not supported
				// TODO: exception handling
				break;
		}
		
		return accountNumber;
	}
	
	/**
	 * Makes text presentation of old-style 
	 * BBAN long/electronic bank account number
	 * 
	 * @param accountNumberLongFormat 
	 * @param position BankGroup specific zero-padding position
	 * @return Bank Account number in format of 123456-987654
	 */
	private static String removeZeros(String accountNumberLongFormat, int position) throws IndexOutOfBoundsException {
	    StringBuilder sb = new StringBuilder(accountNumberLongFormat);
	    	
	    int delInd = position;

		while(accountNumberLongFormat.charAt(position) == '0') {
			sb.deleteCharAt(delInd);
			position++;
		}
		sb.insert(6, "-");
	
	    return sb.toString();
	}

	/**
	 * Creates long/electronic format for given text-formatted account number.
	 * Relies that the isValid parameter is correctly given (isValid if really is valid.)
	 * 
	 * @param  accountNumber in text-format 
	 * @param  isValid boolean true if validated (and valid)
	 * @return Accountnumber in long format
	 */
	public static String getInLongFormat(String accountNumber, boolean isValid) throws Exception {
		String longAccountNumber = accountNumber;
		if(isValid) {
			BankGroup bankGrp = BankGroup.findByAccountNumber(accountNumber);
			longAccountNumber = getInLongFormat(accountNumber, bankGrp);
		}
		return longAccountNumber;
	}
	
	/**
	 * Creates long/electronic format for given text-formatted account number
	 * If already in long/electronic form the same number is returned.
	 * 
	 * TODO: Exception handling (now just base-exception thrown)
	 * 
	 * @param accountNumber in text-format
	 * @param bankGrp BankGroup
	 * @return long/electronic account number
	 * @throws Exception 
	 */
	public static String getInLongFormat(String accountNumber, BankGroup bankGrp) 
			throws Exception {
		
		String longAccountNumber = accountNumber.trim().replace(" ", "").replace("-", "");
		
		// Check if accountNumber is already in electronic format
		if(longAccountNumber.length() == BankAccountNumber.MAX_LENGTH) {
			// Throws Exception if contains non-numerical chars (simple check)
			Long.parseLong(longAccountNumber);
		} else {
			longAccountNumber = formatNumberByBankGroup(longAccountNumber, bankGrp);
		}

		return longAccountNumber;
	}
	
	/**
	 * Generates Bank Group specific electronic account number
	 * 
	 * @param accountNumber with no '-' separator
	 * @param BankGroup Bank group where the given number belongs
	 * @return String  Bank account number in long/electronic format
	 */
	private static String formatNumberByBankGroup(String accountNumber, BankGroup bankGrp) throws Exception {
		String longAccountNumber = accountNumber;
				
		switch(bankGrp) {
			/* Nordea, SHB, SEB, Danske Bank, Tapiola, DnB NOR, Swedbank, S-Bank, AAB and
			Sampo */
			case Nordea:
			case Handelsbanken:
			case SEB:
			case DanskeBank: 
			case Tapiola:
			case DnBNOR:
			case Swedbank:
			case SBank:
			case Sampo:
			case Alandsbanken:
				longAccountNumber = paddWithZeros(accountNumber, 6);
				break;
			/* Savings banks, Aktia and local cooperative banks, cooperative banks, OKO Bank and
				Okobank */
			case SP:
			case OP:
				longAccountNumber = paddWithZeros(accountNumber, 7);
				break;
			default:
				// Not supported
				// TODO: exception handling
				break;
		}
		return longAccountNumber;
	}

	/**
	 * Adds padding zeros to given account number
	 * and generates account number that is 14 numbers long
	 * 
	 * @param String accountNumber
	 * @param int char position from where to start padding
	 * @return String padded number as String
	 */
	private static String paddWithZeros(String accountNumber, int position) throws IndexOutOfBoundsException {
		int paddingLen = BankAccountNumber.MAX_LENGTH - accountNumber.length();
		
		String endPart = accountNumber.substring(position);
		String paddedPart = String.format("%0" + (paddingLen+endPart.length()) + "d", Integer.parseInt(endPart));
		
		return accountNumber.substring(0, position) + paddedPart;
	}
	
	/**
	 * Calculates checksum for given account number (long formatted)
	 * that contains already the checksum number.
	 * Used in validation.
	 * 
	 * @param longAccountNumber
	 * @return calculated checksum
	 */
	public static int validateChecksum(String longAccountNumber) {
		if(longAccountNumber.length() < BankAccountNumber.MAX_LENGTH-1) {
			// Should be in long (zero-padded) format
			return -1;
		}
		
		String numberWithoutChecksum = longAccountNumber.substring(0, BankAccountNumber.MAX_LENGTH-1);
		
		return(getChecksum(numberWithoutChecksum));
	}
	
	/**
	 * Calculates checksum for given account number (without checksum)
	 * 
	 * @param numberWithoutChecksum
	 * @return Checksum for given long-formatted account number
	 */
	public static int getChecksum(String numberWithoutChecksum) {
		int sum = 0;
		for(int i=numberWithoutChecksum.length()-1; i>=0; i--) {
			int digit = Character.getNumericValue(numberWithoutChecksum.charAt(i));
			
			if (i % 2 == 0) {
				digit = digit * 2;
		    }
			
			if(digit > 9) {
				sum = sum + digit-9;
			} else {
				sum = sum + digit;
			}
			
		}

		int cksum = 10 - (sum % 10);

		if(cksum == 10) {
			cksum = 0;
		}
		
		return cksum;
	}

	
}