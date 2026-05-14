# Spring Data Dokümantasyonu

## 1. JPA ve Persistence Katmanı

### 1.1 JPA ve Persistence Nedir?

#### Persistence (Kalıcılık) Nedir?
Bilgisayar bilimlerinde Persistence, bir verinin oluşturulduğu süreç (process) sonlansa bile varlığını sürdürmeye devam etmesi özelliğidir.

Geçici Veri: Uygulama çalıştığı sürece RAM üzerinde tutulan nesnelerdir. Uygulama kapandığında bu veriler kaybolur.

Kalıcı Veri: Veritabanları (SQL/NoSQL) veya dosya sistemleri gibi ortamlara kaydedilen verilerdir. Uygulama yeniden başlatıldığında bu veriler geri yüklenebilir.

#### JPA (Java Persistence API) Nedir?
JPA, Java nesnelerinin (POJO) ilişkisel veritabanlarına (RDBMS) nasıl kaydedileceğini, nasıl güncelleneceğini ve nasıl sorgulanacağını tanımlayan bir spesifikasyondur (kural setidir).

JPA kendisi bir kütüphane veya araç değil, bir standarttır. Bu standart, Java dünyasındaki ORM (Object-Relational Mapping) yaklaşımını kolaylaştırmak için oluşturulmuştur.

Temel Bileşenleri:
Entity: Veritabanındaki bir tabloya karşılık gelen Java sınıfıdır.

EntityManager: Veritabanı işlemlerini (kaydetme, silme, bulma) yöneten ana arayüzdür.

JPQL (Java Persistence Query Language): Tablolar yerine doğrudan Java nesneleri üzerinden sorgu yazılmasını sağlayan dildir.

Spring Boot projelerinde çoğunlukla bir JPA implementasyonu olan Hibernate kullanılır.

### 1.2 ORM (Object Relational Mapping)

ORM yaklaşımında:

Nesne Dünyası	Veritabanı Dünyası
Class	Table
Object	Row
Field	Column
Reference	Foreign Key

Örnek:
```
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
}

Bu yapı aşağıdaki tabloya dönüştürülebilir:

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255)
);
```

### 1.3 Persistence Context

Persistence Context, entity nesnelerinin yönetildiği belleksel alandır.

Bir entity:

managed
detached
removed
transient

durumlarından birinde bulunabilir.

##2. Entity Lifecycle

## 2.1 Transient

Henüz veritabanı ile ilişkisi olmayan nesne.

User user = new User();

### 2.2 Managed

Persistence Context tarafından yönetilen nesne.
```
entityManager.persist(user);
```

### 2.3 Detached

Context dışına çıkmış nesne.

entityManager.detach(user);

### 2.4 Removed

Silinmek üzere işaretlenen nesne.

```
entityManager.remove(user);
```

## 3. Spring Data Repository Yapısı
### 3.1 Repository Kavramı

Repository pattern, veri erişim katmanını soyutlar.

Spring Data sayesinde boilerplate kod büyük ölçüde azalır.

### 3.2 CrudRepository
```
public interface UserRepository 
       extends CrudRepository<User, Long> {
}
```
Sağlanan metodlar:

```
save()
findById()
delete()
existsById()
```

### 3.3 JpaRepository
```
public interface UserRepository 
       extends JpaRepository<User, Long> {
}
```
Ek avantajlar:

pagination
batch işlemleri
flush desteği

### 3.4 Persistence Abstraction

Spring Data, alttaki ORM implementasyonunu soyutlar.

Uygulama:

Hibernate
EclipseLink
başka bir provider

kullansa bile repository kodu değişmeden kalabilir.

Bu yaklaşım:

loose coupling
maintainability
testability

sağlar.

## 4.  Query Mekanizmaları

### 4.1 Derived Query Methods

Method isminden query üretilebilir.

List<User> findByUsername(String username);

Spring bunu otomatik SQL'e dönüştürür.


### 4.2 Custom Query

Özel sorgular için kullanılır.
```
@Query("SELECT u FROM User u WHERE u.username = :username")
User findCustom(@Param("username") String username);
```
Native SQL:
```
@Query(
  value = "SELECT * FROM users WHERE username = ?1",
  nativeQuery = true
)
```
```
User nativeFind(String username);
```
### 4.3 Criteria Query

Dinamik sorgu oluşturmak için kullanılır.

Özellikle:

filtreleme
arama ekranları
dinamik WHERE koşulları

