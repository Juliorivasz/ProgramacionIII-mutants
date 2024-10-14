package com.example.mutants.services;

import com.example.mutants.dto.StatsResponse;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private long countMutantDna = 0;
    private long countHumanDna = 0;

    public void incrementMutantCount() {
        countMutantDna++;
    }

    public void incrementHumanCount() {
        countHumanDna++;
    }

    public StatsResponse getStats() {
        return new StatsResponse(countMutantDna, countHumanDna);
    }
}

