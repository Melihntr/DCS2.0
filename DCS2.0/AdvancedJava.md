# 1. Multithreading

### 1.1 Thread ve Process Nedir?

#### Process (İşlem):

-   Tanım:  Bir programın işletim sistemi tarafından yönetilen bağımsız çalıştırılmasıdır. Her process, kendi  bağımsız bellek alanı  (heap, stack) ve kaynaklara sahiptir.
-   Özellikler:
    -   İzole:  Process'ler birbirinden tamamen ayrıdır. Hata bir process'te başka birini etkilemez.
    -   Kaynak Kullanımı:  Process oluşturma maliyetlidir (bellek, CPU).
    -   İletişim:  Process'ler arası iletişim (IPC - Inter-Process Communication) karmaşıktır (örn. pipe, socket).

#### Thread (İş Parçacığı):

-   Tanım:  Bir process içinde çalışan,  aynı bellek alanını paylaşan  hafif iş birimleridir.
-   Özellikler:
    -   Paylaşım:  Thread'ler aynı heap belleği paylaşır, ancak kendi stack'leri vardır.
    -   Hızlı Oluşum:  Thread oluşturma, process'e göre çok daha hızlıdır.
    -   Risk:  Hata bir thread'de diğerlerini etkileyebilir (örn. bellek sızıntısı).


### 1.2 Java'da Thread Uygulama Seçenekleri

#### a. Thread Sınıfı

-   Kullanım:
    
    ```
    Thread
    ```
    
    sınıfını doğrudan extends ederek thread oluşturabilirsiniz.
    
    ```
    class MyThread extends Thread {
        public void run() {
            System.out.println("Thread çalışıyor");
        }
    }
    new MyThread().start();
    
    ```
    
-   Avantaj:  Basit ve hızlı uygulama.
-   Dezavantaj:  Sınıf inheritance'ı sınırlar (Java'da bir sınıf başka bir sınıfı extend edemez).

#### b. Runnable Arayüzü
-   Kullanım:
    
    ```
    Runnable
    ```
    
    arayüzünü implement ederek thread oluşturabilirsiniz.
    
    ```
    class MyRunnable implements Runnable {
        public void run() {
            System.out.println("Runnable çalışıyor");
        }
    }
    new Thread(new MyRunnable()).start();
    
    ```
    
-   Avantaj:  Sınıf inheritance'ı gerektirmez, daha esnek.

####  c. Callable ve Future

(Java 5+)

-   Kullanım:
    
    ```
    Callable
    ```
    
    arayüzü, thread'den sonuç döndürmek için kullanılır.
    
    ```
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<Integer> future = executor.submit(() -> 42);
    System.out.println(future.get()); // 42
    
    ```
    
-   Avantaj:  Thread'den dönüş değeri almak için ideal.

### 1.3 Daemon Thread

-   Daemon thread'leri, arka planda çalışan ve uygulamanın sonlandığında otomatik olarak sonlandırılan thread'lerdir.
-   Non-daemon thread'lerden farklı olarak, JVM, tüm non-daemon thread'lerin bitmesini beklerken daemon thread'lerden bağımsız olarak sonlanabilir.

Not:Daemon thread’ler JVM kapanırken otomatik öldürülür. Eğer I/O işlemi daemon thread’deyse ve uygulamada sadece daemon thread kalırsa JVM kapanır ve işlem yarıda kesilir. Bu yüzden kritik I/O işleri daemon thread’de yapılmaz.

Örnek:

```
public class DaemonThreadExample {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Daemon thread çalışıyor...");
            }
        });
        daemonThread.setDaemon(true); // Thread'i daemon yap
        daemonThread.start();

        // Main thread'in bitmesini bekleyelim
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread bitti!");
    }
}
```

### 1.4 join() Methodu
-   ```
    join()
    ```
    
    metodu, bir thread'in diğer thread'ler tarafından beklendiği anlamına gelir.
-   Bir thread'in join() metodu çağrıldığında, o thread'in tamamen çalışmasını bekler.
-   Bu, thread'lerin sıralı bir şekilde çalışmasını sağlamaya yarar.

Örnek:

```
public class JoinExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread çalışıyor...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread bitti!");
        });

        thread.start();
        try {
            thread.join(); // Main thread, thread'in bitmesini bekler
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread devam ediyor...");
    }
}
```
### 1.5 Thread Yaşam Döngüsü ve Durumları

Java'da bir thread, aşağıdaki yaşam döngüsünde bulunur:

1.  New (Yeni): Thread oluşturuldu ancak henüz başlatılmadı.
2.  Runnable (Çalışabilir): Thread,
    
    ```
    start()
    ```
    
    metodu ile başlatılmış ve JVM tarafından yürütülebilir durumda.
3.  Blocked (Engellenmiş): Thread, bir kaynak için bekliyor.
4.  Waiting (Bekliyor): Thread, diğer thread'lerden birini bekliyor.
5.  Timed Waiting (Zamanlanmış Bekleme): Thread, belirli bir süreye kadar bekliyor.
6.  Terminated (Bitmiş): Thread tamamen çalıştı ve sona erdi.

### 1.6 Interrupt

-   Bir thread'in çalışmasını kesmek için interrupt()  metodu kullanılır.
-   ```
    interrupt()
    ```
    
    metodu, thread'in
    
    ```
    interrupted()
    ```
    
    durumunu
    
    ```
    true
    ```
    
    yapar.
-   Bir thread, interrupt() metodu ile kesildiğinde, InterruptedException fırlatabilir.

Not: InterruptedException fırlatıldığında JVM interrupted flag’i otomatik temizler (false yapar).
Eğer interrupt durumunu korumak istiyorsan catch içinde tekrar:

Thread.currentThread().interrupt();

çağırılmalıdır. Aksi halde üst katman interrupt olduğunu anlamaz.

Örnek:

```
public class InterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread kesildi!");
                    break;
                }
                System.out.println("Thread çalışıyor...");
            }
        });
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); // Thread'i kes
    }
}
```

### 1.7 Thread ve Process Farkları

