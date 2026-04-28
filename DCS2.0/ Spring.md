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
Spring, bean tanımını gördüğünde önce bean örneğini oluşturur (instantiate).

Örnek:

```java

package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class UserService {

    public UserService() {
        System.out.println("Constructor çalıştı");
    }
}
```

Alternatif oluşturma yolları:

Constructor (normal new) — en yaygın.
Factory method (ör. @Bean metodu) — özel oluşturma mantığı için.
Static factory — MyFactory.create() gibi statik factory metodları.
Örnek factory:

```java

@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return UserServiceFactory.create();
    }
}
```

#### 2.5 Dependency Injection (Bağımlılık Enjeksiyonu)
Spring bağımlılıkları inject eder. En iyi uygulama: constructor injection — immutable, test edilebilir ve zorunlu bağımlılıkları açıkça ifade eder.

Örnek (constructor injection):

```java

package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

Alternatifler:

Setter injection: opsiyonel bağımlılıklar için.
Field injection: kolay görünür ama tavsiye edilmez (test ve tasarım zayıflatır).

#### 2.6 Aware Interface’ler
Bean’lerin Spring container hakkında bilgi almasını sağlar. Yaygın olanlar:

BeanNameAware — bean adını alır.
BeanFactoryAware — BeanFactory erişimi.
ApplicationContextAware — ApplicationContext erişimi.
Örnek:

```java

package com.example.demo;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
}
```
Kullanım uyarısı: Aware ara yüzlerini aşırı kullanmak kodun container bağımlılığını artırır — sadece gerektiğinde kullanın.

#### 2.7 BeanPostProcessor (KRİTİK NOKTA)
BeanPostProcessor, Spring’in “magic” yaptığı yerlerden biridir. Tüm bean’ler initialize edilmeden önce ve sonra müdahale edebilirsiniz — burada AOP proxy oluşturma, log wrap, security intercept gibi işlemler yapılır.

Basit örnek:

```java

package com.example.demo;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) {
        // init öncesi değişiklik/izleme
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) {
        // proxy oluşturma veya wrap (AOP burada devreye girebilir)
        return bean;
    }
}
```

Not: Bu sınıfa dikkatlice davranın; tüm bean'leri etkiler ve performans/yan etkiler yaratabilir.

#### 2.8 Initialization Phase
Bean hazır hale gelmeden önceki callback noktaları:

```
@PostConstruct (en yaygın)
java

@PostConstruct
public void init() {
    System.out.println("Init çalıştı");
}
```
InitializingBean (afterPropertiesSet)

```java

@Override
public void afterPropertiesSet() {
    // init logic
}
```
Custom init method (@Bean(initMethod = "init"))
Tercih: basitliği ve framework agnostikliği için @PostConstruct.

#### 2.9 Destroy Phase
Context kapanırken çalışır — sadece container tarafından yönetilen bean’lerde garanti edilir (örn. singleton).


@PreDestroy
```java

@PreDestroy
public void destroy() {
    // cleanup
}
DisposableBean
java

@Override
public void destroy() {
    // cleanup
}
```

Custom destroy (@Bean(destroyMethod = "cleanup"))
ÖNEMLİ: prototype scope için Spring, destroy callback’lerini çağırmaz — cleanup sorumluluğu uygulayıcıdadır (bkz alıntı). Eğer prototip bean kaynak açıyor ise:

Manuel cleanup yapın (bean kullanan taraf close()/cleanup() çağırsın), veya
BeanPostProcessor ile takip edip temizleyin, veya
@Scope(proxyMode = ...) + lifecycle wrapper kullanın.
Örnek manuel cleanup:

```java

