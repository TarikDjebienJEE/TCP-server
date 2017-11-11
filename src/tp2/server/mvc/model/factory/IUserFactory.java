package tp2.server.mvc.model.factory;

import tp2.server.mvc.model.entity.User;


/**
 * Fabrique de user
 *
 * @author tarik DJEBIEN
 * @version 1.0
 */
public interface IUserFactory {

	/**
	 * Instanciation d'un nouvel objet de type User
	 * @return un nouveau Carnet
	 * @author tarik
	 */
	public User createUser(String login, String password);

}
