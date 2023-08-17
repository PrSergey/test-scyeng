package com.example.skyengtest.model;

import com.example.skyengtest.constant.StatusDelivery;
import com.example.skyengtest.constant.StatusItem;
import com.example.skyengtest.constant.TypeItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostalItem {

    private long id;

    private TypeItem type;

    private PostOffice postoffice;

    private StatusItem statusItem;

    private List<History> historyItem;

}