TransientResource r = context.getBean(TransientResource.class);
try {
    // kullan
} finally {
    r.cleanup(); // manuel çağrı
}
```

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

## 3. Spring Boot Auto-Configuration — Hızlı Geliştirme
Spring Boot auto-configuration, uygulamayı hızlı başlatmak için sık kullanılan bean'leri ve konfigürasyonları otomatik olarak oluşturur. Amaç: boilerplate azaltmak ve geliştiricinin yalnızca özelleştirmek istediği noktaları override etmesine izin vermektir.

Nasıl çalışır?

Boot, classpath'teki spring-boot-autoconfigure ve diğer jar'lardaki auto-configuration sınıflarını yükler.
Her auto-config sınıfı @ConditionalOn... anotasyonlarıyla (ör. @ConditionalOnClass, @ConditionalOnMissingBean, @ConditionalOnProperty) çalışır; koşullar sağlanıyorsa bean'leri tanımlar.
Eğer uygulamanızda aynı tipte bir bean zaten tanımlıysa (@ConditionalOnMissingBean) otomatik konfigürasyon geri çekilir (back off).
Avantajlar

Hızlı prototipleme ve geliştirme.
Sık kullanılan yapıların (DataSource, JPA, Jackson, MVC vb.) hazır olması.
İyi tanımlanmış varsayılanlar: çoğu uygulamada hiç ekstra konfigürasyon gerekmez.
Dezavantaj / dikkat

Otomatik yapılandırmayı anlamadan değiştirmek hatalara yol açabilir.
Özelleştirme gerektiğinde, hangi bean’in oluşturulduğunu bilmek önemlidir.

#### 3.1 Örnek: DataSource otomatik konfigürasyonunu override etme
Varsayılan: JDBC sürücüsü classpath'te ve spring.datasource.* property'leri varsa Boot otomatik DataSource oluşturur. Kendi DataSource bean'inizi sağlarsanız auto-config devre dışı kalır.

```java

// src/main/java/com/example/config/CustomDataSourceConfig.java
package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CustomDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
        cfg.setUsername("user");
        cfg.setPassword("pass");
        return new HikariDataSource(cfg);
    }
}
```

Bu bean sayesinde DataSourceAutoConfiguration @ConditionalOnMissingBean(DataSource.class) koşulunu sağlamadığı için Boot otomatik DataSource oluşturmaz.

#### 3.2 Kendi Auto-Configuration sınıfınızı oluşturma (library için)

```java

// src/main/java/com/example/autoconfig/HelloAutoConfiguration.java
package com.example.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "helloBean")
    public String helloBean() {
        return "hello";
    }
}
```
Kütüphane olarak paketliyorsanız META-INF/spring.factories (veya daha yeni Boot sürümlerinde spring-autoconfigure mekanizması) ile kaydetmeniz gerekir.
Auto-config sınıfları koşullu, küçük ve test edilebilir olmalı; varsayılanları belgelerle açıklayın.
#### 3.3 Önemli anotasyonlar ve nerede kullanılır
@ConditionalOnClass — belirli bir sınıf classpath'te varsa.
@ConditionalOnMissingBean — belirli bir bean yoksa.
@ConditionalOnProperty — belirli bir property aktifse.
@ConditionalOnBean — belirli bir bean varsa.
@AutoConfigureAfter / @AutoConfigureBefore — sıra kontrolü.
##### 3.4 Best practices (özet)
Boot’un sağladığı auto-config’e güvenin; sadece gerektiğinde override edin.
Kendi auto-config yazıyorsanız: @Conditional... kullanın, isimlendirmeyi ve dokümantasyonu net yapın.
Testlerde auto-config davranışını doğrulayın (@SpringBootTest veya @ImportAutoConfiguration).
Auto-config’i spring.autoconfigure.exclude ile uygulama bazında kapatabilirsiniz.
## 4. Configuration, Profiles, Properties — Ortam Yönetimi
Spring Boot’ta konfigürasyon dış kaynaklardan okunur ve belirli bir öncelik sırasına göre uygulanır. Profil (profile) mekanizması farklı ortamlara göre (dev/test/prod) farklı property set’leri kullanmayı sağlar.

#### 4.1 Yapı ve öncelik (kısaca)
Öncelik (yüksekten düşüğe):

Command line args (--spring.profiles.active=prod, --server.port=8081)
SPRING_APPLICATION_JSON (env)
OS environment variables
application-{profile}.properties / application-{profile}.yml
application.properties / application.yml
@PropertySource, @ConfigurationProperties sınıfları
(Ayrıntılar için Boot Externalized Configuration dokümanına bakın.)
#### 4.2 Profile kullanımı
Profile dosya isimlendirmesi: application-dev.yml, application-prod.yml vb.
Profili aktifleştirme:
Environment variable: export SPRING_PROFILES_ACTIVE=dev
JVM argümanı: -Dspring.profiles.active=dev
Komut satırı: java -jar app.jar --spring.profiles.active=dev
Programatik: new SpringApplicationBuilder(App.class).profiles("dev").run(args);
Örnek application.yml ile profile kullanımı:

```yaml

