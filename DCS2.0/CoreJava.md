# 1. Java Ekosistemi 

1.1  Java Virtual Machine (JVM), Java programlarını belleğe yükler ve çalıştırır. Bu sayede, değişiklik yapmadan tüm işletim sistemlerinde çalışmasını sağlar. JVM, bellek yönetimi, garbage collection ve multi-threading gibi özellikler sağlar.

1.2 Java Runtime Environment (JRE), Java standart kütüphanelerini içerir, örneğin matematik işlemleri, istisna işlemleri (exception), Throwable sınıfı gibi.

1.3 Java Development Kit (JDK), Java dosyalarını çalıştırılabilir class dosyalarına dönüştüren javac derleyicisini içerir. Ayrıca, built-in kütüphanelerin kaynak kodları, hata ayıklama araçları, görüntüleme araçları, JAR dosyası oluşturma ve yönetme araçları, test araçları gibi geliştirme araçları da bulunur.

1.4 Hiyerarşi şu şekildedir: JDK'nın içinde JRE ve Java Standart Kütüphanesi bulunur. JRE'nin içinde ise JVM yer alır.

1.5 JDK, JRE, JVM Farkı

-   JDK (Java Development Kit): Java geliştirme ortamıdır. JDK, Java programları geliştirmek, derlemek ve çalıştırma için gerekli araçları içerir. JDK, JRE'yi içerir.
-   JRE (Java Runtime Environment): Java çalışma ortamıdır. JRE, Java programlarını çalıştırma için gerekli bileşenleri içerir. JRE, JVM'yi içerir.
-   JVM (Java Virtual Machine): Java sanal makinedir. JVM, Java programlarını çalıştırma için gerekli ortamı sağlar.

1.6 Hangisini Ne Zaman Kullanırım-Kullanmalıyım?

-   JDK: Java programları geliştirmek, derlemek, debugging ve daha fazlası için JDK kullanmalısınız.
-   JRE: Java programlarını çalıştırma için JRE kullanmalısınız.
-   JVM: JVM, JRE'nin bir bileşenidir. JVM'yi doğrudan kullanmazsınız.

1.7 Backward Compatibility

Backward compatibility, eski sistemlerin, yeni sistemlerle uyumlu çalışabilme yeteneğidir. Java'da backward compatibility, eski Java programlarının, yeni Java sürümlerinde çalışabilmesini sağlar.

Java Backward Compatibility Mekanizması

Java backward compatibility mekanizması, aşağıdaki bileşenleri içerir:

1.  ClassLoader: ClassLoader, sınıfları yükler ve onları çalışma ortamına hazırlar. ClassLoader, sınıfları önce kendi klasöründen yükler, sonra üst klasörlerinden yükler.
2.  Class Version: Java sınıf dosyaları, sürüm bilgisini içerir. JRE, sınıf dosyasının sürümünü kontrol eder ve eğer sürüm uyumluysa, sınıf dosyasını yükler.
3.  Method Resolution: JRE, metodları çözme işlemi sırasında, eski metodları yeni metodlarla uyumlu hale getirir.

1.8 Java Sürüm Uyumluluğu

Java sürüm uyumluluğu, aşağıdaki şekilde çalışır:

-   İleri uyumluluk: Yeni Java sürümleri, eski Java sürümlerinde çalışan programları çalıştırabilir.
-   Geriye uyumluluk: Eski Java sürümleri, yeni Java sürümlerinde çalışan programları çalıştıramayabilir.

Java Backward Compatibility Örneği

```
// Java 8'de çalışan bir program
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

```

Bu program, Java 11'de de çalışabilir. Ancak, eğer program Java 11'de yeni bir özellik kullanıyorsa, Java 8'de çalışmayabilir.

1.9 JAR (Java Archive)

JAR, Java sınıf dosyalarını, kaynak kodları ve diğer dosyaları içeren bir arşiv formatıdır. JAR, Java programlarını dağıtmak ve çalıştırma için kullanılır.

JAR Nasıl Oluşturulur?

JAR oluşturmak için, aşağıdaki adımları takip edebilirsiniz:

1.  javac: Java sınıf dosyalarını derleyin. Örneğin:
    
    ```
    javac *.java
    ```
    
2.  jar: JAR dosyasını oluşturun. Örneğin:
    
    ```
    jar cvf myjar.jar *.class
    ```
    

JAR Örneği

```
// Java sınıf dosyasını derleyin
javac HelloWorld.java

// JAR dosyasını oluşturun
jar cvf HelloWorld.jar HelloWorld.class

```

JAR Çalıştırma

JAR dosyasını çalıştırma için, aşağıdaki komutu kullanabilirsiniz:

```
java -jar HelloWorld.jar
```
 
 # 2. Değişkenler
Değişkenler, programda değer depolamak için kullanılan isimlerdir. Java'da değişkenler, veri türlerine göre sınıflandırılır.


2.1 Dil Temelleri 

    Primitif Veri Türleri:
    ```
	Range formulü:
	(-(2^n-1) , (2^n-1)-1)
	```
	
byte: 8 bitlik işaretli tamsayı (-128 ila 127)
	short: 16 bitlik işaretli tamsayı (-32.768 ila 32.767)
	int: 32 bitlik işaretli tamsayı (-2.147.483.648 ila 2.147.483.647)
	long: 64 bitlik işaretli tamsayı (-9.223.372.036.854.775.808 ila 9.223.372.036.854.775.807)
	float: 32 bitlik kayan noktalı sayı
	double: 64 bitlik kayan noktalı sayı
	boolean: mantıksal değer (true veya false)
	char: 16 bitlik Unicode karakter

2.2 Referans Veri Türleri:

	Diziler (array)
	Sınıflar (class)
	Arayüzler (interface)

2.3 Control Flow

Control flow, programın akışını kontrol eden yapıların bütünüdür.

2.3.1 If-Else Yapısı

	If-else yapısı, belirli bir koşulun gerçekleşip gerçekleşmediğini kontrol etmek için kullanılır.
	
2.3.2 Switch Yapısı

	Switch yapısı, bir değişkenin değerine göre farklı kod bloklarını çalıştırmak için kullanılır.
	
2.3.3 Loop Yapıları

	Loop yapıları, belirli bir kod bloğunu tekrar tekrar çalıştırmak için kullanılır.

For Loop:
	Kullanım Alanı: Belirli bir sayıda iterasyon yapmak için kullanılır.
	Özellikleri:
	Başlangıç, bitiş ve artış değerlerini belirtir.
	Döngü değişkeni otomatik olarak artırılır.
	Döngü koşulu başlangıçta kontrol edilir.
	```
	for (int i = 0; i < 5; i++){
	    System.out.println(i);
	}
	```

