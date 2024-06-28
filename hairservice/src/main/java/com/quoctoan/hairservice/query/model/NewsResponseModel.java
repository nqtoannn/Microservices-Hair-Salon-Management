package com.quoctoan.hairservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseModel {
    private String id;
    private String title;
    private String imageUrl;
    private String description;
}
