package exceptions;

import javax.security.sasl.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String message){
        super(message);
    }
}
