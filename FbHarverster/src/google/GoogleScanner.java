package google;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

import harvester.Harvester;
import harvester.data.IData;
import harvester.data.IPage;
import harvester.scheme.Scheme;
import harvester.scheme.SchemeParseError;

/**
 * Scan for google links on a person
 * @author nyradr
 */
public class GoogleScanner {

	private WebClient browser;
	private Scheme scheme;
	private Harvester harvest;
	
	public GoogleScanner(WebClient clt) throws FileNotFoundException, SchemeParseError{
		browser = clt;
		clt.getOptions().setThrowExceptionOnScriptError(false);
		scheme = new Scheme(new File("xml/google.xml"));
		harvest = new Harvester(scheme, browser);
	}
	
	public Set<String> scan(String name){
		Set<String> res = new HashSet<>();
		
		for(int i = 0; i < 30; i += 10)
			res.addAll(getUrls(format(name), i));
		
		return res;
	}

	/**
	 * Format name to google search
	 * @param name name
	 * @return
	 */
	private String format(String name){
		return name.replace(" ", "+");
	}
	
	/**
	 * Get all urls of the google search result
	 * @param name
	 * @return
	 */
	private List<String> getUrls(String name, int start){
		List<String> res = null;
		
		try {
			String url = "https://www.google.com/search?q=" + name + "&start=" + start;

			IPage page = harvest.get(url, "google");
			IData data = page.getByName("g_res");
			res = data.getTexts();
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
