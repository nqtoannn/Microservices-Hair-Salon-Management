package com.ngocthong.appointmentservice.command.saga;

import com.ngocthong.appointmentservice.command.event.AppointmentCreateEvent;
import com.ngocthong.commonservice.command.UpdateStatusUserCommand;
import com.ngocthong.commonservice.model.HairServiceResponseCommonModel;
import com.ngocthong.commonservice.model.SalonResponseCommonModel;
import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailHairService;
import com.ngocthong.commonservice.query.GetDetailSalon;
import com.ngocthong.commonservice.query.GetDetailUser;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class AppointmentSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;
    @StartSaga
    @SagaEventHandler(associationProperty = "serviceId")
    private void handle(AppointmentCreateEvent event) {
        try {
            GetDetailSalon getDetailSalon = new GetDetailSalon(event.getSalonId());
            SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                    SalonResponseCommonModel.class
            )).join();
            if (salonResponseCommonModel.getIsActive()) {
                System.out.println("OK");
            }
            else {
                System.out.println("Salon is not active");
            }
            GetDetailHairService getDetailHairService = new GetDetailHairService(event.getServiceId());
            try {
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                String serviceName = hairServiceResponseCommonModel.getServiceName();
                System.out.println("service name: " + serviceName);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            String userId = event.getUserId();
            GetDetailUser getDetailUser = new GetDetailUser(userId);
            try {
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(
                        getDetailUser,
                        ResponseTypes.instanceOf(
                                UserResponseCommonModel.class
                        )
                ).join();
                System.out.println("user status: " + userResponseCommonModel.getStatus());
                try {
                    UpdateStatusUserCommand updateStatusUserCommand = new UpdateStatusUserCommand(userId, "OK");
                    commandGateway.sendAndWait(updateStatusUserCommand);
                    System.out.println("Update status user successfully");
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
//            rollBackAddRecord(event.getId());
            System.out.println(e.getMessage());
        }
    }
}
