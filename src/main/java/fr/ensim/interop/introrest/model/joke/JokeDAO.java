package fr.ensim.interop.introrest.model.joke;

import org.springframework.data.repository.CrudRepository;


public interface JokeDAO extends CrudRepository<Joke, Long> {
    public Joke findJokesById(Long id);
}
