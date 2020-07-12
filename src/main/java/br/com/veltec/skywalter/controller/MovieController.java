package br.com.veltec.skywalter.controller;

import br.com.veltec.skywalter.entities.MovieDTO;
import br.com.veltec.skywalter.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final Validator validator;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(movieService.getListMovies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(movieService.getMovies(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody MovieDTO movie) {
        return new ResponseEntity<>(movieService.postMovies(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateById(@Valid @PathVariable Integer id, @Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.putMovie(id, movieDTO), HttpStatus.OK);
    }

    @GetMapping("/letter_metrics_top10")
    public ResponseEntity<Object> letterMetrics() {
        return new ResponseEntity<>(movieService.getLetterMetrics(), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationError(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getBindingResult().getFieldErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>("Erro interno do sistema", HttpStatus.BAD_REQUEST);
    }
}
