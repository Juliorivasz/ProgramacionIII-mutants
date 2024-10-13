package com.example.mutants.services;

import com.example.mutants.dto.StatsResponse;
import com.example.mutants.entities.DnaRegister;
import com.example.mutants.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantService {

    @Autowired
    private DnaRepository dnaRepository;

    public boolean isMutant(String[] dna) {
        int size = dna.length;

        // Comprobación horizontal y vertical
        for (int i = 0; i < size; i++) {
            String row = dna[i];
            StringBuilder col = new StringBuilder();

            for (int j = 0; j < size; j++) {
                // Comprobación vertical
                col.append(dna[j].charAt(i));
            }

            if (checkSequence(row) || checkSequence(col.toString())) {
                return true;
            }
        }

        // Comprobación de diagonales
        return checkDiagonals(dna);
    }

    private boolean checkSequence(String sequence) {
        int count = 1;
        char lastChar = sequence.charAt(0);

        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == lastChar) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                lastChar = sequence.charAt(i);
                count = 1;
            }
        }
        return false;
    }

    private boolean checkDiagonals(String[] dna) {
        int size = dna.length;

        // Comprobación diagonal de izquierda a derecha
        for (int i = 0; i < size - 3; i++) {
            for (int j = 0; j < size - 3; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j + 1) &&
                        dna[i].charAt(j) == dna[i + 2].charAt(j + 2) &&
                        dna[i].charAt(j) == dna[i + 3].charAt(j + 3)) {
                    return true;
                }
            }
        }

        // Comprobación diagonal de derecha a izquierda
        for (int i = 0; i < size - 3; i++) {
            for (int j = 3; j < size; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j - 1) &&
                        dna[i].charAt(j) == dna[i + 2].charAt(j - 2) &&
                        dna[i].charAt(j) == dna[i + 3].charAt(j - 3)) {
                    return true;
                }
            }
        }

        return false;
    }


    public StatsResponse getStats() {
        long countMutantDna = dnaRepository.countByIsMutant(true);
        long countHumanDna = dnaRepository.countByIsMutant(false);
        double ratio = (countHumanDna > 0) ? (double) countMutantDna / countHumanDna : 0;

        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }

    public void saveDna(String[] dna, boolean isMutant) {
        if (!dnaRepository.existsByDna(dna)) {
            DnaRegister register = new DnaRegister();
            register.setDna(dna);
            register.setMutant(isMutant);
            dnaRepository.save(register);
        }
    }
}

