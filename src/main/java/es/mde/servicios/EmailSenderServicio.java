package es.mde.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServicio {
  private static final Logger log = LoggerFactory.getLogger(EmailSenderServicio.class);

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Autowired
  public EmailSenderServicio(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void enviarEmail(String destinatario, String asunto, String mensaje) {
    try {
      SimpleMailMessage email = new SimpleMailMessage();
      email.setFrom(fromEmail);
      email.setTo(destinatario);
      email.setSubject(asunto);
      email.setText(mensaje);
      mailSender.send(email);
      log.info("Correo enviado correctamente a {}", destinatario);
    } catch (Exception e) {
      log.error("Error al enviar el correo a {}: {}", destinatario, e.getMessage());
    }
  }

}
