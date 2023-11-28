package br.com.mrb.restwithspringbootandjava.services;

import br.com.mrb.restwithspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.mrb.restwithspringbootandjava.mapper.DozerMapper;
import br.com.mrb.restwithspringbootandjava.mapper.custom.PersonMapper;
import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.repository.PersonRepository;
import br.com.mrb.restwithspringbootandjava.vo.v1.PersonVO;
import br.com.mrb.restwithspringbootandjava.vo.v2.PersonVOV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;


    private Logger logger = Logger.getLogger(PersonServices.class.getName());


    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        var listPerson = this.repository.findAll();
        return DozerMapper.parseListObjects(listPerson, PersonVO.class);
    }

    public PersonVO findById(Long id){
        logger.info("Find one Person");
        var person = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(person, PersonVO.class);
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person!");
        var person =DozerMapper.parseObject(personVO, Person.class);
        repository.save(person);
        return DozerMapper.parseObject(person, PersonVO.class);
    }

    public PersonVOV2 createV2(PersonVOV2 personVO) {
        logger.info("Creating one person with V2!");
        var person = PersonMapper.convertVoTOEntity(personVO);
        repository.save(person);
        return PersonMapper.convertEntityToVo(person);
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");
        var updatePerson = this.repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        updatePerson.setFirstName(person.getFirstName());
        updatePerson.setLastName(person.getLastName());
        updatePerson.setAddress(person.getAddress());
        updatePerson.setGender(person.getGender());

        repository.save(updatePerson);

        return DozerMapper.parseObject(updatePerson, PersonVO.class);
    }

    public void delete(Long id) {
      logger.info("Deleting one person!");
        var deletePerson = this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
       repository.delete(deletePerson);
    }



}
