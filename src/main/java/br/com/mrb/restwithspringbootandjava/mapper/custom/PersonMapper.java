package br.com.mrb.restwithspringbootandjava.mapper.custom;

import java.util.Date;

import br.com.mrb.restwithspringbootandjava.model.Person;
import br.com.mrb.restwithspringbootandjava.vo.v2.PersonVOV2;
import org.springframework.stereotype.Service;



@Service
public class PersonMapper {
	
	public  static  PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}
	
	
	public static Person convertVoTOEntity(PersonVOV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		//vo.setBirthDay(new Date());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}

}
