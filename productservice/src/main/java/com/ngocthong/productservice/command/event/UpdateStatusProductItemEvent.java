package com.ngocthong.productservice.command.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusProductItemEvent {
    private String productItemId;
    private String status;
}
