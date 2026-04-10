import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AdvancedFactoryDemo {

    public static void main(String[] args) {

        Payment payment = PaymentFactory.create("CREDIT");
        payment.pay();
    }
}

class PaymentFactory {

    private static final Map<String, Supplier<Payment>> registry = new HashMap<>();

    static {
        registry.put("CREDIT", CreditCardPayment::new);
        registry.put("PAYPAL", PayPalPayment::new);
    }

    public static Payment create(String type) {
        Supplier<Payment> supplier = registry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown type");
        }
        return supplier.get();
    }
}
