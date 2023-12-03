package br.com.mrb.restwithspringbootandjava.services;

import br.com.mrb.restwithspringbootandjava.controller.BookController;
import br.com.mrb.restwithspringbootandjava.controller.PersonController;
import br.com.mrb.restwithspringbootandjava.exceptions.RequiredObjectIsNullException;
import br.com.mrb.restwithspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.mrb.restwithspringbootandjava.mapper.DozerMapper;
import br.com.mrb.restwithspringbootandjava.model.Book;
import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.repository.BookRepository;
import br.com.mrb.restwithspringbootandjava.repository.PersonRepository;
import br.com.mrb.restwithspringbootandjava.vo.v1.BookVO;
import br.com.mrb.restwithspringbootandjava.vo.v1.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;


    private Logger logger = Logger.getLogger(BookServices.class.getName());


    public List<BookVO> findAll() {
        logger.info("Finding all books!");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;
    }

    public BookVO findById(Long id){
        logger.info("Find one Book");
        var book = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(book, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO bookVO) {
        if (bookVO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one person!");
        var book =DozerMapper.parseObject(bookVO, Book.class);
        repository.save(book);
        var vo = DozerMapper.parseObject(book, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }



    public BookVO update(BookVO bookVO) {

        if (bookVO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(bookVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(bookVO.getAuthor());
        entity.setLaunchDate(bookVO.getLaunchDate());
        entity.setPrice(bookVO.getPrice());
        entity.setTitle(bookVO.getTitle());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
      logger.info("Deleting one book!");
        var deleteBook = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
       repository.delete(deleteBook);
    }



}