-   Process, yürütülebilir bir dosya veya programdır.
-   Thread ise, bir işlem içinde yürütülen bir iş parçacığıdır.
-   Process, kendi adres alanında çalışır ve kaynakları kendi başına yönetir.
-   Thread, aynı işlem içindeki diğer thread’lerle kaynakları paylaşır ve işlem kaynaklarını kullanır.
-   Process, bir işlemi yöneten ve planlayan işletim sistemi tarafından yönetilir.
-   Thread, bir işlem içindeki iş parçacıklarını yöneten işletim sistemi tarafından yönetilir.
-   Process, başka bir process tarafından veya işletim sistemi tarafından başlatılabilir.
-   Thread, bir process tarafından oluşturulabilir.
-   Process’ler, birbirleriyle işbirliği yapmak için işletim sistemi aracılığıyla iletişim kurmak zorundadır.
-   Thread’ler, aynı işlem içindeki diğer thread’lerle işbirliği yapabilir ve birbirlerine mesaj gönderebilir.

Kısaca process ile thread arasındaki fark: Process çalışan uygulamanın kendisi iken; thread ise process içinde çalışan bir iş parçacığıdır.

### 1.8 ExecutorService Nedir ve Ne Zaman Kullanılır?

#### ExecutorService Tanımı

-   Tanım:  Java Concurrency API'sinin parçasıdır. Thread havuzu (thread pool) yönetimi ve görev (task) çalıştırma işlemlerini soyutlar.
-   Amaç:  Thread oluşturma, yönetme ve kaynakların verimli kullanılması için yüksek seviyeli bir arayüz sağlar.

#### ExecutorService Türleri

1. newSingleThreadExecutor()
    
   - Kullanım:  Sırayla bir thread ile görevleri çalıştırır.
   - Örnek:  Günlük kaydı gibi sıralı işlemler.
    
    ```
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> System.out.println("Sıralı işlem"));
    
    ```
    
2.  
    newFixedThreadPool(n)
    
    -   Kullanım:  Sabit sayıda thread ile paralel işlem.
    -   Örnek:  10 thread ile 100 görevi paralel çalıştırma.
    
    ```
    ExecutorService executor = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 100; i++) {
        executor.execute(() -> System.out.println("Paralel işlem"));
    }
    
    ```
    
3. 
    newCachedThreadPool()
    
    
    -   Kullanım:  Gereksinime göre thread oluşturur (kısaca yaşayan görevler için).
    -   Örnek:  Kısa süren, yüksek hacimli görevler.
    
    ```
    ExecutorService executor = Executors.newCachedThreadPool();
    executor.execute(() -> System.out.println("Geçici işlem"));  
    ```
4. ScheduledThreadPool
-   Kullanım: Periyodik görevler için kullanılır.
    
    ```
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    scheduler.scheduleAtFixedRate(() -> System.out.println("Periyodik Görev"), 0, 1, TimeUnit.SECONDS);
    ```
    

#### ExecutorService Avantajları

-   Kaynak Yönetimi:  Thread havuzu ile kaynak israfı önlenir.
-   Görev Sıralaması:
    
    ```
    submit()
    ```
    
    ,
    
    ```
    invokeAll()
    ```
    
    gibi metodlarla görevler yönetilebilir.
-   Hata Yönetimi:
    
    ```
    Future
    ```
    
    nesneleri ile hata yakalama ve sonucu alma kolaydır.

### 1.9 Liveness Sorunları (Deadlock, Livelock, Starvation)

#### Deadlock Nedir?

Deadlock, süreçlerin kaynak edinme nedeniyle birbirini engellemesi ve hiçbir sürecin ilerleyememesi durumudur.

Deadlock Örneği:  
Her iki süreç de bir kaynak tutmuş ve diğerinin kaynaklarını beklemektedir. Bu bir deadlock durumudur çünkü süreçlerden biri kaynaklarını bırakmadıkça ilerleyemez.

#### Deadlock İçin Gerekli Koşullar

Deadlock senaryosunu karakterize etmek için aşağıdaki dört koşul aynı anda geçerli olmalıdır:

1.  Özdeşlik (Mutual Exclusion):  En az bir kaynak, bir süreç tarafından paylaşılamaz şekilde tutulmalıdır. Diğer süreçler bu kaynağı istediklerinde beklemelidir.
2.  Tut ve Bekle (Hold and Wait):  Bir süreç, bir kaynak tutarken başka kaynakları istemelidir.
3.  İptal Yok (No Preemption):  Bir kaynak, bir süreçten zorla alınamaz. Süreç, kaynakları isteğine göre bırakmalıdır.
4.  Dairesel Bekleme (Circular Wait):  Süreçlerin bir döngüsü {p0, p1, p2,.., pn} olmalıdır. Örneğin, p0, p1’in kaynaklarını beklerken, p1, p0’ın kaynaklarını bekler.

#### Deadlock Nasıl Önlenir?

Deadlock’u önlemek için yukarıdaki koşullardan en az birinin geçersiz olması gerekir:

-   Özdeşlik:  Bazı durumlarda geçersiz olabilir. Örneğin, salt okunur bir dosya sisteminde birden fazla süreç paylaşılan erişim alabilir. Ancak bazı kaynaklar (örn. mutex kilit) paylaşılamazdır.
-   Tut ve Bekle:  Bu koşulu önlemek için bir süreç, herhangi bir kaynak tutmazken diğer kaynakları istemelidir.
-   İptal Yok:  Bu koşulu geçersiz kılmak için bir süreç, yeni istenen kaynak mevcut değilse tüm kaynakları otomatik olarak bırakmalıdır.
-   Dairesel Bekleme:  Tüm kaynak türlerine bir sıralama verilerek, süreçler kaynakları artan sırayla istemelidir.


### Livelock (Canlı Kilit)

Bu bölümde, deadlock’a benzer ancak ince farklarla ayrılan  livelock  konusunu tartışacağız.

####  Livelock Nedir?

Livelock durumunda, süreçlerin durumu sürekli değişir ancak hiçbir süreç görevini tamamlayamaz.

Livelock Örneği:  
Yukarıdaki şekil, "süreç 1" ve "süreç 2" arasındaki bir livelock örneğini göstermektedir. Her iki süreç de ortak bir kaynak istemektedir. Her süreç, diğerinin aktif olup olmadığını kontrol eder. Eğer aktifse, kaynak diğerine devredilir. Ancak her iki süreç de pasif olduğunda, kaynaklar sonsuz döngüde birbirine devredilir.

Gerçek dünya örneği: İki kişi aynı anda telefonla konuşmak istemişse ve her ikisi de hatın meşgul olduğunu fark edip aynı aralıklarla yeniden denemişse, bu bir livelock örneğidir.

####  Deadlock ve Livelock Arasındaki Fark

Deadlock’ta süreçler durum değiştirmezken, livelock’ta süreçler sürekli kaynak durumunu değiştirir. Ancak bu değişiklikler hiçbir ilerleme sağlamaz.


### Starvation (Açlık)

