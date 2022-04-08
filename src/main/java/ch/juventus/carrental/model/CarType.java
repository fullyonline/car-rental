package ch.juventus.carrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CarType {
    LIMUSINE("LIMUSINE");

    private String value;

    @JsonCreator
    CarType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CarType fromText(String text){
        for(CarType type : CarType.values()){
            if(type.toString().equals(text)){
                return type;
            }
        }
        throw new IllegalArgumentException();
    }
}