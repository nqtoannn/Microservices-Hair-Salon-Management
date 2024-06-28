package com.ngocthong.appointmentservice.command.aggregate;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ngocthong.appointmentservice.command.command.CreateSalonCommand;
import com.ngocthong.appointmentservice.command.command.UpdateSalonCommand;
import com.ngocthong.appointmentservice.command.event.SalonCreateEvent;
import com.ngocthong.appointmentservice.command.event.SalonUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.sql.Update;
import org.springframework.beans.BeanUtils;

@Aggregate
public class SalonAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
    public SalonAggregate() {

    }

    @CommandHandler
    public SalonAggregate(CreateSalonCommand command) {
        SalonCreateEvent salonCreateEvent = new SalonCreateEvent();
        BeanUtils.copyProperties(command, salonCreateEvent);
        AggregateLifecycle.apply(salonCreateEvent);
    }

    @EventSourcingHandler
    public void on (SalonCreateEvent event) {
        this.id = event.getId();
        this.address = event.getAddress();
        this.name = event.getName();
        this.phoneNumber = event.getPhoneNumber();
        this.isActive = event.getIsActive();
    }

    @CommandHandler
    public void handle(UpdateSalonCommand command) {
        SalonCreateEvent salonCreateEvent = new SalonCreateEvent();
        BeanUtils.copyProperties(command, salonCreateEvent);
        AggregateLifecycle.apply(salonCreateEvent);
    }

    @EventSourcingHandler
    public void on (SalonUpdateEvent event) {
        this.id = event.getId();
        this.address = event.getAddress();
        this.name = event.getName();
        this.phoneNumber = event.getPhoneNumber();
        this.isActive = event.getIsActive();
    }

}
