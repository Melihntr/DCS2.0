S# Java Creational (Yaratımsal) Design Pattern'ler

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

### Amaç (Detaylı Açıklama)

Singleton Design Pattern, Creational Design Pattern (yaratımsal tasarım desenleri) grubunda yer alır ve temel amacı bir sınıftan çalışma zamanı boyunca yalnızca **tek bir nesne (instance)** oluşturulmasını garanti etmektir.

Bu pattern aslında çok kritik bir problemi çözer:

> "Bir nesnenin kontrolsüz şekilde birden fazla oluşturulmasını nasıl engellerim?"

Çünkü büyük sistemlerde aynı class'tan birden fazla instance oluşturulması:

* veri tutarsızlığına
* gereksiz memory kullanımına
* concurrency problemlerine
  sebep olabilir.

---

### Kullanım Amacı (Detaylı)

Singleton pattern aşağıdaki durumlarda tercih edilir:

* Birden fazla sınıfın aynı instance üzerinde işlem yapması gerekiyorsa
* Uygulama genelinde tek bir state tutulacaksa
* Kaynak yönetimi kritikse (örn: database connection pool, logger)
* Nesnenin unique olması gerekiyorsa

Örneğin:

* Logger sistemi → Her yerden aynı logger kullanılmalı
* Config reader → Tek bir config objesi yeterli

---

### Temel Mantık (Adım Adım)

Singleton pattern uygulanırken şu kurallar uygulanır:

1. Constructor **private** yapılır
   → Böylece dışarıdan `new` ile instance oluşturulamaz

2. Sınıf içinde **static bir instance** tutulur

3. Dış dünyaya bu instance'ı veren bir **getInstance() metodu** sağlanır

Bu yapı sayesinde:

* Nesne creation kontrol altına alınır
* Tek instance garantilenir

---

### Yapısal Mantık (Behavior Açıklaması)

* `createSingleton()` → sadece sınıfın kendi içinde çalışır
* `getInstance()` → dış dünyaya erişim sağlar

Yani:

> Object creation logic dışarıdan tamamen gizlenir

---

### Neden Singleton Kullanırız?

#### 1. Kontrollü Erişim

Tek bir instance olduğu için o nesneye erişim tamamen kontrol altındadır.

#### 2. Global Variable Alternatifi

Global değişkenler:

* kontrolsüzdür
* her yerden değiştirilebilir

Singleton:

* encapsulation sağlar
* erişimi merkezi hale getirir

#### 3. Lazy Initialization (Önemli Nokta)

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
* Serialization dikkatli yönetilmeli

---

### Singleton Pattern Dezavantajları

Her ne kadar güçlü olsa da bazı önemli dezavantajları vardır:

* Global state oluşturur → test yazmayı zorlaştırır
* Dependency Injection ile çakışabilir
* Multithreading ortamda yanlış implement edilirse bug üretir

---

### 1.1 Eager Initialization

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

### 1.2 Lazy Initialization

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

### 1.3 Thread-Safe (Synchronized)

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

### 1.4 Double Checked Locking (DCL)

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

### 1.5 Bill Pugh (En Önerilen)

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

### 1.6 Enum Singleton (En Güvenlisi)

```java
public enum SingletonEnum {
    INSTANCE;

    public void doSomething() {
        System.out.println("Doing something");
    }
}
```

Avantaj:

* Serialization safe
* Reflection'a karşı korumalı

---

### Best Practice'ler

* Production'da genelde **Enum veya Bill Pugh** tercih edilir
* Constructor içinde reflection kontrolü yapılabilir
* Serialization için `readResolve()` override edilebilir

### Pitfall'lar

* Global state → test yazmayı zorlaştırır
* Distributed sistemlerde tek instance garanti değildir

---

## 2. Factory Pattern

### Amaç (Detaylı Açıklama)

Factory Design Pattern, nesne oluşturma (object creation) sürecini client (kullanan sınıf) kodundan ayırmak için kullanılır.

Temel problem şudur:

> "Kod içinde sürekli new kullanarak farklı tipte nesneler oluşturmak sistemi nasıl etkiler?"

Eğer her yerde `new` ile nesne oluşturursak:

* Kod sıkı bağlı (tight coupling) hale gelir
* Değişiklik yapmak zorlaşır
* Yeni tip eklemek tüm sistemi etkiler

Factory pattern bu problemi çözerek şunu sağlar:

> "Nesnenin nasıl oluşturulacağını değil, sadece ne istediğimizi söyleriz"

---

### Temel Mantık

Factory pattern'de:

* Nesne oluşturma sorumluluğu ayrı bir sınıfa verilir
* Client sadece factory'yi çağırır
* Hangi class'ın üretileceğini factory belirler

Yani:

> Object creation logic merkezi hale getirilir

---

### Neden Factory Kullanırız?

#### 1. Loose Coupling (Gevşek Bağlılık)

