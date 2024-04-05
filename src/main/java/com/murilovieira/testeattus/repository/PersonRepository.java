package com.murilovieira.testeattus.repository;

import com.murilovieira.testeattus.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value =
            "SELECT p "
                    + "FROM Person p ",
            countQuery = "SELECT p "
                    + "FROM Person p ")
    public Page<Person> listBy(Pageable pagination);
}
