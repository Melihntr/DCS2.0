// Component arayüzü
interface Coffee {
    double cost();
    String description();
}

// Konkret componente
class SimpleCoffee implements Coffee {
    @Override
    public double cost() { return 2.0; }
    @Override
    public String description() { return "Basit kahve"; }
}

// Decorator sınıfı
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
}

// Konkret decorator
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.5;
    }

    @Override
    public String description() {
        return decoratedCoffee.description() + ", süt eklenmiş";
    }
}

// Kullanım
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println("Fiyat: $" + coffee.cost() + " - " + coffee.description());

        Coffee milkCoffee = new MilkDecorator(coffee);
        System.out.println("Fiyat: $" + milkCoffee.cost() + " - " + milkCoffee.description());
    }
}
