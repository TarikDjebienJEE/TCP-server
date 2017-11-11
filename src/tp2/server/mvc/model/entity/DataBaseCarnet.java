package tp2.server.mvc.model.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tp2.server.mvc.model.exception.CarnetNotFoundException;
import tp2.server.mvc.model.exception.UserNotFoundException;
import tp2.server.mvc.view.IHM;
import tp2.server.util.Logger;

public class DataBaseCarnet {
	
	private static final Logger logger = Logger.getLogger(DataBaseCarnet.class.getName());


	private Map<Integer, Carnet> lesCarnets;
	private Set<User> lesUsers;

	private static final DataBaseCarnet bddCarnet = new DataBaseCarnet();

	public static DataBaseCarnet getInstance(){
		return bddCarnet;
	}

	private DataBaseCarnet(){
		setLesCarnets(new ConcurrentHashMap<Integer, Carnet>());
		setLesUsers(new HashSet<User>());
	}

	public synchronized boolean addCarnet(){
		try{
			lesCarnets.put(Carnet.getIdCurrentVal(), new Carnet());
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public synchronized boolean removeCarnet(int idCarnet) throws CarnetNotFoundException{
		getCarnet(idCarnet);
		lesCarnets.remove(idCarnet);
		return true;
	}

	public Carnet findCarnetById(int idCarnet) throws CarnetNotFoundException{
		return getCarnet(idCarnet);
	}

	public Set<Carnet> findAllCarnet(){
		Set<Carnet> res = new HashSet<Carnet>();
		if(!lesCarnets.keySet().isEmpty()){
			for(Integer id : lesCarnets.keySet()){
				res.add(lesCarnets.get(id));
			}
		}
		return res;
	}

	public Personne selectByName(int idCarnet, String pName) throws CarnetNotFoundException{
		return getCarnet(idCarnet).findPersonneByName(pName);
	}

	public boolean addPersonne(int idCarnet, Personne personneToAdd) throws CarnetNotFoundException{
		return getCarnet(idCarnet).addPersonne(personneToAdd);
	}

	public boolean updatePersonne(int idCarnet, Personne personneToUpdate) throws CarnetNotFoundException{
		return getCarnet(idCarnet).updatePersonne(personneToUpdate);
	}

	public boolean removePersonne(int idCarnet, String pName) throws CarnetNotFoundException{
		return getCarnet(idCarnet).removePersonne(pName);
	}

	/**
	 * @param lesCarnets the lesCarnets to set
	 */
	private void setLesCarnets(Map<Integer, Carnet> lesCarnets) {
		this.lesCarnets = lesCarnets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lesCarnets == null) ? 0 : lesCarnets.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataBaseCarnet other = (DataBaseCarnet) obj;
		if (lesCarnets == null) {
			if (other.lesCarnets != null)
				return false;
		} else if (!lesCarnets.equals(other.lesCarnets))
			return false;
		return true;
	}

	public Carnet getCarnet(int carnetId) throws CarnetNotFoundException {
		Carnet c = lesCarnets.get(new Integer(carnetId));
		if(c != null){
			return c;
		}else throw new CarnetNotFoundException();
	}

	public boolean checkUserAuthentification(User user) throws UserNotFoundException{
		String loginSend = user.getLogin();
		String passwordSend = user.getPassword();
		for(User currentUser : getLesUsers()){
			if(loginSend.equals(currentUser.getLogin()) && passwordSend.equals(currentUser.getPassword())){
				return true;
			}
		}
		throw new UserNotFoundException();
	}

	/**
	 * @return the lesUsers
	 */
	private Set<User> getLesUsers() {
		return lesUsers;
	}

	/**
	 * @param lesUsers the lesUsers to set
	 */
	private void setLesUsers(Set<User> lesUsers) {
		this.lesUsers = lesUsers;
	}
	
	public void description(){
		DataBaseCarnet dbc = getInstance();
		System.out.println(dbc.lesCarnets);
		System.out.println(dbc.lesUsers);
	}

	public void addUser(String login, String password) {
		User user = new User(login, password);
		if(lesUsers.add(user)){
			IHM.writeOnConsoleServer(logger.getInfo("Creation of user(login:"+login+", password:"+password+")."));
		}else{
			IHM.writeOnConsoleServer(logger.getInfo("The login:"+login+" exists, choose another please."));
		}
	}

}
