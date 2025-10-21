package aston.app.entity;

public class Motorcycle extends Vehicle {
    public Motorcycle(Long id, String model, int price) {
        super(id, model, price);
    }

    public static MotorcycleBuilder builder() {
        return new MotorcycleBuilder();
    }

    public static class MotorcycleBuilder {
        private Long id;
        private String model;
        private int price;

        MotorcycleBuilder() {
        }

        public MotorcycleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MotorcycleBuilder model(String model) {
            this.model = model;
            return this;
        }

        public MotorcycleBuilder price(int price) {
            this.price = price;
            return this;
        }

        public Motorcycle build() {
            return new Motorcycle(this.id, this.model, this.price);
        }
    }
}
