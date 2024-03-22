package com.app.cires_tech.Repository;

import com.app.cires_tech.Model.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query(value = """
            SELECT t FROM Token t INNER JOIN Person p
            ON t.person.id = p.id
            WHERE p.id = :id AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokenByMember(UUID id);

    Optional<Token> findByToken(String token);
}
