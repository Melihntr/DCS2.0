# SPRING & SPRING BOOT DETAYLI DOKÜMANTASYON
## 1. Spring Core: IoC ve Dependency Injection
#### 1.1 Inversion of Control nedir?
Inversion of Control, nesnelerin ya da program parçalarının kontrolünü bir container'a veya framework'e devreden yazılım mühendisliği ilkesidir. Genellikle nesne yönelimli programlama bağlamında kullanılır.

Geleneksel programlamanın aksine — bizim yazdığımız özel kodun bir kütüphaneyi çağırdığı model — IoC, framework'ün program akışının kontrolünü ele almasını ve bizim özel kodumuzu çağırmasını sağlar. Bunu mümkün kılmak için framework'ler ek davranışlarla birlikte soyutlamalar sunar. Kendi davranışımızı eklemek istersek framework sınıflarını genişletmemiz veya kendi sınıflarımızı plug-in yapmamız gerekir.

#### Bu mimarinin avantajları:

Bir görevin yürütülmesini uygulamasından ayırma (decoupling)
Farklı implementasyonlar arasında geçişi kolaylaştırma
Programın daha modüler olması
Bir bileşeni izole ederek test etmeyi, bağımlılıkları mock etmeyi ve bileşenlerin sözleşmeler (contracts) üzerinden iletişim kurmasını kolaylaştırma
IoC’yi farklı mekanizmalarla elde edebiliriz: Strategy pattern, Service Locator, Factory pattern ve Dependency Injection (DI).

#### 1.2 Dependency Injection nedir?
Dependency injection, IoC’yi uygulamak için kullandığımız bir desendir; burada kontrolün tersine çevrilmesi, bir nesnenin bağımlılıklarının atanmasıdır.

Nesneleri diğer nesnelerle bağlama ya da nesneleri başka nesnelere "inject" etme işi, nesnelerin kendileri yerine bir assembler (bağlayıcı) tarafından yapılır.

Geleneksel programlamada bir nesne bağımlılığı şöyle oluşturulurdu:

```

public class Store {
    private Item item;
 
    public Store() {
        item = new ItemImpl1();    
    }
}
```
Yukarıdaki örnekte Store sınıfı içinde Item interface'inin bir implementasyonu örneklenmektedir.

DI kullanarak aynı örneği implementasyonu belirtmeden şöyle yazabiliriz:

```

public class Store {
    private Item item;
    public Store(Item item) {
        this.item = item;
    }
}
```
Sonraki bölümlerde Item implementasyonunu metadata aracılığıyla nasıl sağlayabileceğimizi göreceğiz.

IoC ve DI basit kavramlardır ancak sistemlerimizi yapılandırma biçiminde derin etkileri vardır; bu yüzden tam olarak anlamaya değerdir.

#### 1.3 Spring IoC Container nedir?
IoC container, IoC'yi uygulayan frameworklerin ortak bir özelliğidir.

Spring framework'te ApplicationContext arayüzü IoC container'ı temsil eder. Spring container, bean olarak bilinen nesnelerin örneklenmesinden, yapılandırılmasından, birbirine bağlanmasından ve yaşam döngülerinin yönetilmesinden sorumludur.

Spring, ApplicationContext'in birkaç implementasyonunu sağlar: standalone uygulamalar için AnnotationConfigApplicationContext, ClassPathXmlApplicationContext, FileSystemXmlApplicationContext; web uygulamaları için WebApplicationContext.

Bean'leri assemble etmek için container yapılandırma metadata'sını okur — bu XML konfigürasyon veya annotation tabanlı olabilir.

Manuel container örneği (XML tabanlı):

```

ApplicationContext context
  = new ClassPathXmlApplicationContext("applicationContext.xml");
Annotation tabanlı bir container örneği:

```

AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();
AnnotationConfigApplicationContext örneği yaratıp ona bir veya daha fazla konfigürasyon sınıfı verdiğinizde, @Bean ve ilgili annotasyonları tarar; tanımlı bean'leri başlatır ve yaşam döngülerini yönetir. Orijinal örnekte olduğu gibi, metadata kullanılarak item bağımlılığı ayarlanabilir ve container çalışma zamanında bean'leri assemble eder.

Spring'de Dependency Injection constructor, setter veya field aracılığıyla yapılabilir.

#### 1.4 Constructor-tabanlı DI
Constructor tabanlı DI durumunda, container bağımlılıkları temsil eden argümanlarla bir constructor'ı çağırır.

Spring her argümanı öncelikle tipe göre, sonra attribute adlarına, sonra gerektiğinde indeks ile çözer. Annotation örneği:

