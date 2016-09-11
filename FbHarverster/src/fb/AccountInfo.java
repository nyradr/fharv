package fb;

import java.util.HashSet;
import java.util.Set;

import fb.account.IAccountInfo;
import fb.account.IPhoto;
import fb.account.Sexe;

/**
 * Implementation for account informations
 * @author nyradr
 *
 */
class AccountInfo implements IAccountInfo {
	
	private String fbid;
	private String name;
	private Sexe sexe;
	private Set<String> langs;
	private Set<String> friends;
	private Set<IPhoto> photos;
	
	public AccountInfo(String fbid) {
		this.fbid = fbid;
		langs = new HashSet<>();
		friends = new HashSet<>();
		photos = new HashSet<>();
	}
	
	@Override
	public String getFbId() {
		return fbid;
	}
	
	public void setName(String n){
		name = n;
	}
	
	@Override
	public String getName(){
		return name;
	}

	public void setSexe(Sexe s){
		sexe = s;
	}
	
	@Override
	public Sexe getSexe() {
		return sexe;
	}

	public void addSpoken(String s){
		langs.add(s);
	}
	
	@Override
	public Set<String> getSpoken() {
		return langs;
	}

	public void setFriends(Set<String> f){
		friends = f;
	}
	
	@Override
	public Set<String> getFriends() {
		return friends;
	}
	
	public void setPhotos(Set<IPhoto> p){
		photos = p;
	}

	@Override
	public Set<IPhoto> getPhotos() {
		return photos;
	}

	@Override
	public String getJson() {
		String json = "{";
		
		json += "\"id\":\"" + fbid + "\",";
		json += "\"name\":\"" + name + "\",";
		json += "\"sexe\":\"" + sexe.getVal() + "\",";
		
		json += "\"langs\":[";
		boolean fst = true;
		for(String l : langs){
			if(!fst)
				json += ",";
			else
				fst = false;
			json += "\"" + l + "\"";
		}
		json += "],";
		
		json += "\"friends\":[";
		fst = true;
		for(String f : friends){
			if(!fst)
				json += ",";
			else
				fst = false;
			json += "\"" + f + "\"";
		}
		json += "],";
		
		json += "\"photos\":[";
		fst = true;
		for(IPhoto p : photos){
			if(!fst)
				json += ",";
			else
				fst = false;
			json += p.getJson();
		}
		json += "]";
		
		return json + "}";
	}

}
