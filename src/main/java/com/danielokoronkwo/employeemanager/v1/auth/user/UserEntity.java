package com.danielokoronkwo.employeemanager.v1.auth.user;

import com.danielokoronkwo.employeemanager.common.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class UserEntity  extends BaseEntity  {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "security_token")
    private String securityToken;

    @Column(name = "security_token_requested_at")
    private Date securityTokenRequestedAt;

    public UserEntity() {
    }

    public UserEntity(Long id, String publicId, Date createdAt, Date updatedAt, String firstName, String lastName, String email, String phoneNumber, String securityToken, Date securityTokenRequestedAt) {
        super(id, publicId, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.securityToken = securityToken;
        this.securityTokenRequestedAt = securityTokenRequestedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public Date getSecurityTokenRequestedAt() {
        return securityTokenRequestedAt;
    }

    public void setSecurityTokenRequestedAt(Date securityTokenRequestedAt) {
        this.securityTokenRequestedAt = securityTokenRequestedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
