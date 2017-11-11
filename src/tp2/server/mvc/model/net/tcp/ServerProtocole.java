package tp2.server.mvc.model.net.tcp;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tp2.server.mvc.model.entity.Carnet;
import tp2.server.mvc.model.entity.DataBaseCarnet;
import tp2.server.mvc.model.entity.Personne;
import tp2.server.mvc.model.entity.User;
import tp2.server.mvc.model.exception.CarnetNotFoundException;
import tp2.server.mvc.model.exception.UserNotFoundException;
import tp2.server.mvc.model.factory.IPersonneFactory;
import tp2.server.mvc.model.factory.IUserFactory;
import tp2.server.mvc.model.factory.impl.PersonneFactory;
import tp2.server.mvc.model.factory.impl.UserFactory;
import tp2.server.mvc.view.IHM;
import tp2.server.util.Logger;

public final class ServerProtocole {

	private static final String MESSAGE_AJOUTER_PERSONNE  		= "ajouterPersonne";
	private static final String MESSAGE_MODIFIER_PERSONNE 		= "modifierPersonne";
	private static final String MESSAGE_RETIRER_PERSONNE  		= "retirerPersonne";
	private static final String MESSAGE_CHERCHER_PERSONNE 		= "chercherPersonne";
	private static final String MESSAGE_LISTER_PERSONNE   		= "listerPersonne";

	private static final String MESSAGE_AJOUTER_CARNET    = "ajouterCarnet";
	private static final String MESSAGE_RETIRER_CARNET    = "retirerCarnet";
	private static final String MESSAGE_CHERCHER_CARNET   = "chercherCarnet";
	private static final String MESSAGE_LISTER_CARNET	  = "listerCarnet";
	
	private static final String MESSAGE_AUTHENTICATE_USER = "connectUser";
	public static final String MESSAGE_EXIT_APP 		  = "EXIT";

	private static final String MESSAGE_ORDER_SUCCESS 	= "OK";
	private static final String MESSAGE_ORDER_FAILED 	= "KO";
	private static final String MESSAGE_ORDER_ERROR     = "ERROR";
	private static final String PARAMS_SEPARATOR 		= ";";
	private static final String ORDER_SEPARATOR 		= ":";

	private static final int POSITION_VALUE_ID_CARNET 		= 0;
	private static final int POSITION_VALUE_PERSONNE_NAME 	= 1;
	private static final int POSITION_VALUE_PERSONNE_EMAIL 	= 2;
	private static final int POSITION_VALUE_PERSONNE_URL 	= 3;
	private static final int POSITION_VALUE_PERSONNE_INFO 	= 4;
	
	private static final int POSITION_VALUE_USER_LOGIN 		= 0;
	private static final int POSITION_VALUE_USER_PASSWORD 	= 1;

	private static final Logger logger					= Logger.getLogger(ServerProtocole.class.getName());
	

