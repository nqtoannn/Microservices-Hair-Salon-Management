package com.quoctoan.hairservice.command.data;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "ServiceHair")
public class ServiceHair extends BaseEntity{
    
	@Id
    private String id;
    private String serviceName;
    private Double price;
    private String description;
    private String url;
    private String status;
	
}