Bu bölümde, deadlock veya bir açgözlü süreç nedeniyle oluşan  starvation  konusunu tartışacağız.

#### Starvation Nedir?

Starvation, bir sürecin gerekli kaynaklara erişememesi ve görevini tamamlayamaması durumudur.

Starvation Örneği:  
Yukarıdaki şekil, "süreç 2" ve "süreç 3"’ün CPU kaynaklarına erişememesini göstermektedir çünkü "süreç 1" uzun süre kaynakları kullanmaktadır.

####  Starvation Nedenleri

Starvation, deadlock, livelock veya bir açgözlü süreç nedeniyle oluşabilir.

-   Deadlock veya livelock senaryolarında süreçler kaynakları edinemez.
-   Bir süreç, kaynakları uzun süre kullanırken diğer süreçler beklemek zorunda kalır.

####  Starvation Nasıl Önlenir?

Starvation’ı önlemek için:

1.  Öncelikli Kuyruk ve Yaşlandırma (Aging):  Bekleyen süreçlerin önceliği zamanla artırılır. Bu sayede uzun süre bekleyen süreçler yüksek öncelik alır.
2.  Round-Robin Dağıtım:  Kaynaklar her sürece eşit şekilde verilir. Bu yöntem, her sürece kaynak kullanım fırsatı sunar.


# 2.  Concurrency

### 2.1 Concurrency vs. Parallelism: İşlem Yönetimindeki Farklılıklar

#### Parallelism Nedir?

Birden fazla işlemci yada görüntü işlemcisi gibi işlem yürütme yeteneğine sahip donanımın bulunduğu sistemlerde bir işlemin sonuçlarını daha hızlı elde etmek için kullanılan bir yöntemdir. Bir iş belirli parçalara bölünür ve sisteme dahil olan işlemcilere paylaştırılır, işlemciler aynı anda kendilerine düşen işlemleri yaparlar bu şekilde bir iş olması gerekenden daha az sürede tamamlanır. Hesaplaması çok uzun sürecek işlemlerde sıklıkla kullanılan bir yöntemdir. Coin mining yapan sistemlerin arkasında da bu yapı vardır, bir çok ekran kartı aynı işin parçalara ayrılmış her bir bölümünü tamamlamaya çalışır. Burada bilinmesi gereken bir başka nokta paralel programlamada aynı anda birden fazla iş  yapılmasıdır.

#### Concurrency Nedir?

Eş zamanlı programlama bir iş bitmeden diğer bir işe geçilmesi durumudur. İşlemci üzerinde birden fazla çekirdek olduğundan ve her çekirdek bir işlem yürütebildiğinden elimizdeki işi concurrent bir yapıda çalıştırırsak response time’ı düşürebiliriz.

### 2.2 Critical Section (Kritik Bölge)

Tanım:  
Critical section, bir thread’in paylaşılan kaynaklara eriştiği ve bu erişimin diğer thread’ler tarafından kesilmesi durumunda tutarsızlık (inconsistency) oluşabileceği kod bloğudur. Bu bölge, aynı anda sadece bir thread tarafından çalıştırılmalıdır.

Özellikleri:

-   Paylaşılan Kaynak Erişimi:  Critical section, genellikle bir veri yapısı (örn. liste, dizi) veya değişkenin güncellenmesi için kullanılır.
-   Mutual Exclusion (Kilit):  Bu bölgeye erişim, bir kilit mekanizması (örn.
    
    ```
    synchronized
    ```
    
    ) ile korunur.

Örnek:

```
public class Counter {
    private int count = 0;

    public void increment() {
        // Critical section: count değişkeni paylaşılan bir kaynaktır
        count++; // read-modify-write işlemi
    }
}

```

Yukarıdaki örnekte,

```
count++
```

işlemi

```
read-modify-write
```

adımlarından oluşur. Birden fazla thread bu kritik bölgeye aynı anda erişirse, count değişkeni tutarsız bir duruma gelebilir.

### 2.3 Race Condition (Yarış Durumu)

Tanım:  
Race condition, birden fazla thread’in paylaşılan bir kaynağa eşzamanlı erişim yapması sonucu ortaya çıkan, beklenmeyen ve tahmin edilemeyen bir durumdur. Bu durum, thread’lerin erişim sırasına bağlıdır.

Neden Oluşur?

-   Critical Section’un Eksik Koruması:  Critical section’a erişim,
    
    ```
    synchronized
    ```
    
    gibi bir kilit mekanizması ile korunmazsa, race condition oluşur.
-   Non-Atomic İşlemler:
    
    ```
    count++
    ```
    
    gibi işlemler, atomik değildir. Bu nedenle, thread’ler bu işlemi parçalara ayıracak şekilde keserse, tutarsızlık oluşur.

Örnek:

```
public class Counter {
    private int count = 0;

    public void increment() {
        count++; // Race condition oluşabilir
    }
}

```

Yukarıdaki örnekte,

```
count++
```

işlemi thread’ler tarafından kesilirse,


count değişkeni yanlış bir değer alabilir. Örneğin, iki thread’in count değerini 100 olarak okuması ve her ikisinin de 101’e artırması durumunda, son değer 101 olurken, doğru değer 102 olmalıdır.
### 2.4 `volatile`  Anahtar Kelimesi

Java'da `volatile` anahtar kelimesi , bir değişkenin değerinin farklı iş parçacıkları tarafından değiştirileceğini belirtmek için kullanılır. Bu, bir değişkende yapılan değişikliklerin diğer iş parçacıkları tarafından her zaman görülebilmesini sağlayarak iş parçacığı önbellekleme sorunlarını önler.

Kullanım:`volatile`anahtar kelimesi, öncelikle çoklu iş parçacıklı programlamada bir değişkenin güncellemelerinin iş parçacıkları arasında öngörülebilir bir şekilde yayılmasını sağlamak için kullanılır.

Örnek
Multi-Threadling with Volatile

```
public class VolatileCounter {
    private volatile int counter = 0;
    public void increment() {
        counter++;
    }
    public int getCounter() {
        return counter;
    }
    public static void main(String[] args) throws InterruptedException {
        VolatileCounter vc = new VolatileCounter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                vc.increment();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter value: " + vc.getCounter());
    }
}
```


Bu örnekte, `counter` `volatile` bir değişken olarak işaretlenmiştir . İki iş parçacığı `counter`değişkeni eş zamanlı olarak artırır. `volatile`Anahtar kelimesi, her iş parçacığının `counter`değişkeninin en son değerini görmesini sağlar .

