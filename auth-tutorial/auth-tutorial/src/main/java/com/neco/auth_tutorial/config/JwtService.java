package com.neco.auth_tutorial.config;

import org.springframework.stereotype.Service;

/*To manipulate Jwt tokens, like generating one, extracting information from the token, we need to add new dependencies to pom.xml.
JWT stands for Json Web Token. JWT consists of three parts which are Header, Payload and Signature.
#Header consists of two parts which are the type of the token(Jwt), and the algorithm for encoding.
#Payload part contains claims. Claims are statements about an entity and typically represent user or additional info.
There are 3 types of claims which are registered, public and private.
#Signature part is used to verify of the center of the JWT. Verifies claims and checks if the message is changed along the way.
You can find an example representation of JWT on JWT-representation.png. */

@Service
public class JwtService{


    public String extractUsername(String jwtToken) {

        return null;
    }
}
