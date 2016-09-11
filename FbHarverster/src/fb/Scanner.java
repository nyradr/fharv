package fb;

import java.net.URI;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import harvester.Harvester;

/**
 * Base class for a Facebook page scanner
 * @author nyradr
 */
abstract class Scanner {
	public static final String url_mfb = "https://m.facebook.com/";
	
	protected Harvester harvester;
	
	public Scanner(Harvester h){
		harvester = h;
	}
	
	/**
	 * Get Facebook id from string
	 * @param str string to clear : [/]id?...
	 * @return
	 */
	protected String clearFbId(String str){
		if(str.charAt(0) == '/')
			str = str.substring(1);
		
		if(str.indexOf('?') >= 0)
			str = str.substring(0, str.indexOf('?'));
		
		return str;
	}
	
	/**
	 * Format date and return id on YYYY-MM-DD format
	 * @param dt date to format
	 * @return date formated
	 */
	protected String date(String dt){
		return dt;
	}
	
	/**
	 * Construct fb url from string value
	 * @param val part of the path url (host could be inside)
	 * @return url or ""
	 */
	protected String buildUrl(String val){
		if(val == null)
			return "";
		
		String url = "";
		val = val
				.replace(url_mfb.substring(0, url_mfb.length() -1), "");
		
		if(val.length() > 0)
			if(val.charAt(0) == '/')
				val = val.substring(1);
		val = url_mfb + val;
		
		if(isUrl(val))
			url = val;
		
		return url;
	}
	
	/**
	 * Test if the string (with url_mfb) can be a valid facebook url
	 * @param el element 1to test
	 * @return true is the url is correct
	 */
	protected boolean isUrl(String s){
		try{
			if(s != null)
				return (URI.create(url_mfb + s) != null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
}
