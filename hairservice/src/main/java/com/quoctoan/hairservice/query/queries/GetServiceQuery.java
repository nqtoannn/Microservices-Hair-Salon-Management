package com.quoctoan.hairservice.query.queries;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetServiceQuery {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
