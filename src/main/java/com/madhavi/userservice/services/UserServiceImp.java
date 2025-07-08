package com.madhavi.userservice.services;

import com.madhavi.userservice.models.Token;
import com.madhavi.userservice.models.User;
import com.madhavi.userservice.repositories.TokenRepository;
import com.madhavi.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImp(UserRepository userRepository ,
                          PasswordEncoder passwordEncoder,
                          TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        //first encrypt the password using Bcrypt algo before storing into db
        user.setHashedPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("user with email " + email + " not found in the database");

        }
        User user = optionalUser.get();
        if(passwordEncoder.matches(password , user.getHashedPassword())){
            //credentials right need to generate the token
            Token token = createToken(user);
            Token savedToken = tokenRepository.save(token);
            return savedToken;
        }
        return null;
    }

    @Override
    public User ValidateToken(String tokenValue) {
        //return null;
        //check if the token is present in the db, token is not deleted and
        //expiry of the token > current time and deleted should be false
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryDateGreaterThan(
                tokenValue,
                false,
                new Date()
        ); //current time
        if(optionalToken.isEmpty()){
            return null;
            //Invalid token
        }
        Token token = optionalToken.get();
        User user = token.getUser();
        return user;
    }

    @Override
    public void logout(String value) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryDateGreaterThan(
                value,
                false ,
                new Date()
        );
        if(optionalToken.isEmpty()){
            //throw token invalid Exception
            return;
        }
        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);

    }
    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphabetic(128));
        //Expiry time of token is 30 days from now
        LocalDate today = LocalDate.now();
        LocalDate thirdyDaysAfterCurrentTime = today.plusDays(30);
        Date expirtAt = Date.from(thirdyDaysAfterCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryDate(expirtAt);
        return token;
    }
}
