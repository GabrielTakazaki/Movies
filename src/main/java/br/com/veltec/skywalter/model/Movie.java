package br.com.veltec.skywalter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity(name = "movie")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    @Size(max = 30, message = "{message.title}")
    private String title;
    @Column(name = "release_date")
    private Instant releaseData;
    @Column(name = "synopsis ", columnDefinition = "TEXT")
    private String synopsis;
    @Column(name = "user_rating")
    @Min(value = 0, message = "{message.nota}")
    @Max(value = 10, message = "{message.nota}")
    private int userRating;
}
