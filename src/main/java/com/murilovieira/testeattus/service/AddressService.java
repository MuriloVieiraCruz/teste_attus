package com.murilovieira.testeattus.service;


import com.murilovieira.testeattus.dto.AddressDetailsCreateDto;
import com.murilovieira.testeattus.dto.AddressDetailsUpdateDto;
import com.murilovieira.testeattus.dto.PersonDetailsUpdateDto;
import com.murilovieira.testeattus.entity.Address;
import com.murilovieira.testeattus.entity.enums.AddressType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    public Address save(AddressDetailsCreateDto addressDetailsCreateDto);

    public Address update(Long addressId, AddressDetailsUpdateDto addressDetailsUpdateDto);

    public Address findById(Long addressId);

    public Page<Address> listBy(Long personId,Pageable pagination);
}