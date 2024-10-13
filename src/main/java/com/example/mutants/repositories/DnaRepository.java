package com.example.mutants.repositories;

import com.example.mutants.entities.DnaRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaRepository extends JpaRepository<DnaRegister, Long> {
    long countByIsMutant(boolean isMutant);
    boolean existsByDna(String[] dna);
}

