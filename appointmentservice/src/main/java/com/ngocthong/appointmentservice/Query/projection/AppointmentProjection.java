package com.ngocthong.appointmentservice.Query.projection;

import com.ngocthong.appointmentservice.Query.model.AppointmentResponse;
import com.ngocthong.appointmentservice.Query.model.MonthlyRevenue;
import com.ngocthong.appointmentservice.Query.queries.*;
import com.ngocthong.appointmentservice.command.data.Appointment;
import com.ngocthong.appointmentservice.command.data.AppointmentRepository;
import com.ngocthong.commonservice.model.HairServiceResponseCommonModel;
import com.ngocthong.commonservice.model.SalonResponseCommonModel;
import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailHairService;
import com.ngocthong.commonservice.query.GetDetailSalon;
import com.ngocthong.commonservice.query.GetDetailUser;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AppointmentProjection {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<AppointmentResponse> handle(GetAllAppointmentsQuery getAllAppointmentsQuery) {
        List<Appointment> appointmentResponseList = appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();
        appointmentResponseList.forEach(a -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(a, appointmentResponse);
            try {
                GetDetailHairService getDetailHairService = new GetDetailHairService(a.getServiceId());
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                appointmentResponse.setServiceName(hairServiceResponseCommonModel.getServiceName());
                appointmentResponse.setPrice(hairServiceResponseCommonModel.getPrice());
                GetDetailUser getDetailUser = new GetDetailUser(a.getUserId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setUserName(userResponseCommonModel.getFullName());

                GetDetailUser getDetailCustomer = new GetDetailUser(a.getCustomerId());
                UserResponseCommonModel customerResponseCommonModel = queryGateway.query(getDetailCustomer, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setCustomerName(customerResponseCommonModel.getFullName());

                GetDetailSalon getDetailSalon = new GetDetailSalon(a.getSalonId());
                SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                        SalonResponseCommonModel.class
                )).join();
                appointmentResponse.setSalonName(salonResponseCommonModel.getName());
                appointmentResponse.setSalonAddress(salonResponseCommonModel.getAddress());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            appointmentResponses.add(appointmentResponse);
        });
        return appointmentResponses;
    }

    @QueryHandler
    public List<AppointmentResponse> handle(GetAllAppointmentByCustomerIdQuery getAllAppointmentByCustomerIdQuery) {
        List<Appointment> appointmentResponseList = appointmentRepository.findAllByCustomerId(getAllAppointmentByCustomerIdQuery.getCustomerId());
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();
        appointmentResponseList.forEach(a -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(a, appointmentResponse);
            try {
                GetDetailHairService getDetailHairService = new GetDetailHairService(a.getServiceId());
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                appointmentResponse.setServiceName(hairServiceResponseCommonModel.getServiceName());
                appointmentResponse.setPrice(hairServiceResponseCommonModel.getPrice());
                GetDetailUser getDetailUser = new GetDetailUser(a.getUserId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setUserName(userResponseCommonModel.getFullName());

                GetDetailUser getDetailCustomer = new GetDetailUser(a.getCustomerId());
                UserResponseCommonModel customerResponseCommonModel = queryGateway.query(getDetailCustomer, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setCustomerName(customerResponseCommonModel.getFullName());

                GetDetailSalon getDetailSalon = new GetDetailSalon(a.getSalonId());
                SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                        SalonResponseCommonModel.class
                )).join();
                appointmentResponse.setSalonName(salonResponseCommonModel.getName());
                appointmentResponse.setSalonAddress(salonResponseCommonModel.getAddress());

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            appointmentResponses.add(appointmentResponse);
        });
        return appointmentResponses;
    }

    @QueryHandler
    public List<MonthlyRevenue> handle(GetRevenueByYear getRevenueByYear) {
        List<Object[]> results = appointmentRepository.findMonthlyRevenueByYear(getRevenueByYear.getYear());
        List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();
        for (Object[] result : results) {
            int month = (int) result[0];
            double totalRevenue = (double) result[1];
            monthlyRevenues.add(new MonthlyRevenue(getRevenueByYear.getYear(), month, totalRevenue));
        }
        return monthlyRevenues;
    }

    @QueryHandler
    public List<MonthlyRevenue> handle(GetRevenueBetween getRevenueBetween) {
        // Fetch the results from the repository
        List<Object[]> results = appointmentRepository.findMonthlyRevenueByYearAndMonths(
                getRevenueBetween.getYear(),
                getRevenueBetween.getStartMonth(),
                getRevenueBetween.getEndMonth()
        );

        // Initialize a map to hold monthly revenues, defaulting to 0.0
        Map<Integer, Double> monthlyRevenueMap = new HashMap<>();
        for (int month = getRevenueBetween.getStartMonth(); month <= getRevenueBetween.getEndMonth(); month++) {
            monthlyRevenueMap.put(month, 0.0);
        }

        // Populate the map with actual revenue data from the results
        for (Object[] result : results) {
            int month = (int) result[1];
            double totalRevenue = ((Number) result[2]).doubleValue();
            monthlyRevenueMap.put(month, totalRevenue);
        }

        // Convert the map entries to a list of MonthlyRevenue objects
        List<MonthlyRevenue> monthlyRevenues = monthlyRevenueMap.entrySet().stream()
                .map(entry -> new MonthlyRevenue(getRevenueBetween.getYear(), entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Return the list sorted by month for consistency
        monthlyRevenues.sort(Comparator.comparingInt(MonthlyRevenue::getMonth));

        return monthlyRevenues;
    }

    @QueryHandler
    public List<AppointmentResponse> handle(GetAllAppointmentByEmployeeIdQuery getAppointmentByEmployeeIdQuery) {
        List<Appointment> appointmentResponseList = appointmentRepository.findAllAppointmentForEmployee(getAppointmentByEmployeeIdQuery.getEmployeeId());
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();
        appointmentResponseList.forEach(a -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(a, appointmentResponse);
            try {
                GetDetailHairService getDetailHairService = new GetDetailHairService(a.getServiceId());
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                appointmentResponse.setServiceName(hairServiceResponseCommonModel.getServiceName());
                appointmentResponse.setPrice(hairServiceResponseCommonModel.getPrice());
                GetDetailUser getDetailUser = new GetDetailUser(a.getUserId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setUserName(userResponseCommonModel.getFullName());

                GetDetailUser getDetailCustomer = new GetDetailUser(a.getCustomerId());
                UserResponseCommonModel customerResponseCommonModel = queryGateway.query(getDetailCustomer, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setCustomerName(customerResponseCommonModel.getFullName());

                GetDetailSalon getDetailSalon = new GetDetailSalon(a.getSalonId());
                SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                        SalonResponseCommonModel.class
                )).join();
                appointmentResponse.setSalonName(salonResponseCommonModel.getName());
                appointmentResponse.setSalonAddress(salonResponseCommonModel.getAddress());

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            appointmentResponses.add(appointmentResponse);
        });
        return appointmentResponses;
    }

    @QueryHandler
    public List<AppointmentResponse> handle(GetAppointmentByEmployeeIdQuery getAppointmentByEmployeeIdQuery) {
        List<Appointment> appointmentResponseList = appointmentRepository.findAppointmentForEmployee(getAppointmentByEmployeeIdQuery.getEmployeeId());
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();
        appointmentResponseList.forEach(a -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(a, appointmentResponse);
            try {
                GetDetailHairService getDetailHairService = new GetDetailHairService(a.getServiceId());
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                appointmentResponse.setServiceName(hairServiceResponseCommonModel.getServiceName());
                appointmentResponse.setPrice(hairServiceResponseCommonModel.getPrice());
                GetDetailUser getDetailUser = new GetDetailUser(a.getUserId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setUserName(userResponseCommonModel.getFullName());

                GetDetailUser getDetailCustomer = new GetDetailUser(a.getCustomerId());
                UserResponseCommonModel customerResponseCommonModel = queryGateway.query(getDetailCustomer, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setCustomerName(customerResponseCommonModel.getFullName());

                GetDetailSalon getDetailSalon = new GetDetailSalon(a.getSalonId());
                SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                        SalonResponseCommonModel.class
                )).join();
                appointmentResponse.setSalonName(salonResponseCommonModel.getName());
                appointmentResponse.setSalonAddress(salonResponseCommonModel.getAddress());

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            appointmentResponses.add(appointmentResponse);
        });
        return appointmentResponses;
    }

    @QueryHandler
    public List<AppointmentResponse> handle(GetAppointmentDoneByEmployeeIdQuery query) {
        List<Appointment> appointmentResponseList = appointmentRepository.
        findAppointmentDoneByEmployee(query.getEmployeeId());
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();
        appointmentResponseList.forEach(a -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(a, appointmentResponse);
            try {
                GetDetailHairService getDetailHairService = new GetDetailHairService(a.getServiceId());
                HairServiceResponseCommonModel hairServiceResponseCommonModel = queryGateway.query(getDetailHairService, ResponseTypes.instanceOf(
                        HairServiceResponseCommonModel.class
                )).join();
                appointmentResponse.setServiceName(hairServiceResponseCommonModel.getServiceName());
                appointmentResponse.setPrice(hairServiceResponseCommonModel.getPrice());
                GetDetailUser getDetailUser = new GetDetailUser(a.getUserId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setUserName(userResponseCommonModel.getFullName());

                GetDetailUser getDetailCustomer = new GetDetailUser(a.getCustomerId());
                UserResponseCommonModel customerResponseCommonModel = queryGateway.query(getDetailCustomer, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                appointmentResponse.setCustomerName(customerResponseCommonModel.getFullName());

                GetDetailSalon getDetailSalon = new GetDetailSalon(a.getSalonId());
                SalonResponseCommonModel salonResponseCommonModel = queryGateway.query(getDetailSalon, ResponseTypes.instanceOf(
                        SalonResponseCommonModel.class
                )).join();
                appointmentResponse.setSalonName(salonResponseCommonModel.getName());
                appointmentResponse.setSalonAddress(salonResponseCommonModel.getAddress());

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            appointmentResponses.add(appointmentResponse);
        });
        return appointmentResponses;
    }

}