```

@Configuration
public class AppConfig {

    @Bean
    public Item item1() {
        return new ItemImpl1();
    }

    @Bean
    public Store store() {
        return new Store(item1());
    }
}
```
@Configuration sınıfın bean tanımlarının kaynağı olduğunu belirtir. @Bean metodları bean tanımlarını oluşturur; isim verilmezse metod adı bean adı olur.

Varsayılan singleton scope için Spring önce önbellekte bir örnek olup olmadığını kontrol eder; yoksa yeni bir örnek oluşturur. prototype scope kullanılıyorsa her çağrıda yeni örnek döner.

XML ile aynı konfigürasyon şu şekilde ifade edilir:

```xml

<bean id="item1" class="org.baeldung.store.ItemImpl1" /> 
<bean id="store" class="org.baeldung.store.Store"> 
    <constructor-arg type="ItemImpl1" index="0" name="item" ref="item1" /> 
</bean>
```
#### 1.5 Setter-tabanlı DI
Setter tabanlı DI'de container, bean'i no-arg constructor ile örnekledikten sonra setter metodlarını çağırır. Annotation ile örnek:

```java

@Bean
public Store store() {
    Store store = new Store();
    store.setItem(item1());
    return store;
}
```
XML ile aynı yapı:

```xml

<bean id="store" class="org.baeldung.store.Store">
    <property name="item" ref="item1" />
</bean>
```
Constructor ve setter tabanlı injection aynı bean içinde birlikte kullanılabilir. Spring dokümantasyonu, zorunlu bağımlılıklar için constructor tabanlı injection, opsiyonel bağımlılıklar için setter tabanlı injection kullanmayı önerir.

##### 1.6 Field-tabanlı DI
Field tabanlı DI için alanları @Autowired ile işaretleyebiliriz:

```java

public class Store {
    @Autowired
    private Item item; 
}
```

Container, Store nesnesini oluştururken eğer constructor veya setter ile inject yoksa, reflection kullanarak Item'ı inject eder.

Bazı dezavantajları:

Reflection kullandığı için constructor/setter'a kıyasla daha maliyetli olabilir.
Sınıfa çok sayıda bağımlılık eklemek kolaylaşır; constructor kullanılsaydı birden fazla argüman olması sınıfın tek bir sorumluluktan sapmasını akla getirebilir (SRP ihlali).
Spring ayrıca @Autowired ile tip bazlı autowiring sağlar; aynı tipe sahip birden fazla bean varsa @Qualifier ile isim belirtebilirsiniz.

#### 1.7 Autowiring
Autowiring, Spring container'ın tanımlanmış bean'ler arasındaki bağımlılıkları otomatik çözmesini sağlar.

XML konfigürasyonda dört autowiring modu vardı: no, byName, byType, constructor. Ancak autowire özelliği Spring 5.1 itibariyle eskimiştir; güncel projelerde annotation tabanlı @Autowired ve @Qualifier tercih edilir.

Örnek @Qualifier kullanımı:

```java

public class Store {
    
    @Autowired
    @Qualifier("item1")
    private Item item;
}
```
XML ile byType örneği (eski stil):

```xml

<bean id="store" class="org.baeldung.store.Store" autowire="byType"> </bean>
```
Autowiring'leri açıkça constructor argümanları veya setter'larla override edebilirsiniz.

##### 1.8 Lazy başlatılan bean'ler
Varsayılan olarak container tüm singleton bean'leri başlatma sırasında oluşturur. Bunu önlemek için bean'i lazy-init ile işaretleyebilirsiniz:

```xml

<bean id="item1" class="org.baeldung.store.ItemImpl1" lazy-init="true" />
Böylece item1 bean'i yalnızca ilk çağrıldığında oluşturulur; başlangıç süresi kısalır fakat konfigürasyon hatalarını yalnızca bean ilk talep edildiğinde keşfedersiniz.
```
## 2. Spring Bean Lifecycle & Scope’lar

#### 2.1 Bean Nedir?

Spring Boot’ta bean, uygulamanın temel yapı taşlarını oluşturan Java nesneleridir. Normalde new anahtar kelimesi kullanarak bir sınıftan nesne üretirebilirsin. Spring’te ise bu nesneler Spring Container tarafından üretilir. Ayrıca bu nesnelerin tüm yaşam döngüsü de container tarafından yönetilir. Bu şekilde nesnelerin proje içerisindeki yönetimi Dependncy Injection ile otomatikleşir. Şimdi buradaki terimleri biraz detaylandıralım.

Spring’in olayı şu:

“Objeyi sen yaratma, ben yaratayım ve yöneteyim.”

#### 2.2 Bean Lifecycle (Yaşam Döngüsü)

