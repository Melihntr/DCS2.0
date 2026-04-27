# Behavorial Patterns
## 1. Strategy Design Pattern
#### 1.1 Amaç

Strategy pattern, bir algoritma ailesini tanımlayıp her birini ayrı sınıflar halinde kapsülleyerek, bu algoritmaların çalışma zamanında birbirleriyle değiştirilebilir olmasını sağlar. Bu yaklaşım, davranışın nesneden ayrılmasını ve esnek bir şekilde yönetilmesini mümkün kılar.

#### 1.2 Problem Tanımı

Bir sistemde aynı işlevi gerçekleştiren ancak farklı yöntemler kullanan birden fazla algoritma bulunduğunda, genellikle koşullu ifadeler (if-else veya switch-case) kullanılır. Bu durum zamanla kodun büyümesine, bakımının zorlaşmasına ve Open/Closed Principle ihlaline yol açar. Yeni bir algoritma eklemek mevcut kodu değiştirmeyi gerektirir.

#### 1.3 Yapı ve Çözüm

Strategy pattern, algoritmaları ortak bir arayüz altında toplar ve her algoritmayı bu arayüzü implemente eden ayrı sınıflar olarak tanımlar. Bu sayede istemci kodu yalnızca arayüzle etkileşime girer.

```
interface PaymentStrategy {
    void pay();
}

class CreditCardPayment implements PaymentStrategy {
    public void pay() {
        System.out.println("Paid with credit card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay() {
        System.out.println("Paid with PayPal");
    }
}
```
```
class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        strategy.pay();
    }
}
```
```
PaymentContext context = new PaymentContext(new CreditCardPayment());
context.execute();
```
#### 1.4 Çalışma Mantığı

Context sınıfı, algoritmanın nasıl çalıştığını bilmez; yalnızca ilgili arayüzü kullanır. Hangi algoritmanın çalışacağı çalışma zamanında belirlenir. Bu durum davranışın dinamik olarak değiştirilebilmesini sağlar.

#### 1.5 Kullanım Alanları

Bir işlemin birden fazla varyasyonu bulunduğunda
Koşullu ifadelerin büyüyerek karmaşık hale geldiği durumlarda
Algoritmaların runtime’da değiştirilebilir olması gerektiğinde

#### 1.6 Best Practices

Strategy sınıfları mümkün olduğunca stateless olmalıdır
Dependency injection ile strategy seçimi yapılmalıdır
Ortak davranışlar tekrar edilmemeli, abstraction doğru yapılmalıdır

#### 1.7 Dezavantajlar ve Pitfall’lar

Sınıf sayısında artış olabilir
Yanlış abstraction yapılırsa gereksiz karmaşıklık oluşur

##### 1.8 Özet

Strategy pattern, algoritmaların birbirinden bağımsız ve değiştirilebilir olmasını sağlayarak esnek ve genişletilebilir bir yapı sunar.

## 2. Observer Design Pattern
#### 2.1 Amaç

Observer pattern, bir nesnede meydana gelen durum değişikliklerinin bu nesneye bağlı diğer nesnelere otomatik olarak bildirilmesini sağlar. Bu yapı, publish-subscribe modelinin temelini oluşturur.

#### 2.2 Problem Tanımı

Bir nesnenin durum değişikliği birden fazla bileşeni etkiliyorsa, bu bileşenlerin manuel olarak çağrılması sistemde sıkı bağımlılığa neden olur. Ayrıca bu yaklaşım genişletilebilir değildir ve bakım maliyeti yüksektir.

#### 2.3 Yapı ve Çözüm

Observer pattern, Subject (gözlemlenen) ve Observer (gözlemleyen) olmak üzere iki temel bileşen içerir. Subject, observer listesi tutar ve değişiklik olduğunda tüm observer’lara bildirim gönderir.

```
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String message);
}

class UserObserver implements Observer {
    public void update(String message) {
        System.out.println("Received: " + message);
    }
}
```
```
class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
```

