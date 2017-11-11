package tp2.server.mvc.model.factory.impl;

import tp2.server.mvc.model.entity.Carnet;
import tp2.server.mvc.model.factory.ICarnetFactory;


public class CarnetFactory implements ICarnetFactory {

	@Override
	public Carnet createCarnet() {
		return new Carnet();
	}

}
