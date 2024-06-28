package com.quoctoan.hairservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HairServiceResponseModel {
	private String id;
	private String serviceName;
    private Double price;
    private String description;
    private String url;
	private String status;
}
