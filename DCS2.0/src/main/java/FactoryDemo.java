public class FactoryDemo {

    public static void main(String[] args) {

        Payment payment1 = PaymentFactory2.createPayment("CREDIT");
        payment1.pay();

        Payment payment2 = PaymentFactory2.createPayment("PAYPAL");
        payment2.pay();
    }
}

// Interface
interface Payment {
    void pay();
}

// Implementations
class CreditCardPayment implements Payment {
    public void pay() {
        System.out.println("Paid with Credit Card");
    }
}

class PayPalPayment implements Payment {
    public void pay() {
        System.out.println("Paid with PayPal");
    }
}

// Factory
class PaymentFactory2 {

    public static Payment createPayment(String type) {

        if (type.equalsIgnoreCase("CREDIT")) {
            return new CreditCardPayment();
        } else if (type.equalsIgnoreCase("PAYPAL")) {
            return new PayPalPayment();
        }

        throw new IllegalArgumentException("Invalid payment type");
    }
}
