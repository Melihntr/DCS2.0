# SPRING & SPRING BOOT DOKÜMANTASYONU
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

#### 1.6 Field-tabanlı DI
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
#### 2.14 Session Scope
Her kullanıcı session’ı için 1 bean @Scope("session")
#### 2.15 Application Scope
ServletContext boyunca 1 instance @Scope("application")
#### 2.16 WebSocket Scope
WebSocket session bazlı @Scope("websocket")
#### 2.17 Scope + Injection Problemi (IMPORTANT)

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

Dezavantaj

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
## 5. Spring Web REST Temelleri ve İstek Yaşam Döngüsü
REST (Representational State Transfer), web servisleri tasarlamak için kullanılan standart mimari stildir. Spring Framework, spring-webmvc modülü ile bu mimariyi en üst düzeyde destekler.

#### 5.1 İstek Yaşam Döngüsü (Request Lifecycle)
Bir istemciden gelen HTTP isteğinin sunucu içindeki yolculuğu şu adımlardan oluşur:

İstemci İsteği: İstemci, belirli bir HTTP metodu ve URL ile sunucuya istek atar (Örn: GET /api/v1/users).

DispatcherServlet: Spring Boot'un "Front Controller" mekanizmasıdır. Gelen tüm HTTP isteklerini ilk karşılayan ana yönlendiricidir.

Handler Mapping: DispatcherServlet, gelen isteğin URL'sine ve HTTP metoduna bakarak uygun Controller metodunu bulur.

Controller & İş Mantığı: İlgili metot çalışır, parametreleri alır ve Service (iş mantığı) katmanını tetikler.

Message Converters (Jackson): İşlem sonucunda Controller bir Java objesi döndürdüğünde, HttpMessageConverter araya girer ve bu objeyi istemcinin anlayacağı JSON veya XML formatına dönüştürür (Serialization).

HTTP Yanıtı: Oluşturulan JSON verisi ve uygun HTTP statü kodu istemciye geri gönderilir.


#### 5.2 Temel REST Anotasyonları
Spring uç noktalarını (endpoints) tanımlamak için deklaratif anotasyonlar kullanılır.

@RestController: Sınıfın bir Controller olduğunu ve metot dönüş değerlerinin doğrudan HTTP yanıt gövdesine yazılacağını belirtir (@Controller + @ResponseBody).

@RequestMapping: Sınıf bazında temel URL'i tanımlar. (Örn: @RequestMapping("/api/v1/products")).

HTTP Metot Eşleştirmeleri:

@GetMapping: Veri okuma (Read).

@PostMapping: Yeni kaynak oluşturma (Create).

@PutMapping: Mevcut kaynağı tamamen güncelleme (Update).

@PatchMapping: Mevcut kaynağı kısmen güncelleme (Partial Update).

@DeleteMapping: Kaynak silme (Delete).

<img width="4000" height="2250" alt="image" src="https://github.com/user-attachments/assets/109eca82-880b-489c-9e8d-b53347989f1c" />


#### 5.3 Veri Bağlama (Data Binding) Yöntemleri
İstemciden gelen veriyi Java nesnelerine dönüştürmek için 4 temel yöntem kullanılır:

@PathVariable: URL yolu içindeki dinamik değişkenleri okur (/users/{id}).

@RequestParam: URL sonundaki sorgu parametrelerini okur (/users?role=admin&sort=asc).

@RequestBody: POST/PUT isteklerinde gelen JSON gövdesini (Body) Java DTO sınıflarına eşler.

@RequestHeader: HTTP başlıklarındaki (Authorization, Accept-Language vb.) verileri okur.


#### 5.4 HTTP Yanıt Yönetimi (ResponseEntity)
Esnek ve standart bir API tasarımı için, yanıtların statü kodları ve başlıklarıyla birlikte dönülmesi gerekir.

```Java
@GetMapping("/{id}")
public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    UserDTO user = userService.findById(id);
    return ResponseEntity.ok(user); // HTTP 200
}

@PostMapping
public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO dto) {
    UserDTO created = userService.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created); // HTTP 201
}
```

#### 5.5 Katmanlı Mimari ve Veri Taşıma (DTO)
Spring Boot projeleri "Separation of Concerns" (Sorumlulukların Ayrılığı) prensibiyle 3 ana katmana bölünür.

#### 5.6 Katmanlar ve Görevleri
Web Katmanı (@RestController): HTTP isteklerini karşılar, veriyi doğrular (Validation), iş katmanına iletir ve HTTP yanıtı döner.

İş Katmanı (@Service): Uygulamanın beynidir. Algoritmalar, hesaplamalar ve iş kuralları burada çalışır. Veritabanı ile Controller arasında köprü görevi görür.

Veri Erişim Katmanı (@Repository): Veritabanı işlemlerini (CRUD) yapar. Spring Data JPA kullanılarak SQL yazmadan veritabanı ile iletişim kurulur.


#### 5.7 Entity ve DTO Ayrımı
Entity: Veritabanındaki tabloyu temsil eden sınıftır. İçerisinde şifreler, kritik tarih bilgileri ve ilişkiler barındırabilir. Dış dünyaya açılması güvenlik açığı yaratır.

DTO (Data Transfer Object): Sadece katmanlar arası (veya istemci-sunucu arası) veri taşımak için tasarlanmış basit Java nesneleridir. İstemci veriyi DTO olarak gönderir, Controller bunu Service'e iletir, Service Entity'ye çevirip kaydeder.

Örnek Mimari Akışı:

```Java
// 1. DTO (Sadece taşınacak veriler)
public class UserCreateDTO {
    private String username;
    private String email;
    // Getter & Setter
}

// 2. Entity (Veritabanı tablosu)
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String role; // İstemcinin bilmesine gerek olmayan alan
}

// 3. Service (İş Mantığı ve Dönüşüm)
@Service
public class UserService {
    public UserCreateDTO createUser(UserCreateDTO dto) {
        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setRole("USER");
        userRepository.save(entity);
        return dto; // Başarılıysa DTO dön
    }
}
```

#### 5.8 İleri Düzey API Standartları
Profesyonel bir API'nin performans, güvenlik ve sürdürülebilirlik gereksinimlerini karşılaması için bazı ileri düzey teknikler uygulanır.


#### 5.9 Sayfalama ve Sıralama (Pagination & Sorting)
Büyük veri setlerini tek seferde dönmek yerine parçalar halinde sunmak için Pageable kullanılır.


```Java
@GetMapping
public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable) {
    return ResponseEntity.ok(productService.findAll(pageable));
}
```
```
// İstek: /api/products?page=0&size=20&sort=price,desc
```

#### 5.10 Versiyonlama (API Versioning)
Geriye dönük uyumluluğu korumak için API'ler versiyonlanır. En yaygın yöntem URI versiyonlamadır.

```
@RequestMapping("/api/v1/users") (Eski Sürüm)

@RequestMapping("/api/v2/users") (Yeni Sürüm)
```

