package fb;

import java.util.HashSet;
import java.util.Set;

import fb.account.IPhoto;
import harvester.Harvester;
import harvester.data.IPage;

/**
 * Scanner for a photo page (and likes)
 * 
 * TODO ; add image url scan
 * @author nyradr
 */
class PhotoScan extends Scanner{

	/**
	 * {@link IPhoto} implementation
	 * @author nyradr
	 */
	private class Photo implements IPhoto{
		String post;
		String url;
		Set<String> likes;
		Set<String> refs;
		
		public Photo(){
			likes = new HashSet<>();
			refs = new HashSet<>();
		}
		
		@Override
		public Set<String> getLikes() {
			return likes;
		}
		@Override
		public Set<String> getReferenced() {
			return refs;
		}
		@Override
		public String getDate() {
			return post;
		}

		@Override
		public String getUrl(){
			return url;
		}

		@Override
		public String getJson() {
			String json = "{";
			json += "\"post\":\"" + post + "\",";
			json += "\"url\":\"" + url + "\",";
			json += "\"likes\":[";
			
			boolean fst = true;
			for(String lk : likes){
				if(!fst)
					json += ",";
				else
					fst = false;
				json += "\"" + lk + "\"";
			}
			json += "],";
			
			json += "\"refs\":[";
			fst = true;
			for(String r : refs){
				if(!fst)
					json += ",";
				else
					fst = false;
				json += '\"' + r + "\"";
			}
			
			return json + "]}";
		}
	}
	
	private static final String phscheme = "photo";
	private static final String ph_date = "ph_date";
	private static final String ph_likes = "ph_likes";
	
	private static final String lkscheme = "likes";
	private static final String lk_likes = "lk_pers";
	private static final String lk_next = "lk_next";
	
	public PhotoScan(Harvester h) {
		super(h);
	}
	
	/**
	 * Scan photo likes pages
	 * @param url url of the photo likes page
	 * @return Set of all referenced id
	 */
	private Set<String> scanLikes(String url){
		Set<String> likes = new HashSet<>();
		
		try{
			IPage page = harvester.get(url, lkscheme);
			
			for(String lk : page.getByName(lk_likes).getTexts())
				likes.add(clearFbId(lk));
			
			// next page
			String next = buildUrl(page.getByName(lk_next).getText());
			
			// next page
			if(!next.isEmpty())
				likes.addAll(scanLikes(next));
		}catch(Exception e){
			e.printStackTrace();
		}
		return likes;
	}
	
	/**
	 * Scan all informations of a photo
	 * @param url photo url
	 * @return photo informations
	 */
	public IPhoto scan(String url){
		Photo ph = new Photo();
		
		try{
			IPage page = harvester.get(url, phscheme);
			
			ph.post = page.getByName(ph_date).getText();
			
			String likesUrl = buildUrl(page.getByName(ph_likes).getText());
			if(!likesUrl.isEmpty())
				ph.likes = scanLikes(likesUrl);
			
			System.out.println("POST " + ph.post);
			System.out.println("LK " + ph.likes.size());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ph;
	}
}
