package com.ngocthong.appointmentservice.Query.projection;

import com.ngocthong.appointmentservice.Query.queries.GetAllSalonsQuery;
import com.ngocthong.appointmentservice.command.data.Salon;
import com.ngocthong.appointmentservice.command.data.SalonRepository;
import com.ngocthong.commonservice.model.SalonResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailSalon;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalonProjection {
    @Autowired
    private SalonRepository salonRepository;

    @QueryHandler
    public SalonResponseCommonModel handle(GetDetailSalon getDetailSalon) {
        SalonResponseCommonModel model = new SalonResponseCommonModel();
        String id = getDetailSalon.getSalonId();
        System.out.println("id: " + id);
        Salon salon = salonRepository.getById(id);
        BeanUtils.copyProperties(salon, model);
        return model;
    }

    @QueryHandler
    public List<SalonResponseCommonModel> handle(GetAllSalonsQuery getAllSalonsQuery){
        List<Salon> salonList = salonRepository.findAll();
        List<SalonResponseCommonModel> listSalons = new ArrayList<>();
        salonList.forEach(s -> {
            SalonResponseCommonModel model = new SalonResponseCommonModel();
            BeanUtils.copyProperties(s, model);
            listSalons.add(model);
        });
        return listSalons;
    }

}
