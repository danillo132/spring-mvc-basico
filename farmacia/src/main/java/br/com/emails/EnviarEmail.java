package br.com.emails;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public class EnviarEmail {

	
	private String userName = "javaevida13@gmail.com";
	private String password = "f/4=V!pHAcX,QW6";
	private String listaDestinarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	
	
	public EnviarEmail(String listaDestinarios, String nomeRemetente, String assuntoEmail, String textoEmail) {

		this.listaDestinarios = listaDestinarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	
	
	public void enviaEmail(boolean enviohtml) throws Exception{
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls", "true"); // Autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor do gmail
		properties.put("mail.smtp.port", "465"); // Porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // Expecifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conexão
																							// ao SMTP
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, password);
			}
		});
		
		Address[] toUser = InternetAddress.parse(listaDestinarios);
	
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // Quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUser); // E-mail de destino
		message.setSubject(assuntoEmail);
	
	
	
	
	if(enviohtml) {
		message.setContent(textoEmail, "text/html; charset=utf-8");
		
	}else {
		message.setText(textoEmail);
	}
	
	Transport.send(message);
	
	}
	
}
