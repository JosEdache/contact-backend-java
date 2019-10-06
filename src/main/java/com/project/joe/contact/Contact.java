package com.project.joe.contact;


import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "contacts")
public class Contact {
    @Getter(onMethod_ = {@GeneratedValue(strategy = GenerationType.IDENTITY), @Id})
    private Integer uid;
    private String phoneNumber, firstName, lastName, email, address, imageUrl;
}
