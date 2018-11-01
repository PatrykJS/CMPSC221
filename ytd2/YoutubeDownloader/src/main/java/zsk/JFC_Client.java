package zsk;

import zsk.helper.Properties;


public class JFC_Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		JFCMainClient client = new JFCMainClient(properties);
		
		client.start(args);

	}

}
