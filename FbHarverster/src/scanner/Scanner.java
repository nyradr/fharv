package scanner;

import java.io.FileNotFoundException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

import google.GoogleScanner;
import harvester.scheme.SchemeParseError;

/**
 * Manage all scanners
 * @author nyradr
 */
public class Scanner {

	private WebClient browser;
	private GoogleScanner google;
	
	public Scanner() throws FileNotFoundException, SchemeParseError{
		browser = new WebClient(BrowserVersion.FIREFOX_45);
		browser.getOptions().setCssEnabled(false);
		browser.getOptions().setThrowExceptionOnScriptError(false);
		
		google = new GoogleScanner(browser);
	}
	
	public void scan(String name){
		google.scan(name);
	}
	
}
