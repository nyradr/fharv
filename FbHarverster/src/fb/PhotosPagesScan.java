package fb;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fb.account.IPhoto;
import harvester.Harvester;
import harvester.data.IData;
import harvester.data.IPage;

/**
 * Scanner for the main photos pages scan
 * @author nyradr
 */
class PhotosPagesScan extends Scanner{

	private static final String phscheme = "allphotos";
	private static final String phs_all = "ap_all";
	
	private static final String phs_scheme = "photos";
	private static final String phs_photo = "ph_phs";
	private static final String phs_next = "ph_next";
	
	private PhotoScan phscan;
	
	public PhotosPagesScan(Harvester h) {
		super(h);
		
		phscan = new PhotoScan(h);
	}
	
	/**
	 * Scan photos page and all the next pages
	 * @param url page url
	 * @return Set of scanned photos
	 */
	private Set<IPhoto> scanAll(String url){
		Set<IPhoto> photos = new HashSet<>();
		try{
			IPage page = harvester.get(url, phs_scheme);
			IData phs = page.getByName(phs_photo);
			
			for(String ph : phs.getTexts()){
				String phurl = buildUrl(ph);
				if(!phurl.isEmpty())
					photos.add(phscan.scan(phurl));
			}
			
			String next = buildUrl(page.getByName(phs_next).getText());
			System.out.println("NEXT PHOTO : " + next);
			if(!next.isEmpty())
				photos.addAll(scanAll(next));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return photos;
	}
	
	/**
	 * Scan all photos and get informations
	 * @param url main photo page
	 * @return list of all photos scanned
	 */
	public Set<IPhoto> scan(String url){
		try{
			IPage page = harvester.get(url, phscheme);
			
			String allurl = buildUrl(page.getByName(phs_all).getText());
			if(!allurl.isEmpty())
				return scanAll(allurl);
			else
				System.out.println("ERROR : NO ALL PHOTOS AT : " + url);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
