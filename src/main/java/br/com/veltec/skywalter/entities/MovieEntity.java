package br.com.veltec.skywalter.entities;

import lombok.Data;

import java.time.Instant;

@Data
public class MovieEntity {
    private Integer id;
    private String title;
    private Instant releaseData;
    private String synopsis;
    private int userRating;
}
