package com.example.dev.qa.spgproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.qa.spgproj.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}