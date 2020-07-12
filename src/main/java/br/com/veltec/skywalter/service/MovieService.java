package br.com.veltec.skywalter.service;

import br.com.veltec.skywalter.entities.LetterMetrics;
import br.com.veltec.skywalter.entities.MovieDTO;
import br.com.veltec.skywalter.entities.MovieEntity;

import java.util.List;

public interface MovieService {
    public List<MovieEntity> getListMovies();

    public Object getMovies(Integer id);

    public MovieEntity postMovies(MovieDTO movies);

    public Object putMovie(Integer id, MovieDTO movie);

    public List<LetterMetrics> getLetterMetrics();
}
