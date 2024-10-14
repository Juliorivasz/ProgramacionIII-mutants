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
}

