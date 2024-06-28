package com.ngocthong.productservice.command.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Category extends BaseEntity {
    @Id
    private String id;
    private String categoryName;
}
