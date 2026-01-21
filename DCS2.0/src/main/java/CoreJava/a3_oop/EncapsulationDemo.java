package CoreJava.a3_oop;

public class EncapsulationDemo {
    private double balance;

    public void deposit(double amount) {
        balance += amount;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(){
        this.balance=balance;
    }
}