While Loop:
	Kullanım Alanı: Belirli bir koşul gerçekleşirken döngüyü devam ettirmek için kullanılır.
	Özellikleri:
	Sadece döngü koşulunu belirtir.
	Döngü değişkenini manuel olarak artırmanız gerekir.
	Döngü koşulu başlangıçta kontrol edilir.
	```
	int i = 0;
	while (i < 5) {
	    System.out.println(i);
	    i++;
	}
	```
Do-While Loop:
Kullanım Alanı: En az bir kez döngüyü çalıştırıp, belirli bir koşul gerçekleşirken döngüyü devam ettirmek için kullanılır.
	Özellikleri:
	Döngü koşulu döngü sonunda kontrol edilir.
	Döngü değişkenini manuel olarak artırmanız gerekir.
	En az bir kez döngü çalışır.
	```
	int i = 0;
	do {
	    System.out.println(i);
	    i++;
	} while (i < 5);
	```

2.4 Ternary Operator (Üçlü Operatör)

Ternary operator, Java'da kullanılan bir operatördür. Bu operatör, bir koşulun sonucuna göre iki değerden birini döndürür.

Ternary Operator Syntax

Ternary operator'un syntax'ı aşağıdaki gibidir:

```
koşul ? değer1 : değer2;

```

Ternary Operator Nasıl Çalışır?

Ternary operator, aşağıdaki şekilde çalışır:

1.  Koşul Değerlendirmesi: İlk olarak, koşul değerlendirilir.
2.  Koşul Doğru ise: Eğer koşul doğru ise, değer1 döndürülür.
3.  Koşul Yanlış ise: Eğer koşul yanlış ise, değer2 döndürülür.

Ternary Operator Örneği

```
int x = 5;
String sonuc = x > 10 ? "x 10'dan büyük" : "x 10'dan küçük";
System.out.println(sonuc); // x 10'dan küçük
```
2.5 Static Anahtar Kelimesi
```
static
```

anahtar kelimesi, bir değişkenin, yöntemin veya bloğun sınıf seviyesinde olduğunu belirtmek için kullanılır. Bu, o değişken, yöntem veya blok'a sınıfın tüm örnekleri tarafından erişilebileceği anlamına gelir.

Static Değişkenler:

-   Sınıf seviyesinde tanımlanır.
-   Sınıfın tüm örnekleri tarafından paylaşılır.
-   Sınıfın yüklenmesiyle birlikte bellekte yer kaplar.
-   Programın çalışması boyunca bellekte kalır.

Static Yöntemler:

-   Sınıf seviyesinde tanımlanır.
-   Sınıfın tüm örnekleri tarafından çağrılabilir.
-   Static yöntemler, sadece static değişkenlere ve diğer static yöntemlere erişebilir.

Static Blok:

-   Sınıfın yüklenmesiyle birlikte çalışan kod bloklarıdır.
-   Static bloklar, sınıfın yüklenmesi sırasında sırasıyla çalıştırılır.

Java'da, static değerler  Method Area'da depolanır. Method Area, Java Virtual Machine (JVM) tarafından yönetilen bir bellek alanıdır. Bu alanda, sınıfın metadata'sı, static değişkenler, methodlar ve diğer sınıf-level veri yapıları depolanır.

Method Area,  PermGen  (Java 7 ve öncesi) veya  Metaspace  (Java 8 ve sonrası) olarak da adlandırılır. Bu bellek alanı, sınıfın yüklenmesi sırasında oluşturulur ve sınıfın tüm yaşam döngüsü boyunca varlığını sürdürür.

GC (Garbage Collector), Method Area'da depolanan static değerleri temizlemez. Bunun nedeni, static değerlerin sınıfın tüm örnekleri tarafından paylaşılması ve sınıfın yüklenmesi sırasında oluşturulmasıdır. GC, yalnızca nesnelerin ve referansların yönetimiyle ilgilenir ve Method Area'da depolanan static değerleri dikkate almaz.

Örnek:

```
public class StaticOrnek {
    private static int sayac = 0; // Static değişken

    public StaticOrnek() {
        sayac++;
    }

    public static void main(String[] args) {
        System.out.println("StaticOrnek.main()");
        StaticOrnek ornek1 = new StaticOrnek();
        StaticOrnek ornek2 = new StaticOrnek();
        System.out.println("Sayac: " + sayac);
    }
}

```

Bu örnekte,

```
sayac
```

adlı static değişken, sınıfın tüm örnekleri tarafından paylaşılır.

```
ornek1
```

ve

```
ornek2
```

nesnelerinin oluşturulması

```
sayac
```

değerini artırır. GC,

```
sayac
```

değişkenini dikkate almaz çünkü bu değişken sınıfın tüm örnekleri tarafından paylaşılır.

Sonuç:

Java'da

```
static
```

anahtar kelimesi, sınıf seviyesinde değişken, yöntem veya blok tanımlamak için kullanılır. Static değişkenler, sınıfın tüm örnekleri tarafından paylaşılır ve programın çalışması boyunca bellekte kalır. GC, static değişkenleri dikkate almaz çünkü bunlar sınıfın tüm örnekleri tarafından paylaşılır.

# 3. OOP Temelleri

 3.1 Encapsulation (Kapsülleme)

Encapsulation, bir nesnenin verilerini (özelliklerini) ve bu verileri işleyen metotları (fonksiyonları) bir araya getirmesidir. Bu sayede, nesnenin içindeki verilere dışarıdan erişim kısıtlanabilir ve kontrollü bir şekilde erişim sağlanabilir. Java'da encapsulation, erişim belirleyicileri (public, private, protected) kullanarak uygulanır.


"this" Anahtar Kelimesi

-   Bir sınıfın kendi örneğine (instance) referans verir.
-   Kullanım: Parametrelerle sınıf değişkenlerini ayırmak için.
    


Örnek:

```
public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}

```

Private : Yalnızca tanımlandığı sınıf içinde erişilebilir. Başka sınıflar veya kod parçaları bu öğeye doğrudan erişemez.

Protected: Sınıfın kendi paketi içinde veya sınıfın alt sınıfları( yani bu sınıftan türetilen diğer sınıflar ) tarafından erişilebilir. Ayrıca, farklı bir paketteyseniz, yalnızca alt sınıflar bu öğeye erişebilir.

Public: Herkes tarafından erişilebilir. Başka bir pakette veya aynı pakette olsanız da, herhangi bir sınıf bu öğeye erişebilir. Kısıtsız erişim bulunur.

Constructor Chaining (Constructor Zinciri)