#### 5.11 İçerik Pazarlığı (Content Negotiation)
Endpoint'in hangi formatı kabul edip hangi formatı döneceğini kesin çizgilerle belirlemek için consumes ve produces parametreleri kullanılır.

```Java
@PostMapping(consumes = "application/json", produces = "application/xml")
```
#### 5.12 CORS (Cross-Origin Resource Sharing)
Farklı domain veya portlardan (Örn: React uygulamasından) gelen isteklere izin vermek için @CrossOrigin anotasyonu veya global olarak WebMvcConfigurer ayarı kullanılır.


#### 5.13 HATEOAS (Hypermedia)
REST olgunluk modelinin en üst seviyesidir. API sadece veri dönmez, istemciye bir sonraki adımda yapabileceği eylemlerin URL linklerini de (_links) sağlar.


#### 5.14 Validation API (Gelen Verinin Doğrulanması)
API'ye gelen hiçbir veriye güvenilmemelidir. Verilerin Service katmanına inmeden önce Controller seviyesinde deklaratif olarak denetlenmesini sağlayan yapı Jakarta Bean Validation (JSR 380) API'dir.


Gereksinim: spring-boot-starter-validation bağımlılığı pom.xml dosyasına eklenmelidir.

#### 5.15 Temel Validasyon Anotasyonları
Kurallar DTO sınıflarındaki alanlar üzerinde tanımlanır:

Metin/Boşluk: @NotNull (Boş olamaz), @NotEmpty (Uzunluk > 0), @NotBlank (Sadece boşluk karakteri olamaz).

Boyut/Sınır: @Size(min, max) (Uzunluk/Eleman sayısı kısıtı), @Min, @Max, @Positive, @Negative.

Tarih: @Past (Geçmiş tarih), @Future (Gelecek tarih).

Format: @Email (E-posta formatı), @Pattern(regexp=) (Düzenli ifade kuralı).

#### 5.16 DTO ve Validasyon Entegrasyonu
Kuralların tanımlandığı örnek bir DTO:

```Java
public class UserRegistrationDTO {

    @NotBlank(message = "Kullanıcı adı boş bırakılamaz.")
    @Size(min = 4, max = 20, message = "Kullanıcı adı 4-20 karakter arası olmalıdır.")
    private String username;

    @Email(message = "Geçerli bir e-posta adresi giriniz.")
    private String email;

    @Min(value = 18, message = "Kayıt için 18 yaşından büyük olmalısınız.")
    private Integer age;
}
```

#### 5.17 Doğrulamanın Tetiklenmesi (@Valid)
Bu kuralların çalışması için Controller tarafında ilgili metodun parametresine @Valid veya @Validated eklenmelidir.

```Java
@PostMapping("/register")
public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
    // Kurallara uyulmazsa kod buraya girmez, MethodArgumentNotValidException fırlatılır.
    userService.register(dto);
    return ResponseEntity.ok("Kayıt başarılı");
}
```

#### 5.18  İleri Düzey Validasyon (Custom Validator Yazmak)
Standart anotasyonların yetmediği durumlarda (Örn: TC Kimlik No Algoritması) kendi anotasyonunuzu ve işleyicinizi (Validator) yazabilirsiniz.

Anotasyon Tanımı: @Constraint(validatedBy = CustomValidator.class) ile anotasyon oluşturulur.

Validator Sınıfı: ConstraintValidator<Anotasyon, Tip> arayüzü implemente edilerek isValid() metodu ezilir (override edilir). İçerisine ilgili kompleks iş mantığı (modüler aritmetik vs.) yazılır.


