package fb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import fb.account.IAccountInfo;
import fb.account.IPhoto;
import fb.account.Sexe;
import harvester.Harvester;
import harvester.data.IPage;
import harvester.scheme.Scheme;
import harvester.scheme.SchemeParseError;

public class FbManager {
	private final String fb = "https://www.facebook.com/";
	private final String mfb = "https://www.facebook.com/";
	
	private WebClient browser;
	private Scheme scheme;
	private Harvester harvest;
	// scanners
	BaseInfoScan basescan;
	FriendsScan frdscran;
	PhotosPagesScan photoscan;
	
	private Exception lastError;
	
	public FbManager(WebClient browser) throws FileNotFoundException, SchemeParseError{
		this.browser = browser;
		Scheme scheme = new Scheme(new File("xml/fbscheme.xml"));
		harvest = new Harvester(scheme, browser);
		
		frdscran = new FriendsScan(harvest);
		photoscan = new PhotosPagesScan(harvest);
		
		lastError = null;
	}
	
	/*	Get the last notable error produced
	 */
	public Exception getLastError(){
		return lastError;
	}
	
	/**
	 * Log into Facebook
	 * @param user Facebook user name
	 * @param pass account password
	 * @return true if logged sucess
	 */
	public boolean login(String user, String pass){
		boolean logged = false;
		
		try{
			Map<String, String> log = new TreeMap<>();
			log.put("email", user);
			log.put("pass", pass);
			
			IPage page = harvest.submit("https://m.facebook.com/", "login", log);
			
			// login verification
			String title = page.getByName("title").getText();
			logged = !title.toLowerCase().contains("log into");
		}catch(Exception e){
			e.printStackTrace();
			lastError = e;
		}
		
		return logged;
	}
	
	/**
	 * Complete scan of facebook account
	 * @param fbid Account facebook id
	 * @return harvested account data
	 */
	public IAccountInfo scan(String fbid){
		String accUrl = Scanner.url_mfb + fbid + "/";
		
		AccountInfo account = basescan.scan(accUrl, fbid);
		
		account.setPhotos(
			photoscan.scan(accUrl + "photos"));
		
		account.setFriends(
			frdscran.scan(accUrl + "friends"));
		
		return account;
	}
	
	// tested id : melanie.gergaud, melle.malexandra
	
	public static void main(String [] a){
		try{
			FbManager fb = new FbManager(new WebClient());
			if(fb.login("fscan@gmx.fr", "FbScanner")){
				System.out.println("LOGIN SUCCESS");
				IAccountInfo account = fb.scan("melanie.gergaud");
				
				//for(String frd : account.getFriends())
				//	System.out.println("F : " + frd);
				
				
			}else
				System.out.println("LOGIN ERROR");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