-   Bir sınıfın birden fazla constructor'ı arasında devreye girme.
-   Kullanım: Belirli bir constructor'ın diğer constructor'ları çağırması.
    
    ```
    class Box {
        int length;
        
        Box() { // Default constructor
            this(5); // this(5) diğer constructor'ı çağırır.
        }
        
        Box(int l) { // Parametreli constructor
            this.length = l;
        }
    }
    ```
  

3.2 Inheritance (Miras)

Inheritance, bir nesnenin başka bir nesnenin özelliklerini ve davranışlarını devralmasıdır. Java'da inheritance, extends anahtar kelimesi kullanılarak uygulanır. Bir sınıf, başka bir sınıftan miras alabilir ve onun özelliklerini ve davranışlarını kendi üzerine ekleyebilir.

Örnek:

```
public class Animal {
    public void sound() {
        System.out.println("The animal makes a sound.");
    }
}

public class Dog extends Animal {
    public void sound() {
        System.out.println("The dog barks.");
    }
}
```

3.3 Polymorphism (Çok biçimlilik)

Polymorphism, bir nesnenin farklı şekillerde davranabilmesidir. Java'da polymorphism, method overriding (bir alt sınıfın üst sınıfın methodunu kendi özel şekilde tanımlaması) veya method overloading (aynı isimde farklı parametrelerle method tanımlama) kullanılarak uygulanır.

3.3.1 Runtime Polymorphism

-   Tanım: Runtime Polymorphism, bir nesnenin hangi metodun çağrılacağına çalışma zamanında karar verilmesi işlemidir.
-   Örnek: Bir üst sınıfın referansını kullanarak alt sınıfın metodunu çağırmak.
-   Kullanım Alanı: Runtime Polymorphism, nesnelerin davranışlarını çalışma zamanında değiştirmek için kullanılır.

3.3.2 Compile Time Polymorphism

-   Tanım: Compile Time Polymorphism, bir nesnenin hangi metodun çağrılacağına derleme zamanında karar verilmesi işlemidir.
-   Örnek: Fonksiyon overloading (aynı isimli farklı parametreli fonksiyonlar).
-   Kullanım Alanı: Compile Time Polymorphism, derleme zamanında metod çağrılarını belirlemek için kullanılır.


Runtime Polymorphism Örnekleri

```
// Runtime Polymorphism örneği
public class Hayvan {
    public void ses() {
        System.out.println("Hayvan ses ediyor");
    }
}

public class Köpek extends Hayvan {
    @Override
    public void ses() {
        System.out.println("Köpek havlıyor");
    }
}

public class Kedi extends Hayvan {
    @Override
    public void ses() {
        System.out.println("Kedi miyavlıyor");
    }
}

public class Main {
    public static void main(String[] args) {
        Hayvan hayvan = new Köpek();
        hayvan.ses(); // Köpek havlıyor

        hayvan = new Kedi();
        hayvan.ses(); // Kedi miyavlıyor
    }
}

```

Compile Time Polymorphism Örnekleri

```
// Compile Time Polymorphism örneği
public class Matematik {
    public int topla(int a, int b) {
        return a + b;
    }

    public double topla(double a, double b) {
        return a + b;
    }

    public int topla(int a, int b, int c) {
        return a + b + c;
    }
}

public class Main {
    public static void main(String[] args) {
        Matematik matematik = new Matematik();
        System.out.println(matematik.topla(1, 2)); // 3
        System.out.println(matematik.topla(1.5, 2.5)); // 4.0
        System.out.println(matematik.topla(1, 2, 3)); // 6
    }
}
```

3.4 Abstraction (Soyutlama)

Abstraction, karmaşık sistemleri basit ve anlaşılır hale getirmek için detayları gizleme ve sadece gerekli bilgileri ortaya koyma işlemidir. Java'da abstraction, abstract sınıflar ve interface'ler kullanılarak uygulanır.

Not: Java 8 sonrası Interface'lerde static methodlar kullanılarak body oluşturulabilir.

Örnek:

```
// Abstract sınıf
public abstract class AbstractAnimal {
    private String name;

    public AbstractAnimal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void makeSound();
}

// Interface
public interface Flyable {
    void fly();
}

// Hem abstract sınıfı genişleten hem de interface'i uygulayan sınıf
public class Bird extends AbstractAnimal implements Flyable {
    public Bird(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("Bird is singing");
    }

    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Main {
    public static void main(String[] args) {
        Bird bird = new Bird("Kuş");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();
    }
}
```
3.5 Coupling ve Cohesion Java'da Nedir?

Coupling (Bağlılık):

Coupling, bir yazılım sistemindeki sınıfların, modüllerin veya bileşenlerin birbirlerine ne kadar bağımlı olduğunu ölçen bir kavramdır. Java'da, coupling genellikle sınıfların birbirlerine ne kadar bağımlı olduğunu gösterir.

-   Tight Coupling (Sıkı Bağlılık):  Bir sınıfın diğer sınıflara çok fazla bağımlı olmasıdır. Bu durum, bir sınıfın değişmesi durumunda diğer sınıfların da etkilenmesine neden olabilir. Tight coupling, genellikle kötü bir tasarım pratikleridir çünkü kodun esnekliğini azaltır ve bakımını zorlaştırır.
    
-   Loose Coupling (Geçişli Bağlılık):  Bir sınıfın diğer sınıflara az bağımlı olmasıdır. Bu durum, bir sınıfın değişmesi durumunda diğer sınıfların etkilenmesini azaltır. Loose coupling, genellikle iyi bir tasarım pratikleridir çünkü kodun esnekliğini artırır ve bakımı kolaylaştırır.
    

Cohesion (Bütünlük):

Cohesion, bir sınıfın, modülün veya bileşenin kendi içinde ne kadar bütünleşik olduğunu ölçen bir kavramdır. Java'da, cohesion genellikle bir sınıfın kendi içinde ne kadar odaklanmış olduğunu gösterir.

-   High Cohesion (Yüksek Bütünlük):  Bir sınıfın kendi içinde bütünleşmiş ve odaklanmış olmasıdır. Bu durum, bir sınıfın kendi görevini yerine getirebildiğini gösterir. High cohesion, genellikle iyi bir tasarım pratikleridir çünkü kodun okunabilirliğini ve bakımı kolaylaştırır.
    
-   Low Cohesion (Düşük Bütünlük):  Bir sınıfın kendi içinde dağınık ve çoklu görevlerle yüklü olmasıdır. Bu durum, bir sınıfın kendi görevini yerine getiremediğini gösterir. Low cohesion, genellikle kötü bir tasarım pratikleridir çünkü kodun okunabilirliğini ve bakımı zorlaştırır.
    

