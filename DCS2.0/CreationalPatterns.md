# Java Creational (Yaratımsal) Design Pattern'ler

Bu doküman, Java'da en önemli üç yaratım (creational) tasarım desenini detaylı şekilde açıklar:

* Singleton
* Factory
* Builder

Her pattern için:

* Ne işe yaradığı
* Ne zaman kullanılması gerektiği
* Farklı initialize (oluşturma) yöntemleri
* Best practice'ler
* Gerçek hayatta kullanım mantığı
* Java kod örnekleri

---

## 1. Singleton Pattern

### Amaç

Singleton Design Pattern, Creational Design Pattern (yaratımsal tasarım desenleri) grubunda yer alır ve temel amacı bir sınıftan çalışma zamanı boyunca yalnızca **tek bir nesne (instance)** oluşturulmasını garanti etmektir.

Bu pattern aslında çok kritik bir problemi çözer:

> "Bir nesnenin kontrolsüz şekilde birden fazla oluşturulmasını nasıl engellerim?"

Çünkü büyük sistemlerde aynı class'tan birden fazla instance oluşturulması:

* veri tutarsızlığına
* gereksiz memory kullanımına
* concurrency problemlerine
  sebep olabilir.

---

### 1.1 Kullanım Amacı

Singleton pattern aşağıdaki durumlarda tercih edilir:

* Birden fazla sınıfın aynı instance üzerinde işlem yapması gerekiyorsa
* Uygulama genelinde tek bir state tutulacaksa
* Kaynak yönetimi kritikse (örn: database connection pool, logger)
* Nesnenin unique olması gerekiyorsa

Örneğin:

* Logger sistemi → Her yerden aynı logger kullanılmalı
* Config reader → Tek bir config objesi yeterli

---

### 1.2 Temel Mantık

Singleton pattern uygulanırken şu kurallar uygulanır:

1. Constructor **private** yapılır
   → Böylece dışarıdan `new` ile instance oluşturulamaz

2. Sınıf içinde **static bir instance** tutulur

3. Dış dünyaya bu instance'ı veren bir **getInstance() metodu** sağlanır

Bu yapı sayesinde:

* Nesne creation kontrol altına alınır
* Tek instance garantilenir

---

### 1.3 Yapısal Mantık (Behavior Açıklaması)

* `createSingleton()` → sadece sınıfın kendi içinde çalışır
* `getInstance()` → dış dünyaya erişim sağlar

Yani:

> Object creation logic dışarıdan tamamen gizlenir

---

### 1.4 Neden Singleton Kullanırız?

#### 1. Kontrollü Erişim

Tek bir instance olduğu için o nesneye erişim tamamen kontrol altındadır.

#### 2. Global Variable Alternatifi

Global değişkenler:

* kontrolsüzdür
* her yerden değiştirilebilir

Singleton:

* encapsulation sağlar
* erişimi merkezi hale getirir

### 1.5 Lazy Initialization (Önemli Nokta)

Global değişkenlerde:

* uygulama başlar başlamaz nesne oluşturulur

Singleton’da:

* nesne sadece ihtiyaç duyulduğunda oluşturulur

Bu özellikle büyük ve resource-heavy objelerde çok kritiktir.

Örnek:

> Database connection objesi uygulama başında değil, ilk ihtiyaçta oluşturulur

---

### Önemli Tasarım Detayı

Singleton’ın en kritik iki özelliği:

1. **Tek instance garantisi**
2. **Global erişim noktası**

Bu iki özellik birlikte sağlanmazsa doğru bir Singleton implementasyonu olmaz.

---

### Dikkat Edilmesi Gerekenler (Çok Kritik)

* Class public olduğu için teorik olarak farklı paketlerden erişilebilir
* Eğer constructor doğru şekilde korunmazsa (reflection gibi yöntemlerle) birden fazla instance oluşturulabilir

Bu yüzden:

* Reflection'a karşı önlem alınmalı


---

### Singleton Pattern Dezavantajları

Her ne kadar güçlü olsa da bazı önemli dezavantajları vardır:

* Global state oluşturur → test yazmayı zorlaştırır
* Dependency Injection ile çakışabilir
* Multithreading ortamda yanlış implement edilirse bug üretir

---

### 1.6 Eager Initialization