için uygundur.
```
CriteriaBuilder cb = entityManager.getCriteriaBuilder();

CriteriaQuery<User> cq = cb.createQuery(User.class);

Root<User> root = cq.from(User.class);

cq.select(root)
  .where(cb.equal(root.get("username"), "melih"));

List<User> result =
    entityManager.createQuery(cq).getResultList();
```
Avantajları:

type-safe yaklaşım
dinamik query üretimi
compile-time kontrol


## 5. Entity İlişkileri

### 5.1 One-to-One
```
@OneToOne
private Address address;
```
### 5.2 One-to-Many
```
@OneToMany(mappedBy = "user")
private List<Order> orders;
```

### 5.3 Many-to-One
```
@ManyToOne
private User user;
```
### 5.4 Many-to-Many
```
@ManyToMany
private List<Role> roles;
```
## 6. Unidirectional ve Bidirectional İlişkiler

### 6.1 Unidirectional

İlişki tek taraftan bilinir.
```
@OneToMany
private List<Order> orders;
```
Avantaj:

basit yapı

Dezavantaj:

bazı join işlemlerinde yetersizlik

### 6.2 Bidirectional

Her iki taraf birbirini bilir.
```
@OneToMany(mappedBy = "user")
private List<Order> orders;
```
```
@ManyToOne
private User user;
```
Avantaj:

daha güçlü navigasyon
daha doğal modelleme

Dezavantaj:

sonsuz JSON recursion riski

Çözüm:
```
@JsonManagedReference
@JsonBackReference
```
veya:
```
@JsonIgnore
```

## 7. Lazy ve Eager Loading

### 7.1 Lazy Loading

Veri ihtiyaç halinde yüklenir.
```
@OneToMany(fetch = FetchType.LAZY)
```
Avantaj:

performans

Dezavantaj:

LazyInitializationException riski

### 7.2 Eager Loading

Veri anında yüklenir.
```
@OneToMany(fetch = FetchType.EAGER)
```
Avantaj:

erişim kolaylığı

Dezavantaj:

gereksiz veri çekimi
memory maliyeti


### 7.3 N+1 Problemi

En yaygın ORM problemlerinden biridir.

Örnek:

1 query ile userlar gelir
her user için ayrı order querysi çalışır

Toplam:

1 + N query

Çözüm:

fetch join
entity graph
batch fetching

Örnek:
```
@Query("""
SELECT u FROM User u
JOIN FETCH u.orders
""")
List<User> findAllWithOrders();
```

## 8. Veri Tutarlılığı ve Optimistic Locking

### 8.1 Veri Tutarlılığı

ACID prensipleri:

Harf	Açılım
A	Atomicity
C	Consistency
I	Isolation
D	Durability


### 8.2 Optimistic Locking

Çakışmaları versiyon kontrolüyle yönetir.
```
@Version
private Long version;
```
Senaryo:

iki kullanıcı aynı veriyi günceller
ilk işlem başarılı olur
ikinci işlem exception alır

Exception:
```
OptimisticLockException
```
Avantaj:

yüksek performans
düşük lock maliyeti

Dezavantaj:

retry mekanizması gerekebilir


### 8.3 Pessimistic Locking

Gerçek lock kullanır.
```
@Lock(LockModeType.PESSIMISTIC_WRITE)
```
Avantaj:

güçlü tutarlılık

Dezavantaj:

deadlock riski
performans kaybı


## 9. Relational Database Özellikleri

İlişkisel veritabanlarının temel özellikleri genellikle Codd kuralları ile açıklanır.

Yaygın olarak bahsedilen “8 maddeden en az 4’ü sağlanmalı” ifadesi akademik olarak tam doğru değildir.
Aslında ilişkisel model:

Edgar F. Codd tarafından tanımlanmıştır
12 kural ile açıklanır

Temel ilişkisel özellikler:

Tablo tabanlı yapı
Primary key kullanımı
Foreign key ilişkileri
Veri bütünlüğü
SQL desteği
Normalize edilebilir yapı
Atomic veri yapısı
Relation mantığı


## 10. Normalization

### 10.1 1NF

Atomic veri.

Yanlış:

phones = "123,456"

Doğru:

ayrı tablo


### 10.2 2NF

Partial dependency kaldırılır.

### 10.3 3NF

Transitive dependency kaldırılır.

Normalization amacı:

veri tekrarını azaltmak
tutarlılığı artırmak


## 11. Join Mekanizmaları

