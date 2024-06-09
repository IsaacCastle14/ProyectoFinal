/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Model;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Marco
 */
public class EmailSender {

    private static String host = "mareligen30@gmail.com";
    private static String key = "rznrokhvykyaojuz";
    String subject;
    public String emailTo;
    private String content;
    private Properties properties = new Properties();
    private Session session;
    private MimeMessage email;

    public EmailSender() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", key);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");

    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public void createEmail() {
        session = Session.getDefaultInstance(properties);
        try {
            email = new MimeMessage(session);
            email.setFrom(new InternetAddress(host));
            email.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            email.setSubject(subject);
            email.setText(content, "ISO-8859-1", "html");
        } catch (AddressException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail() {
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(host, key);
            transport.sendMessage(email, email.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSubjectNewUser() {
        this.subject = "Account Confirmation";
    }

    public void setContent(String name, String user, String phone, String carne, String emailPr) {
        this.content
                = "¡Bienvenido(a) a [Nombre de la Universidad]!\n"
                + "<br>Estimado(a)" + name + "<br>\n"
                + "<br>Nos complace darte la bienvenida a nuestro sistema de gestión universitaria. Tu registro ha sido completado exitosamente y ahora tienes acceso a una variedad de recursos y herramientas que te ayudarán en tu trayectoria académica.<br>\n"
                + "\n"
                + "Account details:<br>\n"
                + "\n"
                + "- User: " + user + "<br>\n"
                + "- Email: " + emailPr + "<br>\n"
                + "- Phone: " + phone + "<br>\n"
                + "- Carne: " + carne + "<br>\n"
                + "\n"
                + "A partir de ahora, podrás: <br>\n"
                + "\n"
                + "<br>Consultar y actualizar tu información personal.<br>\n"
                + "\n"
                + "<br>Inscribirte en cursos y actividades.<br>\n"
                + "\n"
                + "<br>Acceder a tu historial académico.<br>\n"
                + "\n"
                + "<br>Comunicarse con profesores y compañeros.<br>\n"
                + "\n"
                + "<br>Si tienes alguna pregunta o necesitas asistencia, no dudes en contactarnos a través de nuestro soporte técnico o visitar el centro de ayuda en el sistema.<br>\n"
                + "\n"
                + "<br>Te deseamos mucho éxito en tus estudios y esperamos que aproveches al máximo todas las oportunidades que ofrece nuestra universidad.<br>\n"
                + "\n"
                + "<br>Attentively,<br>\n"
                + "Equipo de Soporte Técnico\n"
                + "[Nombre de la Universidad]";
    }
}
