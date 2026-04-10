public class BuilderDemo {

    public static void main(String[] args) {

        User user = new User.Builder("Melih")
                .age(22)
                .email("melih@example.com")
                .build();

        System.out.println(user);
    }
}

class User {

    private final String name;
    private final int age;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    public static class Builder {

        // required
        private final String name;

        // optional
        private int age;
        private String email;

        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {

            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("Name is required");
            }

            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}