Coupling ve Cohesion Java'da Neden Önemlidir?

Coupling ve cohesion, Java'da iyi bir kod tasarımı için kritik öneme sahiptir. Düşük coupling ve yüksek cohesion, kodun esnekliğini, okunabilirliğini ve bakımı kolaylaştırır. Ayrıca, bu prensipler, kodun daha sürdürülebilir ve entegre edilebilir hale gelmesine yardımcı olur.

3.6  Java'da Singleton Nedir?

Singleton, bir sınıfın yalnızca bir örneğini (instance) oluşturabilmesini sağlayan bir tasarım desenidir. Bu desen, bir sınıfın tek bir örneğini yönetmek ve bu örneği tüm sistem tarafından paylaşmak için kullanılır.

----------

3.6.1 Singleton'in Anahtar Özellikleri:

1.  Tek Örnek: Bir sınıfın yalnızca bir örneği oluşturulabilir.
2.  Global Erişim: Bu tek örneğe tüm sistem tarafından erişilebilir.

----------

3.6.2 Singleton Nasıl Çalışır?

-   Bir sınıfın constructor'ını gizler ve sınıfın kendi içinde bir örneğini oluşturur.
-   Bu örneği isteyenler için bir metot sağlar.

----------

Singleton Implementasyonları:

Simple Singleton (Static Method)

```
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {} // Constructor'ı gizle
    
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

```

Lazy Initialization (Gecikmeli Başlatma)

```
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {} // Constructor'ı gizle
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

```

Enum ile Singleton

```
public enum Singleton {
    INSTANCE;
    
    public void method() {
        // Singleton metotları
    }
}

```
3.6.3 Singleton Avantajları:

-   Kaynak Yönetimi: Singleton, kaynakları (örneğin, veritabanı bağlantıları) tek bir örnekte yöneterek performansı artırır.
-   Global Erişim: Singleton, tüm sistem tarafından erişilebilen bir nesne sağlar.



3.6.4 Singleton Dezavantajları:

-   Test Edilemezlik: Singleton'lar, test etme sürecinde zorlu olabilir.
-   Bağımlılık: Singleton'lar, sınıf bağımlılıklarını artıracaktır.
-   Concurrency (Eşzamanlılık) Sorunları: Çoklu thread'de Singleton'ın yönetimi zor olabilir.



3.6.5 Singleton Kullanım Durumları:

-   Veritabanı bağlantıları.
-   Cache yönetimi.
-   Logging (Günlük) sistemleri.
-   Ayarlar (Configuration) yönetimi

# 4. Object Class Metodları

Object sınıfı, Java'da tüm sınıfların üst sınıfıdır. Bu sınıf, nesnelerin temel davranışlarını tanımlar. Object sınıfının önemli metodları vardır. Bu metodlar, nesnelerin eşitliğini, hash kodlarını, kopyalarını ve string temsillerini belirler.

 4.1 toString() Metodu

metodu, nesnenin bir string temsilini döndürür. Bu metod, nesnenin detaylarını string formatında gösterir. Nesnenin string temsilini almak için kullanılır. Örneğin, bir nesneyi konsola yazdırırken

```
toString()
```

metodu kullanılır. Bu sayede, nesnenin detayları kolayca görülebilir.

4.2 equals() Metodu

metodu, nesnenin başka bir nesne ile eşit olup olmadığını kontrol eder. Bu metod, nesnelerin eşitliğini belirler. İki nesne eşit olduğunda, bu metod
```
true
```

döndürür. Nesnelerin eşitliğini kontrol etmek için kullanılır. Örneğin, bir nesneyi bir koleksiyonda ararken

```
equals()
```

metodu kullanılır.

4.2.1 equals() Metodu == Operatörü ve  Arasındaki Fark

 == operatörü, nesnelerin referanslarını karşılaştırır. Yani iki nesne aynı referansa sahip ise,

```
==
```

operatörü

```
true
```

döndürür. Ancak,

```
equals()
```

metodu, nesnelerin değerlerini karşılaştırır. Yani, iki nesne aynı değerlere sahip olsa bile, farklı referanslara sahip olabilirler.

Örneğin:

```
String str1 = new String("Hello");
String str2 = new String("Hello");

System.out.println(str1 == str2); // false
System.out.println(str1.equals(str2)); // true
```

4.3 hashCode() Metodu

metodu, nesnenin bir hash kodunu döndürür. Bu metod, nesnelerin hash kodlarını belirler. Hash kodları, nesnelerin benzersiz bir şekilde tanımlanmasına yardımcı olur. Nesnelerin hash kodlarını almak için kullanılır. Örneğin, bir nesneyi bir hash tablosunda saklarken

```
hashCode()
```

metodu kullanılır.

4.4 clone() Metodu

metodu, nesnenin bir kopyasını oluşturur. Bu metod, nesnelerin kopyalarını oluşturur. Nesnenin kopyası, orijinal nesne ile aynı özelliklere sahiptir. Nesnelerin kopyalarını oluşturmak için kullanılır. Örneğin, bir nesneyi değiştirmeden önce yedeklemek için

```
clone()
```

metodu kullanılır.

4.5 Doğru Nesne Davranışı

Doğru nesne davranışı, nesnelerin yukarıdaki metodları doğru şekilde uygulamasıdır. Bu sayede, nesnelerin eşitliği, hash kodları, kopyaları ve string temsilleri doğru şekilde belirlenebilir. Doğru nesne davranışı, programların daha güvenilir, daha hızlı ve daha kolay yönetilebilir olmasını sağlar.

Örnekler:

```
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Person(name, age);
    }
}

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("John", 30);
        Person person2 = new Person("John", 30);

        System.out.println(person1.toString()); // Person [name=John, age=30]
        System.out.println(person1.equals(person2)); // true
        System.out.println(person1.hashCode() == person2.hashCode()); // true

        try {
            Person personCopy = (Person) person1.clone();
            System.out.println(personCopy.toString()); // Person [name=John, age=30]
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
```

4.6 Reference vs Objects vs Instance vs Class

-   Reference (Referans):  Bir nesneye işaret eden bir değişken.
-   Object (Nesne):  Bellekte bir sınıfın örneği.
-   Instance (Örnek):  Bir sınıfın oluşturulduğu nesne.
-   Class (Sınıf):  Nesnelerin şablonu.
    
    ```
    class Car { // Class
        String color; // Özellik
    }
    
    Car myCar = new Car(); // myCar: Reference, new Car(): Instance
    ```

