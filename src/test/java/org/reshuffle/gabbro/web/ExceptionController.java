package org.reshuffle.gabbro.web;

import org.reshuffle.gabbro.errors.NotFoundException;
import org.reshuffle.gabbro.errors.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExceptionController {

    @RequestMapping(value = "/404")
    public ResponseEntity<Void> notFoundException() {
        throw new NotFoundException();
    }

    @RequestMapping(value = "/order")
    public ResponseEntity<Void> getOrder() {
        throw new OrderNotFoundException();
    }

    @RequestMapping(value = "/npe")
    public ResponseEntity<Void> getNpe() {
        throw new NullPointerException("Just for test");
    }

    @RequestMapping(value = "/bad-credentials")
    public ResponseEntity<Void> badCredentials() {
        throw new BadCredentialsException("Username or password incorrect");
    }

    @RequestMapping(value = "/arguments", method = RequestMethod.POST)
    public ResponseEntity<Void> argumentValidate(@RequestBody @Valid ArgumentVO vo) {
        return ResponseEntity.ok().build();
    }
}
