package scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.configuration.WebBrowser;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import fb.FbManager;
import fb.account.IAccountInfo;
import harvester.scheme.SchemeParseError;

/**
 * Manage the facebook scan and the relation with the server
 * @author nyradr
 */
public class FbScanManager implements IScanOver{
	
	private String serverAddress;
	private final String server_send = "send.php";
	private final String server_get = "get.php";
	
	private WebClient browser;
	private FbManager manager;
	
	private int maxthread;
	private Set<ScanThread> scans;
	
	public FbScanManager(String addr, String user, String pass, int maxth) throws FileNotFoundException, SchemeParseError{
		browser = new WebClient();
		
		serverAddress = addr;
		manager = new FbManager(browser);
		manager.login(user, pass);
		maxthread = maxth;
		scans = new HashSet<>();
	}
	
	
	/**
	 * Start facebook scan
	 * @param fbid entry point facebook id
	 */
	public void start(String fbid){
		scan(fbid);
	}
	
	/**
	 * Start facebook scan by asking id to the server
	 */
	public void start(){
		try{
			scan(getnext());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Scan a facebook account
	 * @param epoint Facebook id to scan
	 */
	public void scan(String epoint){
		if(scans.size() <= maxthread && epoint != ""){
			log(epoint + " : start scan");
			ScanThread sc = new ScanThread(manager, epoint, this);
			sc.start();
			scans.add(sc);
		}
	}
	
	@Override
	public void onScanOver(ScanThread st, IAccountInfo acc) {
		log(st.getFbid() + " : end scan");
		
		scans.remove(st);
		postAccount(acc);
		
		// start new scans
		int test = 0;	// for infinite loop security
		while(scans.size() < maxthread && test < maxthread){
			start();
			test++;
		}
	}
	
	/**
	 * Send account data to server
	 * @param acc account
	 */
	private void postAccount(IAccountInfo acc){
		try {
			WebRequest req = new WebRequest(new URL(serverAddress + server_send), HttpMethod.POST);
			List<NameValuePair> params = new ArrayList<>();
			params.add(new NameValuePair("fb", acc.getJson()));
			req.setRequestParameters(params);
			
			browser.getPage(req);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ask to the server the next facebook id to scan
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public String getnext() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		HtmlPage page = browser.getPage(serverAddress + server_get);
		
		return page.asXml();
	}

	/**
	 * Get the number of current scans
	 * @return
	 */
	public int scansLength(){
		return scans.size();
	}
	
	/**
	 * Get all facebook ids currently scanned
	 * @return
	 */
	public List<String> getScanning(){
		return scans.stream()
				.map(x -> x.getFbid()).collect(Collectors.toList());
	}
	
	/**
	 * Log message into the standard output
	 * @param mess
	 */
	public void log(String mess){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = f.format(new Date());
		
		System.out.println(dt + " : " + mess);
		
	}
}
