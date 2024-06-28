package com.quoctoan.hairservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import com.ngocthong.commonservice.model.HairServiceResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailHairService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quoctoan.hairservice.command.data.ServiceHair;
import com.quoctoan.hairservice.command.data.ServiceHairRepository;
import com.quoctoan.hairservice.query.model.HairServiceResponseModel;
import com.quoctoan.hairservice.query.queries.GetAllServicesQuery;
import com.quoctoan.hairservice.query.queries.GetServiceQuery;



@Component
public class HairServiceProjection {
	@Autowired
	private ServiceHairRepository serviceHairRepository;
	
	@QueryHandler
	public HairServiceResponseModel handle(GetServiceQuery getServiceQuery) {
		HairServiceResponseModel model = new HairServiceResponseModel();
		ServiceHair serviceHair = serviceHairRepository.getById(getServiceQuery.getId().toString());
		BeanUtils.copyProperties(serviceHair, model);
		return model;
	}
	
	@QueryHandler
	public List<HairServiceResponseModel> handle(GetAllServicesQuery getAllServicesQuery){
		List<ServiceHair> listEntity = serviceHairRepository.findAll();
		List<HairServiceResponseModel> listHairServiceResponseModels = new ArrayList<>();
		listEntity.forEach(s -> {
			HairServiceResponseModel model = new HairServiceResponseModel();
			BeanUtils.copyProperties(s, model);
			listHairServiceResponseModels.add(model);
		});
		return listHairServiceResponseModels;
	}

	@QueryHandler
	public HairServiceResponseCommonModel handle(GetDetailHairService getDetailHairService) {
		HairServiceResponseCommonModel model = new HairServiceResponseCommonModel();
		ServiceHair serviceHair = serviceHairRepository.getById(getDetailHairService.getHairServiceId());
		BeanUtils.copyProperties(serviceHair, model);
		System.out.println("Salon name 1: " + model.getServiceName());
		return model;
	}

}
