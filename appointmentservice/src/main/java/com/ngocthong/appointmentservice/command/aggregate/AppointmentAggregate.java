package com.ngocthong.appointmentservice.command.aggregate;

import com.ngocthong.appointmentservice.command.command.CreateAppointmentCommand;
import com.ngocthong.appointmentservice.command.command.UpdateAppointmentCommand;
import com.ngocthong.appointmentservice.command.command.UpdateStatusAppointmentCommand;
import com.ngocthong.appointmentservice.command.event.AppointmentCreateEvent;
import com.ngocthong.appointmentservice.command.event.AppointmentUpdateEvent;
import com.ngocthong.appointmentservice.command.event.AppointmentUpdateStatusEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@Aggregate
public class AppointmentAggregate {
    @AggregateIdentifier
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private Double price;
    private String customerId;
    private String salonId;
    private String serviceId;
    private String userId;

    public AppointmentAggregate() {

    }

    @CommandHandler
    public AppointmentAggregate(CreateAppointmentCommand createAppointmentCommand) {
        AppointmentCreateEvent appointmentCreateEvent = new AppointmentCreateEvent();
        BeanUtils.copyProperties(createAppointmentCommand, appointmentCreateEvent);
        AggregateLifecycle.apply(appointmentCreateEvent);
    }

    @EventSourcingHandler
    public void on(AppointmentCreateEvent event) {
        this.appointmentId = event.getAppointmentId();
        this.appointmentDate = event.getAppointmentDate();
        this.appointmentTime = event.getAppointmentTime();
        this.price = event.getPrice();
        this.customerId = event.getCustomerId();
        this.salonId = event.getSalonId();
        this.serviceId = event.getServiceId();
        this.userId = event.getUserId();
    }


    @CommandHandler
    public void handle(UpdateAppointmentCommand updateAppointmentCommand) {
        AppointmentUpdateEvent appointmentUpdateEvent = new AppointmentUpdateEvent();
        BeanUtils.copyProperties(updateAppointmentCommand, appointmentUpdateEvent);
        AggregateLifecycle.apply(appointmentUpdateEvent);
    }


    @EventSourcingHandler
    public void on(AppointmentUpdateEvent event) {
        this.appointmentId = event.getAppointmentId();
        this.appointmentDate = event.getAppointmentDate();
        this.appointmentTime = event.getAppointmentTime();
        this.salonId = event.getSalonId();
        this.serviceId = event.getServiceId();
        this.userId = event.getUserId();
    }

    @CommandHandler
    public void handle(UpdateStatusAppointmentCommand updateStatusAppointmentCommand) {
        AppointmentUpdateStatusEvent appointmentUpdateStatusEvent = new AppointmentUpdateStatusEvent();
        BeanUtils.copyProperties(updateStatusAppointmentCommand, appointmentUpdateStatusEvent);
        AggregateLifecycle.apply(appointmentUpdateStatusEvent);
    }


    @EventSourcingHandler
    public void on(AppointmentUpdateStatusEvent event) {
        this.appointmentId = event.getAppointmentId();
        this.status = event.getStatus();
    }


}
