package fb;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import harvester.Harvester;
import harvester.data.IPage;

/**
 * Scan account friends page
 * @author nyradr
 */
class FriendsScan extends Scanner{

	private static final String frd_scheme = "friends";
	private static final String frd_lst = "friend";
	private static final String frd_nxt = "nextfrd";
	
	public FriendsScan(Harvester hv){
		super(hv);
	}
	
	/**
	 * Scan friends page<br>
	 * Also scan all following friends pages
	 * @param url first friend page to scan
	 * @return Set of all fb ids referenced
	 */
	public Set<String> scan(String url){
		Set<String> friends = new HashSet<>();
		
		try {
			IPage page = harvester.get(url, frd_scheme);
			
			for(String link : page.getByName(frd_lst).getTexts())
				friends.add(clearFbId(link));
			
			String next = buildUrl(page.getByName(frd_nxt).getText());
			if(!next.isEmpty())
				friends.addAll(scan(next));
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		
		return friends;
	}
}
