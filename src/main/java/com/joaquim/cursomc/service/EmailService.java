package com.joaquim.cursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.joaquim.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
