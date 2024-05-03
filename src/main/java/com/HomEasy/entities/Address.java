package com.HomEasy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 1, message = "Street name must contain 1 character")
    private String street;

    @NotBlank
    @Size(min = 1, message = "BuildingName name must contain 1 character")
    private String buildingName;

    @NotBlank
    @Size(min = 1, message = "City name must contain 1 character")
    private String city;

    @NotBlank
    @Size(min = 1, message = "State name must contain 1 character")
    private String state;

    @NotBlank
    @Size(min = 1, message = "Country name must contain 1 character")
    private String country;

    @NotBlank
    @Size(min = 1, message = "Pincode name must contain 1 character")
    private String pincode;

//    @ManyToMany(mappedBy = "addresses")
//    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

}
