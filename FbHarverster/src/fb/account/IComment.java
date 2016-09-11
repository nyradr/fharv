package fb.account;

/**
 * Represent comment on publication
 * @author nyradr
 */
public interface IComment {
	
	/**
	 * Get the comment poster
	 * @return Facebook id
	 */
	public String getPoster();
	
	/**
	 * Get comment post date
	 * @return YYYY-MM-DD
	 */
	public String getDate();
}
