# SPRING & SPRING BOOT DETAYLI DOKÜMANTASYON
## 1. Spring Core: IoC ve Dependency Injection
#### 1.1 Inversion of Control (IoC)

IoC, bir uygulamada nesnelerin oluşturulması ve yönetilmesi sorumluluğunun geliştiriciden framework’e devredilmesidir.

Klasik yaklaşım:

```

UserService service = new UserService();

Spring yaklaşımı:

@Autowired
UserService service;
```


Burada:

Nesneyi sen yaratmıyorsun
Spring Container yaratıyor

#### 1.2 Dependency Injection (DI)

DI, bir nesnenin bağımlılıklarının dışarıdan verilmesidir.

Türleri:

#### Constructor Injection (Best Practice)

```
@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
}
```

#### Field Injection (Önerilmez)

```
@Autowired
private UserRepository repo;
```

#### Setter Injection
```
@Autowired
public void setRepo(UserRepository repo) {
    this.repo = repo;
}
```
## 2. Bean Lifecycle ve Scopes
#### 2.1 Bean Lifecycle

Spring bean lifecycle:

Instantiate edilir
Dependency inject edilir
@PostConstruct çalışır
Kullanılır
@PreDestroy çalışır
@PostConstruct
public void init() {}

@PreDestroy
public void destroy() {}

#### 2.2 Bean Scopes
Scope	Açıklama
singleton	Default, tek instance
prototype	Her çağrıda yeni object
request	HTTP request başına
session	HTTP session başına
@Scope("prototype")

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
