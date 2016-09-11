package fb.account;

/**
 * List of supported sexes
 * @author nyradr
 */
public enum Sexe {
	/**
	 * Unknow sexe
	 */
	UNK		(0x00),
	/**
	 * Male
	 */
	MALE	(0x01),
	/**
	 * Femele
	 */
	FEMELE	(0x02);
	
	private char val;
	
	private Sexe(int s){
		val = (char) s;
	}
	
	public char getVal(){
		return val;
	}
}
