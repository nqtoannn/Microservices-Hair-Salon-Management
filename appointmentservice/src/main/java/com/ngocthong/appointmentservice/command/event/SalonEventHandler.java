package com.ngocthong.appointmentservice.command.event;

import com.ngocthong.appointmentservice.command.data.Salon;
import com.ngocthong.appointmentservice.command.data.SalonRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalonEventHandler {
    @Autowired
    private SalonRepository salonRepository;

    @EventHandler
    public void on(SalonCreateEvent event) {
        Salon salon = new Salon();
        BeanUtils.copyProperties(event, salon);
        salonRepository.save(salon);
    }

    @EventHandler
    public void on(SalonUpdateEvent event) {
        Salon salon = salonRepository.getById(event.getId());
        salon.setName(event.getName());
        salon.setAddress(event.getAddress());
        salon.setPhoneNumber(event.getPhoneNumber());
        salon.setIsActive(event.getIsActive());
        salonRepository.save(salon);
    }


}
