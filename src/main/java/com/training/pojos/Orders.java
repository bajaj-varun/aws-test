package com.training.pojos;

import lombok.Data;

@Data
public class Orders {
    private long ordertime, orderid;
    private String itemid;
    private double orderunits;
    private Address address;
}

