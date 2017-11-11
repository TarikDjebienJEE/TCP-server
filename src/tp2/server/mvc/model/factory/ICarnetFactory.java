package tp2.server.mvc.model.factory;

import tp2.server.mvc.model.entity.Carnet;


/**
 * Fabrique de carnet
 *
 * @author tarik DJEBIEN
 * @version 1.0
 */
public interface ICarnetFactory {

	/**
	 * Instanciation d'un nouvel objet de type Carnet
	 * @return un nouveau Carnet
	 * @author tarik
	 */
	public Carnet createCarnet();

}
