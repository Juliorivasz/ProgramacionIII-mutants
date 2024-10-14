package com.example.mutants.controllers;

import com.example.mutants.dto.DnaRequest;
import com.example.mutants.services.MutantService;
import com.example.mutants.services.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MutantControllerTest {

    @InjectMocks
    private MutantController mutantController;

    @Mock
    private MutantService mutantService;

    @Mock
    private StatsService statsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMutant_withInvalidDnaCharacters() {
        String[] invalidDna = {"ATGC", "CAGT", "TTAT", "AGAZ"}; // 'Z' es un carácter inválido
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(invalidDna);

        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("EL ADN INGRESADO NO ES VALIDO, DEBE TENER SOLO 'A,C,G,T' ", response.getBody());
    }

    @Test
    void testIsMutant_withInvalidDnaSize() {
        String[] invalidDna = {"ATGC", "CAGT", "TTAT"}; // Tamaño no válido (menos de 4 filas)
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(invalidDna);

        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El ADN INGRESADO NO ES VALIDO, DEBE SER NXN", response.getBody());
    }

    @Test
    void testIsMutant_withValidMutantDna() {
        String[] validMutantDna = {"AAAA", "AAAA", "AAAA", "AAAA"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(validMutantDna);

        when(mutantService.isMutant(validMutantDna)).thenReturn(true);

        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mutante", response.getBody());
        verify(mutantService, times(1)).isMutant(validMutantDna);
        verify(statsService, times(1)).incrementMutantCount();
    }

    @Test
    void testIsMutant_withValidNonMutantDna() {
        String[] validNonMutantDna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(validNonMutantDna);

        when(mutantService.isMutant(validNonMutantDna)).thenReturn(false);

        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No mutante", response.getBody());
        verify(mutantService, times(1)).isMutant(validNonMutantDna);
        verify(statsService, times(1)).incrementHumanCount();
    }
}






