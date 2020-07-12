package br.com.veltec.skywalter.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class MovieDTO {
    @Size(max = 30, message = "{message.title}")
    @Pattern(regexp = "(\\w|\\s)+", message = "{message.regex}")
    private String title;
    private Instant releaseData;
    private String synopsis;
    @Min(value = 0, message = "{message.nota}")
    @Max(value = 10, message = "{message.nota}")
    private int userRating;
}
