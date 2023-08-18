package com.example.skyengtest.model;

import com.example.skyengtest.constant.DeliveryStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private long id;

    private PostOffice office;

    private PostalItem item;

    private DeliveryStatus status;

    public History(PostalItem item, PostOffice office, DeliveryStatus status) {
        this.office = office;
        this.item = item;
        this.status = status;
    }
}
