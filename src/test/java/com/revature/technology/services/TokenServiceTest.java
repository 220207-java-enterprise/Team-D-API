package com.revature.technology.services;

import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.models.User;
import com.revature.technology.models.UserRole;
import com.revature.technology.repositories.UserRepository;
import com.revature.technology.util.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenServiceTest {

    private TokenService sut;
    private JwtConfig mockJwtConfig;

    @BeforeEach
    public void setup(){
        mockJwtConfig = mock(JwtConfig.class);
        sut = new TokenService(mockJwtConfig);
    }

    @Test
    public void test_generateToken_givenValidPrincipal(){
        Principal subject = new Principal(new User("1","username", "email@email.com", "first", "last", "p4$$WORD", true,
                new UserRole()));
        SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
        int expiration = 5 * 60 * 60 * 1000; // # of ms in 5 hours
        byte[] saltyBytes = DatatypeConverter.parseBase64Binary("salt");

        Key signingKey = new SecretKeySpec(saltyBytes, sigAlg.getJcaName());

        when(mockJwtConfig.getSigAlg()).thenReturn(sigAlg);
        when(mockJwtConfig.getExpiration()).thenReturn(expiration);
        when(mockJwtConfig.getSigningKey()).thenReturn(signingKey);

        String token = sut.generateToken(subject);

        Assertions.assertNotNull(token);
    }
}
