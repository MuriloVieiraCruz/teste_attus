package com.murilovieira.testeattus.service.impl;

import com.murilovieira.testeattus.dto.AddressDetailsCreateDto;
import com.murilovieira.testeattus.dto.AddressDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Address;
import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.entity.enums.AddressType;
import com.murilovieira.testeattus.repository.AddressRepository;
import com.murilovieira.testeattus.repository.PersonRepository;
import com.murilovieira.testeattus.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Address save(AddressDetailsCreateDto addressDetailsCreateDto) {

        Person personFound = findPerson(addressDetailsCreateDto.personId());

        Address newAddress = Address.builder()
                .street(addressDetailsCreateDto.street())
                .cep(addressDetailsCreateDto.cep())
                .number(addressDetailsCreateDto.number())
                .city(addressDetailsCreateDto.city())
                .state(addressDetailsCreateDto.state())
                .addressType(addressDetailsCreateDto.addressType())
                .person(personFound)
                .build();

        changeOtherAddressType(personFound.getId(), newAddress.getAddressType());

        return addressRepository.save(newAddress);
    }

    @Override
    @Transactional
    public Address update(Long addressId, AddressDetailsUpdateDto addressDetailsUpdateDto) {

        Address addressSaved = this.findById(addressId);
        findPerson(addressDetailsUpdateDto.personId());

        updateField(addressDetailsUpdateDto.street(), addressSaved::setStreet);
        updateField(addressDetailsUpdateDto.cep(), addressSaved::setCep);
        updateField(addressDetailsUpdateDto.number(), addressSaved::setNumber);
        updateField(addressDetailsUpdateDto.city(), addressSaved::setCity);
        updateField(addressDetailsUpdateDto.state(), addressSaved::setState);

        addressDetailsUpdateDto.addressType().ifPresent(type -> {
            changeOtherAddressType(addressId, type);
            addressSaved.setAddressType(type);
        });

        return addressRepository.save(addressSaved);
    }

    @Override
    public Address findById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("Address not found"));
    }

    @Override
    public Page<Address> listBy(Long personId, Pageable pagination) {
        this.findById(personId);
        return addressRepository.listBy(personId, pagination);
    }

    private void changeOtherAddressType(Long personId, AddressType addressType) {
        if (addressType.equals(AddressType.PRIMARY)) {
            Address primaryAddress = addressRepository.findPrimaryAddressByPersonId(personId, addressType);
            if (primaryAddress != null) {
                primaryAddress.setAddressType(AddressType.SECONDARY);
                addressRepository.save(primaryAddress);
            }
        }
    }

    private Person findPerson(Long personId) {
        return personRepository.findById(personId).orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

    private <T> void updateField(Optional<T> value, Consumer<T> setter) {
        value.ifPresent(setter);
    }
}
