package com.example.myapplication;

public enum Karten {
    Cerberus(10, "Fire", "cerberus10.jpg"),
    Charmander(7,"Fire","charmander7.jpg.jpg"),
    Deathwing(12,"Fire","deathwing12.jpg"),
    Dolphin(10,"Water","delfinu10.jpg"),
    Fox(5,"Grass","fox5.jpg"),
    Gorilla(12,"Grass","gorilla12.jpg"),
    Penguin(6,"Water","penguin6.jpg"),
    Shark(12,"Water","rechin12.jpg"),
    Tiger(10,"Grass","tiger10.jpg");

    private int power;
    private String type;
    private String path;

    Karten(int power, String type, String path) {
        this.power = power;
        this.type = type;
        this.path = path;
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
