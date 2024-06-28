package com.ngocthong.appointmentservice.command.event;

import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.command.data.Appointment;
import com.ngocthong.appointmentservice.command.data.AppointmentRepository;
import com.ngocthong.commonservice.model.HairServiceResponseCommonModel;
import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailHairService;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventHandler {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private transient QueryGateway queryGateway;

    @EventHandler
    public void on(AppointmentCreateEvent event) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(event, appointment);
        try{
            GetDetailHairService getDetailHairService = new GetDetailHairService(event.getServiceId());
            HairServiceResponseCommonModel hairServiceResponseCommonModel =  queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                    HairServiceResponseCommonModel.class
            )).join();
            appointment.setPrice(hairServiceResponseCommonModel.getPrice());

        }catch (Exception a){
            System.out.println(a.getMessage());
        }

        appointment.setStatus("WAITING");
        appointmentRepository.save(appointment);
    }

    @EventHandler
    public void on(AppointmentUpdateEvent event) {
        Appointment appointment = appointmentRepository.getById(event.getAppointmentId());
        appointment.setSalonId(event.getSalonId());
        appointment.setServiceId(event.getServiceId());
        appointment.setAppointmentDate(event.getAppointmentDate());
        appointment.setAppointmentTime(event.getAppointmentTime());
        appointment.setUserId(event.getUserId());
        appointmentRepository.save(appointment);
    }

    @EventHandler
    public void on(AppointmentUpdateStatusEvent event) {
        Appointment appointment = appointmentRepository.getById(event.getAppointmentId());
        appointment.setStatus(event.getStatus());
        appointmentRepository.save(appointment);
    }


}
