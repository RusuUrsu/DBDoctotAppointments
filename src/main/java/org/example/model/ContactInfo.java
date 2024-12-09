package org.example.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ContactInfo implements Serializable {

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "address", nullable = true)
    private String address;

    public ContactInfo() {
        // Default constructor required by JPA
    }

    public ContactInfo(String phoneNumber, String email, String address) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Phone: " + phoneNumber + " | Email: " + email + " | Address: " + address;
    }
}
