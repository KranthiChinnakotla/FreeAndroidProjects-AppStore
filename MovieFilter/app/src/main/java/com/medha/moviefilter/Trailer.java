package com.medha.moviefilter;

import java.io.Serializable;

/**
 * Created by Prathyusha on 1/12/17.
 */

public class Trailer implements Serializable {

  String type,key,name;


    @Override
    public String toString() {
        return "Trailer{" +
                "type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