### 11.1 INNER JOIN

Eşleşen kayıtlar.
```sql
SELECT *
FROM users u
INNER JOIN orders o
ON u.id = o.user_id
```

### 11.2 LEFT JOIN

Sol tablo tamamen gelir.


### 11.3 RIGHT JOIN

Sağ tablo tamamen gelir.


### 11.4 FULL OUTER JOIN

Her iki tablo tamamen gelir.


### 11.5 FETCH JOIN

JPA özel yaklaşımıdır.
```
SELECT u FROM User u
JOIN FETCH u.orders
```
N+1 problemini azaltır.


## 12. Inheritance Mapping

### 12.1 Single Table Strategy

Tüm classlar tek tabloda tutulur.

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

Avantaj:

hızlı query

Dezavantaj:

çok NULL alan oluşabilir


### 12.2 Joined Strategy

Her class ayrı tabloda tutulur.

@Inheritance(strategy = InheritanceType.JOINED)

Avantaj:

normalize yapı

Dezavantaj:

fazla join


### 12.3 Table Per Class

Her class kendi tablosuna sahiptir.
```
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
```
Genellikle önerilmez.

Neden?

UNION query maliyeti
karmaşık sorgular
düşük performans


## 13. Composition Over Inheritance

Modern yazılım tasarımında inheritance yerine composition önerilir.

Kötü örnek:

Vehicle
 └── FlyingVehicle

Daha iyi yaklaşım:

Vehicle HAS-A Engine
Vehicle HAS-A NavigationSystem

Avantaj:

düşük bağlılık
daha esnek yapı
yeniden kullanılabilirlik


## 14. H2 Veritabanı

H2 Database Engine hafif bir ilişkisel veritabanıdır.

Kullanım alanları:

test
development
prototyping

Özellikleri:

in-memory çalışabilir
hızlı başlangıç
embedded yapı

Bağımlılık:
```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

## 15. Hibernate DDL Yönetimi

### 15.1 Tablo Oluşturma Süreci

Hibernate entityleri analiz eder:

annotation okuma
metadata üretimi
ilişki çözümleme
foreign key oluşturma
DDL üretimi
16.2 ddl-auto Modları
spring.jpa.hibernate.ddl-auto=create

Seçenekler:

Mod	Açıklama
create	Baştan oluşturur
update	Eksikleri günceller
create-drop	Açılışta oluşturur kapanışta siler
validate	Şemayı doğrular
none	İşlem yapmaz


### 15.3 Join Kararı Neye Göre Verilir?

ORM şu durumlarda join üretir:

entity relationship varsa
fetch strategy uygunsa
JPQL içinde join varsa
eager ilişki varsa


## 16. Transaction Yönetimi

### 16.1 @Transactional
```
@Transactional
public void transfer() {
}
```
Spring:

transaction başlatır
commit eder
hata olursa rollback yapar


### 16.2 Isolation Levels
Seviye	Açıklama
READ_UNCOMMITTED	Dirty read olabilir
READ_COMMITTED	En yaygın
REPEATABLE_READ	Aynı veri korunur
SERIALIZABLE	En güvenli


## 17. Veri Güvenliği
### 17.1 SQL Injection

Yanlış:
```
String sql =
 "SELECT * FROM users WHERE username='"
 + username + "'";
```
Doğru:
```
@Query("SELECT u FROM User u WHERE u.username=:username")
```

### 17.2 Password Güvenliği

Şifreler hashlenmelidir.

Önerilen:

BCrypt
BCryptPasswordEncoder


### 17.3 Least Privilege Principle

Database user:

yalnızca gerekli yetkilere sahip olmalıdır


### 17.4 Audit Logging

Kim:

ne zaman
hangi veriyi değiştirdi

takip edilmelidir.


## 18. Önemli İleri Seviye Konular

### 18.1 Cascade Types

@OneToMany(cascade = CascadeType.ALL)

Türler:

PERSIST
MERGE
REMOVE
REFRESH
DETACH


### 18.2 Orphan Removal
```
@OneToMany(orphanRemoval = true)
```
İlişkiden çıkan child otomatik silinir.

### 18.3 DTO Projection

Entity yerine DTO dönmek performans sağlar.
```
SELECT new com.app.UserDto(u.id,u.name)
FROM User u
```
### 18.4 Entity Graph

Performans optimizasyonu sağlar.
```
@EntityGraph(attributePaths = {"orders"})
```
