package br.com.note.mail.config;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class EmailServerAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("gugaariel@gmail.com",
                "suasenha123");
    }

}