## 6. Merkezi Hata Yönetimi (Global Exception Handling)
RESTful mimaride hatalar, sistemin çökmesi anlamına gelmez; aksine, istemciye neyin yanlış gittiğini anlatan standart bir iletişim yöntemidir. Hataların Controller metotları içinde tek tek try-catch bloklarıyla yakalanması kodu kirletir (Spaghetti Code) ve DRY (Don't Repeat Yourself) prensibine aykırıdır.

Spring Framework, bu sorunu çözmek için AOP (Aspect-Oriented Programming - Cephe Yönelimli Programlama) mantığıyla çalışan @RestControllerAdvice anotasyonunu sunar.

#### 6.1 Neden Merkezi Hata Yönetimine İhtiyacımız Var?
Standart Yanıt Formatı: İster 404 (Bulunamadı), ister 400 (Doğrulama Hatası), ister 500 (Sunucu Hatası) olsun; mobil veya frontend geliştiricisi her zaman aynı JSON yapısında (timestamp, status, message vb.) bir hata nesnesi bekler.

Güvenlik (Information Disclosure): Sunucuda oluşan bir NullPointerException'ın veya SQL hatasının detayları istemciye sızmamalıdır. Kötü niyetli kişiler bu detayları kullanarak sistemin zafiyetlerini bulabilir. Merkezi yönetim bunu engeller.

Temiz Kod (Clean Code): İş mantığı (Service) veya Controller kodları sadece "başarılı" (Happy Path) senaryolara odaklanır. İstisnalar (Exceptions) fırlatılır ve merkezi bir sınıf bu istisnaları havada yakalar.


#### 6.2 Adım 1: Standart Hata Modelinin (DTO) Oluşturulması
Tüm hatalarımızı sarmalayacağımız ve istemciye döneceğimiz ortak bir şablon sınıfı oluşturmalıyız.

```Java
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

// Eğery fields null ise JSON'a dahil etme (Temiz bir çıktı için)
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ApiError {
    private LocalDateTime timestamp; // Hatanın oluştuğu an
    private Integer status;          // HTTP Statü Kodu (Örn: 400, 404, 500)
    private String error;            // Hata Başlığı (Örn: "Bad Request")
    private String message;          // Kullanıcıya/Geliştiriciye gösterilecek anlaşılır mesaj
    private String path;             // Hatanın oluştuğu uç nokta (Endpoint)
    
    // Sadece Validation (Doğrulama) hatalarında dolacak olan, alan bazlı hata listesi
    private Map<String, String> validationErrors; 

    // Constructor (Zaman damgasını otomatik atar)
    public ApiError(Integer status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getter ve Setter metotları...
}
```

#### 6.3 Adım 2: Özel (Custom) İstisna Sınıflarının Yazılması
İş mantığınızda belirli durumlara özgü hatalar fırlatmak en iyi pratiktir. Örneğin veritabanında id'si 5 olan kullanıcı bulunamadığında genel bir hata yerine spesifik bir hata fırlatmalıyız.

```Java
// RuntimeException'dan türetilir, böylece checked exception zorunluluğu olmaz.
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

#### 6.4 Adım 3: @RestControllerAdvice ile Merkezi Sınıfın İnşası
Bütün büyünün gerçekleştiği yer burasıdır. Bu sınıf, tüm Controller'ları dışarıdan dinleyen bir "gözetmen" (Interceptor) gibi çalışır.

```Java
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Tüm uygulama genelinde fırlatılan Exception'ları dinler ve JSON olarak döner
@RestControllerAdvice 
public class GlobalExceptionHandler {

    /**
     * 1. DURUM: Özel "Bulunamadı" Hatası (Business Exception)
     * Service katmanında "throw new ResourceNotFoundException('Kullanıcı bulunamadı')" 
     * dendiğinde bu metot devreye girer.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex, 
            HttpServletRequest request) {
            
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(), // 404
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * 2. DURUM: BÖLÜM 4'teki Validation (Doğrulama) Hataları
     * Controller'da @Valid anotasyonu başarısız olduğunda Spring bu hatayı fırlatır.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
            
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Validation Error",
                "İstek doğrulama kurallarından geçemedi.",
                request.getRequestURI()
        );

        // İç içe geçmiş doğrulama hatalarını ayıkla ve Map içine koy
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // Örn: Anahtar: "email", Değer: "Geçerli bir e-posta giriniz."
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        apiError.setValidationErrors(validationErrors);

        return ResponseEntity.badRequest().body(apiError);
    }

    /**
     * 3. DURUM: Öngörülemeyen Sistem Hataları (Catch-All)
     * Uygulamada yakalanmayan bir NullPointerException, veritabanı çökmesi vb.
     * olursa, sistemin kapanmasını gizler ve güvenli bir 500 döner.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaughtException(
            Exception ex, 
            HttpServletRequest request) {
            
        // Loglama işlemi kesinlikle buraya eklenmelidir (örn: log.error(ex.getMessage(), ex))
        
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Internal Server Error",
                "Sunucu tarafında beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", 
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

#### 6.5 Sistemin Bütünleşik Çalışma Çıktısı (Örnek Senaryo)
Diyelim ki bir istemci, Bölüm 4'te yazdığımız sisteme kayıt olmak için bir POST isteği attı, ancak yaşını 15 (kural: min 18) ve e-postasını boş gönderdi.

İstek Controller'a gelir. @Valid devreye girer.

Doğrulama başarısız olur ve Controller metoduna hiç girilmeden Spring MethodArgumentNotValidException fırlatır.

@RestControllerAdvice bu istisnayı havada yakalar.

handleValidationExceptions metodu çalışır ve istemciye aşağıdaki mükemmel yapılandırılmış JSON yanıtını HTTP 400 durumu ile döner:


```JSON
{
  "timestamp": "2026-05-04T22:15:30.456",
  "status": 400,
  "error": "Validation Error",
  "message": "İstek doğrulama kurallarından geçemedi.",
  "path": "/api/v1/users/register",
  "validationErrors": {
    "email": "E-posta adresi boş geçilemez.",
    "age": "Kayıt için 18 yaşından büyük olmalısınız."
  }
}
```

#### 6.6 Spring Boot 3 & RFC 7807 (Modern Standart: Problem Details)
Eğer projede Spring Boot 3 (Spring Framework 6) kullanıyorsanız, yukarıdaki özel ApiError sınıfını yazmak yerine, global bir endüstri standardı olan RFC 7807 (Problem Details for HTTP APIs) yapısını kullanabilirsiniz.

Spring Boot 3 bunu yerleşik olarak destekler. application.properties dosyasına şu satırı eklediğinizde:

```Properties
spring.mvc.problem-details.enabled=true
```
Spring, standart hataları otomatik olarak RFC 7807'nin ProblemDetail yapısına (type, title, status, detail, instance alanları içeren standart bir formata) dönüştürecektir. Profesyonel ve yeni nesil projelerde bu standarda geçiş giderek artmaktadır.

## 7. Spring Core - Aspect Oriented Programming (AOP) ve Merkezi Yönetim
Nesne Yönelimli Programlama (OOP), kodları sınıflar ve nesneler aracılığıyla yukarıdan aşağıya (hiyerarşik) organize etmede mükemmeldir. Ancak OOP, farklı sınıfların yatayda ortak olarak ihtiyaç duyduğu bazı işlevleri yönetmekte yetersiz kalır. AOP, OOP'nin bu eksiğini kapatan tamamlayıcı bir paradigmadır.

#### 7.1 Cross-Cutting Concerns (Kesişen İlgiler) Nedir?
Bir e-ticaret sistemindeki "Sipariş Verme" metodunu düşünün:

Loglama: İşlem başladı logu yaz.

Güvenlik: Kullanıcının yetkisi var mı kontrol et.

İşlem (Transaction): Veritabanı bağlantısını aç.

ASIL İŞ (Core Concern): Siparişi oluştur, stoktan düş, sepeti boşalt.

Performans: İşlem ne kadar sürdü hesapla.

İşlem (Transaction): Sorun yoksa Commit et, hata varsa Rollback yap.

Yukarıdaki 6 adımın sadece 1 tanesi (4. Adım) asıl iş mantığıdır. Diğer 5 adım, sistemdeki hemen hemen her serviste tekrar eden Cross-Cutting Concerns (Kesişen İlgiler) olarak adlandırılır. Eğer bu kodları her metoda kopyala-yapıştır yaparsanız (Boilerplate Code), kod okunamaz hale gelir ve bakımı imkansızlaşır.

Çözüm (AOP): AOP, bu kesişen ilgileri (loglama, güvenlik vb.) ayrı sınıflar (Aspect) içine çeker ve asıl iş mantığı koduna hiç dokunmadan, bu özellikleri dışarıdan (çalışma zamanında) metoda "enjekte" eder.


#### 7.2 AOP Temel Terminolojisi
AOP mimarisini kurabilmek için Spring'in kullandığı terimleri bilmek önemlidir:

Aspect (Cephe): Kesişen ilginin (örneğin Loglama) kodlandığı modüler sınıftır.

Join Point (Katılım Noktası): Uygulamanın çalışma anında (runtime), araya girilebilecek olası her bir noktadır. Spring AOP özelinde bu sadece metotların çalıştırılma anıdır.

Advice (Tavsiye/Müdahale): Belirli bir Join Point'te çalıştırılacak olan eylemdir (Örn: "Metot çalışmadan hemen önce şu logu yaz").

Pointcut (Kesim Noktası): Hangi Join Point'lerde araya girileceğini belirten filtreleme kurallarıdır (Regex veya Anotasyon tabanlı kurallar dizisi).

Target Object (Hedef Nesne): Tavsiyenin (Advice) uygulanacağı asıl iş mantığını barındıran nesnedir.

Weaving (Dokuma): Aspect ile Target Object'in birleştirilme sürecidir. Spring bunu Proxy (Vekil) nesneler oluşturarak çalışma zamanında (Runtime) yapar.


#### 7.3 Advice (Tavsiye) Türleri
Spring AOP, hedefe 5 farklı zamanlamada müdahale edebilir:

@Before: Hedef metot çalışmaya başlamadan hemen önce çalışır.

@AfterReturning: Hedef metot hiçbir hata fırlatmadan başarıyla tamamlandıktan sonra çalışır.

@AfterThrowing: Hedef metot bir Exception fırlattığında (çöktüğünde) çalışır.

@After (Finally): Metot başarılı da olsa, hata da fırlatsa, metot bittiği an çalışır.

@Around: En güçlü türdür. Hedef metodu tamamen sarar. Metodun öncesinde bir şeyler yapabilir, metodun çalışmasını tetikleyebilir (proceed()), sonrasında başka şeyler yapabilir veya metodun hiç çalışmamasını sağlayabilir.


#### 7.4 Uygulama Entegrasyonu ve Mimarisi
Bir "Merkezi Loglama ve Performans Ölçüm" sistemi kurarak AOP'yi projemize entegre edelim.

Adım 1: Bağımlılıkların Eklenmesi
Spring Boot projelerinde AOP kullanmak için pom.xml içerisine şu kütüphane eklenir:

```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

Adım 2: Özel Anotasyon Oluşturma (Modern Yaklaşım)
Pointcut kurallarını "şu paketteki şu isimli metotlar" diye belirlemek yerine, genellikle özel bir anotasyon oluşturulur ve bu anotasyonu koyduğumuz metotların AOP tarafından algılanması sağlanır.

```Java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Çalışma zamanında okunabilsin
@Retention(RetentionPolicy.RUNTIME) 
// Sadece metotların üzerine yazılabilsin
@Target(ElementType.METHOD) 
public @interface TrackExecutionTime {
    // Sadece bir etiket görevi görecek.
}
```

Adım 3: Aspect Sınıfının (Merkezi Yönetim) İnşası
İşte projedeki tüm loglama ve performans metriklerini tek bir merkezden yöneteceğimiz Aspect sınıfımız:

```Java
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect // Bu sınıfın bir Aspect olduğunu belirtir.
@Component // Spring IOC Container tarafından yönetilmesi için.
@Slf4j // Loglama için Lombok anotasyonu
public class LoggingAndPerformanceAspect {

    /**
     * POINTCUT TANIMI
     * com.example.service paketi altındaki ve sonu 'Service' ile biten 
     * tüm sınıflardaki metotları hedef al.
     */
    @Pointcut("execution(* com.example.service.*Service.*(..))")
    public void serviceMethods() {}

    /**
     * @Before ADVICE
     * Yukarıda tanımlanan 'serviceMethods' kuralına uyan bir metot çalışmadan önce bu blok tetiklenir.
     */
    @Before("serviceMethods()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("[BİLGİ] {}.{} metodu çalıştırılmaya başlandı.", className, methodName);
    }

    /**
     * @AfterThrowing ADVICE
     * Service metotlarından herhangi biri hata fırlattığında devreye girer.
     * Bölüm 5'teki ExceptionHandler'dan farkı: Bu istek dönecek JSON'u hazırlamaz, 
     * sadece sistem tarafında iz bırakmak (Log yazmak/Bildirim atmak) için kullanılır.
     */
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("[HATA] {} metodunda istisna oluştu: {}", methodName, ex.getMessage());
    }

    /**
     * @Around ADVICE ve CUSTOM ANNOTATION KULLANIMI
     * Adım 2'de oluşturduğumuz @TrackExecutionTime anotasyonunu nerede görürse orayı sarar.
     * Metodun ne kadar sürede çalıştığını milisaniye cinsinden hesaplar.
     */
    @Around("@annotation(com.example.annotation.TrackExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Asıl metodu ÇALIŞTIR! (Bu satırı yazmazsanız asıl metot asla çalışmaz)
        Object result = joinPoint.proceed(); 

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String methodName = joinPoint.getSignature().getName();
        log.info("[PERFORMANS] {} metodu {} ms sürede tamamlandı.", methodName, executionTime);

        // Asıl metodun sonucunu çağıran yere geri döndür.
        return result; 
    }
}
```

Adım 4: İş Mantığı Katmanında (Service) Kullanımı
Artık Service katmanımız tertemizdir. Loglama veya zaman hesaplama kodları Service sınıflarını kirletmez.

```Java
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // @Before sayesinde bu metot tetiklenmeden önce log yazılacak.
    public void createOrder(OrderDTO order) {
        // ... Sadece sipariş oluşturma kodları (Business Logic) ...
    }

    // @Around (TrackExecutionTime) sayesinde bu metodun süresi ölçülecek.
    @TrackExecutionTime
    public void generateMonthlyReport() {
        // ... Çok uzun süren raporlama kodları ...
    }
}
```

#### 7.5 Spring AOP'nin Mimari Sınırları ve Proxy Mantığı
Spring AOP arka planda Dynamic Proxies kullanır. Spring uygulamanız ayağa kalkarken, eğer OrderService sınıfınızda AOP kullanılmışsa, Spring bu sınıfı doğrudan Controller'a enjekte etmez. Bunun yerine OrderService sınıfını miras alan sahte bir kopya (Proxy) oluşturur.
Controller, createOrder() metodunu çağırdığında aslında Proxy'nin metodunu çağırır. Proxy araya girer (Log yazar), sonra gerçek createOrder() metodunu tetikler.

Kritik Kural (Self-Invocation Sorunu):
Bu Proxy mimarisinden dolayı, AOP sadece sınıflar arası çağrılarda (Örn: Controller -> Service) çalışır. Eğer OrderService içindeki createOrder() metodu, yine aynı sınıf içindeki @TrackExecutionTime anotasyonlu generateMonthlyReport() metodunu this.generateMonthlyReport() şeklinde çağırırsa, AOP devreye girmez! Çünkü çağrı doğrudan asıl nesnenin kendi içindedir, Proxy nesnesine dışarıdan bir giriş yapılmamıştır. Bu, Spring mülakatlarında sıkça sorulan teknik bir detaydır.

# 8. Ekstralar

#### 8.1 Spring'in Temel Özellikleri ile Spring ve Spring Boot Farkı
Spring Framework, Java uygulamaları geliştirmek için kullanılan kapsamlı bir altyapıdır. Temel felsefesi Inversion of Control (IoC) ve Dependency Injection (DI) üzerine kuruludur. Nesnelerin (Bean'lerin) yaşam döngüsünü ve birbirleri arasındaki bağımlılıkları senin yerine Spring Container yönetir. Bu, kodun "gevşek bağlı" (loosely coupled) ve test edilebilir olmasını sağlar.

Spring vs. Spring Boot:

Spring Framework: Bir alet çantasıdır. İhtiyacın olan modülleri (MVC, Data, Security) eklersin, ancak veritabanı bağlantılarından web sunucusu ayarlarına kadar sayısız XML veya Java tabanlı konfigürasyonu senin yapman gerekir.

Spring Boot: Spring'in üzerine inşa edilmiş, "opinionated" (kendi doğruları olan) bir çerçevedir. Amacı konfigürasyon yükünü ortadan kaldırmaktır. Sadece spring-boot-starter-web bağımlılığını eklersin, Spring Boot senin bir web API'si yazacağını anlar, Tomcat'i ayağa kaldırır, DispatcherServlet'i ayarlar ve Jackson kütüphanesini JSON dönüşümleri için hazır hale getirir.


#### 8.2 Tomcat ve Embedded Tomcat (Gömülü Tomcat) Farkı
Tomcat Nedir?
Tomcat, Java Servlet'lerini ve JavaServer Pages (JSP) teknolojilerini çalıştıran bir web sunucusu ve Servlet konteyneridir. Gelen HTTP isteklerini karşılar, Java koduna (Spring'e) iletir ve cevabı HTTP formatında geri döner.

Geleneksel Tomcat (Standalone): Sunucuya fiziksel olarak Tomcat kurulur. Yazdığın uygulama bir .war (Web Application Archive) dosyası olarak derlenir ve Tomcat'in webapps klasörüne atılarak deploy edilir. Sunucu konfigürasyonunu (portlar, thread havuzları) işletim sistemi üzerindeki Tomcat ayar dosyalarından yaparsın.

Embedded Tomcat (Spring Boot Yaklaşımı): Tomcat'in kendisi, senin uygulamanın içine bir kütüphane (.jar) olarak gömülür. Uygulamanı çalıştırdığında (java -jar uygulama.jar), içinde bulunan Tomcat otomatik olarak main metodu üzerinden başlatılır. Ortam bağımsızdır; Java yüklü olan her yerde sunucu kurmaya gerek kalmadan anında ayağa kalkar.


#### 8.3 Maven ve Multi-Module (Çoklu Modül) Mimarisi
Maven Nedir?
Maven, bir proje yönetim ve yapılandırma aracıdır. Temel olarak pom.xml üzerinden projenin bağımlılıklarını (hangi kütüphanelerin kullanılacağını), derleme sürecini (build lifecycle: compile, test, package) ve eklentilerini yönetir.

Multi-Module Maven Projeleri Ne İşe Yarar?
Büyük ölçekli, kurumsal yazılımlarda tüm kodu tek bir pakette tutmak (monolith) bir süre sonra "spagetti koda" ve "tight coupling" (sıkı bağımlılık) sorunlarına yol açar. Multi-module yaklaşımı, projeyi mantıksal alt parçalara böler:

core-module (Ortak utility'ler, domain modelleri)

data-module (Veritabanı işlemleri, repository'ler - sadece core'u bilir)

api-module (REST Controller'lar - data ve core'u bilir)

Avantajı: Katmanlar arası sınırları zorunlu kılar. core-module içindeki bir sınıfın, api-module içindeki bir sınıfa erişmesi Maven seviyesinde engellenir. Bu da SOLID prensiplerine sadık kalan, temiz bir mimari sunar.

ArchUnit Maven Nedir?
ArchUnit, Java mimarinizi birim testleri ile kontrol etmenizi sağlayan bir kütüphanedir. Maven ile entegre edilerek (maven-surefire-plugin üzerinden), derleme (build) aşamasında mimari kurallarınızın ihlal edilip edilmediğini denetler.

Örnek ArchUnit Kuralı: "Controller sınıfları sadece Service sınıflarını çağırebilir, doğrudan Repository sınıflarına erişemez."
Bu test başarısız olursa, Maven derlemeyi durdurur. Geliştirici ekibinin zamanla mimariyi bozmasını otomatize edilmiş bir şekilde engeller.


#### 8.4 Spring Boot'un "Kutsal Üçlüsü": Temel Anotasyonlar
Spring Boot projelerinin ana sınıfında gördüğün @SpringBootApplication anotasyonu aslında şu üç anotasyonun birleşimidir:

@Configuration: Bu sınıfın, Spring Container için bean (nesne) tanımlamaları içerdiğini belirtir. Proje ayağa kalkarken Spring bu sınıfa bakar ve gerekli nesneleri oluşturur.

@ComponentScan: Spring'e, "Bu sınıfın bulunduğu paketi ve tüm alt paketlerini tara; üzerinde @Component, @Service, @Repository gibi anotasyonlar olan sınıfları bul ve IoC Container'a dahil et" der.

@EnableAutoConfiguration: Spring Boot'un sihridir. pom.xml dosyandaki kütüphanelere bakarak "Senin projende Spring Data JPA ve MySQL driver'ı var, öyleyse ben veritabanı bağlantı havuzunu (HikariCP) ve EntityManager'ı otomatik ayarlıyorum" diyerek manuel konfigürasyon yükünü bitirir.


#### 8.5 @RestController, İstek Anatomisi ve Spring İçindeki Yeri
@RestController Spring'in Neyini Kullanır?
@RestController aslında @Controller ve @ResponseBody anotasyonlarının birleşimidir. Arka planda Spring MVC'nin kalbi olan DispatcherServlet'i kullanır.
Gelen her HTTP isteği önce DispatcherServlet'e düşer. O, hangi URL'in hangi Controller metoduna gideceğini bulur (HandlerMapping). Ardından metodun döndürdüğü Java nesnesini (örneğin bir UserDto), HttpMessageConverter (genellikle Jackson kütüphanesi) kullanarak doğrudan JSON formatına çevirir ve HTTP response gövdesine yazar.

İstek Verisi Alma Yöntemleri (Binding)
1. @RequestBody
Gelen HTTP isteğinin gövdesindeki (body) JSON veya XML verisini, bir Java nesnesine dönüştürür. Genellikle POST veya PUT işlemlerinde veri yaratırken/güncellerken kullanılır.

```Java
@PostMapping("/api/users")
public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
    // userDto içindeki veriler JSON'dan otomatik olarak eşlenmiştir.
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
}
```

2. @PathVariable
İsteğin yapıldığı URL yolunun (path) bir parçası olan değişkenleri almak için kullanılır. RESTful standartlarında belirli bir kaynağı (resource) hedef alırken (id bazlı aramalarda) çok kritiktir.

```Java
// İstek: GET /api/users/1453
@GetMapping("/api/users/{id}")
public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
    return ResponseEntity.ok(userService.findById(userId));
}
```

3. @RequestHeader
HTTP isteğinin başlık (header) kısmındaki meta verileri okumak için kullanılır. Authorization token'ları, dil seçenekleri (Accept-Language) veya özel takip (trace) id'leri için kullanılır.

```Java
@GetMapping("/api/secure-data")
public ResponseEntity<String> getSecureData(@RequestHeader("Authorization") String token) {
    // Token doğrulama işlemleri
    return ResponseEntity.ok("Secure content");
}
```
4. @ResponseBody
Spring Framework'te (ve Spring Boot'ta) bir metot tarafından döndürülen Java nesnesinin, doğrudan HTTP yanıtının gövdesine (response body) yazılmasını sağlar.Genellikle RESTful API'lerde kullanılan bu anotasyonun temel işlevleri şunlardır:
JSON/XML Dönüşümü: Metotların doğrudan Java nesneleri (POJO, List, Map vb.) döndürmesine olanak tanır ve bu nesneleri otomatik olarak JSON veya 
XML formatına serileştirir (serializes).

HttpMessageConverter Kullanımı: Spring, dönüş değerini uygun formata çevirmek için HttpMessageConverter mekanizmasını (genellikle Jackson kütüphanesi) kullanır.

Görünüm Çözücüyü Atlar: @ResponseBody kullanıldığında, Spring sonucu bir HTML görünümü (view) olarak işlemeye çalışmaz, doğrudan veriyi istemciye (tarayıcı veya mobil uygulama) gönderir.


#### 8.6 HTTP Durum Kodları (REST Standartları)
Doğru kurumsal API'ler, istemciye ne olduğunu doğru HTTP kodlarıyla anlatmalıdır:

200 OK: İstek başarılı (GET, PUT).

201 Created: Yeni bir kaynak başarıyla oluşturuldu (POST).

204 No Content: İşlem başarılı ama dönülecek bir veri yok (Genelde DELETE).

400 Bad Request: İstemcinin gönderdiği veri hatalı veya eksik (@Valid ile doğrulama başarısız olursa fırlatılır).

401 Unauthorized: Kimlik doğrulaması (Authentication) eksik veya hatalı.

403 Forbidden: Kimlik biliniyor ama bu kaynağa erişim yetkisi (Authorization) yok.

404 Not Found: İstenilen kaynak veya URL bulunamadı.

500 Internal Server Error: Sunucu tarafında (backend'de) beklenmedik bir hata/exception oluştu.


#### 8.7 Spring'de Kullanılan Tasarım Desenleri (Design Patterns)
Spring Framework, "Tekerleği yeniden icat etme" felsefesiyle, kanıtlanmış tasarım desenlerinin üzerine inşa edilmiştir:

Singleton Pattern: Spring Container'daki bean'ler varsayılan olarak Singleton'dır. Uygulama boyunca bir sınıftan sadece tek bir nesne (instance) üretilir ve her yere o verilir (bellek optimizasyonu).

Factory Pattern: BeanFactory ve ApplicationContext, nesnelerin üretimini ve yaşam döngüsünü yöneten fabrika sınıflarıdır. Nesne oluşturma sorumluluğunu (new anahtar kelimesi) senin elinden alır.

Proxy Pattern (AOP - Aspect Oriented Programming): @Transactional veya @Cacheable gibi anotasyonları kullandığında, Spring arka planda gerçek nesnenin etrafına bir "Proxy" (vekil) nesne sarar. Metod çalışmadan önce transaction başlatır (AOP ile), metot bitince commit eder.

Template Method Pattern: Tekrar eden boilerplate kodları (bağlantı aç, sorgu at, hata yakala, bağlantı kapat) gizlemek için kullanılır. Örn: JdbcTemplate, RestTemplate, MongoTemplate.

Front Controller Pattern: Bahsettiğimiz DispatcherServlet, uygulamaya gelen tüm istekleri karşılayan tek ve merkezi bir kontrolcüdür.

Strategy Pattern: Spring Security'deki şifreleme yöntemleri (BCrypt, Argon2) veya MVC'deki HandlerMapping (isteğin kime gideceğini belirleme stratejisi) bu deseni kullanır. Interfaceler üzerinden bağımlılıkları çalışma anında değiştirebilmeyi sağlar.


#### 8.8 HTTP Metotlarının İhlali: 
GET ile POST, POST ile PUT Yapılır mı?

Yapılır ama asla yapılmamalıdır. REST mimarisinin tüm doğasını, güvenlik standartlarını ve performans optimizasyonlarını çöpe atmış olursun.

Farklar ve Neden Yapılmamalı?

GET ile POST (Veri Kaydetmek) Neden Kötüdür?

GET istekleri veri okumak içindir. Tarayıcılar, CDN'ler ve Proxy sunucuları GET isteklerini cache'ler (önbelleğe alır). Sen GET ile veritabanına kullanıcı kaydedersen, tarayıcı o URL'i cache'ler ve aynı isteği tekrar attığında sunucuya gitmeden "Başarılı" dönebilir ama arka planda veri kaydedilmez.

GET isteklerinde gövde (body) yoktur, veriler URL'e yazılır (/kayit?isim=ali&sifre=123). URL'ler loglanır! Şifreler ve hassas veriler sunucu loglarında, tarayıcı geçmişinde kabak gibi görünür.

URL uzunluk sınırları vardır (yaklaşık 2000 karakter). Büyük bir form verisini GET ile gönderemezsin.

POST ile PUT (Veri Güncellemek) Neden Kötüdür?

Buradaki anahtar kelime Idempotent (Etkisiz/Tekrarlanabilir) olmaktır.

PUT idempotent'tir. Bir kaydı "Adını Ahmet yap" diye 10 kere PUT edersen, sonuç hep aynıdır (Adı Ahmet olur).

POST idempotent DEĞİLDİR. Bir kaydı POST ile 10 kere gönderirsen, veritabanında 10 tane yeni kayıt oluşur. Sen POST metoduyla bir güncelleme işlemi tasarlarsan, ağda bir kopukluk olup istemci (client) isteği tekrar gönderdiğinde (retry mekanizması), sistemin ne tepki vereceği belirsizleşir. HTTP standartlarını ihlal ettiğin için diğer geliştiriciler ve entegrasyon yapan sistemler API'ni yanlış kullanır.


#### 8.9 Spring'de CommandLineRunner Nedir?
Uygulama tamamen ayağa kalktıktan, tüm bean'ler (nesneler) Spring Container'a yüklendikten hemen sonra, ama dışarıdan istek (HTTP request) almaya başlamadan hemen önce sadece bir kez çalışan bir arayüzdür (interface).

Ne işe yarar?

Veritabanına varsayılan değerleri (örneğin Admin kullanıcısı, roller, ülkeler listesi) eklemek (Data Seeding).

Uygulama başlarken dış bir API'den (örneğin TCMB'den güncel kurlar) veri çekip Redis cache'e doldurmak.

Başlangıçta yapılması gereken dosya sistemi kontrolleri (gerekli klasörler var mı diye bakmak).


#### 8.10 Loglama, SLF4J ve Log Seviyeleri
SLF4J (Simple Logging Facade for Java) Nedir?
SLF4J, kendi başına bir loglama kütüphanesi değildir; bir arayüzdür (Facade Pattern). Spring projelerinde kodunu SLF4J standartlarında yazarsın (örneğin Lombok'taki @Slf4j anotasyonu ile). Arka planda işi yapan gerçek motoru (Logback, Log4j2 vb.) istediğin zaman sadece pom.xml'den değiştirerek projeye entegre edebilirsin ve kodunda tek bir satır bile değiştirmen gerekmez.

Log Seviyeleri (Opsiyonlar ve Farkları)

Log seviyeleri bir filtreleme sistemidir. Prod ortamında sadece INFO ve üzerini, Dev ortamında DEBUG ve üzerini görmek istersin.

log.trace(): En ince detaydır. Döngülerin içine girip çıktığını, bayt seviyesindeki işlemleri yazarsın. Üretim ortamında (production) ASLA açılmaz, diski saniyeler içinde doldurur.

log.debug(): Geliştiriciler içindir. Hangi if-else bloğuna girildi, metota hangi parametreler geldi. (Örn: log.debug("Kullanıcı id: {} için hesaplama başladı", id)).

log.info(): Normal iş akışıdır. Uygulamanın sağlıklı çalıştığını gösterir. (Örn: "Uygulama 8080 portunda ayağa kalktı", "Ahmet adlı kullanıcı sisteme giriş yaptı").

log.warn(): Bir şeyler ters gitti ama uygulama çökmedi, kendi kendini toparladı veya varsayılan bir değere geçti. (Örn: "Redis'e bağlanılamadı, veritabanından okunuyor").

log.error(): Gerçek bir hata durumu. Bir Exception fırlatıldı, bir işlem yarıda kaldı, kullanıcıya 500 dönüldü. Mutlaka birinin incelemesi gerekir.

log.fatal() (SLF4J'de error içine Exception objesi verilerek yönetilir): Uygulamanın çalışmasını durduracak düzeyde kritik hatalardır (Veritabanı tamamen çöktü).


#### 8.11 Log İzleme Sistemleri (Graylog vb.) ve Dışarı Aktarım (Export)
Sunucuya bağlanıp terminalden .txt log okumak 1990'larda kaldı. Modern sistemlerde loglar merkezi bir yere akar.

Graylog / ELK Stack (Elasticsearch, Logstash, Kibana) Özellikleri:

Yüzlerce mikroservisten gelen tüm logları tek bir ekranda birleştirir.

Logları JSON olarak parse edip içinde SQL gibi sorgu atmanı sağlar (Örn: level:ERROR AND userId:1453).

Alerting (Uyarı): "Son 5 dakikada 100'den fazla ERROR logu gelirse bana Slack'ten mesaj at ve mail gönder" diyebilirsin.

Logları Uygulamadan Dışarı Çıkarma Yöntemleri:

File Appender (TXT Dump): Loglar sunucuda bir .log dosyasına yazılır.

Rolling File Appender (Zipleyerek Yedekleme): Konfigürasyon yaparsın; "Log dosyası 50 MB olunca veya gün bitince gece saat 00:00'da eski logu al, zip'le (arşivle), yeni ve boş bir .log dosyası aç" dersin.

Ağ Üzerinden (Network Socket): Log dosyasını diske hiç yazmadan, doğrudan ağ üzerinden Graylog'a gönderirsin.


Ağ Üzerinden Gönderimde TCP vs UDP Farkı:

TCP ile Göndermek: Güvenilirdir. Logun Graylog'a ulaştığından emin olur (Three-way handshake). Dezavantajı: Yavaştır. Graylog sunucusu anlık yavaşlarsa, senin ana uygulaman da logu göndermeyi beklediği için yavaşlar (Backpressure).

UDP ile Göndermek: "Ateşle ve Unut" (Fire and forget). Çok hızlıdır, uygulamanı asla yavaşlatmaz. Dezavantajı: Ağda bir yoğunluk varsa bazı log paketleri kaybolabilir ve senin ruhun duymaz.


#### 8.12 Senaryo: Çok Yoğun Bir Sistemde Loglama (Asenkron Yaklaşım)
Case: Saniyede 10.000 istek (TPS) alan bir uygulaman var. Hem hızlıca cevap dönmelisin hem de logları Graylog'a göndermelisin. Standart loglama yaparsan, diske/ağa yazma işlemi (I/O) yüzünden HTTP thread'leri bloke olur ve sistem kilitlenir.

Çözüm: Asynchronous Logging (Asenkron Loglama) + Message Queue

Burada Log4j2Async Appender (LMAX  Disruptor kütüphanesi tabanlı) kullanmalısın.

Uygulama log yazdığında, bunu diske veya ağa değil, RAM üzerindeki çok hızlı bir kuyruğa (RingBuffer) atar. Bu işlem mikrosaniyeler sürer, böylece HTTP isteği anında cevap döner (Thread serbest kalır).

Arka planda çalışan ayrı bir Worker Thread, RAM'deki bu logları toplar (batch) ve Kafka veya RabbitMQ gibi bir mesaj kuyruğuna fırlatır (Tercihen UDP ile veya Kafka'nın kendi asenkron producer'ı ile).

Kafka'daki logları da Logstash/Graylog kendi hızında tüketir. Ana uygulaman asla log yazma hantallığından etkilenmez.


#### 8.13 @Valid Olmadan Validasyon Nasıl ve Nerede Yapılır?
@Valid ve @NotNull, @Size gibi anotasyonlar Controller katmanında gelen DTO'ları kontrol eder. Eğer bunları kullanmazsan, Spring’in
org.springframework.validation.Validator
arayüzünü kullanarak doğrulama mantığı yazılabilir:

```
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "error.username.empty", "Username cannot be empty");
        }
    }
}
```

2. Service Katmanında Kullanımı
Validator, service katmanında doğrudan çağrılır:

```
@Service
public class UserService {
    private final Validator validator;

