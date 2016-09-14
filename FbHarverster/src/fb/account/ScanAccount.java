package fb.account;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represent a facebook account used by the scanner
 * @author nyradr
 */
public class ScanAccount {

	private String mail;
	private String pass;
	private int day;
	private int month;
	private int year;
	
	/**
	 * Create new scanner account
	 * @param mail account email
	 * @param pass account password
	 * @param day day of birth
	 * @param month of birth
	 * @param year of birth
	 */
	public ScanAccount(String mail, String pass, int day, int month, int year){
		this.mail = mail;
		this.pass = pass;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	/**
	 * Get account email
	 * @return
	 */
	public String getMail(){
		return mail;
	}
	
	/**
	 * Get account password
	 * @return
	 */
	public String getPass(){
		return pass;
	}
	
	/**
	 * Get the day of birth
	 * @return
	 */
	public int getDay(){
		return day;
	}
	
	/**
	 * Get the month of  birth
	 * @return
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Get the year of birth
	 * @return
	 */
	public int getYear(){
		return year;
	}
}
