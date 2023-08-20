package com.example.skyengtest.model;

import com.example.skyengtest.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_item")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_office_id")
    private PostOffice office;

    @ManyToOne
    @JoinColumn(name = "postal_item_id")
    private PostalItem item;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @Column(name = "created")
    private LocalDateTime localDateTime;

    public History(PostalItem item, PostOffice office, DeliveryStatus status, LocalDateTime localDateTime) {
        this.office = office;
        this.item = item;
        this.status = status;
        this.localDateTime = localDateTime;
    }
}
