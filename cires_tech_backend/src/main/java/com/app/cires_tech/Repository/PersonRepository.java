package com.app.cires_tech.Repository;

import com.app.cires_tech.Model.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsername(String userName);

    @Query("SELECT p FROM Person p ORDER BY p.country ASC")
    List<Person> findLimitedUsersOrderByCountryAsc(@Param("limit") Integer limit);

}