	public static String sendResponseToClient(DataBaseCarnet repertoire, String clientRequest){

		if(clientRequest != null && !clientRequest.isEmpty()){
			
			if(clientRequest.startsWith(MESSAGE_AJOUTER_PERSONNE)){

				IPersonneFactory pf = new PersonneFactory();
				String[] params = (clientRequest.split(ORDER_SEPARATOR)[1]).split(PARAMS_SEPARATOR);
				Personne personneToAdd = pf.createPersonne(
						responseFormatter(getNameValue(params)), 
						responseFormatter(getEmailValue(params)), 
						responseFormatter(getUrlValue(params)), 
						responseFormatter(getInfoValue(params))
						);
				try {
					return DataBaseCarnet.getInstance().addPersonne(getIdCarnetValue(params), personneToAdd) ? MESSAGE_ORDER_SUCCESS : MESSAGE_ORDER_FAILED;
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+getIdCarnetValue(params)+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}

			}else if(clientRequest.startsWith(MESSAGE_MODIFIER_PERSONNE)){

				IPersonneFactory pf = new PersonneFactory();
				String[] params = (clientRequest.split(ORDER_SEPARATOR)[1]).split(PARAMS_SEPARATOR);
				Personne personneToUpdate = pf.createPersonne(
						responseFormatter(getNameValue(params)), 
						responseFormatter(getEmailValue(params)), 
						responseFormatter(getUrlValue(params)), 
						responseFormatter(getInfoValue(params))
						);
				try {
					return DataBaseCarnet.getInstance().updatePersonne(getIdCarnetValue(params), personneToUpdate) ? MESSAGE_ORDER_SUCCESS : MESSAGE_ORDER_FAILED;
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+getIdCarnetValue(params)+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}

			}else if(clientRequest.startsWith(MESSAGE_RETIRER_PERSONNE)){

				String[] params = (clientRequest.split(ORDER_SEPARATOR)[1]).split(PARAMS_SEPARATOR);
				try {
					return DataBaseCarnet.getInstance().removePersonne(
							Integer.parseInt(responseFormatter(Integer.toString(getIdCarnetValue(params)))), 
							responseFormatter(getNameValue(params))) 
							? MESSAGE_ORDER_SUCCESS 
							: MESSAGE_ORDER_FAILED;
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+getIdCarnetValue(params)+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}

			}else if(clientRequest.startsWith(MESSAGE_CHERCHER_PERSONNE)){

				String[] params = (clientRequest.split(ORDER_SEPARATOR)[1]).split(PARAMS_SEPARATOR);
				Personne p;
				try {
					p = DataBaseCarnet.getInstance().selectByName(
							Integer.parseInt(responseFormatter(Integer.toString(getIdCarnetValue(params)))), 
							responseFormatter(getNameValue(params))
							);
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+getIdCarnetValue(params)+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}
				if(p != null){
					StringBuffer buff = new StringBuffer()
					.append(MESSAGE_ORDER_SUCCESS)
					.append(ORDER_SEPARATOR)
					.append(requestFormatter(p.getNom()))
					.append(PARAMS_SEPARATOR)
					.append(requestFormatter(p.getEmail()))
					.append(PARAMS_SEPARATOR)
					.append(requestFormatter(p.getUrl()))
					.append(PARAMS_SEPARATOR)
					.append(requestFormatter(p.getInfo()));
					return buff.toString();
				}else{
					return MESSAGE_ORDER_FAILED;
				}

			}else if(clientRequest.startsWith(MESSAGE_LISTER_PERSONNE)){

				int idCarnet = Integer.parseInt((clientRequest.split(ORDER_SEPARATOR)[1]).trim());
				Carnet carnetCible = null;
				try {
					carnetCible = DataBaseCarnet.getInstance().getCarnet(idCarnet);
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+idCarnet+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}

				List<Personne> lesPersonneDuCarnet = carnetCible.getLesPersonnes();
				if(!lesPersonneDuCarnet.isEmpty()){
					StringBuffer buff = new StringBuffer().append(MESSAGE_ORDER_SUCCESS).append(ORDER_SEPARATOR);
					for(Iterator<Personne> it = lesPersonneDuCarnet.iterator();it.hasNext();){
						buff.append(requestFormatter(it.next().getNom()));
						if(it.hasNext()){
							buff.append(PARAMS_SEPARATOR);
						}
					}
					return buff.toString();
				}else{
					return MESSAGE_ORDER_FAILED;
				}
			
				
			}if(clientRequest.startsWith(MESSAGE_AJOUTER_CARNET)){
				return DataBaseCarnet.getInstance().addCarnet() ? MESSAGE_ORDER_SUCCESS : MESSAGE_ORDER_FAILED;

			}else if(clientRequest.startsWith(MESSAGE_RETIRER_CARNET)){

				int idCarnet = Integer.valueOf((clientRequest.split(ORDER_SEPARATOR)[1])).intValue();
				try {
					return DataBaseCarnet.getInstance().removeCarnet(idCarnet) ? MESSAGE_ORDER_SUCCESS : MESSAGE_ORDER_FAILED;
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+idCarnet+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}

			}else if(clientRequest.startsWith(MESSAGE_CHERCHER_CARNET)){

				int idCarnet = Integer.valueOf((clientRequest.split(ORDER_SEPARATOR)[1])).intValue();
				Carnet c;
				try {
					c = DataBaseCarnet.getInstance().findCarnetById(idCarnet);
				} catch (CarnetNotFoundException e) {
					IHM.writeOnConsoleServer(logger.getError("Le carnet("+idCarnet+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}
				if(c != null){
					StringBuffer buff = new StringBuffer()
					.append(MESSAGE_ORDER_SUCCESS)
					.append(ORDER_SEPARATOR)
					.append(requestFormatter(Integer.toString(c.getId())))
					.append(PARAMS_SEPARATOR);
					return buff.toString();
				}else{
					return MESSAGE_ORDER_FAILED;
				}

			}else if(clientRequest.startsWith(MESSAGE_LISTER_CARNET)){

				Set<Carnet> lesCarnets = DataBaseCarnet.getInstance().findAllCarnet();
				if(!lesCarnets.isEmpty()){
					StringBuffer buff = new StringBuffer().append(MESSAGE_ORDER_SUCCESS).append(ORDER_SEPARATOR);
					for(Iterator<Carnet> it = lesCarnets.iterator();it.hasNext();){
						buff.append(requestFormatter(Integer.toString(it.next().getId())));
						if(it.hasNext()){
							buff.append(PARAMS_SEPARATOR);
						}
					}
					return buff.toString();
				}else{
					return MESSAGE_ORDER_FAILED;
				}
			
			}else if(clientRequest.startsWith(MESSAGE_AUTHENTICATE_USER)){
				IUserFactory uf = new UserFactory();
				String[] params = (clientRequest.split(ORDER_SEPARATOR)[1]).split(PARAMS_SEPARATOR);
				User user = uf.createUser(
						responseFormatter(getLoginValue(params)), 
						responseFormatter(getPasswordValue(params))
						);
				try {
					return DataBaseCarnet.getInstance().checkUserAuthentification(user) ? MESSAGE_ORDER_SUCCESS : MESSAGE_ORDER_FAILED;
				} catch (UserNotFoundException unfe) {
					IHM.writeOnConsoleServer(logger.getError("Le user("+user+") n'existe pas."));
					return MESSAGE_ORDER_ERROR;
				}
			}else if(clientRequest.equalsIgnoreCase(MESSAGE_EXIT_APP)){
				return MESSAGE_ORDER_SUCCESS;
			}else{
				return MESSAGE_ORDER_FAILED;
			}
		}
		return MESSAGE_ORDER_FAILED;
	}


	private static int getIdCarnetValue(String[] params){
		return Integer.parseInt(params[POSITION_VALUE_ID_CARNET]);
	}

	private static String getNameValue(String[] params){
		return params[POSITION_VALUE_PERSONNE_NAME];
	}

	private static String getEmailValue(String[] params){
		return params[POSITION_VALUE_PERSONNE_EMAIL];
	}

	private static String getUrlValue(String[] params){
		return params[POSITION_VALUE_PERSONNE_URL];
	}

	private static String getInfoValue(String[] params){
		return params[POSITION_VALUE_PERSONNE_INFO];
	}
	
	private static String getLoginValue(String[] params) {
		return params[POSITION_VALUE_USER_LOGIN];
	}
	
	private static String getPasswordValue(String[] params) {
		return params[POSITION_VALUE_USER_PASSWORD];
	}
	
	public static String requestFormatter(String request){
		return request.replaceAll(ORDER_SEPARATOR, "&order").replaceAll(PARAMS_SEPARATOR, "&separator");
	}
	
	public static String responseFormatter(String response){
		return response.replaceAll("&order", ORDER_SEPARATOR).replaceAll("&separator", PARAMS_SEPARATOR);
	}

}
