package sample.account.domain;

import sample.account.util.BankAccountUtils;

/**
 * BankAccountNumber domain class
 * Contains validity and text/long(electronic)
 * information about account number.
 * 
 * @author vpotry
 *
 */
public class BankAccountNumber {
	public static final int MAX_LENGTH = 14; // Account number max length in 'long' electronic format
	public static final int MIN_LENGTH = 9; // Account number min length (123456-78)
	
	private String textNumber;
    private String electronicNumber;
    
	private boolean isValid;
	private String reasonIfInvalid;
	
	/**
	 * Constructor. Given number may be
	 * either in text/electronic format
	 * 
	 * @param number BankAccount number
	 */
	public BankAccountNumber(String number) {
		this.setNumber(number);
	}

	/**
	 * @param number BankAccount number in numerical format
	 */
	public BankAccountNumber(long number) {
		this(Long.toString(number));
	}

	/**
	 * @return String Text-formatted account number
	 */
	public String getTextNumber() {
		return textNumber;
	}
    
	/**
	 * @return String electronic(long) account number
	 */
    public String getElectronicNumber() {
		return electronicNumber;
	}

    /**
     * Sets and creates text/electronic account numbers
     * depending on the given format.
     * 
     * @param number text/electronic account number
     */
	public void setNumber(String number) {
		this.reasonIfInvalid = BankAccountUtils.validateAccountNumber(number);
		this.isValid = "".equals(reasonIfInvalid);
		
		try {
			this.textNumber = BankAccountUtils.getInTextFormat(number, this.isValid);
		} catch (Exception e) {
			// TODO: Exception handling!
			e.printStackTrace();
			this.reasonIfInvalid = "Failed to create text-number.";
			this.isValid = false;
		}
		
		try {
			this.electronicNumber =  BankAccountUtils.getInLongFormat(number, this.isValid);
		} catch (Exception e) {
			// TODO: Exception handling!
			e.printStackTrace();
			this.reasonIfInvalid = "Failed to create long/electronic-number.";
			this.isValid = false;
		}
	}
	

	/**
	 * @return boolean  true if account number is passed validation
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * 
	 * @return String Bank group name
	 */
	public String getBankName() {
		if(!isValid) {
			return "Unknown Account/Bank";
		}
		
		return BankGroup.findByAccountNumber(electronicNumber).getBankName();
	}

	/**
	 * @return String reason if invalid or "" if valid
	 */
	public String getReasonIfInvalid() {
		return reasonIfInvalid;
	}

	/**
	 * @param reasonIfInvalid  empty String if ok
	 */
	public void setReasonIfInvalid(String reasonIfInvalid) {
		this.reasonIfInvalid = reasonIfInvalid;
	}
}