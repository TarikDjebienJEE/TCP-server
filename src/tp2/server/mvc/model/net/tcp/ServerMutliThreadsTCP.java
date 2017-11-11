package tp2.server.mvc.model.net.tcp;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import tp2.server.configuration.ConfigurationDefault;
import tp2.server.mvc.view.IHM;
import tp2.server.util.Logger;


public class ServerMutliThreadsTCP {
	
	private static Logger logger = Logger.getLogger(ServerMutliThreadsTCP.class.getName());

	public ServerMutliThreadsTCP() throws Exception {

		int port = ConfigurationDefault.PORT;
		ServerSocket serverSocket = new ServerSocket(port);

		IHM.writeOnConsoleServer(logger.getInfo("Server started on port "+serverSocket.getLocalPort()+"."));
		IHM.writeOnConsoleServer(logger.getInfo("Server waiting connection from clients..."));
		
		while(true) {
			
			Socket connectionTCP = serverSocket.accept();
			InetAddress ipClient = connectionTCP.getInetAddress();
			IHM.writeOnConsoleServer(logger.getInfo("Server connected with client "+ipClient));
			new GestionnaireClient(connectionTCP);
		}
		
	}
	
	public void startX(){
		IHM.getInstance();
	}


}
