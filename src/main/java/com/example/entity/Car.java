package com.example.entity;

public class Car extends Vehicle{
    public Car(Long id, String model, int price) {
        super(id, model, price);
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public static class CarBuilder {
        private Long id;
        private String model;
        private int price;

        CarBuilder() {}

        public CarBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder price(int price) {
            this.price = price;
            return this;
        }

        public Car build() {
            return new Car(this.id, this.model, this.price);
        }
    }
}
