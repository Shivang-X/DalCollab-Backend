package com.DalCollab.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="userId")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 1, max = 20, message= "Username must be between 1 and 20 characters long ")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
    @NotNull
    private String userName;

    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits only")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must contain only Numbers")
    @NotNull
    private String mobileNumber;

    @Email
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    private String password;

    private String tagline;

    private String bio;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private List<Project> projects;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> interests;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