Bean Lifecycle, bir bean’in oluşturulma, yapılandırma, başlatma, kullanım ve yok edilme aşamalarını ifade eder. Bir bean, Spring Container tarafından yönetildiğinde, belirli bir yaşam döngüsünü takip eder ve bu döngü boyunca farklı aşamalardan geçer. Bir spring bean oluşturulması için aşağıdaki aşamalardan geçmektedir.

#### 2.3 Spring Container
Spring in kalbidir. Uygulamadaki bean lerin oluşturulması, yapılandırılması, yaşam döngüsü vb. görevleri yönetir. Inversion of Control yaklaşımına sahiptir. IoC yaklaşımında kod akışında nesne oluşturulmaz. Container nesne oluşturulacağı zaman kontrolü alır, nesneyi oluşturur, yapılandırır kullanıma uyguna getirir ve daha kontrolü tekrar kod akışına devreder. Böyle sizin kodda nesne yönetimine karışmazken container bunu sizin yerinize yönetir.


#### 1- BeanFactory: 
Temel IoC container, bean leri lazy loading (gerektiğinde yükleme) ile yönetir. Daha az özellik, daha hafif.

#### 2- ApplicationContext:
BeanFactory’nin gelişmiş versiyonu. Eager loading (başlangıçta yükleme) yapar, ayrıca event yayınlama, AOP, uluslararasılaştırma (i18n) gibi ek özellikler sağlar. Spring Boot projelerinde genelde budur.

#### Tam Lifecycle Akışı

Bean tanımı okunur (annotation / XML / config)

Bean instance oluşturulur (constructor)
⁠
Dependency Injection yapılır
⁠
Aware interface’ler çağrılır
⁠
BeanPostProcessor → before init
⁠
Init method çalışır
⁠
BeanPostProcessor → after init
⁠
Bean kullanıma hazır hale gelir
⁠
Context kapanırken destroy phase çalışır

#### 2.4 Instantiation (Nesne Oluşturma)

Spring bean’i instantiate eder:

```
@Component

public class UserService {

    public UserService() {

        System.out.println("Constructor çalıştı");

    }

}


Alternatifler:

Constructor injection
⁠
```
Factory method (
@Bean)
⁠```

Static factory
#### 2.5 Dependency Injection
Spring bağımlılıkları enjekte eder:
```
@Component

public class OrderService {

 

    private final PaymentService paymentService;

 

    public OrderService(PaymentService paymentService) {

        this.paymentService = paymentService;

    }

}
```

Best practice: constructor injection

#### 2.6 Aware Interface’ler

Spring container hakkında bilgi verir.

Interface AçıklamaBeanNameAwareBean adını verir BeanFactoryAwareBeanFactory erişimi ApplicationContextAwareContext erişimi
```
@Component

public class MyBean implements BeanNameAware {

    @Override

    public void setBeanName(String name) {

        System.out.println("Bean name: " + name);

    }

}
```

#### 2.7 BeanPostProcessor (KRİTİK NOKTA)

Spring’in “magic” yaptığı yer burası.

Before Initialization

```
@Component

public class MyProcessor implements BeanPostProcessor {

 

    @Override

    public Object postProcessBeforeInitialization(Object bean, String name) {

        return bean;

    }

}
```

After Initialization

```
@Override

public Object postProcessAfterInitialization(Object bean, String name) {

    return bean;

}
```

Burada yapılanlar:

Proxy oluşturma (AOP)
⁠
Logging wrap
⁠
Security intercept

#### 2.8 Initialization Phase
Bean hazır hale gelmeden önce çalışır.

1. @PostConstruct (EN ÇOK KULLANILAN)

```
@PostConstruct

public void init() {

    System.out.println("Init çalıştı");

}
```

2. InitializingBean

```
@Override

public void afterPropertiesSet() {

}
```
3. Custom Init
```
@Bean(initMethod = "init")
```

##### 2.9 Destroy Phase

Context kapanırken çalışır.

1. @PreDestroy

```
@PreDestroy

public void destroy() {

}
```

2. DisposableBean

```
@Override

