package org.esprit.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residence")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "sqrm")
    private Double sqrm; // square in meters

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "residence")
    private List<User> inhabitants;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Residence() {
        inhabitants = new ArrayList<>();
    }

    public Residence(String address, String addressNumber, Integer floor, Double sqrm) {
        super();
        this.address = address;
        this.floor = floor;
        this.sqrm = sqrm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Double getSqrm() {
        return sqrm;
    }

    public void setSqrm(double sqrm) {
        this.sqrm = sqrm;
    }

    public List<User> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<User> inhabitant) {
        this.inhabitants = inhabitant;
    }

}
