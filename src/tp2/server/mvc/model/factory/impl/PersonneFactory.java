package tp2.server.mvc.model.factory.impl;

import tp2.server.mvc.model.entity.Personne;
import tp2.server.mvc.model.factory.IPersonneFactory;

public class PersonneFactory implements IPersonneFactory {

	@Override
	public Personne createPersonne(String nom, String email, String url, String info) {
		return new Personne(nom, email, url, info);
	}

}