### 2.5 `ThreadLocal` Anahtar Kelimesi

Bu, her bir iş parçacığının değişkene erişirken kendi bağımsız kopyasına sahip olduğu, iş parçacığına özgü değişkenler sağlar. Bu, iş parçacıkları arasında paylaşım veya çekişme olmamasını sağlayarak, çok iş parçacıklı ortamlarda iş parçacığı başına durumu yönetmek için güçlü bir araç haline getirir.

 Başlıca Özellikleri `ThreadLocal`:

1.  **İş parçacığı izolasyonu** : Her iş parçacığının değişkenin kendi örneği bulunur, bu da senkronizasyon sorunlarını önler.
2.  **Kullanım Örnekleri** :

-   Web uygulamalarında kullanıcı oturumlarının sürdürülmesi.
-   Veritabanı bağlantılarının iş parçacığı havuzlarında yönetilmesi.
-   Dağıtılmış sistemlerde işleme özgü verilerin depolanması.

**3. Yaşam Döngüsü** : Bir değişkendeki değerler, `ThreadLocal`iş parçacığı aktif olduğu sürece veya değişken manuel olarak kaldırılana kadar kalır.

`ThreadLocal` Nasıl Kullanılır

Temel Örnek:
```
public  class  ThreadLocalExample {   
    private  static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Başlangıç ​​Değeri" );   
public  static  void  main (String[] args) {   
        Runnable  task  = () -> {   
            String  threadName  = Thread.currentThread().getName();   
 threadLocal.set( "Değer " + threadName + " için");   
 System.out.println(threadName + ": " + threadLocal.get());   
 };   
        Thread  thread1  =  new  Thread (task, "Thread-1" );   
        Thread  thread2  =  new  Thread (task, "Thread-2" );   
 thread1.start();   
 thread2.start();   
 }   
}
```

### 2.6 `synchronized` Anahtar Kelimesi

Java’da `synchronized` anahtar sözcüğü, çoklu iş parçacıklı (multithreaded) ortamlarda

paylaşılan kaynaklara eşzamanlı erişimi kontrol etmek amacıyla kullanılır.

Temel amacı race condition oluşumunu engellemek ve kritik bölgelerde

karşılıklı dışlama (mutual exclusion) sağlamaktır.

Temel Özellikleri:

- Aynı anda yalnızca bir iş parçacığı kritik bölgeye girebilir.

- Monitor lock (içsel kilit) mekanizması kullanır.

- Bellek görünürlüğü sağlar (memory visibility).

- Atomik yürütme garantisi sunar.


#### Kullanım Biçimleri

#### Metot Seviyesinde
```
class Counter {

private int count = 0;

public synchronized void increment() {

count++;

}

}
```
Bu kullanımda kilit ilgili nesnenin (this) üzerinde alınır.

#### Statik Metotta
```
class Counter {

private static int count = 0;

public static synchronized void increment() {

count++;

}

}
```
Bu durumda kilit Counter.class nesnesi üzerinde alınır.

#### Blok Seviyesinde
```
class Counter {

private int count = 0;

public void increment() {

synchronized (this) {

count++;

}

}

}
```

Bu yöntem yalnızca kritik bölgeyi kilitler ve daha kontrollüdür.

#### Özel Kilit Nesnesi ile
```
class Counter {

private int count = 0;

private final Object lock = new Object();

public void increment() {

synchronized (lock) {

count++;

}

}

}
```

### 2.7 `synchronized` ve `volatile`Karşılaştırılması

synchronized:

- Kilitleme yapar

- Atomiklik sağlar

- Görünürlük sağlar

volatile:

- Kilitleme yapmaz

- Atomiklik sağlamaz

- Görünürlük sağlar

#### Volatile'ın Yetersiz Olduğu Durum
```
class Counter {

private volatile int count = 0;

public void increment() {

count++; // atomik değildir

}

}
```

count++ işlemi atomik olmadığı için race condition oluşabilir.

### 2.8. AtomicInteger


AtomicInteger sınıfı, Java'da thread-safe bir integer değeri yönetmek için kullanılır. Bu sınıf,

```
java.util.concurrent.atomic
```

paketinde bulunur ve

```
synchronized
```

blokları yerine daha verimli ve esnek bir alternatif sunar.

#### Özellikleri:

-   Thread-Safe:  Birden fazla thread'in aynı anda erişmesine rağmen hata yapmaz.
-   Visibility:  Değişikliklerin diğer thread'ler tarafından hemen görüldüğü garantilenir.
-   Ordering:  Değişikliklerin doğru bir sırayla yürütüldüğü garantilenir.
-   Hesaplamalar:
    
    ```
    getAndIncrement()
    ```
    
    ,
    
    ```
    incrementAndGet()
    ```
    
    ,
    
    ```
    getAndSet()
    ```
    
    ,
    
    ```
    compareAndSet()
    ```
    
    gibi metotlar ile atomik hesaplamalar yapılabilir.

### 2.9 Java'da Kilitler

Java'da "lock" (kilit), birden fazla thread'in aynı kaynak üzerinde işlem yapmasını yönetmek için kullanılan bir mekanımdır. Lock'lar, veri tutarlılığını sağlamak ve race condition (yapışak koşul) gibi sorunları önlemek için kullanılır.


### 2.10 ReentrantLock vs. synchronized

```
ReentrantLock
```

ve

```
synchronized
```

blokları, Java'da kritik bölümleri kilitlemek ve thread'lerin aynı anda aynı kod parçacığını çalıştırmamasını sağlamak için kullanılır. Ancak, bu iki yöntem arasında bazı temel farklılıklar vardır.

#### ReentrantLock:

-   Elastik:  Kilitlenebilir ve kilidi elden çıkartılabilir (
    
    ```
    lockInterruptibly()
    ```
    
    ).
-   Fairness:  Kilitlenme sırasına göre thread'lerin erişimine izin verilebilir.
-   Condition Variables:
    
    ```
    newCondition()
    ```
    
    metodu ile koşullu değişkenler oluşturulabilir.
-   Recursive:  Aynı thread'in birden fazla kez kilidi almasına izin verir.

#### synchronized:

-   Basit:  Kullanımı kolaydır.
-   Performans:
    
    ```
    ReentrantLock
    ```
    
    'e göre daha hızlıdır.
-   Kısıtlamalar:  Kilitlenebilirlik ve koşullu değişkenler gibi gelişmiş özelliklere sahip değildir.

Örnek:

