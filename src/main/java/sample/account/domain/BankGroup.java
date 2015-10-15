package sample.account.domain;

/**
 * BankGroup domain-object for supported Banks
 * 
 * @author vpotry
 *
 */
public enum BankGroup {
    Nordea("Nordea", "1", "2"),
    Handelsbanken("Handelsbanken", "31"),
    SEB("Skandinaviska Enskilda Banken", "33"),
    DanskeBank("Danske Bank", "34"),
    Tapiola("Tapiola", "36"),
    DnBNOR("DnB NOR Bank ASA", "37"),
    Swedbank("Swedbank", "38"),
    SBank("S-Pankki", "39"),
    SP("Sp/Pop/Aktia", "4"),
    OP("Op/OKO", "5"),
    Alandsbanken("Alandsbanken", "6"),
    Sampo("Sampo Pankki", "8"),
    Unknown("UnknownBank", "XX");
    
    private String bankName;
    private String[] ids;
    
    /**
     * Constructor
     * 
     * @param String name
     * @param String[] ids
     */
    private BankGroup(String name, String... ids) {
    	this.bankName = name;
    	this.ids = ids;
    }
    
    /**
     * @param id  BankId of the bank group
     * @return BankGroup that matches given id
     */
    public static BankGroup findById(int id) {
    	BankGroup bankGrp = BankGroup.Unknown;
    	
    	return bankGrp;
    }
    
    /**
     * @return String Human-readable bank name
     */
    public String getBankName() {
        return this.bankName;
    }
    
    /**
     * One BankGroup may hold several
     * id's
     * 
     * @return int[] BankGroup ids
     */
    public String[] getIds() {
        return this.ids;
    }
    
    /**
     * Check Bank group from given 
     * clear text / electronic account number
     * 
     * @param accountNumber
     * @return BankGroup  BankGroup.{bank} or BankGroup.Unknown
     */
    public static BankGroup findByAccountNumber(String accountNumber) {
    	BankGroup bankGrp = BankGroup.Unknown;
    	
    	if(accountNumber != null) {
    		
    		accountNumber = accountNumber.trim().replace(" ", "");
    		
    		BankGroup[] groups = BankGroup.values();
    		int i=0;
    		
    		while(i < groups.length && bankGrp == BankGroup.Unknown) {
    			
    			String[] bankgroupIds = groups[i].getIds();
    			for(String id : bankgroupIds) {
    				if(accountNumber.startsWith(id)) {
    					bankGrp = groups[i];
    				}
    			}
    			
    			i++;
    		}
    	}
    	
    	return bankGrp;
    }
    
    /**
     * 
     * @param accountNumber
     * @return BankGroup
     */
    public static BankGroup findByAccountNumber(long accountNumber) {
    	return findByAccountNumber(Long.toString(accountNumber));
    }
}
