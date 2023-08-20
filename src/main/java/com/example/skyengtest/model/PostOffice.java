package com.example.skyengtest.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post_office")
public class PostOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "office_index")
    private String index;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "addres_id")
    private Address address;
}
