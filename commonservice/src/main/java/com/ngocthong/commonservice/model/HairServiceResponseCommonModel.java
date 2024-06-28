package com.ngocthong.commonservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HairServiceResponseCommonModel {
    private String id;
    private String serviceName;
    private Double price;
    private String description;
    private String url;
}
