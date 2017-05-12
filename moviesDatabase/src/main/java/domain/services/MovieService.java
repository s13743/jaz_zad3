package domain.services;

import java.util.ArrayList;
import java.util.List;

import domain.Actor;
import domain.Comment;
import domain.Movie;
import domain.Rating;

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
	
	public void addComment (Movie movie, Comment comment) {
		comment.setId(movie.getComments().size());
		movie.getComments().add(comment);
	}
	
	public void deleteComment (Movie movie, int commentId) {
		db.get(movie.getId()).getComments().remove(commentId);
	}
	
	public void addRating(Movie movie, Rating rating) {
		rating.setId(movie.getRatings().size());
		movie.getRatings().add(rating);
		
		double sum = 0;
		for (Rating r: movie.getRatings()) {
			sum = sum + r.getRating();
		}

		double movieRating = sum/movie.getRatings().size();

		movie.setMovieRating(movieRating);
	}
	
	public List<Actor> addActor(int movieId) {
		MovieService db = new MovieService();
		List<Actor> actorsForMovie = new ArrayList<Actor>();
		List<Actor> actorsDb = new ActorService().getAll();
		
		for (Movie movie : db.getAll()) {
			if (movie.getId() == movieId) {
				for (Integer actorId : movie.getActors()) {
					for (Actor actor : actorsDb) {
						if (actor.getId() == actorId) {
							actorsForMovie.add(actor);
						}
					}
				}
			}
		}
		
		return actorsForMovie;
	}
}