Client kod, concrete class'lara bağımlı olmaz.

Örnek:

```java
Shape shape = ShapeFactory.createShape("CIRCLE");
```

Client artık `Circle` class'ını bilmek zorunda değil.

---

#### 2. Open/Closed Principle

Yeni bir class eklemek istediğimizde:

* Mevcut kodu değiştirmeyiz
* Sadece factory'yi genişletiriz

---

#### 3. Merkezi Kontrol

Tüm object creation tek bir yerden yönetilir.

Bu sayede:

* Logging eklenebilir
* Caching yapılabilir
* Object pool uygulanabilir

---

### Gerçek Hayat Mantığı

Factory'yi şu şekilde düşünebilirsin:

> Bir restoranda yemek sipariş ediyorsun.

* Sen "pizza" diyorsun
* Nasıl yapıldığı seni ilgilendirmez
* Mutfak (factory) üretimi yapar

---

### Kullanım Senaryoları

* Payment sistemleri (CreditCard, PayPal vs.)
* Notification sistemleri (Email, SMS)
* Database driver seçimi
* UI component üretimi

---

### Dezavantajlar

* Fazladan abstraction
* Küçük projelerde gereksiz karmaşıklık

---

## 3. Builder Pattern

### Amaç (Detaylı Açıklama)

Builder pattern, çok sayıda parametreye sahip karmaşık nesneleri daha okunabilir ve kontrollü bir şekilde oluşturmak için kullanılır.

Temel problem:

> "Çok parametreli constructor'lar nasıl yönetilir?"

---

### Problem: Telescoping Constructor

```java
User(String name)
User(String name, int age)
User(String name, int age, String email)
User(String name, int age, String email, String phone)
```

Bu yaklaşım:

* Okunamaz
* Hata yapmaya açık
* Sürdürülemez

---

### Builder Mantığı

Builder pattern bu problemi şu şekilde çözer:

* Nesne adım adım oluşturulur
* Her parametre ayrı method ile set edilir
* Sonunda build() çağrılır

Yani:

> "Nesne creation süreci parçalara bölünür"

---

### Neden Builder Kullanırız?

#### 1. Okunabilirlik

```java
User user = new User.Builder()
    .setName("Melih")
    .setAge(22)
    .setEmail("melih@example.com")
    .build();
```

Bu yapı constructor'a göre çok daha anlaşılırdır.

---

#### 2. Optional Parametre Yönetimi

Her alan zorunlu değildir.

---

#### 3. Immutable Object

Builder ile oluşturulan objeler immutable yapılabilir:

* Thread-safe olur
* Yanlışlıkla değiştirilemez

---

#### 4. Validation Kontrolü

build() içinde:

* zorunlu alanlar kontrol edilir
* invalid state engellenir

---

### Gerçek Hayat Mantığı

Builder pattern şu duruma benzer:

> Burger siparişi veriyorsun

* Ekstra peynir
* Soğan yok
* Çift köfte

Her şeyi adım adım seçiyorsun → en sonunda oluşturuluyor

---

### Kullanım Senaryoları

* DTO / Entity oluşturma
* API request objeleri
* Config objeleri
* Complex domain model'ler

---

### Dezavantajlar

* Küçük objelerde gereksizdir
* Fazladan class yazımı

---

### Amaç

Karmaşık nesneleri adım adım oluşturmak.

### Problem (Telescoping Constructor)

```java
User(String name)
User(String name, int age)
User(String name, int age, String email)
```

Bu yapı sürdürülemez.

---

### Çözüm: Builder

```java
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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

Kullanım:

```java
User user = new User.Builder()
    .setName("Melih")
    .setAge(22)
    .setEmail("melih@example.com")
    .build();
```

---

### Avantajlar

* Okunabilirlik
* Immutable object
* Optional parametre yönetimi

### Best Practice'ler

* build() içinde validation yap
* Immutable class kullan
* Method chaining uygula

### Pitfall'lar

* Gereksiz kullanım (küçük objelerde)

---

## Genel Karşılaştırma

| Pattern   | Ne İşe Yarar             | Ne Zaman Kullanılır |
| --------- | ------------------------ | ------------------- |
| Singleton | Tek instance             | Shared resource     |
| Factory   | Nesne üretimi soyutlama  | Dinamik tip seçimi  |
| Builder   | Karmaşık nesne oluşturma | Çok parametre       |

---

## Kritik Notlar (Interview Seviyesi)

* Singleton çoğu zaman DI ile replace edilir
* Factory → polymorphism'i zorlar
* Builder → immutable design ile birlikte düşünülmeli

---

## Sonuç

Bu üç pattern birlikte kullanıldığında:

* Daha temiz kod
* Daha sürdürülebilir mimari
* Daha test edilebilir sistemler elde edilir

Gerçek projelerde genelde bu pattern'ler **Spring, Hibernate gibi framework'lerin içinde zaten kullanılır**.
