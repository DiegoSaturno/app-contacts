package com.diegooliveira.contacts.models;

public enum PhoneType {
    MOBILE(2, "Celular", "Mobile"),
    WORK(3, "Trabalho", "Work"),
    HOME(1, "Casa", "Home"),
    MAIN(12, "Principal", "Main");

    private int Id;
    private String AndroidName;
    private String Name;

    PhoneType(int id, String name, String androidName) {
        this.Id = id;
        this.Name = name;
        this.AndroidName = androidName;
    }

    public int getId() {
        return this.Id;
    }

    public String getAndroidName() {
        return this.AndroidName;
    }

    public static String[] listEnumNames() {
        return new String[] { MOBILE.Name, WORK.Name, HOME.Name, MAIN.Name };
    }

    public static PhoneType getByName(String name) {
        for (PhoneType phoneType : values()) {
            if (phoneType.Name.toLowerCase().compareTo(name.toLowerCase()) == 0)
                return phoneType;
        }

        return MAIN;
    }

    public static PhoneType getById(int id) {
        for (PhoneType phoneType : values()) {
            if (phoneType.Id == id)
                return phoneType;
        }

        return MAIN;
    }
}
