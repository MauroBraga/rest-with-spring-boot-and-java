package br.com.mrb.restwithspringbootandjava.repository;

import br.com.mrb.restwithspringbootandjava.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {}
