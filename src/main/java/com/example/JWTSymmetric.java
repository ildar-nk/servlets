package com.example;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.example.security.Roles;

import javax.management.relation.Role;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

public class JWTSymmetric {

    public static void main(String[] args) throws IOException, JOSEException {

        final Path path = Paths.get("shared-secret.txt");
        {
            final SecureRandom random = new SecureRandom();
            final byte[] secretBytes = new byte[32];
            random.nextBytes(secretBytes);
            final String secretString = Base64.getUrlEncoder().withoutPadding().encodeToString(secretBytes);
            if (Files.notExists(path)) {
                Files.writeString(path, secretString);

            }

        }
        {
            final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
            final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("vasya")
                    .claim("roles", new String[]{Roles.USERS_VIEW_ALL})
                    .build();
            final SignedJWT jwt = new SignedJWT(header, claimsSet);

            final String encodedSecret = Files.readString(path);
            final byte[] secretBytes = Base64.getUrlDecoder().decode(encodedSecret);

            final MACSigner signer = new MACSigner(secretBytes);

            jwt.sign(signer);
            final String token = jwt.serialize();
            System.out.println("token = " + token);
        }
    }
}