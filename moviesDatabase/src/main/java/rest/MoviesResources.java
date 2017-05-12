package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Actor;
import domain.Comment;
import domain.Movie;
import domain.Rating;
import domain.services.ActorService;
import domain.services.MovieService;

@Path("/movies")
public class MoviesResources {
	
	private MovieService db = new MovieService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAll() {
		return db.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Movie movie) {
		db.add(movie);
		return Response.ok(movie.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Movie movie) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		movie.setId(id);
		db.update(movie);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		db.delete(result);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{movieId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		if (result.getComments() == null) {
			result.setComments(new ArrayList<Comment>());
		}
		return result.getComments();
	}
	
	@POST
	@Path("/{id}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("id") int movieId, Comment comment) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return Response.status(404).build();
		}
		if (result.getComments() == null) {
			result.setComments(new ArrayList<Comment>());
		}
		////////////////////
		comment.setId(result.getComments().size());
		////////////////////
		result.getComments().add(comment);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}/comments/{commentId}")
	public Response deleteComment(@PathParam("id") int movieId, @PathParam("commentId") int commentId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return Response.status(404).build();
		}
		db.deleteComment(result, commentId);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{movieId}/ratings")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rating> getRatings(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		if (result.getRatings() == null) {
			result.setRatings(new ArrayList<Rating>());
		}
		return result.getRatings();
	}
	
	@POST
	@Path("/{id}/ratings")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRatings(@PathParam("id") int movieId, Rating rating) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return Response.status(404).build();
		}
		if (result.getRatings() == null) {
			result.setRatings(new ArrayList<Rating>());
		}
		////////////////////////////
		rating.setId(result.getRatings().size());
		////////////////////////////
		result.getRatings().add(rating);
		
		double sum = 0;
		for (Rating r: result.getRatings()) {
			sum = sum + r.getRating();
		}

		double movieRating = sum/result.getRatings().size();

		result.setMovieRating(movieRating);
		return Response.ok().build();
	}
	
	//wyświetlenie aktorów danego filmu
	
	@GET
	@Path("/{movieId}/actors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getActors(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		
//		if (result.getActors() == null) {
//			result.setActors(new ArrayList<Actor>());
//		}
//		
//		return result.getActors();
		
		List<Actor> actorsForMovie = new ArrayList<Actor>();
		
		List<Actor> actorsDb = new ActorService().getAll();
		
		//iteruje bazę filmów
		for (Movie movie : db.getAll()) {
			//sprawdzam czy film jest równy filmowi przekazanemu w parametrze
			if (movie.getId() == movieId) {
				
				for (Integer actorId : movie.getActors()) {
					
					//przeitereuj baze aktorów
					for (Actor actor : actorsDb) {
						if (actor.getId() == actorId) {
							actorsForMovie.add(actor);
						}
					}
					
				}
				
			}
		}
		
		//pętla zaminiejące liste intów na liste filmów aktora
		
		return actorsForMovie;
	}
}
