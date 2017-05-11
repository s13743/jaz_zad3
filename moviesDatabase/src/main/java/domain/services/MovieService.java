package domain.services;

import java.util.ArrayList;
import java.util.List;

import domain.Movie;

public class MovieService {
	
	private static List<Movie> db = new ArrayList<Movie>();
	private static int currentId = -1;
	
	public List<Movie> getAll() {
		return db;
	}
	
	public Movie getById(int id) {
		for (Movie movie : db) {
			if (movie.getId() == id) {
				return movie;
			}
		}
		return null;
	}
	
	public void add (Movie movie) {
		movie.setId(++currentId);
		db.add(movie);
	}
	
	public void update (Movie movie) {
		for (Movie m : db) {
			if (m.getId() == movie.getId()) {
				m.setTitle(movie.getTitle());
				m.setYear(movie.getYear());
			}
		}
	}
	
	public void delete (Movie movie) {
		db.remove(movie);
	}
	
	public void deleteComment (Movie movie, int commentId) {
		db.get(movie.getId()).getComments().remove(commentId);
	}
}
