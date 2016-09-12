package fb;

import harvester.Harvester;

/*	Scanner for the basic informations page
 */
class BaseInfoScan extends Scanner{

	public BaseInfoScan(Harvester h) {
		super(h);
	}
	
	/* Scan the basic information page
	 */
	public AccountInfo scan(String url, String fbid){
		AccountInfo acc = new AccountInfo(fbid);
		
		return acc;
	}
}
