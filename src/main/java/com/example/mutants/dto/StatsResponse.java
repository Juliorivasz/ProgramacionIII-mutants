package com.example.mutants.dto;

public class StatsResponse {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

    public StatsResponse(long countMutantDna, long countHumanDna, double ratio) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
        this.ratio = ratio;
    }
}
