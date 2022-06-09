package io.github.aliothliu.gabbro.web;

import javax.validation.constraints.NotBlank;

/**
 * @author liubin
 **/
public class ArgumentVO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
