package com.example.skyengtest.model;

import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.constant.ItemType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "postal_item")
public class PostalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_item")
    private ItemType type;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private PostOffice postOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ItemStatus itemStatus;

    @Column(name = "recipient_name")
    private String recipientName;


}