4.7 Record
Record sınıfları, bir veri yapısını temsil etmek için kullanılır ve veri yapısının özelliklerine doğrudan erişim sağlar. Örneğin, bir “Person” veri yapısını temsil etmek için bir record sınıfı oluşturabilir ve bu sınıfın özellikleri “name” (isim), “age” (yaş) ve “dob” (doğum tarihi) olabilir. Daha sonra, “Person” sınıfından nesneler oluşturabilir ve bu nesnelerin özelliklerine doğrudan erişebiliriz. Böylece kolaylıkla DTO’lar oluşturabiliriz.

# 5. Collections

5.1 Java'da koleksiyonlar, birden fazla nesneyi barındırabilen ve gerektiğinde nesne ekleme, silme gibi işlemlere imkan sağlayan nesnelerdir. Koleksiyonun içerisinde barındırdığı nesnelere "eleman" denir. Java, koleksiyon çatısı altında üç ana koleksiyon tipi sunmaktadır: Set, List ve Map.

5.2 List

-   List  arayüzü, sıralı bir koleksiyonu (dizi benzeri bir yapı) temsil eder.
-   List  içindeki öğelerin sırası önemlidir ve kullanıcı, listeye eklenen her öğenin tam yerini kontrol edebilir.
-   Listler, aynı öğelerin birden fazla kez bulunmasına izin verir (yani, yinelenen öğelere izin verir).
-   `ArrayList`,  `LinkedList`  gibi sınıflar  List arayüzünü uygular ve genellikle dinamik diziler olarak kullanılır.
- Bazı eylemlerin karmaşıklıkları şu şekildedir:

	-   Başa veya sona ekleme: O(1)
	-   Ortaya ekleme: O(n)
	-   Eleman arama: O(n)
	-   Başa veya sona silme: O(1)
	-   Ortaya silme: O(n)
	
5.2.1 Linked List ve ArrayList Farkları ve Benzerlikleri

Farklar:

1.  Depolama:
    
    -   ArrayList: Elemanlar birbiriyle devam eden bellek konumlarında depolanır.
    -   LinkedList: Elemanlar devam eden bellek konumlarında değil, her bir düğüm bir sonraki düğümü işaret eder.
2.  Zaman Kompleksiteleri:
    
    -   Erişim (Index ile):
        -   ArrayList: O(1) - Hızlı erişim.
        -   LinkedList: O(n) - Başlangıçtan itibaren arama gereklidir.
    -   Ekleme/Silme:
        -   ArrayList: O(n) - Genellikle sonda hızlıdır, ortada yavaştır.
        -   LinkedList: O(1) - Ekleme/silme hızlıdır (düğüm biliniyorsa).
3.  Bellek Kullanımı:
    
    -   ArrayList: Daha az bellek kullanır, ancak boyut artışı zamanında performans kaybı olabilir.
    -   LinkedList: Daha fazla bellek kullanır çünkü her düğüm bir sonraki ve önceki düğümü işaret eder.

Benzerlikler:

1.  Sıralı Veri Tutma: Her iki yapı da sıralı veri tutar.
2.  Dinamik Boyut: Her iki yapı da boyutunu değiştirebilir (eleman ekleme/silme).

5.3 Set

-   Set  arayüzü, yinelenen öğeler içermeyen bir koleksiyonu temsil eder.
-   Set  içinde her öğe benzersizdir ve her öğe için yalnızca bir kez yer alabilir.
-   Setlerin öğeleri belirli bir sırayla saklanmaz ve bu yüzden sıralı (indexed) erişim sağlamaz.
-   `HashSet`,  `LinkedHashSet`,  `TreeSet`  gibi sınıflar  `Set`  arayüzünü uygular ve genellikle matematiksel kümeler gibi davranırlar.
- Bazı eylemlerin karmaşıklıkları şu şekildedir:
	-   Eleman ekleme: O(1) (ortalama), O(n) (en kötü)
	-   Eleman arama: O(1) (ortalama), O(n) (en kötü)
	-   Eleman silme: O(1) (ortalama), O(n) (en kötü)
Set implementasyonu, elemanın zaten Set'te olup olmadığını kontrol eder.

Örneğin, HashSet implementasyonu bir hash tablosunu kullanarak elemanları depolar. Bir eleman HashSet'e eklendiğinde, elemanın hash kodu hesaplanır ve eleman, hash koduna göre hash tablosundaki uygun kova'ya eklenir. Eğer elemanın hash kodu, aynı kova'daki mevcut bir elemanın hash kodu ile eşleşirse, equals() metodu çağrılır ve yeni elemanın mevcut elemanla aynı olup olmadığı kontrol edilir. Eğer equals() metodu true dönerse, yeni eleman mevcut bir elemanın kopyası olarak kabul edilir ve Set'e eklenmez.

TreeSet, farklı mekanizmalar kullanarak yalnızca benzersiz elemanların Set'te depolandığını sağlamaktadır. Genellikle, Set implementasyonları, Set'e eklenen nesnelerin equals() ve hashCode() metodlarını kullanarak elemanın bir kopyası olup olmadığını belirler. Eğer iki nesne aynı hash koduna sahip ve equals() metodu tarafından eşit olarak bulunursa, bu nesneler birbirinin kopyası olarak kabul edilir ve Set'te yalnızca bir tane depolanır.

Not edilebilir ki, özel nesneleri bir Set'e eklerken, nesnelerin equals() ve hashCode() metodlarını doğru şekilde implement etmek önemlidir. Bu metodlar doğru şekilde implement edilmediği takdirde, Set'e gizli kopyalar eklenebilir veya eşit olarak görünen iki nesne farklı olarak işlenebilir.

5.4 Map
- Map  arayüzü, anahtar-değer çiftlerini saklayan bir koleksiyonu temsil eder.
-  Map  içinde her anahtar benzersizdir ve her anahtar yalnızca bir değere eşlenir.
- Mapler, öğeleri sıralı bir şekilde saklamaz; ancak bazı  `Map`  uygulamaları (`TreeMap`  gibi) öğeleri belirli bir sırayla tutar.
- `HashMap`,  `LinkedHashMap`,  `TreeMap`  gibi sınıflar  `Map`  arayüzünü uygular ve genellikle anahtar-değer tabanlı veri yapıları olarak kullanılır.
- Bazı eylemlerin karmaşıklıkları şu şekildedir:
	-   Eleman ekleme: O(1) (ortalama), O(n) (en kötü)
	-   Eleman arama: O(1) (ortalama), O(n) (en kötü)
	-   Eleman silme: O(1) (ortalama), O(n) (en kötü)

