package com.murilovieira.testeattus.service.impl;

import com.murilovieira.testeattus.dto.AddressDetailsCreateDto;
import com.murilovieira.testeattus.dto.AddressDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Address;
import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.entity.enums.AddressType;
import com.murilovieira.testeattus.repository.AddressRepository;
import com.murilovieira.testeattus.repository.PersonRepository;
import com.murilovieira.testeattus.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

    @Override
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

        Address addressSaved = addressRepository.save(newAddress);
        changeOtherAddressType(addressSaved.getId(), addressSaved.getAddressType());

        return addressSaved;
    }

    @Override
    public Address update(Long addressId, AddressDetailsUpdateDto addressDetailsUpdateDto) {

        Address addressSaved = this.findById(addressId);
        findPerson(addressDetailsUpdateDto.personId());

        if (addressDetailsUpdateDto.street().isPresent()) {
            addressSaved.setStreet(addressDetailsUpdateDto.street().get());
        }

        if (addressDetailsUpdateDto.cep().isPresent()) {
            addressSaved.setCep(addressDetailsUpdateDto.cep().get());
        }

        if (addressDetailsUpdateDto.number().isPresent()) {
            addressSaved.setNumber(addressDetailsUpdateDto.number().get());
        }

        if (addressDetailsUpdateDto.city().isPresent()) {
            addressSaved.setCity(addressDetailsUpdateDto.city().get());
        }

        if (addressDetailsUpdateDto.state().isPresent()) {
            addressSaved.setState(addressDetailsUpdateDto.state().get());
        }

        if (addressDetailsUpdateDto.addressType().isPresent()) {
            changeOtherAddressType(addressId, addressDetailsUpdateDto.addressType().get());
            addressSaved.setAddressType(addressDetailsUpdateDto.addressType().get());
        }

        return addressRepository.save(addressSaved);
    }

    @Override
    public Address updateAddressType(Long addressId, AddressType addressType) {
        Address addressSaved = this.findById(addressId);
        addressSaved.setAddressType(addressType);
        changeOtherAddressType(addressId, addressType);
        return addressSaved;
    }

    @Override
    public Address findById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
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
        return personRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found"));
    }
}
