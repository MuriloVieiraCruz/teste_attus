package com.murilovieira.testeattus.repository;

import com.murilovieira.testeattus.entity.Address;
import com.murilovieira.testeattus.entity.enums.AddressType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value =
            "SELECT a "
                    + "FROM Address a "
                    + "WHERE a.person.id = :personId ",
            countQuery = "SELECT a "
                    + "FROM Address a "
                    + "WHERE a.person.id = :personId ")
    public Page<Address> listBy(Long personId, Pageable pagination);

    @Query(value = "SELECT a FROM Address a WHERE a.person.id = :personId AND a.addressType = :addressType")
    public Address findPrimaryAddressByPersonId(Long personId, AddressType addressType);
}
