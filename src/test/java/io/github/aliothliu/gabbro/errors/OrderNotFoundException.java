package io.github.aliothliu.gabbro.errors;

import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class OrderNotFoundException extends AbstractThrowableProblem {

    public OrderNotFoundException() {
        super(URI.create("http://www.reshuffle.com"), "Order Not Found", OrderStatus.OrderNotFound(), "Can't find order by [1]");
    }
}
