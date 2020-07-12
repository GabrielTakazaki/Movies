package br.com.veltec.skywalter.service.impl;

import br.com.veltec.skywalter.entities.LetterMetrics;
import br.com.veltec.skywalter.entities.MovieDTO;
import br.com.veltec.skywalter.entities.MovieEntity;
import br.com.veltec.skywalter.model.Movie;
import br.com.veltec.skywalter.repository.MovieRepository;
import br.com.veltec.skywalter.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private static final String REGEX = "([a|e|i|o|u|\\d|\\W])";

    @Override
    public List<MovieEntity> getListMovies() {
        return toMovieEntityList(movieRepository.findAll());
    }

    @Override
    public Object getMovies(Integer id) {
        try {
            return movieToMovieEntity(movieRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return "Id do filme não encontrado";
        }
    }

    @Override
    public MovieEntity postMovies(MovieDTO movies) {
        return movieToMovieEntity(movieRepository.saveAndFlush(movieDTOToMovie(movies)));
    }

    @Override
    public Object putMovie(Integer id, MovieDTO movie) {
        try {
            Movie movieFind = movieRepository.getOne(id);
            movieFind.setUserRating(movie.getUserRating());
            return movieToMovieEntity(movieRepository.saveAndFlush(movieFind));
        } catch (EntityNotFoundException e) {
            return "Id do filme não encontrado";
        }
    }

    @Override
    public List<LetterMetrics> getLetterMetrics() {
        List<Movie> movieList = movieRepository.findAll();
        String allStrings = juntaStrings(movieList);

        Map<String, LetterMetrics> top10 = mapeiaEContaLetras(allStrings);
        List<LetterMetrics> listaTop10 = new ArrayList<>(top10.values());
        Collections.sort(listaTop10);
        return colocaDezItens(listaTop10);
    }

    private List<LetterMetrics> colocaDezItens(List<LetterMetrics> listaTop10) {
        List<LetterMetrics> lista = new ArrayList<>();
        for (int i = 0; i < listaTop10.size() && i < 10; i++) {
            lista.add(listaTop10.get(i));
        }
        return lista;
    }

    private String juntaStrings(List<Movie> movieList) {
        StringBuilder allTitles = new StringBuilder("");
        movieList.forEach(movie -> {
            if (!StringUtils.isBlank(movie.getTitle())) {
                allTitles.append(movie.getTitle().toLowerCase().replaceAll(REGEX, ""));
            }
        });
        return allTitles.toString();
    }

    private Map<String, LetterMetrics> mapeiaEContaLetras(String test) {
        Map<String, LetterMetrics> map = new HashMap<>();
        for (char item : test.toCharArray()) {
            LetterMetrics letterMetrics = map.get(String.valueOf(item));
            if (letterMetrics != null) {
                letterMetrics.setQuantidade(letterMetrics.getQuantidade() + 1);
            } else {
                letterMetrics = new LetterMetrics(item, 1);
            }
            map.put(String.valueOf(item), letterMetrics);
        }
        return map;
    }

    private Movie movieDTOToMovie(MovieDTO movies) {
        Movie movie = new Movie();
        movie.setReleaseData(movies.getReleaseData());
        movie.setSynopsis(movies.getSynopsis());
        movie.setTitle(movies.getTitle());
        movie.setUserRating(movies.getUserRating());
        return movie;
    }

    private MovieEntity movieToMovieEntity(Movie movie) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movie.getId());
        movieEntity.setReleaseData(movie.getReleaseData());
        movieEntity.setSynopsis(movie.getSynopsis());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setUserRating(movie.getUserRating());
        return movieEntity;
    }

    private List<MovieEntity> toMovieEntityList(List<Movie> all) {
        List<MovieEntity> movieEntityList = new ArrayList<>();
        for (Movie movie : all) {
            movieEntityList.add(movieToMovieEntity(movie));
        }
        return movieEntityList;
    }

}
