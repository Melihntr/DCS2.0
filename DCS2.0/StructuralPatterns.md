# Structural Patterns

## 1. ADAPTER PATTERN

### 1.1 Amaç

Uyumsuz iki arayüzü birbirine bağlayarak birlikte çalışmasını sağlamak. Mevcut (çoğunlukla değiştiremeyeceğin) bir sınıfı, sisteminin beklediği arayüze “uydurur”.

### 1.2 Problem

Sistem şu kontratı bekliyor:

```

interface Payment {
    void pay();
}

Elindeki üçüncü parti kütüphane:

class PayPalAPI {
    void makePayment() { /* ... */ }
}
```

İsimler/arayüzler uyuşmuyor; doğrudan kullanamazsın.

### 1.3 Çözüm ve Yapı

Adapter, hedef arayüzü (Target) uygular ve içeride uyumsuz sınıfa (Adaptee) delegasyon yapar.

```

class PayPalAdapter implements Payment {

    private final PayPalAPI api;

    public PayPalAdapter(PayPalAPI api) {
        this.api = api;
    }

    @Override
    public void pay() {
        api.makePayment();
    }
}
```

### 1.4 Çalışma Mantığı
Client sadece Payment arayüzünü bilir.
Adapter, Payment çağrısını alır ve PayPalAPI.makePayment()’e çevirir.
Adaptee değiştirilmez; adaptasyon dışarıda yapılır.


### 1.5 Türler
Class Adapter (inheritance ile, Java’da genelde tercih edilmez)

Object Adapter (composition ile; pratikte yaygın olan)

### 1.6 Ne Zaman Kullanılır
3rd-party/legacy entegrasyon

Farklı servislerin tek bir kontrat altında toplanması

Mikroservis/SDK entegrasyonları

### 1.7 Ne Zaman Kullanılmaz
Zaten ortak bir arayüz varsa

Adaptasyon çok karmaşık dönüşümler gerektiriyorsa (mapping katmanı ayrı tasarlanmalı)

### 1.8 Best Practices
Adapter’ı ince ve tek sorumluluklu tut

Gerekirse mapping’i ayrı sınıflara böl

Exception ve hata durumlarını açıkça yönet

### 1.9 Pitfall’lar
Aşırı adapter katmanı (over-engineering)

Yanlış/eksik dönüşümler (silent data loss)

Performans maliyeti (özellikle ağır dönüşümlerde)


## 2. FACADE PATTERN

### 2.1 Amaç

Karmaşık bir alt sistemi basit bir arayüz üzerinden sunmak. Client’ın alt sistem detaylarını bilmesini engeller.

### 2.2 Problem

Bir işlem için birden fazla servisi sırayla çağırmak gerekiyor:

```
auth.login();
payment.process();
inventory.update();
notification.send();
```

Client, tüm bu bağımlılıkları ve sıralamayı bilmek zorunda.

### 2.3 Çözüm ve Yapı

Facade, bu çağrıları tek bir metoda toplar.

```
class OrderFacade {

    private final AuthService auth;
    private final PaymentService payment;
    private final InventoryService inventory;
    private final NotificationService notification;

    public OrderFacade(AuthService auth, PaymentService payment,
                       InventoryService inventory, NotificationService notification) {
        this.auth = auth;
        this.payment = payment;
        this.inventory = inventory;
        this.notification = notification;
    }

    public void placeOrder() {
        auth.login();
        payment.process();
        inventory.update();
        notification.send();
    }
}
```

Kullanım:

```
orderFacade.placeOrder();
```

### 2.4 Çalışma Mantığı

Client yalnızca Facade’i bilir.

Facade, alt sistemlerin orkestrasyonunu yapar.

Alt sistemler birbirinden bağımsız kalır.

### 2.5 Ne Zaman Kullanılır
Service layer/API gateway

Mikroservis orkestrasyonu

Karmaşık iş akışlarının sadeleştirilmesi

### 2.6 Ne Zaman Kullanılmaz
Basit sistemlerde gereksiz katman oluşturur

Her alt sistem çağrısının client tarafından bilinmesi isteniyorsa

### 2.7 Best Practices
Facade’i ince tut; iş kuralını şişirme

Birden fazla facade (use-case bazlı) tasarlayabilirsin

Transaction, hata yönetimi ve logging’i merkezileştirebilirsin

### 2.8 Pitfall’lar
God class’a dönüşme (aşırı büyüme)

