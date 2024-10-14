package com.example.mutants.controllers;

import com.example.mutants.dto.DnaRequest;
import com.example.mutants.dto.StatsResponse;
import com.example.mutants.services.MutantService;
import com.example.mutants.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private MutantService mutantService;
    @Autowired
    private StatsService statsService;

    @PostMapping("/")
    public ResponseEntity<String> isMutant(@RequestBody DnaRequest dnaRequest) {

        if (!isValidDna(dnaRequest.getDna())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EL ADN INGRESADO NO ES VALIDO, DEBE TENER SOLO 'A,C,G,T' ");
        }else if (!isDnaNxN(dnaRequest.getDna())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ADN INGRESADO NO ES VALIDO, DEBE SER NXN");
        }

        boolean isMutant = mutantService.isMutant(dnaRequest.getDna());

        // Guardar ADN verificado en la base de datos
        mutantService.saveDna(dnaRequest.getDna(), isMutant);

        if (isMutant) {
            statsService.incrementMutantCount();
            return ResponseEntity.ok("Mutante");
        } else {
            statsService.incrementHumanCount();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No mutante");
        }
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        return statsService.getStats();
    }

    private boolean isValidDna(String[] dna) {
        String validChars = "ATCG";
        for (String row : dna) {
            for (char base : row.toCharArray()) {
                if (validChars.indexOf(base) == -1) {
                    return false; // Si encuentra una letra que no es A, T, C o G, retorna falso
                }
            }
        }

        return true;
    }

    private boolean isDnaNxN (String[] dna) {
        int n = dna.length;
        for (String row : dna) {
            if (row.length() != n) {
                return false; // No es una matriz NxN
            }
        }
        return true;
    }
}

