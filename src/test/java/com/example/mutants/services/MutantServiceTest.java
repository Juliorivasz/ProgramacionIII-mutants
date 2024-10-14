package com.example.mutants.services;

import com.example.mutants.dto.StatsResponse;
import com.example.mutants.entities.DnaRegister;
import com.example.mutants.repositories.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutantServiceTest {

    @InjectMocks
    private MutantService mutantService;

    @Mock
    private DnaRepository dnaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMutant_WithMutantDna() {
        String[] dna = {
                "ACGT",
                "ACTT",
                "ATGT",
                "TCGT"
        };

        boolean result = mutantService.isMutant(dna);

        assertTrue(result, "El ADN debería ser considerado mutante.");
    }

    @Test
    void testIsMutant_WithNonMutantDna() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };

        boolean result = mutantService.isMutant(dna);

        assertFalse(result, "El ADN no debería ser considerado mutante.");
    }

    @Test
    void testSaveDna_WhenDnaDoesNotExist() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        boolean isMutant = true;

        when(dnaRepository.existsByDna(dna)).thenReturn(false);

        mutantService.saveDna(dna, isMutant);

        verify(dnaRepository, times(1)).save(any(DnaRegister.class));
    }

    @Test
    void testSaveDna_WhenDnaExists() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        boolean isMutant = true;

        when(dnaRepository.existsByDna(dna)).thenReturn(true);

        mutantService.saveDna(dna, isMutant);

        verify(dnaRepository, times(0)).save(any(DnaRegister.class));
    }
}


