package com.example.entity;

import lombok.Data;

@Data
public abstract class Vehicle implements Comparable<Vehicle>{
    protected Long id;
    protected String model;
    protected int price;

    public Vehicle(Long id, String model, int price) {
        this.id = id;
        this.model = model;
        this.price = price;
    }

    @Override
    public int compareTo(Vehicle v) {
        int idCompare = Long.compare(this.id, v.id);
        if (idCompare != 0) {
            return idCompare;
        }

        int modelCompare = compareString(this.model, v.model);
        if (modelCompare != 0) {
            return modelCompare;
        }

        int priceCompare = Integer.compare(this.price, v.price);
        if (priceCompare != 0) {
            return modelCompare;
        }

        return 0;
    }

    protected int compareString(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());

        int diffNumberChar;

        for (int i = 0; i < minLength; i++) {
            diffNumberChar = s1.charAt(i) - s2.charAt(i);
            if (diffNumberChar < 0) {
                return -1;
            } else if (diffNumberChar > 0) {
                return 1;
            }
        }

        diffNumberChar = s1.length() - s2.length();
        if (diffNumberChar < 0) {
            return -1;
        } else if (diffNumberChar > 0) {
            return 1;
        }
        return 0;
    }
}
