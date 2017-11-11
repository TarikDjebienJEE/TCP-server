package tp2.server.mvc.main;

import java.net.BindException;

import tp2.server.mvc.model.net.tcp.ServerMutliThreadsTCP;
import tp2.server.util.Logger;

public class StartServer {
	
	private static Logger logger = Logger.getLogger(StartServer.class.getName());
	
	public static void main(String[] args) {
		try {
			ServerMutliThreadsTCP server = new ServerMutliThreadsTCP();
			server.startX();
		} catch (BindException be){
			logger.error("Server cannot start : a another server is running on the same address.",be);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Server crash", e);
		}
	}

}