```
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private ReentrantLock lock = new ReentrantLock();
    
    public void method() {
        lock.lock(); // Kilit al
        try {
            // Kritik bölüm
            System.out.println("Kilitli Bölüm: " + Thread.currentThread().getName());
        } finally {
            lock.unlock(); // Kilit bırak
        }
    }
}

```

----------

### 2.11 Semaphores

```
Semaphore
```

sınıfı, birden fazla thread'in aynı kaynaktan yararlanmasını yönetmek için kullanılır. Bu sınıf,

```
java.util.concurrent
```

paketinde bulunur.

Özellikleri:

-   Resource Pooling:  Belirli bir sayıda kaynak kullanmak için idealdir.
-   Signaling:  Thread'ler arasında mesajlaşma sağlamak için kullanılabilir.
-   Fairness:  Kilitlenme sırasına göre thread'lerin erişimine izin verilebilir.

#### Kullanım Alanları:

-   Veritabanı bağlantıları, dosya giriş/çıkışları gibi sınırlı kaynaklar için.
-   Thread'ler arasında mesajlaşma sağlamak için.

Örnek:

```
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int MAX_THREADS = 3;
    private static Semaphore semaphore = new Semaphore(MAX_THREADS);
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire(); // Kaynak kullan
                        System.out.println("Kaynak kullanılıyor: " + Thread.currentThread().getName());
                        Thread.sleep(1000);
                        System.out.println("Kaynak serbest bırakılıyor: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release(); // Kaynak serbest bırak
                    }
                }
            }).start();
        }
    }
}

```

----------

### 2.12 Fork/Join

```
Fork/Join
```

framework, Java 7'de tanıtılmıştır ve paralel görevler çalıştırmak için kullanılır. Bu framework,

```
java.util.concurrent.ForkJoinPool
```

sınıfı ile yönetilir.

Özellikleri:

-   Work-Stealing Algorithm:  Boş kalan thread'lerin diğer thread'lerin görevlerini devralmasına izin verir.
-   Recursive_tasks:  Büyük görevlerin alt görevlere bölünmesine izin verir.
-   Base Case:  Alt görevlerin ne zaman seriel olarak yürütüleceğini belirler.

Kullanım Alanları:

-   Büyük veri setlerini işlemek için.
-   Paralel hesaplamalar yapmak için.

Örnek:

```
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(0, 1000);
        long result = pool.invoke(task);
        System.out.println("Toplam: " + result);
    }
    
    static class SumTask extends RecursiveTask<Long> {
        private static final long serialVersionUID = 1L;
        private final long start;
        private final long end;
        
        public SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Long compute() {
            if (end - start <= 10) { // Base case
                long sum = 0;
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            } else { // Recursive case
                long mid = (start + end) / 2;
                SumTask task1 = new SumTask(start, mid);
                SumTask task2 = new SumTask(mid + 1, end);
                task1.fork();
                task2.fork();
                return task1.join() + task2.join();
            }
        }
    }
}
```

# 3. Async Programming

 Asenkron Programlama Nedir?

Asenkron programlama, bir uygulamanın birden fazla görevi aynı anda çalıştırmayı mümkün kılmak için kullanılan bir yöntemdir. Bu, ana thread'in bloklanmasını önler ve uygulamanın daha hızlı ve daha etkili bir şekilde çalışmasını sağlar. Asenkron programlama, özellikle I/O sınırlı işlemler (örneğin, veritabanı sorguları, web servis çağrıları) için idealdir.

Java'da asenkron programlama,

```
CompletableFuture
```

,

```
ExecutorService
```

, ve diğer sınıflarla yönetilebilir.


#### Asenkron Programlamanın Önemi

1.  Performans Artışı:  Ana thread'in bloklanmasını önler ve diğer görevlerin yürütülmesine izin verir.
2.  Uygulama Yanıt Verme:  Kullanıcıya daha hızlı bir yanıt sunar.
3.  Kaynak Kullanımı:  CPU ve diğer sistem kaynaklarının daha etkili bir şekilde kullanılmasını sağlar.


### 3.1 CompletableFuture

```
CompletableFuture
```

sınıfı, Java 8'de tanıtılmıştır ve asenkron işlemler yönetmek, sonucu beklemek ve işlem zinciri oluşturmak için kullanılır. Bu sınıf,

```
java.util.concurrent
```

paketinde bulunur.

Özellikleri:

-   Asenkron İşlemler:  Görevleri arka planda çalıştırmak ve sonucu beklemek için kullanılır.
-   Zincirleme:
    thenApply(), thenCompose(), whenComplete()
    
    gibi metotlar ile işlem zincirleri oluşturulabilir.
-   Exception Handling:
    
    ```
    exceptionally()
    ```
    
    metodu ile hata durumlarını yönetebilirsiniz.
-   Bekleme: get() metodu ile sonucu beklemek veya join() metodu ile asenkron olarak devam edebilirsiniz.

 Kullanım Alanları:

-   Web servislerini çağırmak, veritabanı sorguları yapmak gibi I/O sınırlı işlemler için.
-   Paralel görevler çalıştırmak için.

Örnek:

```
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Asenkron olarak "Merhaba, Dünya!" üret
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Asenkron işlem başladı: " + Thread.currentThread().getName());
            return "Merhaba, Dünya!";
        });
        
        System.out.println("Ana thread devam ediyor...");
        
        // Sonucu bekleyip ekrana yazdır
        String result = future.get();
        System.out.println("Sonuç: " + result);
    }
}
```
### 3.2  Callback'lar

Asenkron programlamanın bir parçası olarak, görevlerin tamamlandığında veya başarısız olduğunda ne yapacağını belirlemek için callback'lar kullanılır. Java'da bu,


CompletableFuture'ın

thenApply(), whenComplete() ve exceptionally() metotlarıyla yönetilir.

 Örnek:

```
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CallbackExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Merhaba, Dünya!";
        })
        .thenApply(result -> {
            return result.toUpperCase();
        })
        .whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println("Hata oluştu: " + exception.getMessage());
            } else {
                System.out.println("Sonuç: " + result);
            }
        });
        
        future.get();
    }
}

```


### 3.3  Asenkron Hata Yönetimi

Asenkron görevlerde hata yönetimi, uygulamanın robust olmasını sağlamak için önemlidir. Java'da bu,


CompletableFuture'ın

```
exceptionally()
```

metodu ile yönetilir.

Örnek:

```
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncErrorHandlingExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Bir hata oluştu!");
        })
        .exceptionally(e -> {
            System.out.println("Hata yakalandı: " + e.getMessage());
            return "Hata yönetildi!";
        });
        
        String result = future.get();
        System.out.println("Sonuç: " + result);
    }
}
```
    