    public UserService(Validator validator) {
        this.validator = validator;
    }

    public void createUser(User user) {
        ValidationUtils.invokeValidator(validator, user, new BeanPropertyBindingResult(user, "user"));
        // Diğer iş mantığı
    }
```

#### 8.14 Global Exception Handling (@RestControllerAdvice)
Diyelim ki manuel validasyon yaptın ve CustomValidationException fırlattın. Yahut veritabanında olmayan bir id arandığında UserNotFoundException fırlattın. Controller'da her metota try-catch yazmak spagetti koda sebep olur.

Çözüm: Uygulamanın her yerinden fırlatılan hataları havada yakalayan global bir sınıf yazmaktır.

```Java
@RestControllerAdvice // Tüm controller'ları dinleyen global bir kalkan
public class GlobalExceptionHandler {

    // Kendi fırlattığımız özel hatayı yakalar
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(CustomValidationException ex) {
        // Hata mesajını düzgün bir JSON objesine (ErrorResponse) çevirir
        ErrorResponse error = new ErrorResponse("400", "Validasyon Hatası: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Beklenmedik tüm runtime hatalarını (500) yakalar ki dışarıya Java stacktrace'i sızmasın
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        // Güvenlik: Gerçek hatayı dışarı verme, ama logla!
        log.error("Beklenmedik bir sistem hatası oluştu: ", ex); 
        ErrorResponse error = new ErrorResponse("500", "Sunucuda beklenmedik bir hata oluştu.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

#### 8.15 CronJob Nedir ve Log/Sistem Mimarisiyle İlişkisi
CronJob, belirli zaman aralıklarında veya planlanmış spesifik saatlerde otomatik olarak tetiklenen arka plan görevleridir (Scheduled Tasks). Spring'de @EnableScheduling ve @Scheduled(cron = "0 0 2 * * ?") (Her gece saat 02:00'de çalış) anotasyonlarıyla kullanılır.

Anlatılan Senaryoyla İlişkisi (Logları Dışarı Çıkarma):
Logları dışarı aktarma senaryosunda anlattığım "zipleyip göndermek" kısmını hatırlayalım. İşte bunu yapan bir CronJob'dur!

Senaryo Entegrasyonu:

Uygulaman gün boyunca logları sunucudaki app.log dosyasına yazar.

Gece kullanıcı trafiği düştüğünde (örneğin saat 03:00'te) bir CronJob devreye girer.

CronJob metodu, dünün tarihini taşıyan logları bulur, bir .zip veya .tar.gz formatında sıkıştırır (sunucuda yer açar).

Ardından bu zip dosyasını alır ve FTP üzerinden başka bir arşiv sunucusuna veya AWS S3 gibi bulut depolama alanına (Cold Storage) yükler.

Yükleme başarılı olursa sunucudaki eski zip dosyasını siler.

Bu sayede hem sunucunun diski hiçbir zaman dolmaz hem de olası bir yasal inceleme için loglar yıllarca güvenli bir şekilde dış bir kanalda yedeklenmiş olur.


## 9. Generics

#### 9.1 Generics Nedir ve Neden Hayat Kurtarır?
Generics, sınıfları, arayüzleri (interface) ve metotları tanımlarken tipi (type) bir parametre olarak alabilmemizi sağlar.

En büyük iki amacı vardır:

Tip Güvenliği (Type Safety): Hataları çalışma zamanında (Runtime - ClassCastException) değil, kodu yazarken derleme zamanında (Compile Time) yakalamanı sağlar.

Kod Tekrarını Önleme (DRY - Don't Repeat Yourself): Farklı veri tipleri için aynı mantığı yapan sınıfları tekrar tekrar yazmanı engeller.

Generics Olmadan (Eski Yöntem):
Eğer her şeyi Object tipiyle tutarsan, veriyi geri okurken sürekli (String), (User) gibi cast (dönüşüm) işlemi yapman gerekir. Biri gidip Object listesine yanlışlıkla Integer atarsa, kodu çalıştırana kadar hata almazsın.

Generics İle:
Listeyi List<String> olarak tanımladığında, derleyici o listeye Integer eklemene asla izin vermez. Kodu derleyemezsin, böylece hata daha üretim (production) ortamına gitmeden senin bilgisayarında çözülmüş olur.


#### 9.2 Spring Mimarilerinde Generic Sınıf Kullanımı (Enterprise Case)
Bir önceki konuda API'lerden ve istisna yönetiminden (Exception Handling) bahsetmiştik. Profesyonel bir REST API, başarılı da olsa başarısız da olsa istemciye (client) standart bir kapsayıcı (wrapper) objesi dönmelidir. İşte burada Generic sınıflar devreye girer.

```Java
// T: Type (Herhangi bir nesne olabilir: UserDto, ProductDto, List<OrderDto> vb.)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data; // Asıl veri burada duruyor

    // Başarılı cevaplar için constructor
    public ApiResponse(T data, String message) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    // Hata durumları için constructor (Veri yok)
    public ApiResponse(String message) {
        this.success = false;
        this.message = message;
        this.data = null;
    }
    
    // Getter ve Setter'lar...
}
```

Controller'da Kullanımı:

```Java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
    UserDto user = userService.findById(id);
    // T tipi burada UserDto oldu
    ApiResponse<UserDto> response = new ApiResponse<>(user, "Kullanıcı bulundu.");
    return ResponseEntity.ok(response);
}
```
Bu sayede her entity için ayrı bir UserResponse, ProductResponse sınıfı yazmaktan kurtuluruz.

#### 9.3 Generic Metotlar
Bazen tüm sınıfı generic yapmak istemezsin, sadece belirli bir metot farklı tiplerde çalışsın istersin.

```Java
public class JsonMapper {
    
    // <T> metodun generic olduğunu belirtir, T dönüş tipidir.
    public <T> T parseJsonToObject(String json, Class<T> clazz) {
        // Gelen JSON stringini, parametre olarak verilen T tipindeki sınıfa çevirir.
        // Örn: parseJsonToObject(jsonStr, UserDto.class)
        // Burada Object Mapper işlemleri yapılır...
        return null; 
    }
}
```

#### 9.4 Wildcards (Joker Karakterler) ve Sınırlandırmalar (Bounds)
Burası Generics'in en "tricky" (zorlayıcı) ama mimari açıdan en güçlü kısmıdır. Polymorphism (Çok biçimlilik) Generics ile normaldeki gibi çalışmaz. Örneğin Dog, Animal'ın alt sınıfı olabilir ama List<Dog>, List<Animal>'ın alt sınıfı değildir. Bunu çözmek için Wildcard (?) kullanılır.

Unbounded Wildcard (<?>): "Tipi umrumda değil, herhangi bir tip olabilir." Genelde sadece okuma yapılan, tipin önemsiz olduğu loglama gibi metotlarda kullanılır.

Upper Bounded Wildcard (<? extends T>): "T tipi veya T'den türeyen (miras alan) alt sınıflar gelebilir."

Kural: Sadece veriyi okuyacaksan (Producer) kullanırsın. İçine yeni eleman ekleyemezsin (Çünkü alt sınıfın tam olarak ne olduğunu bilemez).

Lower Bounded Wildcard (<? super T>): "T tipi veya T'nin üst sınıfları (parent) gelebilir."

Kural: Sadece koleksiyona veri yazacaksan (Consumer) kullanırsın.

Bu kurala yazılım dünyasında PECS (Producer Extends, Consumer Super) denir.

Örnek (Kalıtım: Shape -> Circle & Square):

```Java
// Sadece Shape veya Shape'i extends edenler (Circle, Square) gelebilir.
// Bu metot listeyi sadece okur (Producer Extends)
public void drawAllShapes(List<? extends Shape> shapes) {
    for (Shape shape : shapes) {
        shape.draw();
    }
    // shapes.add(new Circle()); -> BUNA İZİN VERMEZ! Compile error.
}
```

#### 9.5 Type Erasure (Tip Silinmesi) - Arka Plandaki Sır
Java'da Generics tamamen derleme zamanı (compile-time) bir illüzyondur.
Yazdığın <T> parametreleri veya List<String> ifadeleri kodu derlediğinde (bytecode'a, .class dosyasına dönüştüğünde) silinir.

Derleyici List<String>'in içine String konduğundan emin olduktan sonra, bytecode seviyesinde bunu List ve Object'e çevirir (Geriye uyumluluk - Backward Compatibility için). Bu yüzden çalışma zamanında (Runtime) bir listenin tipinin String mi Integer mı olduğunu instanceof ile doğrudan kontrol edemezsin.

