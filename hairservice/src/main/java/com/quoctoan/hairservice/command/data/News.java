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
@Table(name = "News")
public class News {
    @Id
    private String id;
    private String title;
    private String imageUrl;
    private String description;
}
