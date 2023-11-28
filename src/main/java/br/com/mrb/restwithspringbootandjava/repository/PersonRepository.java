package br.com.mrb.restwithspringbootandjava.repository;

import br.com.mrb.restwithspringbootandjava.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {}
