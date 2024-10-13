package com.example.mutants.controllers;

import com.example.mutants.dto.DnaRequest;
import com.example.mutants.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @PostMapping("/")
    public ResponseEntity<String> isMutant(@RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.isMutant(dnaRequest.getDna());

        // Guardar ADN verificado en la base de datos
        mutantService.saveDna(dnaRequest.getDna(), isMutant);

        if (isMutant) {
            return ResponseEntity.ok("Mutante");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No mutante");
        }
    }
}

