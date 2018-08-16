package member.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail
{
	
	public void sendMail(String clientEmail ,String confirmNumber)
	{
		Properties properties = new Properties();
		// ���� ����
		properties.put("mail.smtp.user", "fj2746@gmail.com"); 
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");

		try
		{
			Authenticator auth = new senderAccount();
			Session session = Session.getInstance(properties, auth);
			// ������ ������ �� ���� ��Ȳ�� �ֿܼ� ����Ѵ�.
			session.setDebug(true); 
			MimeMessage msg = new MimeMessage(session);

			
			Date now = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
			
			
			
			msg.setSubject("[ CH OGV �̸��� ���� ]"+sdf.format(now));
			//�����»�� email
			Address fromAddr = new InternetAddress("fj2746@gmail.com");
			msg.setFrom(fromAddr);
			 // �޴»�� Email
			Address toAddr = new InternetAddress(clientEmail);
			
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			//���� ���۵� ����
			msg.setContent("������ȣ [ "+confirmNumber+" ] ", "text/plain;charset=KSC5601");
			Transport.send(msg);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static class senderAccount extends javax.mail.Authenticator
	{

		@Override
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication("fj2746", "1q2w3e4r."); //
			// @gmail.com �����Ѱ��� ID,PASS
		}
	}
}
