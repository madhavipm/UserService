package com.madhavi.userservice.repositories;

import com.madhavi.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Token save(Token token);

    Optional<Token> findByValue(String value);
    Optional<Token> findByValueAndDeletedAndExpiryDateGreaterThan(String value,
                                                                  boolean deleted,
                                                                  Date currentTime);

}
