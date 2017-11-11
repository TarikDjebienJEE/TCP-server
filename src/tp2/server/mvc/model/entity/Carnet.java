package tp2.server.mvc.model.entity;

import java.util.LinkedList;
import java.util.List;

public class Carnet {
	
	private static int seqId = 1;
	private int id;
	private List<Personne> lesPersonnes;
	
	public Carnet() {
		this.setLesPersonnes(new LinkedList<Personne>());
		this.setId(seqId);
		incrementSeqIdNextVal();
	}
	
	public Personne findPersonneByName(String pName){
		for(Personne p : lesPersonnes){
			if(p.getNom().equalsIgnoreCase(pName)){
				return p;
			}
		}
		return null;
	}
	
	public synchronized boolean addPersonne(Personne personneToAdd){
		Personne p = findPersonneByName(personneToAdd.getNom());
		if(p == null){
			return lesPersonnes.add(personneToAdd);
		}else{
			return false;
		}
		
	}
	
	public synchronized boolean updatePersonne(Personne personneToUpdate){
		for(Personne p : lesPersonnes){
			if(p.getNom().equalsIgnoreCase(personneToUpdate.getNom())){
				boolean remove = lesPersonnes.remove(p);
				boolean update = lesPersonnes.add(personneToUpdate);
				return remove && update;
			}
		}
		return false;
	}
	
	public synchronized boolean removePersonne(String pName){
		for(Personne p : lesPersonnes){
			if(p.getNom().equalsIgnoreCase(pName)){
				return lesPersonnes.remove(p);
			}
		}
		return false;
	}
	
	private synchronized void incrementSeqIdNextVal(){
		seqId = seqId + 1;
	}

	public static int getIdNextVal(){
		return seqId + 1;
	}
	
	public static int getIdCurrentVal(){
		return seqId;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	private void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the lesPersonnes
	 */
	public List<Personne> getLesPersonnes() {
		return lesPersonnes;
	}

	/**
	 * @param lesPersonnes the lesPersonnes to set
	 */
	public void setLesPersonnes(List<Personne> lesPersonnes) {
		this.lesPersonnes = lesPersonnes;
	}
	
	public String toString(){
		return id + lesPersonnes.toString();
	}

}