# 4. JVM Internals

Java'da JVM (Java Virtual Machine) tarafından yönetilen bellek, Heap ve Stack olarak ikiye ayrılır. Her biri farklı amaçlarla kullanılır ve performans üzerinde etkileri vardır. Garbage Collection ise, Java'da bellek yönetimi için kullanılan mekanizmadır. Bu konuları detaylı olarak açıklayalım ve kod örnekleri ile destekleyelim.


### 4.1 Stack Bellek (Yığın Bellek)
Stack bellek, her bir thread (iş parçacığı) için ayrı ayrı oluşturulan ve metod çağrılarının yönetildiği bellek alanıdır.

Stack’te Saklanan Veriler:
- Primitive (ilkel) veri tipleri: int, double, char, boolean vb.
- Nesnelere ait referanslar (object reference)
- Metod parametreleri
- Yerel değişkenler (local variables)
- Metod çağrı bilgileri (stack frame)

Özellikleri:

- LIFO (Last In First Out) mantığı ile çalışır.
- Her thread’in kendine ait stack alanı vardır.
- Bellek yönetimi otomatik ve deterministiktir.
- Stack alanı genellikle heap’e göre daha küçüktür.
- Büyük veri saklamak için uygun değildir.

Örnek:

```

public void example() {
    int x = 10;        // Stack’te saklanır
    String s = "Java"; // s referansı stack’te, String nesnesi heap’te
}
```

Burada:
- x değişkeni doğrudan stack’te tutulur.
- s bir referanstır ve stack’te tutulur.
- "Java" String nesnesi heap’te yer alır.

### 4.2 Heap Bellek
Heap bellek, nesnelerin (object) dinamik olarak oluşturulduğu ve saklandığı bellek alanıdır. JVM içindeki tüm thread’ler heap alanını ortak kullanır.

Heap’te Saklanan Veriler:
- Tüm nesneler (new ile oluşturulan)
- Diziler (arrays)
- Nesne içindeki instance değişkenler

Özellikleri:

- Thread’ler arasında paylaşılır.
- Stack’e göre daha büyüktür.
- Garbage Collector tarafından yönetilir.
- Daha esnek fakat erişimi stack’e göre daha maliyetlidir.

Örnek:
```
Person p = new Person();
```
p referansı stack’te saklanır.
Person nesnesi heap’te oluşturulur.

### 4.3 Referans Türleri (Reference Types)
Java’da bellek yönetimini daha esnek hale getirmek için farklı referans türleri tanımlanmıştır.

 Strong Reference (Güçlü Referans)
Varsayılan referans türüdür.
```
Person p = new Person();
```
p nesneye güçlü referans tutar.

Nesneye en az bir strong reference varsa Garbage Collector bu nesneyi silemez.

Weak Reference (Zayıf Referans)
`java.lang.ref.WeakReference` sınıfı ile oluşturulur.
```
WeakReference<Person> weakRef = new WeakReference<>(new Person());
```
Eğer nesneye yalnızca weak reference kalmışsa, GC çalıştığında nesne hemen temizlenir. Genellikle cache yapılarında kullanılır.

Soft Reference (Yumuşak Referans)
`
java.lang.ref.SoftReference
` ile oluşturulur.
```
SoftReference<Person> softRef = new SoftReference<>(new Person());
```
Bellek yeterliyse nesne korunur. Bellek baskısı oluştuğunda GC tarafından temizlenir. Büyük cache sistemlerinde tercih edilir.

### 4.4 Heap Bellek Yapısı:
Modern JVM’lerde heap alanı kuşaklara (generations) ayrılmıştır. Bu yaklaşım Generational Garbage Collection prensibine dayanır.

Young Generation (Genç Nesil):
Yeni oluşturulan nesneler burada yer alır.
Young Generation üç alt bölüme ayrılır:

Eden Space:
Yeni oluşturulan tüm nesneler önce Eden alanına gelir.
Çoğu nesne burada oluşturulur ve kısa sürede yok edilir.

Survivor Space (S0 ve S1)
Eden’dan sağ kalan nesneler buraya taşınır.
İki adet survivor alanı vardır (S0 ve S1).
Nesneler her Minor GC’de S0 ve S1 arasında taşınır.
Belirli bir yaşa ulaşan nesneler Old Generation’a terfi eder.

Old Generation (Tenured Generation)
Uzun ömürlü nesneler burada tutulur.
Young Generation’dan terfi eden nesneler buraya gelir.
Temizlenmesi daha maliyetlidir.

Metaspace
Java 8 ile birlikte PermGen kaldırılmış ve yerine Metaspace getirilmiştir.
Metaspace’te saklanan veriler:
Class metadata
Method bilgileri
Static alanlar
Heap dışında, native memory üzerinde tutulur.

### 4.5 Garbage Collector (GC) Nasıl Çalışır?
Garbage Collector, erişilemeyen (unreachable) nesneleri tespit ederek heap’ten temizler.

Erişilebilirlik (Reachability) Analizi
GC, Root referanslardan (GC Roots) başlayarak bir grafik oluşturur.

GC Roots:
- Stack’teki aktif referanslar
- Static referanslar
- JNI referansları
- Eğer bir nesne GC Roots’tan erişilemiyorsa, garbage olarak işaretlenir.

### 4.6 Mark and Sweep Algoritması
Mark (İşaretleme): Erişilebilir nesneler işaretlenir.
Sweep (Temizleme): İşaretlenmeyen nesneler silinir.
(Opsiyonel) Compact (Sıkıştırma): Bellek parçalanmasını azaltmak için nesneler taşınır.

### 4.7 Minor GC ve Major GC
Minor GC:
Young Generation üzerinde çalışır.
Daha sık ve hızlıdır.

Major GC (Full GC):
Old Generation üzerinde çalışır.
Daha maliyetlidir.
Uygulama duraklatılabilir (Stop-the-World).

### 4.8 Garbage Collector Türleri

#### Serial GC (Single GC):
- Tek thread kullanır.
- Küçük uygulamalar için uygundur.
- Stop-the-World duraklaması yapar.

Avantaj:
- Basit ve düşük bellek tüketimi.
- Dezavantaj:
- Büyük uygulamalarda yavaş kalır.

#### Parallel GC:
- Birden fazla thread kullanır.
- Throughput odaklıdır.
- Stop-the-World çalışır.

Avantaj:
- Çok çekirdekli sistemlerde daha hızlıdır.

