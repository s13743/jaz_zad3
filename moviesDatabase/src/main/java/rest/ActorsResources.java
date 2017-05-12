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
import domain.Movie;
import domain.services.ActorService;
import domain.services.MovieService;

@Path("/actors")
public class ActorsResources {
	
	private ActorService db = new ActorService();
	
	private MovieService dbMovies = new MovieService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getAll() {
		return db.getAll();
	}
	
	//dodawanie aktorów
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Actor actor) {
		db.add(actor);
		return Response.ok(actor.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Actor result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Actor actor) {
		Actor result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		actor.setId(id);
		db.update(actor);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		Actor result = db.getById(id);
		if (result == null) {
			return Response.status(404).build();
		}
		db.delete(result);
		return Response.ok().build();
	}
	
	//wyświetlanie filmów danego aktora
	
	@GET
	@Path("/{actorId}/movies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getComments(@PathParam("actorId") int actorId) {
		Actor result = db.getById(actorId);
		if (result == null) {
			return null;
		}
		if (result.getMovies() == null) {
			result.setMovies(new ArrayList<Movie>());
		}
		return result.getMovies();
	}
	
	//dodawanie filmu do danego aktora o okreslonym id
	
	@POST
	@Path("/{id}/movies")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMovie(@PathParam("id") int actorId, Movie movie) {
		Actor result = db.getById(actorId);
		if (result == null) {
			return Response.status(404).build();
		}
		if (result.getMovies() == null) {
			result.setMovies(new ArrayList<Movie>());
		}
		
		for (Movie m : dbMovies.getAll()) {
			if (m.getId() == movie.getId()) {
				result.getMovies().add(m);
				//TO NIE DZIALA??????? DLACZEGO ????
//				if (m.getActors() == null) {
//					m.setActors(new ArrayList<Actor>());
//				}
//				m.getActors().add(result);

				if (m.getActors() == null) {
				m.setActors(new ArrayList<Integer>());
				}
				
				
				m.getActors().add(result.getId());
				
			}
		}
		
		return Response.ok().build();
	}

}