5.5 Kullanım Alanları
-   Set: Cache'leme, veri kümesi işlemleri, benzersiz değerlerin saklanması.
-   List: Sıralı veri işleme, kullanıcı listeleri, veri kümesi işlemleri.
-   Map: Anahtar-değer çiftlerinin saklanması, hızlı arama, veri kümesi işlemleri.

# 6. Functional Java 
Java 8 ile birlikte, fonksiyonel programlama konseptleri Java'ya entegre edilmiştir. Fonksiyonel programlama, programlamanın daha kısa, daha okunabilir ve daha verimli hale getirilmesine yardımcı olur.

6.1 Lambda İfadeleri
Lambda ifadeleri, anonim fonksiyonlar olarak da bilinir. Bir lambda ifadesi, bir fonksiyonu tanımlamak için kullanılır. Lambda ifadeleri, üç bölümden oluşur:

-   Parametreler: Fonksiyonun aldığı parametrelerdir.
-   Ok operatörü: Lambda ifadesini gösterir.
-   Gövde: Fonksiyonun yaptığı işlemlerdir.
- Örnek:

```
Runnable r = () -> System.out.println("Hello, World!");

```

Bu örnekte,

```
Runnable
```

arayüzünün

```
run()
```

metodu, lambda ifadesi ile tanımlanmıştır.

Not: Lambda ifadesi 1 satırdan uzunsa { } kullanılmalıdır.

6.2 ForEach Iterasyonu

```
forEach()
```

iterasyonu, Java 8 ile birlikte gelen bir yöntemdir. Bu yöntem, bir koleksiyonun her bir elemanını işlemek için kullanılır.

Basit ForEach Örneği

```
List<String> names = Arrays.asList("John", "Mary", "David");

names.forEach(name -> System.out.println(name));

```

Bu örnekte,

```
names
```

listesinin her bir elemanı

```
forEach()
```

yöntemi ile işlenir. Lambda ifadesi

```
name -> System.out.println(name)
```

her bir elemanı yazdırır.

ForEach ile Method Referansı

```
List<String> names = Arrays.asList("John", "Mary", "David");

names.forEach(System.out::println);

```

Bu örnekte,

```
System.out::println
```

method referansı kullanılır. Bu,

```
System.out.println()
```

metodunu her bir eleman için çağırır.

ForEach ile Consumer

```
import java.util.function.Consumer;

List<String> names = Arrays.asList("John", "Mary", "David");

Consumer<String> consumer = name -> System.out.println(name);

names.forEach(consumer);

```

Bu örnekte,

```
Consumer
```

arayüzü kullanılır.

```
Consumer
```

arayüzü, bir elemanı işleyen bir metod sağlar.

ForEach ile Index

```
List<String> names = Arrays.asList("John", "Mary", "David");

names.forEachIndexed((index, name) -> System.out.println(index + ": " + name));

```

Bu örnekte,

```
forEachIndexed()
```

yöntemi kullanılır. Bu yöntem, her bir elemanın indeksini ve değerini sağlar.

ForEach ile Parallel

```
List<String> names = Arrays.asList("John", "Mary", "David");

names.parallelStream().forEach(name -> System.out.println(name));

```

Bu örnekte,

```
parallelStream()
```

yöntemi kullanılır. Bu yöntem, koleksiyonun her bir elemanını paralel olarak işler.

ForEach Avantajları

-   Daha okunabilir kod sağlar.
-   Daha az kod yazmaya ihtiyaç duyulur.
-   Lambda ifadeleri ve method referansları kullanılabilir.

ForEach Dezavantajları

-   Sadece bir işlem yapabilir.
-   Elemanları değiştirmez.

ForEach Kullanım Alanları

-   Koleksiyonların her bir elemanını işlemek için.
-   Verileri yazdırmak için.
-   Verileri işlemek için.

6.3 Stream API

Stream API, verileri bir akış halinde işler ve sonuç olarak bir değer döndürür.

Stream API'nin Temel Kavramları

-   Stream: Veri akışı.
-   Intermediate operations: Verileri değiştiren işlemler (örneğin,
    
    ```
    filter()
    ```
    
    ,
    
    ```
    map()
    ```
    
    ).
-   Terminal operations: Verileri sonlandıran işlemler (örneğin,
    
    ```
    forEach()
    ```
    
    ,
    
    ```
    collect()
    ```
    
    ).

Stream API'nin Avantajları

-   Daha okunabilir kod sağlar.
-   Daha az kod yazmaya ihtiyaç duyulur.
-   Paralel işlemler yapılabilir.

Stream API'nin Temel Yöntemleri

-   ```
    filter()
    ```
    
    : Verileri filtreler.
-   ```
    map()
    ```
    
    : Verileri değiştirir.
-   ```
    reduce()
    ```
    
    : Verileri birleştirir.
-   ```
    forEach()
    ```
    
    : Verileri yazdırır.
-   ```
    collect()
    ```
    
    : Verileri toplar.

Not: Bu yöntemler  Apache Hadoop'un mapReduce() sisteminden esinlenilmiştir.

Örnek

```
List<String> names = Arrays.asList("John", "Mary", "David");

names.stream()
     .filter(name -> name.startsWith("D"))
     .forEach(System.out::println);

```

Bu örnekte,

```
names
```

listesi bir akış haline getirilir, ardından

```
filter()
```

işlemi ile "D" ile başlayan isimler filtrelenir ve sonuç olarak

```
forEach()
```

işlemi ile yazdırılır.

# 7. Exception Handling

Exception handling, Java'da hataları yönetmek için kullanılan bir mekanizmadır. Hatalar, programın çalışması sırasında ortaya çıkabilir ve programın çökmesine neden olabilir.

7.1 Checked & Unchecked Exceptions

-   Checked Exceptions: Bu tür istisnalar, derleme zamanında kontrol edilir. Programcı, bu tür istisnaları try-catch bloğu ile yakalamak zorundadır. Örnek:
    
    ```
    IOException
    ```
    
    ,
    
    ```
    SQLException
    ```
    
    .
-   Unchecked Exceptions: Bu tür istisnalar, derleme zamanında kontrol edilmez. Programcı, bu tür istisnaları try-catch bloğu ile yakalamak zorunda değildir. Örnek:
    
    ```
    NullPointerException
    ```
    
    ,
    
    ```
    ArrayIndexOutOfBoundsException
    ```
    
    .

7.2 Custom Exception

Custom exception, programcı tarafından tanımlanan bir istisna türüdür. Bu tür istisnalar, programın özel ihtiyaçlarına göre tanımlanabilir.

Custom Exception Örneği

```
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

```

Exception Handling Örneği

