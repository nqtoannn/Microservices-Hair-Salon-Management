package com.quoctoan.hairservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quoctoan.hairservice.command.data.ServiceHair;
import com.quoctoan.hairservice.command.data.ServiceHairRepository;


@Component
public class ServiceEventsHandler {
	
	@Autowired 
	private ServiceHairRepository serviceHairRepository;
	
	@EventHandler
	public void on(ServiceCreateEvent event) {
		ServiceHair serviceHair = new ServiceHair();
		BeanUtils.copyProperties(event, serviceHair);
		serviceHairRepository.save(serviceHair);
	}

	@EventHandler
	public void on(ServiceUpdatedEvent event) {
		ServiceHair serviceHair = serviceHairRepository.getById(event.getId());
		serviceHair.setDescription(event.getDescription());
		serviceHair.setPrice(event.getPrice());
		serviceHair.setUrl(event.getUrl());
		serviceHair.setServiceName(event.getServiceName());
		serviceHairRepository.save(serviceHair);
	}

	@EventHandler
	public void on(UpdateServiceStatusEvent event) {
		ServiceHair serviceHair = serviceHairRepository.getById(event.getId());
		serviceHair.setStatus(event.getStatus());
		serviceHairRepository.save(serviceHair);
	}

//	@EventHandler
//	public void on(ServiceDeletedEvent event) {
//		serviceHairRepository.deleteById(event.getId());
//	}
	
}
