package br.com.veltec.skywalter.service.impl;

import br.com.veltec.skywalter.entities.LetterMetrics;
import br.com.veltec.skywalter.entities.MovieDTO;
import br.com.veltec.skywalter.entities.MovieEntity;
import br.com.veltec.skywalter.model.Movie;
import br.com.veltec.skywalter.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MovieServiceImplTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private final Instant data = Instant.now();

    @Test
    public void testaGetListMovies_RetornaListaMovies() {
        List<Movie> lista = new ArrayList<>();
        lista.add(getMovie(1, data, "Teste", "Title", 10));
        lista.add(getMovie(2, data, "Teste", "Title", 1));
        lista.add(getMovie(3, data, "Teste", "Title", 5));
        Mockito.when(movieRepository.findAll()).thenReturn(lista);
        List<MovieEntity> teste = movieService.getListMovies();
        Assert.assertEquals(3, teste.size());
        Assert.assertEquals(10, teste.get(0).getUserRating());
        Assert.assertEquals(1, teste.get(1).getUserRating());
        Assert.assertEquals(5, teste.get(2).getUserRating());
    }

    @Test
    public void testaGetMovies_RetornaUmMovie_QuandoEnviarOId() {
        Movie movie = getMovie(1, data, "Teste", "Title", 10);
        Mockito.when(movieRepository.getOne(1)).thenReturn(movie);
        MovieEntity movieEntity = (MovieEntity) movieService.getMovies(1);
        Assert.assertEquals(10, movieEntity.getUserRating());
        Assert.assertEquals((Integer) 1, movieEntity.getId());
    }

    @Test
    public void testaGetMovies_RetornaErro_QuandoEnviarOIdInexistente() {
        Mockito.when(movieRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        String erro = (String) movieService.getMovies(1);
        Assert.assertEquals("Id do filme não encontrado", erro);
    }

    @Test
    public void testaPostMovies_RetornaUmMovie_QuandoSalvar() {
        Movie movie = getMovie(1, data, "Teste", "Title", 10);
        MovieDTO movieDTO = new MovieDTO("Teste", data, "Title", 10);

        Mockito.when(movieRepository.saveAndFlush(Mockito.any())).thenReturn(movie);

        MovieEntity movieEntity = movieService.postMovies(movieDTO);
        Assert.assertEquals(movie.getUserRating(), movieEntity.getUserRating());
        Assert.assertEquals(movie.getId(), movieEntity.getId());
    }

    @Test
    public void testaPutMovies_RetornaUmMovie_QuandoAlterarRating() {
        Movie movie = getMovie(1, data, "Teste", "Title", 10);
        MovieDTO movieDTO = new MovieDTO("Teste", data, "Title", 5);

        Mockito.when(movieRepository.getOne(1)).thenReturn(movie);
        Mockito.when(movieRepository.saveAndFlush(movie))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        MovieEntity movieEntity = (MovieEntity) movieService.putMovie(1, movieDTO);
        Assert.assertEquals(movie.getUserRating(), movieEntity.getUserRating());
        Assert.assertEquals(movie.getReleaseData(), movieEntity.getReleaseData());
        Assert.assertEquals(movie.getId(), movieEntity.getId());
    }

    @Test
    public void testaPutMovies_RetornaErro_QuandoNaoExistirMovie() {
        Movie movie = getMovie(2, data, "Teste", "Title", 10);
        MovieDTO movieDTO = new MovieDTO("Teste", data, "Title", 5);

        Mockito.when(movieRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        String erro = (String) movieService.putMovie(1, movieDTO);
        Assert.assertEquals("Id do filme não encontrado", erro);
    }

    @Test
    public void testaGetLetterMetrics_RetornaLista() {
        String cincoZSeisTComCaracteresEspeciais = "        zzzzz, -  ttttTT";
        String vogais = "aeiou";
        String consoantes = "zxcvbnmlçkjhgfdsqwrtyp";

        List<Movie> lista = new ArrayList<>();
        lista.add(getMovie(1, data, "Teste", cincoZSeisTComCaracteresEspeciais, 10));
        lista.add(getMovie(2, data, "Teste", vogais, 1));
        lista.add(getMovie(3, data, "Teste", consoantes, 5));

        Mockito.when(movieRepository.findAll()).thenReturn(lista);
        List<LetterMetrics> top10 = movieService.getLetterMetrics();

        Assert.assertEquals(10, top10.size());
        Assert.assertEquals(new Character('t'), top10.get(0).getLetra());
        Assert.assertEquals(7, top10.get(0).getQuantidade());
        Assert.assertEquals(new Character('z'), top10.get(1).getLetra());
        Assert.assertEquals(6, top10.get(1).getQuantidade());
    }

    @Test
    public void testaGetLetterMetrics_RetornaListaComCinco_QuandoTiverCincoConsoantes() {
        String teste = "     aeaea 1234@~ç";
        String vogais = "aeiou";
        String cincoConsoantes = "zxcvb";

        List<Movie> lista = new ArrayList<>();
        lista.add(getMovie(1, data, "Teste", teste, 10));
        lista.add(getMovie(2, data, "Teste", vogais, 1));
        lista.add(getMovie(3, data, "Teste", cincoConsoantes, 5));

        Mockito.when(movieRepository.findAll()).thenReturn(lista);
        List<LetterMetrics> top10 = movieService.getLetterMetrics();

        Assert.assertEquals(5, top10.size());
        Assert.assertEquals(new Character('b'), top10.get(0).getLetra());
        Assert.assertEquals(1, top10.get(0).getQuantidade());
        Assert.assertEquals(new Character('c'), top10.get(1).getLetra());
        Assert.assertEquals(1, top10.get(1).getQuantidade());
    }

    @Test
    public void testaGetLetterMetrics_RetornaListaVaziaComTituloNullEEmpty() {
        String tituloNulo = null;
        String empty = "";

        List<Movie> lista = new ArrayList<>();
        lista.add(getMovie(4, data, "Teste", tituloNulo, 5));
        lista.add(getMovie(5, data, "Teste", empty, 5));

        Mockito.when(movieRepository.findAll()).thenReturn(lista);
        List<LetterMetrics> top10 = movieService.getLetterMetrics();
        Assert.assertEquals(0, top10.size());
    }


    private Movie getMovie(Integer id, Instant data, String synopsis, String title, int rating) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setReleaseData(data);
        movie.setSynopsis(synopsis);
        movie.setTitle(title);
        movie.setUserRating(rating);
        return movie;
    }
}