```
public class ExceptionHandlingExample {
    public static void main(String[] args) {
        try {
            // Kod burada
            int a = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Finally bloğu");
        }
    }
}

```

Try-Catch-Finally Blokları

-   Try: Kod burada
-   Catch: İstisna yakalama
-   Finally: Her zaman çalışan kod

Best Practices

-   İstisnaları mümkün olduğunca spesifik olarak yakalamak.
-   İstisnaları mümkün olduğunca kısa bir süre içinde yakalamak.
-   İstisnaları logging yapmak.
-   İstisnaları kullanıcıya bildirmek.

Throws ve Throw

-   Throws: Metodun throws ettiği istisnaları bildirir.
-   Throw: İstisna nesnesini fırlatır.

```
public class ThrowsExample {
    public static void main(String[] args) {
        try {
            methodThatThrowsException();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void methodThatThrowsException() throws Exception {
        throw new Exception("Istisna");
    }
}
```
7.3 Final Anahtar Kelimesi

```
final
```

anahtar kelimesi, Java'da değişkenler, metodlar ve sınıfların sabit (immutable) olmasını sağlamak için kullanılır. Bir nesne veya değişken

```
final
```

olarak işaretlendikten sonra, o nesne veya değişkenin değerinin değiştirilememesi veya yeniden atamasının yapılamaması anlamına gelir.

-   Final Değişkenler:  Bir değişken
    
    ```
    final
    ```
    
    olarak işaretlendikten sonra, o değişkenin değerinin yalnızca bir kez atanabilir. Örneğin:
    
    ```
    final int x = 10;
    x = 20; // Bu kod derleme hatası verir çünkü x final olarak işaretlenmiş.
    
    ```
    
-   Final Metodlar:  Bir metod
    
    ```
    final
    ```
    
    olarak işaretlendikten sonra, o metodun alt sınıflarda yeniden tanımlanamaz (override). Örneğin:
    
    ```
    class Animal {
        final void eat() {
            System.out.println("I am eating");
        }
    }
    
    class Dog extends Animal {
        // eat() metodunu yeniden tanımlayamazsınız çünkü final olarak işaretlenmiş.
    }
    
    ```
    
-   Final Sınıflar:  Bir sınıf
    
    ```
    final
    ```
    
    olarak işaretlendikten sonra, o sınıftan miras alınamaz. Örneğin:
    
    ```
    final class MyClass {
        // ...
    }
    
    class MySubClass extends MyClass { // Bu kod derleme hatası verir çünkü MyClass final olarak işaretlenmiş.
        // ...
    }
    
    ```
    

7.4 Finally Anahtar Kelimesi

```
finally
```

anahtar kelimesi, Java'da istisna (exception) yönetimi sırasında kullanılır.

```
try
```

bloğu içinde bir istisna oluşursa bile,

```
finally
```

bloğu her zaman yürütülür. Bu, kaynakların serbest bırakılması gibi temizleme işlemleri için kullanılır.

Örneğin:

```
try {
    // Kaynak açma
    FileReader fileReader = new FileReader("file.txt");
    // ...
} catch (FileNotFoundException e) {
    // Dosya bulunamadığında yürütülecek kod
} finally {
    // Kaynak serbest bırakma
    System.out.println("Kaynaklar temizleniyor...");
}

```

7.5  Finalize Metodu

```
finalize()
```

metodu, Java'da bir nesnenin yok edilmeden önce yürütülecek temizleme işlemleri için kullanılır. Bu metoda, nesne tarafından otomatik olarak çağrılmaz. Bunun yerine, Java Runtime Environment (JRE) tarafından, nesne yok edilmeden önce bu metoda çağrı yapabilir.

Örneğin:

```
public class MyClass {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Nesne temizleniyor...");
        super.finalize();
    }
}

```

Ancak,

```
finalize()
```

metodu kullanmak genellikle önerilmez çünkü:

-   Bu metoda JRE tarafından çağrılmayabilir.
-   Bu metoda çoklu çağrılar olabilir.
-   Bu metoda zamanInsensitive çağrılar olabilir.

Bu nedenle, kaynakların temizlenmesi için

```
try-with-resources
```

gibi daha güvenilir yöntemler tercih edilmelidir.

7.6 Serileştirme (Serialization)

Serileştirme, bir nesnenin bellekten bir byte dizisine çevrilmesini ve bu byte dizisinin dosyaya veya ağ üzerinden aktarılmasını sağlayan bir süreçtir. Java'da, bir sınıfın serileştirilebilir olmasını sağlamak için

```
Serializable
```

arayüzünü implement etmelidir.

Örneğin:

```
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    // Getters ve setters
}

```

Serileştirme işlemi genellikle

```
ObjectOutputStream
```

ve

```
ObjectInputStream
```

sınıflarıyla gerçekleştirilir.

Try-with-resources

```
try-with-resources
```

Java 7'de tanıtılmıştır ve kaynakların otomatik olarak temizlenmesini sağlar. Bu, kaynakların açık kalmasını ve bunun nedeniyle oluşan hataları önlemek için kullanılır.

Örneğin:

```
try (FileReader fileReader = new FileReader("file.txt")) {
    // Dosya işlemleri
} // try bloğu sonunda, fileReader otomatik olarak temizlenir.

```

Bu,

```
finally
```

bloğunda kaynakların temizlenmesi gereken durumlarda kullanmak yerine daha temiz ve güvenilir bir yöntem sunar.

# 8. Ekstra Notlar

1. StringBuilder vs StringBuffer

-   StringBuilder:  Thread-safe değil, performanslı string manipülasyonu.
-   StringBuffer:  Thread-safe, ancak daha yavaş.
-   Kullanım: Çoklu thread'de kullanılırsa StringBuffer, tek thread'de StringBuilder tercih edilir.
    
    ```
    StringBuilder sb = new StringBuilder();
    sb.append("Java");
    
    ```
    

2.  Variable Arguments (varargs)

-   Bir metodda değişken sayıda parametre alabilmek için kullanılır.
-   Kullanım:
    
    ```
    void printNumbers(int... nums) { ... }
    ```
    
    ```
    printNumbers(1, 2, 3); // 3 parametre
    printNumbers(4); // 1 parametre
    
    ```
    

3.  Autoboxing ve Unboxing

-   Autoboxing:  Primitive veri türlerini wrapper sınıflarına otomatik olarak dönüştürme.
-   Unboxing:  Wrapper sınıflarını primitive veri türlerine otomatik olarak dönüştürme.
    
    ```
    Integer num = 5; // Autoboxing
    int x = num; // Unboxing
    
    ```
    

4.  Interface vs Abstract Class

