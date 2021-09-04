package com.leantech.javatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leantech.javatest.model.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long>{

}
