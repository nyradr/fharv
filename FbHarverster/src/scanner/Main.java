package scanner;

public class Main {
	
	private static final String server = "https://localhost/fb/";
	private static final String fbuser = "fscan@gmx.fr";
	private static final String fbpass = "FbScanneraaa";
	private static final int maxth = 1;

	public static void main(String [] args){
		try{
			FbScanManager scanner = new FbScanManager(server, fbuser, fbpass, maxth);
			
			// starting scan
			scanner.scan("melanie.gergaud");
			
			/*if(args.length > 0)
				scanner.start(args[0]);
			else
				scanner.start();
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
