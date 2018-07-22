package br.com.thiago.retry.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.thiago.retry.model.Pedido;

public abstract class AbstractEmailService implements EmailService {
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage msg = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(msg);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(obj.getCliente().getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setReplyTo(sender);
		mailMessage.setSubject("Pedido confirmado! Nº " + obj.getId());
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText(obj.toString());
		return mailMessage;

	}

}
