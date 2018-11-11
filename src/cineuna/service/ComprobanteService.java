/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.util.AppContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author mario
 */
public class ComprobanteService {
    
    
    Boolean sendMail(){
        return null;
        
    }
    
  
    private void exportToMail(String pdf){
     
        final String username = "proyectosuna83@gmail.com";
        final String password = "proy.una";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(AppContext.getInstance().getUsuario().getUsuEmail());
            message.setSubject("Reporte de ganancias del usuario ") ;
            message.setText("Documento de reporte de ganancias de la cancha ");

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();
            messageBodyPart1 = new MimeBodyPart();
            String fileName = ""
                    + "ReporteGanancias.pdf";
            DataSource source = new FileDataSource(pdf);
            messageBodyPart1.setDataHandler(new DataHandler(source));
            messageBodyPart1.setFileName(fileName);

            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
           
        }
        }
        else{
            System.out.print("falta llenar el campo del correo");
        }
    }
}