spring:
  profiles:
    active: dev

---
spring:
  profiles: dev
app:
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password: ""

---
spring:
  profiles: prod
app:
  datasource:
    url: jdbc:postgresql://prod-host/proddb
    username: prod
    password: ${DB_PASSWORD}
```
#### 4.3 Tip güvenli konfigürasyon: @ConfigurationProperties
Tercih edilen yöntem: Çok sayıda property’yi @ConfigurationProperties ile POJO’ya bağlamak.
Avantaj: tip güvenli, IDE desteği, kolay validasyon.
Örnek:

```java

// src/main/java/com/example/config/AppProperties.java
package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank
    private String name;

    @Min(1)
    private int timeout;

    // getters & setters
}
```
application.yml:

```yaml

app:
  name: demo-app
  timeout: 30
```
Eğer validasyon isterseniz @Validated ekleyin ve JSR-380 annotasyonlarını kullanın.
#### 4.4 Gizli (sensitive) değerler ve dışsal kaynaklar
Hassas verileri asla VCS’e koymayın. Kullanılabilecek yollar:
Environment variables (12-factor uygulama yaklaşımı)
External config files (örn. mounted volume) via --spring.config.location
Secrets manager (HashiCorp Vault, AWS Secrets Manager vb.)
Kubernetes Secrets (k8s ortamında)
Örnek placeholder:

```yaml

spring:
  datasource:
    url: jdbc:postgresql://host/db
    username: ${DB_USER:default}
    password: ${DB_PASSWORD}
```
#### 4.5 ConditionalOnProperty ile feature toggle
Property bazlı conditional bean:

```java

@Configuration
@ConditionalOnProperty(name = "feature.x.enabled", havingValue = "true", matchIfMissing = false)
public class FeatureXConfig {
    @Bean
    public FeatureXService featureXService() {
        return new FeatureXService();
    }
}
```
feature.x.enabled=true olduğunda bean yüklenir. Bu şekilde özellik aç/kapat (feature toggle) mekanizması oluşturabilirsiniz.
#### 4.6 Profil + Bean örneği
```java

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public DataSource dataSource() {
        // dev datasource (H2, embedded)
    }
}
```
```
@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public DataSource dataSource() {
        // production datasource
    }
}
```
#### 4.7 Best practices (özet)
Varsayılan ortak konfigürasyon: application.yml içine koyun; profile-specific değerleri application-{profile}.yml içine alın.
Tip güvenli config: @ConfigurationProperties kullanın ve validate edin.
Gizli bilgileri environment variable veya secrets manager ile sağlayın.
Feature toggle: @ConditionalOnProperty kullanarak aç/kapat yapın.
Dökümantasyon: Hangi property’lerin zorunlu olduğuna dair README veya schema sağlayın; CI’de eksik property’leri kontrol edin.
Profile sayısını sınırlı tutun (genelde dev/test/prod yeterlidir); karmaşıklığı artırmaktan kaçının.
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
