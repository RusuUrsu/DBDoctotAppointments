package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinics")
public class Clinic extends Identifiable implements Serializable {

    @Column(name = "name", nullable = true, unique = true)
    private String name;

    @Column(name = "address", nullable = true)
    private String address;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> listOfDoctors = new ArrayList<>();

    public Clinic() {
        // Default constructor required by JPA
    }

    public Clinic(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public List<Doctor> getListOfDoctors() {
        return listOfDoctors;
    }

    public void setListOfDoctors(List<Doctor> listOfDoctors) {
        this.listOfDoctors = listOfDoctors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDoctor(Doctor doctor) {
        this.listOfDoctors.add(doctor);
        doctor.setClinic(this); // Set the relationship in the `Doctor` object
    }

    public void removeDoctor(Doctor doctor) {
        this.listOfDoctors.remove(doctor);
        doctor.setClinic(null); // Remove the relationship in the `Doctor` object
    }

    @Override
    public String toString() {
        return "Clinic: " +
                " | name: '" + name + '\'' +
                " | address: " + address + '\'' +
                " | id: " + id;
    }
}
