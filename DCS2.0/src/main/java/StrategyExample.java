// Strategy interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete strategy classes
class CreditCardStrategy implements PaymentStrategy {
    private String name;
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardStrategy(String name, String cardNumber, String cvv, String expiryDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using credit/debit card " + cardNumber);
    }
}

class PayPalStrategy implements PaymentStrategy {
    private String email;

    public PayPalStrategy(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal account " + email);
    }
}

// Context class
class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void pay(int amount) {
        paymentStrategy.pay(amount);
    }
}

public class StrategyExample {
    public static void main(String[] args) {
        // Create concrete strategy objects
        PaymentStrategy creditCardStrategy = new CreditCardStrategy("John Doe", "1234-5678-9012-3456", "123", "12/2025");
        PaymentStrategy payPalStrategy = new PayPalStrategy("johndoe@example.com");

        // Create context object with a strategy
        PaymentContext paymentContext = new PaymentContext(creditCardStrategy);
        paymentContext.pay(100);

        // Change strategy
        paymentContext.setPaymentStrategy(payPalStrategy);
        paymentContext.pay(200);
    }
}