Alt sistemleri tamamen gizleyip esnekliği azaltma

Çok genel tek bir facade yerine, use-case odaklı tasarlamamak


## 3. DECORATOR PATTERN

### 3.1 Amaç

Var olan bir nesneye, runtime’da, sınıfını değiştirmeden yeni davranışlar eklemek.

### 3.2 Problem

Bir Payment için opsiyonel özellikler eklemek istiyorsun:

logging
security
metrics

Inheritance ile:

LoggingPayment, SecureLoggingPayment, MetricsSecureLoggingPayment…
Kombinasyon patlar.

### 3.3 Çözüm ve Yapı

Decorator, aynı arayüzü uygular ve içindeki nesneyi “sararak” ek davranış katar.

```
interface Payment {
    void pay();
}

class CreditCardPayment implements Payment {
    public void pay() {
        System.out.println("Paid by credit card");
    }
}

abstract class PaymentDecorator implements Payment {
    protected final Payment wrapped;

    protected PaymentDecorator(Payment wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void pay() {
        wrapped.pay();
    }
}

class LoggingDecorator extends PaymentDecorator {

    public LoggingDecorator(Payment wrapped) {
        super(wrapped);
    }

    @Override
    public void pay() {
        System.out.println("Logging start");
        super.pay();
        System.out.println("Logging end");
    }
}
```

Kullanım:

```
Payment p = new LoggingDecorator(new CreditCardPayment());
p.pay();
```

### 3.4 Çalışma Mantığı
Her decorator aynı arayüzü uygular

Çağrıyı önce/sonra zenginleştirir

Birden fazla decorator zincirlenebilir

### 3.5 Ne Zaman Kullanılır
Cross-cutting concerns (logging, security, metrics)

Dinamik özellik ekleme

AOP benzeri davranışlar

### 3.6 Ne Zaman Kullanılmaz
Basit ve sabit davranışlar için

Derin decorator zincirleri gerekecekse (okunabilirlik düşer)

### 3.7 Best Practices
Küçük ve tek sorumluluklu decorator’lar

Sıralamanın etkisini dokümante et

Immutable veya side-effect kontrollü davranışlar tercih et

### 3.8 Pitfall’lar
Debug zorluğu (çok katman)

Yanlış sıralama (ör. security, logging sırası)

Gereksiz sarma (overuse)


## 4. BRIDGE PATTERN

### 4.1 Amaç

Abstraction (yüksek seviye kavram) ile Implementation (detay) katmanlarını ayırarak bağımsız geliştirme ve genişletme sağlamak.

### 4.2 Problem

İki boyut birlikte değişiyor:

Shape (Circle, Square)
Color (Red, Blue)

Inheritance ile:


RedCircle, BlueCircle, RedSquare, BlueSquare …
Kombinasyon sayısı katlanır.

### 4.3 Çözüm ve Yapı

Abstraction, Implementation’a referans tutar (composition). İki eksen ayrılır.

```
interface Color {
    void applyColor();
}

class Red implements Color {
    public void applyColor() {
        System.out.println("Red");
    }
}

abstract class Shape {
    protected final Color color;

    protected Shape(Color color) {
        this.color = color;
    }

    abstract void draw();
}

class Circle extends Shape {

    public Circle(Color color) {
        super(color);
    }

    @Override
    void draw() {
        System.out.print("Circle ");
        color.applyColor();
    }
}
```

Kullanım:

```
Shape s = new Circle(new Red());
s.draw();
```

### 4.4 Çalışma Mantığı
Shape (abstraction) ve Color (implementation) bağımsız hiyerarşiler

Composition ile bağlanır

Her iki eksen ayrı ayrı genişletilebilir


### 4.5 Ne Zaman Kullanılır
İki veya daha fazla bağımsız değişim ekseni varsa

Çapraz kombinasyonlar artıyorsa

Framework/SDK tasarımlarında


### 4.6 Ne Zaman Kullanılmaz
Tek eksen değişiyorsa

Küçük ve sabit kombinasyonlar varsa


### 4.7 Best Practices
Abstraction ve Implementation sınırlarını net çiz

Gereksiz soyutlama ekleme

Bağımlılıkları constructor ile enjekte et

### 4.8 Pitfall’lar
Over-engineering (gereksiz soyutlama)

Yanlış ayrım (gerçekte bağımsız olmayan eksenleri ayırmak)

Okunabilirliği düşüren fazla katman
