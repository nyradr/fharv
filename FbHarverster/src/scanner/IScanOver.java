package scanner;

import fb.account.IAccountInfo;

/**
 * Callback when a scan is over
 * @author nyradr
 */
interface IScanOver {
	
	public void onScanOver(ScanThread e, IAccountInfo acc);
}