-   Interface:  Sadece soyutlamaları barındırır, implementasyonu sınıflar sağlar.
-   Abstract Class:  Soyutlamalar ve implementasyonları bir arada bulundurabilir.
    
    ```
    interface Flyable { // Interface
        void fly();
    }
    
    abstract class Animal { // Abstract Class
        abstract void eat();
    }
    
    ```
    

5.  Comparable vs Comparator

-   Comparable:  Bir sınıfın kendi sıralama mantığı.
-   Comparator:  Başka bir mantıkla sıralama sağlamak için kullanılır.
    
    ```
    class Student implements Comparable<Student> { // Comparable
        int id;
        public int compareTo(Student s) { ... }
    }
    
    Comparator<Student> comp = (s1, s2) -> s1.id - s2.id; // Comparator
    
    ```
    

6.  Static Nested Classes, Inner Classes, Local Classes, Anonymous Classes

-   Static Nested Class:  Sınıf içinde statik bir sınıf.
-   Inner Class:  Sınıf içinde non-static bir sınıf.
-   Local Class:  Bir metod içinde tanımlı bir sınıf.
-   Anonymous Class:  Isimsiz bir sınıf.
    
    ```
    class Outer { // Static Nested Class
        static class Nested {}
    }
    
    class Outer { // Inner Class
        class Inner {}
    }
    
    void method() { // Local Class
        class Local {}
    }
    
    interface Runnable { // Anonymous Class
        void run();
    }
    new Runnable() { // Anonymous Class
        @Override
        void run() {}
    };
    
    ```
    

7.  Stringlerde Concatenation (Birleştirme)

-   String'leri birleştirmek için
    
    ```
    +
    ```
    
    kullanılır.
-   Kullanım:
    
    ```
    String a = "Hello";
    String b = a + " World"; // "Hello World"
    
    ```
    
8.  Mutable vs Immutable

-   Mutable:  Değerleri değişebilir (örneğin,
    
    ```
    StringBuilder
    ```
    
    ).
-   Immutable:  Değerleri değişemez (örneğin,
    
    ```
    String
    ```
    
    ).
    
    ```
    String s = "Hello"; // Immutable
    s = s + " World"; // Yeni bir String oluşturulur.
    
    ```
    

9.  Stringlerin Immutable Olmasının Avantajları ve Dezavantajları

-   Avantajlar:
    -   Thread-safe.
    -   Performans kazançları.
-   Dezavantajlar:
    -   Sıkışık bir kullanımı nedeniyle performans kaybı olabilir.

10.  String Pool

-   Java'da String'lerin optimize edilmesi için bir bellek alanıdır.
-   Kullanım:
    
    ```
    String s = "Hello";
    ```
    
    Pool'dan alınır.
    
    ```
    String s1 = "Hello"; // Pool'da
    String s2 = new String("Hello"); // Heap'de
    
    ```
    

11.  Tüm Classların Süper Class'ı

-   ```
    Object
    ```
    
    sınıfıdır. Tüm sınıflar doğrudan veya dolaysız olarak
    
    ```
    Object
    ```
    
    sınıfından kalıtım alır.

12. Early Return

Java'da early return, bir metodun beklenenden önce geri dönmesi anlamına gelir. Yani, belirli bir koşul gerçekleştiğinde metodun geri kalan kodları çalışmadan önce sonuç döndürmesidir.

Early return, genellikle kodun okunabilirliğini ve sürdürülebilirliğini artırır. Özellikle uzun metodlarda veya kompleks koşullarda hangi kodların hangi durumlarda çalışacağını daha net bir şekilde görmek mümkündür.

Örneğin, normal if-else mantığında:

public void processOrder(Order order) {
    if (order != null) {
        if (order.getTotal() > 0) {
            // işleme devam et
            System.out.println("Order processed");
        } else {
            System.out.println("Order total must be greater than 0");
        }
    } else {
        System.out.println("Order cannot be null");
    }
}

Early return kullanarak:

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
Görüldüğü gibi early return ile kod daha lineer ve okunması daha kolay hale geldi. Artık hangi koşulların hangi mesajları yazdırdığını veya hangi kodların çalıştığını görmek daha kolay.

13.JVM'in Avantaj ve Dezavantajları

JVM'de Olmanın Avantajları:

Platform Bağımsızlığı: JVM, Java kodunu herhangi bir platformda çalıştırabilir. Bu, Java kodunun bir kez yazıldığı ve herhangi bir platformda çalıştırılabileceği anlamına gelir.
Hafıza Yönetimi: JVM, otomatik hafıza yönetimi sağlar. Bu, geliştiricilerin hafıza yönetimiyle uğraşmalarını gerektirmez.
Garbage Collection: JVM, otomatik olarak hafızadan kullanılmayan nesneleri temizler. Bu, hafıza sızıntılarını önler.
Dynamic Loading: JVM, sınıf dosyalarını dinamik olarak yükler. Bu, kodun daha esnek ve modüler olmasını sağlar.
Security: JVM, güvenlik özellikleri sağlar. Örneğin, kodun belirli bir sandbox içinde çalıştırılması.
JVM'de Olmanın Dezavantajları:

Performans: JVM, kodun çalıştırılması için bir abstraction layer oluşturur. Bu, performansı düşürebilir.
Startup Time: JVM, başlatılması zaman alabilir.
Memory Usage: JVM, hafıza kullanımı açısından daha fazla kaynak gerektirebilir.

Go Binary Karşılaştırması:

Go, bir derlenen dilidir. Go kodunu derleyerek bir binary dosyası oluşturulur ve bu dosya doğrudan çalıştırılabilir.

Go Binary Avantajları:

Performans: Go binary dosyaları, doğrudan makine koduna çevrilir. Bu, performansı artırır.
Startup Time: Go binary dosyaları, hızlı bir şekilde başlatılır.
Memory Usage: Go binary dosyaları, hafıza kullanımı açısından daha az kaynak gerektirir.

Go Binary Dezavantajları:

Platform Bağımlılığı: Go binary dosyaları, belirli bir platform için derlenir. Bu, farklı platformlarda çalıştırılamayacağı anlamına gelir.
Hafıza Yönetimi: Go, manuel hafıza yönetimi sağlar. Bu, geliştiricilerin hafıza yönetimiyle uğraşmalarını gerektirir.
Sonuç olarak, JVM ve Go binary dosyalarının avantajları ve dezavantajları farklıdır. JVM, platform bağımsızlığı, hafıza yönetimi ve güvenlik özellikleri sağlar, ancak performansı düşürebilir. Go binary dosyaları, performansı artırır, ancak platform bağımlılığı ve manuel hafıza yönetimi gerektirir.








	
	