#### 2.4 Çalışma Mantığı

Subject üzerinde bir değişiklik gerçekleştiğinde, bu değişiklik tüm observer’lara iletilir. Observer’lar, subject hakkında doğrudan bilgi sahibi olmadan yalnızca gönderilen veriye göre işlem yapar.

#### 2.5 Kullanım Alanları

Event-driven sistemler
GUI framework’leri
Messaging ve notification sistemleri

#### 2.6 Best Practices

Observer listesi thread-safe olacak şekilde tasarlanmalıdır
Observer’ların unregister edilmesi unutulmamalıdır
Event objeleri kullanılarak daha esnek bir yapı kurulabilir

#### 2.7 Dezavantajlar ve Pitfall’lar

Observer’ların kaldırılmaması memory leak’e neden olabilir
Çok sayıda observer performans sorunlarına yol açabilir
Event akışının izlenmesi zor olabilir

#### 2.8 Özet

Observer pattern, bir nesnedeki değişikliklerin bağlı nesnelere otomatik olarak iletilmesini sağlayarak gevşek bağlı (loosely coupled) sistemler kurulmasına yardımcı olur.

## 3. Template Method Design Pattern
#### 3.1 Amaç

Template Method pattern, bir algoritmanın genel iskeletini tanımlar ve bu algoritmanın bazı adımlarının alt sınıflar tarafından özelleştirilmesine izin verir. Böylece algoritmanın genel yapısı korunurken esneklik sağlanır.

#### 3.2 Problem Tanımı

Birden fazla sınıfta benzer işlem adımları bulunmakta, ancak bu adımların bazıları farklı şekilde uygulanmaktadır. Bu durumda kod tekrarı oluşur ve süreç yönetimi zorlaşır.

#### 3.3 Yapı ve Çözüm

Abstract bir sınıf içinde algoritmanın genel akışı tanımlanır. Değişken adımlar abstract metotlar olarak bırakılır ve alt sınıflar tarafından implemente edilir.

```
abstract class DataProcessor {

    public final void process() {
        readData();
        processData();
        writeData();
    }

    protected abstract void readData();
    protected abstract void processData();
    protected abstract void writeData();
}
```

```
class CSVProcessor extends DataProcessor {

    protected void readData() {
        System.out.println("Reading CSV data");
    }

    protected void processData() {
        System.out.println("Processing CSV data");
    }

    protected void writeData() {
        System.out.println("Writing CSV data");
    }
}
```

#### 3.4 Çalışma Mantığı

Template method (process) algoritmanın sırasını belirler ve bu metod genellikle final olarak tanımlanır. Alt sınıflar yalnızca belirli adımları override eder, algoritmanın genel yapısı değişmez.

#### 3.5 Kullanım Alanları

Sabit işlem adımlarına sahip ancak bazı varyasyonlar içeren sistemler
Framework tasarımı
Hook mekanizmaları

#### 3.6 Best Practices

Template method final olarak tanımlanmalıdır
Hook method’lar ile opsiyonel davranışlar sağlanabilir
Gereksiz inheritance kullanımından kaçınılmalıdır

#### 3.7 Dezavantajlar ve Pitfall’lar

Inheritance bağımlılığı oluşturur
Esneklik sınırlıdır
Alt sınıflar arasında karmaşıklık oluşabilir

#### 3.8 Özet

Template Method pattern, algoritmanın genel yapısını sabit tutarken belirli adımların özelleştirilmesine izin vererek kontrollü bir esneklik sağlar.

#### Genel Değerlendirme

Strategy pattern davranışı composition yoluyla değiştirilebilir hale getirir
Observer pattern değişikliklerin yayılmasını sağlar
Template Method pattern algoritma iskeletini koruyarak varyasyonlara izin verir

Bu üç pattern, behavioral kategoride farklı problem türlerini çözer ve birlikte kullanıldıklarında oldukça güçlü ve esnek yazılım mimarileri oluşturulabilir.
