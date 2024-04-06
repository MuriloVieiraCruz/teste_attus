package com.murilovieira.testeattus.service.impl;

import com.murilovieira.testeattus.dto.PersonDetailsCreateDto;
import com.murilovieira.testeattus.dto.PersonDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.repository.PersonRepository;
import com.murilovieira.testeattus.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Person save(PersonDetailsCreateDto personDetailsCreateDto) {
        Person newPerson = Person.builder()
                .name(personDetailsCreateDto.name())
                .birthDate(personDetailsCreateDto.birthDate())
                .build();

        return personRepository.save(newPerson);
    }

    @Override
    @Transactional
    public Person update(Long personId, PersonDetailsUpdateDto personDetailsCreateDto) {
        Person person = this.findById(personId);

        if (personDetailsCreateDto.name().isPresent()) {
            person.setName(personDetailsCreateDto.name().get());
        }

        if (personDetailsCreateDto.birthDate().isPresent()) {
            person.setBirthDate(personDetailsCreateDto.birthDate().get());
        }

        return personRepository.save(person);
    }

    @Override
    public Person findById(Long personId) {
        return personRepository.findById(personId).orElseThrow(() -> new IllegalArgumentException("The person related to the address does not exist"));
    }

    @Override
    public Page<Person> listBy(Pageable pagination) {
        return personRepository.listBy(pagination);
    }
}
