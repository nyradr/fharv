package fb.account;

import java.util.Set;

/**
 * Account information
 * @author nyradr
 */
public interface IAccountInfo {
	
	/**
	 * Get the facebook id
	 * @return
	 */
	public String getFbId();
	
	/**
	 * Get the person name
	 * @return
	 */
	public String getName();
	
	/**
	 * Get person sexe
	 * @return
	 */
	public Sexe getSexe();
	
	/**
	 * Get spoken languages
	 * @return list of string with language names
	 */
	public Set<String> getSpoken();
	
	/**
	 * Get Facebook id of all friends
	 * @return
	 */
	public Set<String> getFriends();
	
	/**
	 * Get posted photos
	 * @return
	 */
	public Set<IPhoto> getPhotos();
	
	/**
	 * Get the json notation of an account informations
	 * @return
	 */
	public String getJson();
}
