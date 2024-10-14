package com.example.mutants.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsResponse {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

    public StatsResponse(long countMutantDna, long countHumanDna) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
        this.ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;
    }
}
