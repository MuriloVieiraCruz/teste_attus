package com.murilovieira.testeattus.controller;

import com.murilovieira.testeattus.dto.PersonDetailsCreateDto;
import com.murilovieira.testeattus.dto.PersonDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    @Qualifier("personServiceImpl")
    private PersonService personService;



    @PostMapping("/create")
    public ResponseEntity<Person> save(
            @RequestBody
            @Valid
            PersonDetailsCreateDto personDetailsCreateDto) {
        Person personSaved = personService.save(personDetailsCreateDto);
        return ResponseEntity.created(URI.create("/person/id/" + personSaved.getId())).build();
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<Person> update(
            @PathVariable("id")
            Long id,
            @RequestBody
            PersonDetailsUpdateDto personDetailsUpdateDto) {
        Person personUpdated = personService.update(id, personDetailsUpdateDto);
        return ResponseEntity.ok().body(personUpdated);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Person> findById(
            @PathVariable("id")
            Long id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(person);
    }

    @GetMapping
    public ResponseEntity<Page<Person>> listBy(
            @RequestParam("page")
            Optional<Integer> page) {

        Pageable pagination = null;
        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Person> persons = personService.listBy(pagination);
        return ResponseEntity.ok().body(persons);
    }
}
