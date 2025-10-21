package aston.app.entity;

public class Moped extends Vehicle{
    public Moped(Long id, String model, int price) {
        super(id, model, price);
    }

    public static MopedBuilder builder() {
        return new MopedBuilder();
    }

    public static class MopedBuilder {
        private Long id;
        private String model;
        private int price;

        MopedBuilder() {}

        public MopedBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MopedBuilder model(String model) {
            this.model = model;
            return this;
        }

        public MopedBuilder price(int price) {
            this.price = price;
            return this;
        }

        public Moped build() {
            return new Moped(this.id, this.model, this.price);
        }
    }
}
