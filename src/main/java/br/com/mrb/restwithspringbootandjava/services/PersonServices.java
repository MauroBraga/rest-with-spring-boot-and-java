package br.com.mrb.restwithspringbootandjava.services;

import br.com.mrb.restwithspringbootandjava.controller.PersonController;
import br.com.mrb.restwithspringbootandjava.exceptions.RequiredObjectIsNullException;
import br.com.mrb.restwithspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.mrb.restwithspringbootandjava.mapper.DozerMapper;
import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.repository.PersonRepository;
import br.com.mrb.restwithspringbootandjava.vo.v1.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;


    private Logger logger = Logger.getLogger(PersonServices.class.getName());


    public List<PersonVO> findAll() {
        logger.info("Finding all people!");

        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO findById(Long id){
        logger.info("Find one Person");
        var person = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(person, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO personVO) {

        if (personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");
        var person =DozerMapper.parseObject(personVO, Person.class);
        repository.save(person);
        var vo = DozerMapper.parseObject(person, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }



    public PersonVO update(PersonVO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
      logger.info("Deleting one person!");
        var deletePerson = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
       repository.delete(deletePerson);
    }



}
