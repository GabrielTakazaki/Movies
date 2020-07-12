package br.com.veltec.skywalter.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Set;

@RunWith(MockitoJUnitRunner.Silent.class)
public class movieDTOValidatorTest {
    private final String maisQueTrintaCaracteres = "AAAAABBBBBBBBcccccccczzzzzaaaaaqwe";
    private final Instant data = Instant.now();
    private Validator validator;

    @Before
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testTitleMessage_QuandoForCerto() {
        MovieDTO movieDTO = new MovieDTO("Titulo", data, "teste", 1);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals(0,
                constraintViolations.size());
    }

    @Test
    public void testTitleMessage_QuandoForConterCaracterEspecial() {
        MovieDTO movieDTO = new MovieDTO("Título", data, "teste", 1);
        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals("Não pode haver caracteres especiais.",
                constraintViolations.iterator().next().getMessage());
        Assert.assertEquals(1,
                constraintViolations.size());
    }

    @Test
    public void testTitleMessage_QuandoForMaiorQueTrinta() {
        MovieDTO movieDTO = new MovieDTO(maisQueTrintaCaracteres, data, "teste", 1);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals("Tamanho do título deve ter no máximo 30 characteres.",
                constraintViolations.iterator().next().getMessage());
        Assert.assertEquals(1,
                constraintViolations.size());
    }

    @Test
    public void testNota_QuandoForMaiorQueDez() {
        MovieDTO movieDTO = new MovieDTO("title", data, "teste", 11);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals("A nota só é aceito números entre 0 a 10.",
                constraintViolations.iterator().next().getMessage());
        Assert.assertEquals(1,
                constraintViolations.size());
    }

    @Test
    public void testNota_QuandoForMenorQueZero() {
        MovieDTO movieDTO = new MovieDTO("title", data, "teste", -1);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals("A nota só é aceito números entre 0 a 10.",
                constraintViolations.iterator().next().getMessage());
        Assert.assertEquals(1,
                constraintViolations.size());
    }

    @Test
    public void testNota_QuandoForDez() {
        MovieDTO movieDTO = new MovieDTO("title", data, "teste", 10);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals(0,
                constraintViolations.size());
    }

    @Test
    public void testNota_QuandoForZero() {
        MovieDTO movieDTO = new MovieDTO("title", data, "teste", 0);

        Set<ConstraintViolation<MovieDTO>> constraintViolations = validator
                .validate(movieDTO);
        Assert.assertEquals(0,
                constraintViolations.size());
    }


}
