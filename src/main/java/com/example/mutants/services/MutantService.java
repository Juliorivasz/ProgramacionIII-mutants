package com.example.mutants.services;

import com.example.mutants.entities.DnaRegister;
import com.example.mutants.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantService {

    @Autowired
    private DnaRepository dnaRepository;

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];

        // convierte la matriz en caracteres
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // horizontal y diagonal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 3; j++) {
                if (matrix[i][j] == matrix[i][j + 1] && matrix[i][j] == matrix[i][j + 2] && matrix[i][j] == matrix[i][j + 3]) {
                    sequencesFound++;
                }
                if (matrix[j][i] == matrix[j + 1][i] && matrix[j][i] == matrix[j + 2][i] && matrix[j][i] == matrix[j + 3][i]) {
                    sequencesFound++;
                }
            }
        }

        // diagonal de arriba-izquierda hacia abajo-derecha
        for (int i = 0; i < n - 3; i++) {
            for (int j = 0; j < n - 3; j++) {
                if (matrix[i][j] == matrix[i + 1][j + 1] && matrix[i][j] == matrix[i + 2][j + 2] && matrix[i][j] == matrix[i + 3][j + 3]) {
                    sequencesFound++;
                }
            }
        }

        // diagonal de abajo-izquierda hacia arriba-derecha
        for (int i = 3; i < n; i++) {
            for (int j = 0; j < n - 3; j++) {
                if (matrix[i][j] == matrix[i - 1][j + 1] && matrix[i][j] == matrix[i - 2][j + 2] && matrix[i][j] == matrix[i - 3][j + 3]) {
                    sequencesFound++;
                }
            }
        }

        return sequencesFound > 1;
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



