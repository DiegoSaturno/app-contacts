package com.diegooliveira.contacts.models;

public enum PhoneType {
    MOBILE(1, "Celular"),
    WORK(2, "Trabalho"),
    HOME(3, "Casa"),
    MAIN(4, "Principal");

    private int Id;
    private String Name;

    PhoneType(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public static String[] listEnumNames() {
        return new String[] { MOBILE.Name, WORK.Name, HOME.Name, MAIN.Name };
    }



    public static PhoneType getById(int id) {
        for (PhoneType phoneType : values()) {
            if (phoneType.Id == id)
                return phoneType;
        }

        return MAIN;
    }
}
