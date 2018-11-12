/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.ComprobanteDto;
import cineuna.util.AppContext;
import cineuna.util.Mensaje;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
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
import javax.ws.rs.core.GenericType;

/**
 *
 * @author mario
 */
public class ComprobanteService {
    
    
    Boolean sendMail(){
        return null;
        
    }
    

    
    public Boolean guardarComp(ComprobanteDto dto){
        ComprobanteDto aux;
        try{
            Respuesta res = new Respuesta();
            Respuesta respuesta = new Respuesta();
            res = guardarComprobante(dto);//cambiar a reserva
            if(res.getEstado()){
                aux = (ComprobanteDto) res.getResultado("Comprobante");
              respuesta = getReport(Long.valueOf(aux.getCompId()));
              byte[] b = (byte[]) respuesta.getResultado("Reporte");
              
               if(respuesta.getEstado()){
                  return exportToMail(b);
               }
            }
        }
        catch(Exception e){
            System.out.println("problema guardando comprobante");
            return false;
        }
        return true;
    }
    
    
  public Respuesta guardarComprobante(ComprobanteDto dto){
        try {     
            Request request = new Request("comprobante/guardarComprobante");
            request.post(dto);
            
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            ComprobanteDto compDto = (ComprobanteDto) request.readEntity(ComprobanteDto.class);
            return new Respuesta(true, "", "", "Comprobante", compDto);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error guardando el comprobante.", ex);
            return new Respuesta(false, "Error guardando comprobante.", "guardarComprobante " + ex.getMessage());
        }
    }
    
     public Respuesta getReport(Long id){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("comprobante/comprobanteReport","{id}",parametros);
            request.get();

            if (request.isError()) {
                System.out.println("error TandaService(Cliente)"+request.getError());
                return new Respuesta(false, request.getError(), "");
            }
            byte[] b = (byte[]) (byte[]) request.readEntity(new GenericType<byte[]>() {});
            return new Respuesta(true, "", "", "Reporte",b);
            
        } catch (Exception ex) {
            Logger.getLogger(TandaService.class.getName()).log(Level.SEVERE, "Error obteniendo comprobante de pago.", ex);
            return new Respuesta(false, "Error obteniendo comprobante de pago.", "getReport " + ex.getMessage());
        }
    }
    String convPdf(byte[] b) throws FileNotFoundException, IOException{
        String outPutFile = "src\\cineuna\\jasper\\moviesListReport.pdf";
         String stream =Base64.getEncoder().encodeToString(b); 
            byte[] bytes = Base64.getDecoder().decode(stream);
            File someFile = new File(outPutFile);
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(bytes);
            fos.flush();
            fos.close();
            return outPutFile;
    }
         
    public Boolean exportToMail(byte[] Cpdf) throws IOException{
        String pdf = convPdf(Cpdf);
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
            InternetAddress.parse(AppContext.getInstance().getUsuario().getUsuEmail()));
            message.setSubject("Comptobante de compra en CINE UNA PZ ") ;
            message.setText("Muchas gracias por preferirnos !!! ");

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();
            messageBodyPart1 = new MimeBodyPart();
            String fileName = ""
                    + "ComprobanteCompra.pdf";
            DataSource source = new FileDataSource(pdf);
            messageBodyPart1.setDataHandler(new DataHandler(source));
            messageBodyPart1.setFileName(fileName);

            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();   
            return false;
        }
    }
}
