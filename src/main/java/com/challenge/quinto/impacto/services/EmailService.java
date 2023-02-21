package com.challenge.quinto.impacto.services;

public interface EmailService {

    void sendEmail(String to, String body);
    void sendEmailSimple(String to, String subject, String body);

    String buildEmail(String name, String link);

}
