package com.murilovieira.testeattus.service;


import com.murilovieira.testeattus.dto.PersonDetailsCreateDto;
import com.murilovieira.testeattus.dto.PersonDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    public Person save(PersonDetailsCreateDto personDetailsCreateDto);

    public Person update(Long personId, PersonDetailsUpdateDto personDetailsUpdateDto);

    public Person findById(Long personId);

    public Page<Person> listBy(Pageable pagination);
}