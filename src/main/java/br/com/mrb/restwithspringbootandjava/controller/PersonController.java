package br.com.mrb.restwithspringbootandjava.controller;

import br.com.mrb.restwithspringbootandjava.exceptions.UnsupportedMathOperationException;
import br.com.mrb.restwithspringbootandjava.math.SimpleMath;
import br.com.mrb.restwithspringbootandjava.model.Greeting;
import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "", method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {
        return services.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
        produces= MediaType.APPLICATION_JSON_VALUE)
    public Person findById(
            @PathVariable(value = "id")String id) {
       return services.findById(id);
    }

    @RequestMapping(method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) {
        return services.create(person);
    }

    @RequestMapping(method=RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person) {
        return services.update(person);
    }


    @RequestMapping(value = "/{id}",
            method=RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") String id) {
        services.delete(id);
    }
}
