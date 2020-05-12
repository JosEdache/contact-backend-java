package edache.joe.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "users")
@Access(AccessType.PROPERTY)
public class User {
    @Getter(onMethod_ = {@Id, @Column(columnDefinition = "VARCHAR(100)")})
    private String id;
    private String name, email;
    @Getter(onMethod_ = {@JsonIgnore})
    @Setter(onMethod_ = {@JsonProperty})
    private String password;
    @Getter(onMethod_ = {@Column(name = "phone_number")})
    private String phoneNumber;
    @Getter(onMethod_ = {@Enumerated(EnumType.STRING)})
    @JsonIgnore
    private UserRole role ;
    @Getter(onMethod_ = {@Column(columnDefinition = "BOOLEAN DEFAULT true")})
    @JsonIgnore
    private boolean enabled ;
    @Getter(onMethod_ = {@Column(name = "provider_id")})
    private String providerId;
    @Getter(onMethod_ = {@Enumerated(EnumType.STRING)})
    private AuthProvider provider;

}
