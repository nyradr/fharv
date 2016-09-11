package fb.account;

import java.util.Set;

/**
 * Represent Photo informations
 * @author nyradr
 */
public interface IPhoto {
	
	/**
	 * Get Facebook id of persons who like the photo
	 * @return
	 */
	public Set<String> getLikes();
	
	/**
	 * Get person referenced on the photo
	 * @return List of facebook id
	 */
	public Set<String> getReferenced();
	
	/**
	 * Get post date
	 * @return "YYYY-MM-DD"
	 */
	public String getDate();
	
	/**
	 * Get image url
	 * @return
	 */
	public String getUrl();
	
	/**
	 * Get the photo information as a json string
	 * @return
	 */
	public String getJson();
}
