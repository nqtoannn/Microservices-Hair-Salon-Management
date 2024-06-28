package com.quoctoan.hairservice.command.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsCreateEvent {
    private String id;
    private String title;
    private String imageUrl;
    private String description;
}
