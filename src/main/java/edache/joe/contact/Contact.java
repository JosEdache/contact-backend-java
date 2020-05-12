package edache.joe.contact;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity(name = "contacts")
@Access(AccessType.PROPERTY)
public class Contact {
    @Getter(onMethod_ = {@GeneratedValue(strategy = GenerationType.IDENTITY), @Id})
    private Integer id;
    private String phoneNumber, firstName, lastName, email, address, imageUrl;
}