public void destroy() {

}
```

3. Custom destroy

```
@Bean(destroyMethod = "cleanup")
```

ÖNEMLİ NOT

prototype scope:

Spring sadece oluşturur

Destroy lifecycle’ı yönetmez

#### 2.10 Bean Scope’ları

Scope = Bean’in yaşam süresi + kaç instance olacağı

#### 2.11 Singleton (Default)

Container başına tek instance
⁠
Tüm uygulama boyunca yaşar @Component
@Scope("singleton")

public class UserService {}

Özellikler:

Default scope
⁠
Thread-safe olmak
senin sorumluluğun
⁠
Stateless olması önerilir
#### 2.12 Prototype
Her injection’da yeni instance @Component
@Scope("prototype")

public class TempBean {}

Özellikler:

Stateful işler için uygun
⁠
Destroy method çağrılmaz
⁠
Lifecycle yarım yönetilir
#### 2.13 Request Scope (Web)
Her HTTP request için yeni bean @Component
@Scope("request")

public class RequestBean {}

Kullanım:

Request bazlı data 
##### 2.14 Session Scope
Her kullanıcı session’ı için 1 bean @Scope("session")
#### 2.15 Application Scope
ServletContext boyunca 1 instance @Scope("application")
#### 2.16 WebSocket Scope
WebSocket session bazlı @Scope("websocket")
##### 2.17 Scope + Injection Problemi (IMPORTANT)

Problem:

Singleton içine prototype inject edersen:

```
@Component

public class A {

    @Autowired

    private B b;

}
```

B prototype olsa bile tek instance olur

Çözüm 1: ObjectProvider

```
@Autowired

private ObjectProvider <B> provider;

 

public void use() {

    B b = provider.getObject();

}
```

Çözüm 2: @Lookup

```
@Lookup

public B getB() {

    return null;

}
```

#### 2.18 Lifecycle + Scope Özet

ScopeInstance SayısıLifecycleSingleton1FullPrototypeÇokPartialRequestRequest başınaFullSessionSession başınaFull

#### 2.19 Production Best Practices

Singleton → stateless yaz
⁠
Prototype → dikkatli kullan (GC load)
⁠
AOP & proxy → BeanPostProcessor mantığını iyi bil
⁠
Lifecycle method’ları → resource yönetimi için kullan
⁠
Constructor injection → default yaklaşım
## 3. Spring Boot Auto Configuration
#### 3.1 Auto Configuration Nedir?

Spring Boot, dependency’lere bakarak otomatik config yapar.

Örnek:

spring-boot-starter-web ekledin
DispatcherServlet otomatik kurulur
3.2 Nasıl çalışır?

@SpringBootApplication içinde:

@EnableAutoConfiguration

Spring classpath’i tarar ve config yükler.

## 4. Configuration, Profiles ve Properties
4.1 application.properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/db
4.2 Profiles

Farklı environment’lar için:

spring.profiles.active=dev

Dosyalar:

application-dev.properties
application-prod.properties
4.3 @Value ve @ConfigurationProperties
@Value("${server.port}")
private int port;

Daha clean:

@ConfigurationProperties(prefix = "app")
class AppConfig {
    private String name;
}
5. Spring Web (REST + Validation)
5.1 REST Controller
@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping
    public List<User> getUsers() {
        return List.of();
    }
}
5.2 Request Handling
@PostMapping
public User create(@RequestBody User user) {
    return user;
}
5.3 Validation
class User {
    @NotNull
    private String name;
}
@PostMapping
public User create(@Valid @RequestBody User user) {
    return user;
}
6. Exception Handling
6.1 Global Exception Handling
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handle(Exception e) {
        return e.getMessage();
    }
}
6.2 Custom Exception
class UserNotFoundException extends RuntimeException {}
7. Spring AOP (Aspect Oriented Programming)
7.1 Amaç

Cross-cutting concern’leri ayırmak:

logging
security
transaction
7.2 Örnek
@Aspect
@Component
class LoggingAspect {

    @Before("execution(* com.app.service.*.*(..))")
    public void log() {
        System.out.println("Method called");
    }
}
8. Spring Data JPA
8.1 Repository
public interface UserRepository extends JpaRepository<User, Long> {}
8.2 Query Methods
List<User> findByName(String name);
8.3 Custom Query
@Query("SELECT u FROM User u WHERE u.name = :name")
List<User> getUsers(@Param("name") String name);
9. Katmanlı Mimari (Layered Architecture)
9.1 Katmanlar
Controller → API
Service → Business logic
Repository → Data access
9.2 Akış
Controller → Service → Repository → DB
10. Best Practices
Constructor Injection kullan
Business logic’i Controller’da yazma
Exception handling merkezi olsun
DTO kullan (Entity expose etme)
Immutable object tercih et
11. Real-World Notlar
Spring aslında büyük ölçüde Proxy + AOP + DI üzerine kurulu
Transaction management → proxy ile çalışır
Security → filter chain + proxy
12. Pitfall’lar
Field injection kullanmak
God service oluşturmak
Exception swallow etmek
Entity’yi direkt API’da döndürmek
Over-engineering
13. Özet

Spring:

Dependency Injection ile loose coupling sağlar
AOP ile cross-cutting concern’leri ayırır
Boot ile config yükünü azaltır
Enterprise uygulamalar için güçlü bir temel sunar
