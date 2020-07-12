package br.com.veltec.skywalter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LetterMetrics implements Comparable<LetterMetrics> {
    private Character letra;
    private int quantidade;

    @Override
    public int compareTo(LetterMetrics o) {
        return o.quantidade - this.quantidade;
    }
}
