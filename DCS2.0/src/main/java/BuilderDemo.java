public class BuilderDemo {

    public static void main(String[] args) {

        Product product = Product.builder()
                .name("Laptop")
                .price(1500)
                .stock(10)
                .build();

        System.out.println(product);
    }
}

class Product {

    private final String name;
    private final double price;
    private final int stock;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.stock = builder.stock;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private double price;
        private int stock;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Product build() {

            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }

            return new Product(this);
        }
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }
}
