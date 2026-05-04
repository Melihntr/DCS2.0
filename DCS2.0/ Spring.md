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

Harika. Önceki bölümlerde API'mizin temellerini attık, katmanlı mimariyi kurduk ve kapıya Validation API ile sıkı bir güvenlik duvarı ördük.

Ancak bir sorunumuz var: İstemci hatalı bir veri gönderdiğinde (örneğin e-posta formatı yanlış olduğunda) veya aradığı bir veriyi bulamadığında, Spring Boot varsayılan olarak istemciye karmaşık, anlaşılması zor ve bazen sunucu detaylarını (stack trace) sızdıran standart bir hata sayfası veya JSON döner.

İşte bu noktada devreye, profesyonel bir API'nin olmazsa olmazı E6: Merkezi Hata Yönetimi (Global Exception Handling) girer. Aşağıda bu konunun akademik ve sektörel standartlardaki detaylı dokümantasyonunu bulabilirsiniz.

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
Ekstra Akademik Detay: Eğer projede Spring Boot 3 (Spring Framework 6) kullanıyorsanız, yukarıdaki özel ApiError sınıfını yazmak yerine, global bir endüstri standardı olan RFC 7807 (Problem Details for HTTP APIs) yapısını kullanabilirsiniz.

Spring Boot 3 bunu yerleşik olarak destekler. application.properties dosyasına şu satırı eklediğinizde:

```Properties
spring.mvc.problem-details.enabled=true
```
Spring, standart hataları otomatik olarak RFC 7807'nin ProblemDetail yapısına (type, title, status, detail, instance alanları içeren standart bir formata) dönüştürecektir. Profesyonel ve yeni nesil projelerde bu standarda geçiş giderek artmaktadır.

## 7. — Spring Core AOP (Aspect Oriented Programming) — Merkezi Yönetim
@ControllerAdvice is a specialization of the @Component annotation which allows to handle exceptions across the whole application in one global handling component. It can be viewed as an interceptor of exceptions thrown by methods annotated with @RequestMapping and similar.


(AOP ile ilgili alıntı sonuçlardan dolaylı gösterildi; aşağıda AOP için doğrudan pratik açıklama ve örnekler yer almaktadır.)

#### 7.1 AOP nedir ve ne için kullanılır?
AOP, loglama, metrik, transaction management, security, caching gibi cross-cutting concern’ları merkezi olarak uygulamanızı sağlar.
Aspect: kesişen sorumlulukları tanımlar. Advice: ne yapılacağı (before/after/around). Pointcut: hangi join point’lerde çalışılacağı (örn. belirli paketlerdeki servis metodları).
#### 7.2 Kurulum
Maven/Gradle: spring-boot-starter-aop ekleyin.
Aspect örneği:
```java

// src/main/java/com/example/aop/LoggingAspect.java
package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.example..service.*.*(..))")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String sig = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();
        try {
            System.out.println("[START] " + sig);
            Object result = pjp.proceed();
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("[END] " + sig + " executed in " + elapsed + "ms");
            return result;
        } catch (Throwable t) {
            System.out.println("[ERROR] " + sig + " -> " + t.getMessage());
            throw t;
        }
    }
}
```
#### 7.3 Yaygın kullanım örnekleri
Execution time metrics: method çalışma süresini ölçüp metrik sistemine gönderme.
Centralized logging: giriş/çıkış parametrelerini loglama (dikkat: hassas verileri maskelen).
Retry / Circuit Breaker / Rate Limiting: belirli davranışları sarmak.
Security checks: metod çağrılarında yetkilendirme doğrulamaları (ancak çoğu durumda Spring Security tercih edilir).
#### 7.4 AOP dikkat edilmesi gerekenler
Self-invocation: aynı bean içerisinden bir metodun başka bir metodunu çağırması proxy’yi atlatır; advice uygulanmaz. Bu yüzden kritik kodu ayrı bean’e taşıyın veya proxy tabanlı yaklaşımları bilin.
Proxy türü: arayüz tabanlı (JDK) veya sınıf tabanlı (CGLIB). Final sınıflar/final metodlar proxy ile sarılamaz.
Performans: Advice tüm çağrılara müdahale edebileceği için kapsamı dar tutun.
Hassas veri: loglama yaparken gizli alanları maskalayın veya loglamayı sınırlayın.
#### 7.5 Örnek: AOP + Exception logging + Metrics
Aşağıdaki aspect örneği, servis metodlarında oluşan istisnaları global loglayıp yeniden fırlatır:
```java

@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.example..service.*.*(..))", throwing = "ex")
    public void logServiceException(JoinPoint jp, Throwable ex) {
        String method = jp.getSignature().toShortString();
        // örn: central logger veya meter kullan
        System.err.println("[SERVICE ERROR] " + method + " -> " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}
```
