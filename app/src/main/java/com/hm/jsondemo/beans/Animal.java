package com.hm.jsondemo.beans;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes({@JsonSubTypes.Type(value = Lion.class),@JsonSubTypes.Type(Elephant.class)})
public abstract class Animal {

    protected String name;
}