Dezavantaj:
- Pause süreleri uzun olabilir.

#### CMS (Concurrent Mark and Sweep):
- GC işleminin çoğunu uygulama çalışırken gerçekleştirir.
- Düşük pause süresi hedefler.

Aşamaları:
- Initial Mark (Stop-the-World)
- Concurrent Mark
- Remark (Stop-the-World)
- Concurrent Sweep

Avantaj:
- Düşük gecikme süresi.

Dezavantaj:
- Bellek parçalanması oluşturabilir.

Java 9 sonrası deprecated edilmiştir.

#### G1 GC (Garbage First):
Java 9 ve sonrası için varsayılan GC’dir. Heap’i küçük bölgelere (region) ayırır. En çok çöp içeren bölgeyi öncelikli temizler.

Özellikleri:
- Düşük ve tahmin edilebilir pause süresi.
- Paralel ve concurrent çalışır.
- Büyük heap’ler için uygundur.

Avantaj:
- Dengeli throughput ve latency sağlar.
- Bellek parçalanmasını minimize eder.

# 5.Performans ve Bellek Yönetimi

Java'da performans ve bellek yönetimi, uygulamanın performansını artırmak ve hataları gidermek için önemlidir.

### 5.1  Performans

Performans, uygulamanın ne kadar hızlı ve verimli çalıştığını ifade eder. Performans optimize etmek, uygulamanın daha hızlı ve daha az bellek kullanmasını sağlamaktır.

#### Performans Optimize Etme:

-   Nesne Yaratma:
    -   Gereksiz nesne yaratma.
    -   String concatination yerine
        ```
        StringBuilder
        ```
        
        kullan.
-   Yerel Değişkenler:
    -   Yerel değişkenleri doğru şekilde kullan.
    -   Yerel değişkenleri kullanma süresini minimize et.
-   Garbage Collection:
    -   GC'yi optimize etmek için, nesnelerin doğru şekilde temizlenmesini sağlamak ve bellek kullanımını yönetmek önemlidir.

Örnek:

```
public class PerformanceExample {
    public static void main(String[] args) {
        // Performans optimize etme
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Merhaba, Dünya! ");
        }
        System.out.println(sb.toString());
    }
}

```

----------

### 5.2  Bellek Sızıntısı (Memory Leak)

Bellek sızıntısı, bellekten serbest bırakılmayan nesnelerden kaynaklanan bir sorundur. Bu, uygulamanın performansını düşürür ve sonunda uygulama çöker.

#### Bellek Sızıntısı Nedenleri:

-   Static Referanslar:
    -   Static değişkenler, nesnelerin temizlenmesini engelleyebilir.
-   Unnecessary Nesne Yaratma:
    -   Unnecessary nesne yaratma, bellek sızıntısı oluşturabilir.
-   Resource Management:
    -   Dosya veya veritabanı bağlantıları gibi kaynaklar, kullanılmadığında kapatılmamışsa bellek sızıntısı oluşturabilir.

Bellek Sızıntısı Giderme

-   Profiling Araçları:
    -   VisualVM, JProfiler gibi araçlar, bellek sızıntılarını tespit etmek için kullanılır.
-   Resource Management:
    -   ```
        try-with-resources
        ```
        
        kullanarak kaynakları otomatik olarak kapatın.

Örnek:

```
public class MemoryLeakExample {
    public static void main(String[] args) {
        // Bellek sızıntısı oluşturma
        while (true) {
            new String("Merhaba, Dünya!");
        }
    }
}

```

Bu örnekte, while döngüsü sonsuza kadar devam eder ve her seferinde yeni bir nesne oluşturulur. Bu, Heap'te yer kaplaması nedeniyle bellek sızıntısı oluşturur.



### 5.3  Profiling

Profiling, uygulamanın performansını ölçmek ve optimize etmek için kullanılan bir yöntemdir.

#### Profiling Araçları:

-   VisualVM:
    -   Java uygulamalarının performansını ölçmek için kullanılır.
-   JProfiler:
    -   Uygulamanın performansını ve bellek kullanımını analiz etmek için kullanılır.
-   YourKit:
    -   Java uygulamalarının performansını ve bellek kullanımını analiz etmek için kullanılır.

#### Profiling Kullanımı:

-   CPU Profiling:
    -   Uygulamanın CPU kullanımını ölçmek için kullanılır.
-   Memory Profiling:
    -   Uygulamanın bellek kullanımını ölçmek için kullanılır.
-   Thread Profiling:
    -   Uygulamanın thread kullanımını ölçmek için kullanılır.

Örnek:

```
public class ProfilingExample {
    public static void main(String[] args) {
        // Profiling yapma
        long startTime = System.currentTimeMillis();
        
        // Uygulama kodu
        for (int i = 0; i < 1000000; i++) {
            // İşlem yapma
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Toplam süre: " + (endTime - startTime) + " ms");
    }
}

```

Bu örnekte, uygulamanın ne kadar süreceğini ölçmek için

```
System.currentTimeMillis()
```

kullanılmıştır.


### 5.4  Stabil Sistem

Stabil sistem, uygulamanın sürekli olarak düzgün çalışmasını sağlamak için önemlidir.

#### Stabil Sistem için Dikkat Edilecekler:

-   Exception Handling:
    -   Uygulamanın hatalarla nasıl başa çıkacağını sağlamak önemlidir.
-   Resource Management:
    -   Kaynakların doğru şekilde kullanıma alınması ve kullanılmadığında kapatılması önemlidir.
-   Thread Safety:
    -   Çoklu thread'lerin aynı nesneleri kullanması sırasında thread safety sağlamak önemlidir.

Örnek:

```
public class StableSystemExample {
    public static void main(String[] args) {
        // Exception Handling
        try {
            // Dosya okuma
            FileReader fr = new FileReader("file.txt");
            // Dosya okuma işlemleri
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + e.getMessage());
        } finally {
            // Dosya kapatma
            System.out.println("Dosya kapatılıyor.");
        }
    }
}

```

Bu örnekte,

```
try-with-resources
```

kullanarak dosya kapatma işlemi otomatik olarak gerçekleştirilmiştir.

# 6. Ekstralar

### 6.1 Heap OutOfMemoryError ve StackOverflowError

StackOverflowError bir thread’e özeldir çünkü her thread’in kendi stack alanı vardır. Bir thread stack overflow alırsa sadece o thread crash olur (exception fırlatır ve yakalanmazsa ölür). Diğer thread’ler çalışmaya devam eder. JVM tamamen kapanmaz (ancak ana thread ölür ve başka non-daemon thread kalmazsa uygulama kapanabilir).

