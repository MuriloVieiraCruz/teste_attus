package com.murilovieira.testeattus.controller;

import com.murilovieira.testeattus.dto.AddressDetailsCreateDto;
import com.murilovieira.testeattus.dto.AddressDetailsUpdateDto;
import com.murilovieira.testeattus.dto.PersonDetailsCreateDto;
import com.murilovieira.testeattus.dto.PersonDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Address;
import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.service.AddressService;
import com.murilovieira.testeattus.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @Autowired
    @Qualifier("addressServiceImpl")
    private AddressService addressService;

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

    @PostMapping("/address/create")
    public ResponseEntity<Address> savePersonAddress(
            @RequestBody
            @Valid
            AddressDetailsCreateDto addressDetailsCreateDto) {
        Address addressSaved = addressService.save(addressDetailsCreateDto);
        return ResponseEntity.created(URI.create("/person/address/id/" + addressSaved.getId())).build();
    }

    @PutMapping("/address/update/id/{id}")
    public ResponseEntity<Address> updatePersonAddress(
            @PathVariable("id")
            Long id,
            @RequestBody
            AddressDetailsUpdateDto addressDetailsUpdateDto) {
        Address addressUpdated = addressService.update(id, addressDetailsUpdateDto);
        return ResponseEntity.ok().body(addressUpdated);
    }

    @GetMapping("/address/id/{id}")
    public ResponseEntity<Address> findPersonAddressById(
            @PathVariable("id")
            Long id) {
        Address address = addressService.findById(id);
        return ResponseEntity.ok().body(address);
    }

    @GetMapping("/address")
    public ResponseEntity<Page<Address>> listPersonAddressBy(
            @RequestParam("id")
            @NotNull(message = "Person id is required")
            Long id,
            @RequestParam("page")
            Optional<Integer> page) {

        Pageable pagination = null;
        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Address> addresses = addressService.listBy(id, pagination);
        return ResponseEntity.ok().body(addresses);
    }
}
