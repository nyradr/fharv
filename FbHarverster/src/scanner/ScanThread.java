package scanner;

import fb.FbManager;
import fb.account.IAccountInfo;

/**
 * Class for scanning a fb account
 * @author nyradr
 */
class ScanThread extends Thread{
	
	private FbManager manager;
	private String fbid;
	private IScanOver clb;
	
	/**
	 * Create new scan thread
	 * @param m Facebook manager instance
	 * @param id Facebook account id
	 */
	public ScanThread(FbManager m, String id, IScanOver clb){
		manager = m;
		fbid = id;
		this.clb = clb;
	}
	
	public String getFbid(){
		return fbid;
	}
	
	public void run(){
		IAccountInfo acc = manager.scan(fbid);
		clb.onScanOver(this, acc);
	}
}