Heap tüm thread’ler tarafından ortak kullanılır. Eğer OutOfMemoryError oluşursa genelde tüm uygulama etkilenir. Teknik olarak hata bir thread’de fırlatılır ama heap global olduğu için uygulama stabilitesi bozulur ve çoğu zaman sistem kullanılmaz hale gelir. Çoğu senaryoda uygulama çöker veya sağlıksız davranır.

### 6.2 Virtual THreadler

Virtual thread (Java 21 ile stable) OS thread yerine JVM tarafından yönetilen hafif thread’dir.
Özellikleri: Çok düşük memory footprint, Milyonlarca thread oluşturulabilir, I/O blocking işlemlerde çok verimli, Gerçek OS thread’e ihtiyaç duyduğunda carrier thread kullanır

Platform thread: OS thread’e bire bir karşılık gelir, Memory maliyeti yüksektir, Sayıları sınırlıdır

Virtual thread: User-mode scheduling, Blocking I/O’da park edilir, Reactive yazmadan yüksek concurrency sağlar

Virtual Thread Eklemek: Yüksek eşzamanlılık gerektiren uygulamalarda virtual thread kullanımı önemlidir. (Not: Sanal thread'ler, özellikle çok sayıda bloke edici I/O işlemi olan senaryolar için idealdir.)

### 6.3 Profesyonel benchmark test için hangi kütüphaneler kullanılır?

JMH (Java Microbenchmark Harness) → en doğru yöntem

Gatling (yük testi)

JMeter (yük testi)

wrk (HTTP benchmark)

YourKit / JProfiler (profiling)

async-profiler (CPU flamegraph)

Microbenchmark için kesinlikle JMH kullanılmalı çünkü:

Dead code elimination’ı engeller

Warmup yapar

JIT etkisini dengeler

### 6.4 Godaki thread mantığı ve Java 21 öncesi/sonrası benzerlik

Go:

Goroutine kullanır

M:N scheduling (çok goroutine az OS thread)

Runtime scheduler vardır

Çok hafif

Java 21 öncesi:

1:1 model (platform thread = OS thread)

Thread pahalı

Thread pool zorunlu

Java 21 sonrası:

Virtual thread geldi

M:N modele yaklaştı

Go’ya çok benzer concurrency modeli

Structured concurrency eklendi

Benzerlik: Goroutine ≈ Virtual Thread
Fark: Go runtime daha baştan concurrency için tasarlanmış, Java sonradan adapte oldu.

### 6.5 Catch içinde direkt basmanın yanlış olduğu

Sadece e.printStackTrace() yapmak kötü pratiktir çünkü:

Exception üst katmana iletilmez

Thread pool içinde kaybolabilir

Business logic bozulur

Doğru yaklaşım:

Ya rethrow et

Ya wrap edip üst katmana ilet

Ya interrupt restore et

Ya logging framework kullan

### 6.6 Her gece Linux’ta bir Python scripti çalıştırma

cron kullanılır.

crontab -e

Örnek:
0 3 * * * /usr/bin/python3 /home/user/script.py

Bu her gece 03:00’te çalışır.

### 6.7 tryLock başka işlemler yapmasına izin veriyor

lock() blocking’tir.
tryLock():

Lock müsaitse alır

Değilse beklemez

Alternatif işlem yapmana izin verir

Deadlock riskini azaltır

### 6.8 ReentrantLock synchronized hız farkı

Eskiden (Java 5-6) ReentrantLock daha performanslıydı.
Modern JVM’lerde synchronized optimize edildi (biased locking, lightweight locking).
Çoğu durumda fark minimaldir.

ReentrantLock avantajı:

tryLock

fairness

condition

interruptible lock

### 6.9 Java 21 I/O’da virtual thread neden daha iyi?

Çünkü:

Blocking I/O’da thread park edilir

OS thread bloke olmaz

Thread pool yazma ihtiyacı azalır

Reactive karmaşıklık gerekmez

CPU-bound işte fark azdır.
I/O-bound işte büyük avantaj sağlar.

### 6.10 ki integer join yapmadan önce compare eden algoritma

Hash Join:

Küçük olan hash table’a alınır

Büyük olan stream edilir

Compare sonrası eşleşme yapılır

Sort-Merge Join:

Önce sort

Sonra iki pointer ile compare

### 6.11 Parallel Stream ve ForkJoinPool
Nasıl Çalışır?
list.parallelStream()
    .map(x -> compute(x))
    .toList();

Bu çağrı arka planda:

ForkJoinPool.commonPool() kullanır

Veriyi parçalara böler

Work-stealing algoritması uygular

Work-Stealing Mantığı

Her worker thread’in kendi deque’su vardır

İşini bitiren thread, diğer thread’lerin kuyruğundan iş çalar

CPU kullanımını maksimize eder

Ne Zaman İyi?

CPU-bound işler

Büyük liste

Saf hesaplama

Shared mutable state yok

Örn:

Büyük matematiksel hesap

Hash hesaplama

Image processing

Ne Zaman Kötü?

I/O-bound işlerde

Örn:

DB çağrısı

HTTP request

Dosya okuma

Çünkü:

commonPool thread sayısı sınırlı (CPU core kadar)

Bir task bloklanırsa diğer işler bekler

Tüm uygulama yavaşlayabilir

Bu yüzden I/O için:

Virtual Thread (Java 21)

Custom Executor

daha doğru seçimdir.

### 6.12 Starvation Gerçek Business Senaryoları
Web Sunucusunda Thread Pool Starvation

Senaryo:

200 thread’lik bir web thread pool var

Her request içinde blocking DB çağrısı var

DB yavaşladı

Sonuç:

Tüm thread’ler bloklanır

Yeni request’ler kuyruğa girer

Sistem cevap veremez

Bu starvation’dır.

Çözüm:

Connection pool limitlerini ayarlamak

Timeout koymak

Virtual thread kullanmak

### 6.13 Java 21 GC İyileştirmesi

En önemli gelişme:

Generational ZGC

Önceden:

ZGC tek generation idi

Şimdi:

Young + Old ayrımı var

Kısa ömürlü objeler hızlı temizleniyor

Daha az CPU

Daha iyi throughput

Business Perspektifi

E-ticaret sistemi düşün:10GB heap,Yoğun request,Sürekli object allocation

Yanlış GC seçimi:500ms pause, Kullanıcı timeout,Ödeme başarısız

Doğru GC (G1 veya ZGC):10ms pause,Stabil sistem,SLA korunur








