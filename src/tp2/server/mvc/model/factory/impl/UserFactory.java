package tp2.server.mvc.model.factory.impl;

import tp2.server.mvc.model.entity.User;
import tp2.server.mvc.model.factory.IUserFactory;

public class UserFactory implements IUserFactory {

	@Override
	public User createUser(String login, String password) {
		return new User(login, password);
	}

}
