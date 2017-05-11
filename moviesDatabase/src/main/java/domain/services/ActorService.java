package domain.services;

import java.util.ArrayList;
import java.util.List;

import domain.Actor;

public class ActorService {
	
	private static List<Actor> db = new ArrayList<Actor>();
	private static int currentId = -1;
	
	public List<Actor> getAll() {
		return db;
	}
	
	public Actor getById(int id) {
		for (Actor actor : db) {
			if (actor.getId() == id) {
				return actor;
			}
		}
		return null;
	}
	
	public void add (Actor actor) {
		actor.setId(++currentId);
		db.add(actor);
	}
	
	public void update (Actor actor) {
		for (Actor a : db) {
			if (a.getId() == actor.getId()) {
				a.setName(actor.getName());
				a.setSurname(actor.getSurname());
			}
		}
	}
	
	public void delete (Actor actor) {
		db.remove(actor);
	}
}
