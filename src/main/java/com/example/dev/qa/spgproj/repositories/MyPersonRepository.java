package com.example.dev.qa.spgproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.qa.spgproj.models.MyPerson;

@Repository
public interface MyPersonRepository extends JpaRepository<MyPerson, Integer> {
}