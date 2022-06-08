package org.reshuffle.gabbro.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class OrderNotFoundException extends AbstractThrowableProblem {

    public OrderNotFoundException() {
        super(URI.create("http://www.reshuffle.com"), "Order Not Found", Status.BAD_REQUEST, "Can't find order by [1]");
    }
}
