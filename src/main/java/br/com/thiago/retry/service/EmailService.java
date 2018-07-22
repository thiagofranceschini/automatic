package br.com.thiago.retry.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.thiago.retry.model.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
