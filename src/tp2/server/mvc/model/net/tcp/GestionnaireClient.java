package tp2.server.mvc.model.net.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import tp2.server.mvc.model.entity.DataBaseCarnet;
import tp2.server.mvc.view.IHM;
import tp2.server.util.Logger;

public class GestionnaireClient extends Thread {
	
	private Socket clientSocket;
	private static Logger logger = Logger.getLogger(GestionnaireClient.class.getName());

	public GestionnaireClient(Socket clientSocket){
		this.clientSocket = clientSocket;
		super.start();
	}

	public void run() {
		
		try{
			
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
			String clientRequest = null;
			String serverResponse = null;

			do{
				clientRequest = inFromClient.readLine();
				IHM.writeOnConsoleServer(logger.getInfo("Client("+clientSocket.getInetAddress()+") gives request :"+clientRequest));
				if(clientRequest!= null && !clientRequest.equalsIgnoreCase(ServerProtocole.MESSAGE_EXIT_APP)){
					serverResponse = ServerProtocole.sendResponseToClient(DataBaseCarnet.getInstance(), clientRequest);
					IHM.writeOnConsoleServer(logger.getInfo("Server gives response :"+serverResponse+" to client("+clientSocket.getInetAddress()+")"));
					outToClient.writeBytes(serverResponse+"\n");
				}
			}while(clientRequest == null || !clientRequest.equalsIgnoreCase(ServerProtocole.MESSAGE_EXIT_APP) );
			
			IHM.writeOnConsoleServer(logger.getInfo("Client("+clientSocket.getInetAddress()+") disconected."));
			clientSocket.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}