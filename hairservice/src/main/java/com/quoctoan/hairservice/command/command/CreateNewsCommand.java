package com.quoctoan.hairservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Setter
@Getter
@AllArgsConstructor
public class CreateNewsCommand {
    @TargetAggregateIdentifier
    private String id;
    private String title;
    private String imageUrl;
    private String description;
}
