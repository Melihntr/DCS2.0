package CoreJava.a8_extras;

class Order {
    private int total =0;

    public int getTotal(){
        return total;
    }
}
public class EarlyReturn {
    public void processOrder(Order order) {
        if (order == null) {
            System.out.println("Order cannot be null");
            return;
        }
        if (order.getTotal() <= 0) {
            System.out.println("Order total must be greater than 0");
            return;
        }
        // işleme devam et
        System.out.println("Order processed");
    }
}
