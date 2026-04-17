// Arayüz (Component)
interface Coffee {
    double cost();
    String description();
}

// Temel kahve (Concrete Component)
class SimpleCoffee implements Coffee {
    @Override
    public double cost() {
        return 2.0;
    }

    @Override
    public String description() {
        return "Basit kahve";
    }
}

// Dekoratör temel sınıf (Decorator)
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
}

// Süt dekoratörü (Concrete Decorator A)
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

// Şeker dekoratörü (Concrete Decorator B)
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.2;
    }

    @Override
    public String description() {
        return decoratedCoffee.description() + ", şeker eklenmiş";
    }
}

// Kullanım
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        // Sadece temel kahve
        Coffee coffee = new SimpleCoffee();
        System.out.println("Temel kahve:");
        System.out.println("Fiyat: $" + coffee.cost());
        System.out.println("Tanım: " + coffee.description());

        // Süt eklenmiş kahve
        coffee = new MilkDecorator(new SimpleCoffee());
        System.out.println("\nSüt eklenmiş kahve:");
        System.out.println("Fiyat: $" + coffee.cost());
        System.out.println("Tanım: " + coffee.description());

        // Şeker eklenmiş kahve
        coffee = new SugarDecorator(new SimpleCoffee());
        System.out.println("\nŞeker eklenmiş kahve:");
        System.out.println("Fiyat: $" + coffee.cost());
        System.out.println("Tanım: " + coffee.description());

        // Hem süt hem şeker eklenmiş kahve
        coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("\nSüt ve şeker eklenmiş kahve:");
        System.out.println("Fiyat: $" + coffee.cost());
        System.out.println("Tanım: " + coffee.description());
    }
}
