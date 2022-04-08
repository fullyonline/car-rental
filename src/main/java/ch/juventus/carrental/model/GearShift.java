package ch.juventus.carrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GearShift {
    AUTOMATIK("AUTOMATIK"),
    MANUEL("MANUEL");

    private String value;

    @JsonCreator
    GearShift(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static GearShift fromText(String text){
        for(GearShift gearShift : GearShift.values()){
            if(gearShift.toString().equals(text)){
                return gearShift;
            }
        }
        throw new IllegalArgumentException();
    }
}