Instance class yüklenirken oluşturulur.

Avantaj:

* Thread-safe

Dezavantaj:

* Kullanılmasa bile memory'de yer kaplar

```java
public class SingletonEager {
    private static final SingletonEager INSTANCE = new SingletonEager();

    private SingletonEager() {}

    public static SingletonEager getInstance() {
        return INSTANCE;
    }
}
```

---

### 1.7 Lazy Initialization

Instance sadece ihtiyaç olduğunda oluşturulur.

Avantaj:

* Memory tasarrufu

Dezavantaj:

* Thread-safe değil

```java
public class SingletonLazy {
    private static SingletonLazy instance;

    private SingletonLazy() {}

    public static SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
```

---

### 1.8 Thread-Safe (Synchronized)

```java
public class SingletonSynchronized {
    private static SingletonSynchronized instance;

    private SingletonSynchronized() {}

    public static synchronized SingletonSynchronized getInstance() {
        if (instance == null) {
            instance = new SingletonSynchronized();
        }
        return instance;
    }
}
```

Dezavantaj:

* Her çağrıda lock → performans düşer

---

### 1.9 Double Checked Locking (DCL)

```java
public class SingletonDCL {
    private static volatile SingletonDCL instance;

    private SingletonDCL() {}

    public static SingletonDCL getInstance() {
        if (instance == null) {
            synchronized (SingletonDCL.class) {
                if (instance == null) {
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }
}
```

Avantaj:

* Hem thread-safe hem performanslı

---

### 1.10 Bill Pugh (En Önerilen)

```java
public class SingletonBillPugh {
    private SingletonBillPugh() {}

    private static class Holder {
        private static final SingletonBillPugh INSTANCE = new SingletonBillPugh();
    }

    public static SingletonBillPugh getInstance() {
        return Holder.INSTANCE;
    }
}
```

Avantaj:

* Lazy
* Thread-safe
* Synchronization yok

---

### 1.11 Enum Singleton (En Güvenlisi)

```java
public enum SingletonEnum {
    INSTANCE;

    public void doSomething() {
        System.out.println("Doing something");
    }
}
```

Avantaj:


* Reflection'a karşı korumalı

---

### 1.12 Best Practice'ler

* Production'da genelde **Enum veya Bill Pugh** tercih edilir
* Constructor içinde reflection kontrolü yapılabilir


### 1.13 Pitfall'lar

* Global state → test yazmayı zorlaştırır
* Distributed sistemlerde tek instance garanti değildir

---

## 2. Factory Design Pattern
### Amaç

Factory Design Pattern, nesne oluşturma (object creation) sürecini merkezi bir yapıya taşıyarak, client kodun hangi class’tan nesne üretileceğini bilmeden çalışmasını sağlar.

Temel problem şudur:

Uygulama içinde sürekli new keyword’ü ile nesne üretmek, kodu zamanla katı (tight coupled) hale getirir.

Örneğin:

Shape shape = new Circle();

Bu kullanımda:

Client doğrudan Circle class’ına bağımlıdır
Yarın Circle yerine Triangle kullanmak istersek → kod değişir
Bu da Open/Closed Principle ihlalidir

Factory pattern bu problemi çözer ve şunu sağlar:

“Nesneyi nasıl oluşturduğum önemli değil, bana gerekli olan nesneyi ver.”

Temel Mantık

Factory pattern'de:

Nesne oluşturma logic’i ayrı bir sınıfa taşınır
Client sadece factory çağırır
Factory hangi nesnenin üretileceğine karar verir

Yani:

Object creation logic merkezi hale getirilir ve soyutlanır

### Factory Pattern Türleri
### 2.1 Simple Factory (Static Factory)

En basit hali.

```
interface Payment {
    void pay();
}

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

class PaymentFactory {

    public static Payment createPayment(String type) {
        switch (type) {
            case "CREDIT":
                return new CreditCardPayment();
            case "PAYPAL":
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
    }
}
```

Kullanım:
```
Payment payment = PaymentFactory.createPayment("CREDIT");
payment.pay();
```

### 2.2 Factory Method Pattern

Burada creation logic subclass’lara bırakılır.

