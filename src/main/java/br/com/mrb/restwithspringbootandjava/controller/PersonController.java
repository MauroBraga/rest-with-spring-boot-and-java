package br.com.mrb.restwithspringbootandjava.controller;

import br.com.mrb.restwithspringbootandjava.exceptions.UnsupportedMathOperationException;
import br.com.mrb.restwithspringbootandjava.math.SimpleMath;
import br.com.mrb.restwithspringbootandjava.model.Greeting;
import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.services.PersonServices;
import br.com.mrb.restwithspringbootandjava.vo.v1.PersonVO;
import br.com.mrb.restwithspringbootandjava.vo.v2.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.mrb.restwithspringbootandjava.converters.NumberConverter.convertToDouble;
import static br.com.mrb.restwithspringbootandjava.converters.NumberConverter.isNumeric;

@RestController
@RequestMapping("/person")
public class PersonController {


    @Autowired
    PersonServices services;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVO> findAll() {
        return services.findAll();
    }

    @GetMapping(value = "/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById(
            @PathVariable(value = "id")Long id) {
       return services.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO create(@RequestBody PersonVO person) {
        return services.create(person);
    }

    @PostMapping(value = "/v2",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
        return services.createV2(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO update(@RequestBody PersonVO person) {
        return services.update(person);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
