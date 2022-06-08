package org.reshuffle.gabbro.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {

    public NotFoundException() {
        super(URI.create("http://www.reshuffle.com"), "404", Status.NOT_FOUND, "Not Found");
    }
}