```
abstract class PaymentCreator {
    public abstract Payment createPayment();

    public void processPayment() {
        Payment payment = createPayment();
        payment.pay();
    }
}

class CreditCardCreator extends PaymentCreator {
    public Payment createPayment() {
        return new CreditCardPayment();
    }
}
Kullanım:
PaymentCreator creator = new CreditCardCreator();
creator.processPayment();
```

### 2.3 Abstract Factory Pattern


Birbiriyle ilişkili nesne gruplarını üretmek için kullanılır.

```
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

class WindowsButton implements Button {
    public void render() {
        System.out.println("Windows Button");
    }
}

class WindowsCheckbox implements Checkbox {
    public void render() {
        System.out.println("Windows Checkbox");
    }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }

    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
```

### Initialization Yaklaşımları

Factory pattern'de farklı initialize yöntemleri vardır:

Static Factory Method
PaymentFactory.createPayment("CREDIT");

Instance Factory
PaymentFactory factory = new PaymentFactory();
factory.createPayment("CREDIT");

Dependency Injection ile Factory

(Spring tarzı)

```
@Component
class PaymentFactory {
    public Payment createPayment(String type) { ... }
}
```

### Best Practices:
Interface üzerinden dön → concrete class dönme
Factory logic’i büyürse → Factory Method’a geç

### Dezavantajlar:
Fazladan abstraction
Küçük projelerde over-engineering
Çok fazla class oluşabilir

## 3. Builder Design Pattern
## Amaç

Builder pattern, çok sayıda parametreye sahip nesneleri daha okunabilir, esnek ve hatasız şekilde oluşturmak için kullanılır.

Temel problem:

Çok parametreli constructor’lar okunamaz ve hata yapmaya çok açıktır.

Problem: Telescoping Constructor
User(String name)
User(String name, int age)
User(String name, int age, String email)
User(String name, int age, String email, String phone)

Bu yapı:

Anlaşılmaz
Parametre sırası karışabilir
Maintenance kabusudur
## 3.1 Builder Pattern Mantığı

Builder ile:

Nesne adım adım oluşturulur
Her field ayrı method ile set edilir
build() ile finalize edilir
## 3.2 Temel Builder Implementasyonu
```
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
        private String name;
        private int age;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
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
            return new User(this);
        }
    }
}
```
### 3.3 Kullanım
```
User user = new User.Builder()
        .name("Melih")
        .age(22)
        .email("melih@example.com")
        .build();
Advanced Builder (Validation + Required Fields)
public User build() {
    if (name == null) {
        throw new IllegalStateException("Name is required");
    }
    return new User(this);
}
```

### 3.4 Initialization Yaklaşımları
### Classic Builder
```
new User.Builder().name("A").build();
Step Builder (Advanced – enforce order)
interface NameStep {
    AgeStep name(String name);
}

interface AgeStep {
    BuildStep age(int age);
}

interface BuildStep {
    User build();
}
```

Bu yöntem ile:

Zorunlu alanlar enforce edilir
Compile-time güvenlik sağlanır

### Lombok Builder (Real-world)
```
@Builder
class User {
    private String name;
    private int age;
}
```


### 3.5 Ne Zaman Kullanılmaz?
2-3 field varsa → gereksiz
Basit DTO → constructor yeterli
### Dezavantajlar
Fazladan class / kod
Küçük objelerde overkill



---

### 3.6 Avantajlar

* Okunabilirlik
* Immutable object
* Optional parametre yönetimi

### 3.7 Best Practice'ler

* build() içinde validation yap
* Immutable class kullan
* Method chaining uygula

### 3.8 Pitfall'lar

* Gereksiz kullanım (küçük objelerde)

---

## 3.9 Genel Karşılaştırma

| Pattern   | Ne İşe Yarar             | Ne Zaman Kullanılır |
| --------- | ------------------------ | ------------------- |
| Singleton | Tek instance             | Shared resource     |
| Factory   | Nesne üretimi soyutlama  | Dinamik tip seçimi  |
| Builder   | Karmaşık nesne oluşturma | Çok parametre       |

---

## 3.10 Sonuç

Bu üç pattern birlikte kullanıldığında:

* Daha temiz kod
* Daha sürdürülebilir mimari
* Daha test edilebilir sistemler elde edilir

Gerçek projelerde genelde bu pattern'ler **Spring, Hibernate gibi framework'lerin içinde zaten kullanılır**.
