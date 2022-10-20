package io.github.aliothliu.gabbro.errors;

import io.github.aliothliu.gabbro.GabbroStatus;

public class OrderStatus extends GabbroStatus {

    public OrderStatus(Long bizCode, String reasonPhrase) {
        super(bizCode, reasonPhrase);
    }

    public static OrderStatus OrderNotFound() {
        return new OrderStatus(40002001L, "Order Not Found");
    }

    @Override
    protected int bizCodeToStatusCode(Long bizCode) {
        return 400;
    }
}
