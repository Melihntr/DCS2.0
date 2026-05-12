# Spring Data, Modern Veri Yönetimi ve Kalıcılık Mimarileri Dokümantasyonu

# 1. — NoSQL Concepts & Document Database Veri Modelleme

## 1.1 Giriş

Geleneksel ilişkisel veritabanları (Relational Database Management Systems - RDBMS) uzun yıllar boyunca kurumsal yazılım geliştirme süreçlerinin temel veri saklama teknolojisi olmuştur. Ancak internet ölçeğinde çalışan sistemlerin yaygınlaşması, yüksek hacimli veri üretimi, gerçek zamanlı işlem ihtiyaçları ve yatay ölçeklenebilirlik gereksinimleri yeni veri saklama yaklaşımlarını ortaya çıkarmıştır. Bu ihtiyaçların sonucunda NoSQL veritabanları ortaya çıkmıştır.

NoSQL kavramı yalnızca “SQL kullanmayan” sistemleri ifade etmez. Modern yaklaşımda “Not Only SQL” olarak değerlendirilir. Amaç ilişkisel veritabanlarının güçlü yönlerini tamamen ortadan kaldırmak değil, belirli problemlerde daha uygun veri saklama modelleri sağlamaktır.

---

## 1.2 NoSQL Nedir?

NoSQL veritabanları:

- Şemasız veya esnek şemaya sahip olabilir.
- Yatay ölçeklenebilirliği destekler.
- Büyük veri hacimlerinde performans avantajı sağlar.
- Dağıtık mimariye uygundur.
- Bazı durumlarda eventual consistency yaklaşımını benimser.
- İlişkisel model yerine farklı veri modelleri kullanır.

NoSQL sistemleri genellikle aşağıdaki problemleri çözmek için tercih edilir:

1. Büyük veri (Big Data)
2. Gerçek zamanlı analiz
3. Yüksek trafik
4. Mikroservis mimarileri
5. Hızlı geliştirme ihtiyacı
6. Değişken veri yapıları
7. Global dağıtık sistemler

---

## 1.3 NoSQL Türleri

#### 1.3.1 Key-Value Databases

Veriler anahtar-değer şeklinde tutulur.

Örnekler:

- Redis
- Riak
- Amazon DynamoDB

Avantajları:

- Çok hızlıdır.
- Cache sistemleri için uygundur.
- Basit veri erişimi sağlar.

Dezavantajları:

- Karmaşık sorgular desteklenmez.
- İlişkisel model zayıftır.

---

#### 1.3.2 Document Databases

Veriler JSON benzeri dokümanlar halinde tutulur.

Örnekler:

- Couchbase
- MongoDB
- CouchDB

Avantajları:

- Esnek şema
- Nested object desteği
- JSON tabanlı veri modeli
- Mikroservis mimarilerine uygunluk

---

#### 1.3.3 Column Family Databases

Veriler kolon aileleri şeklinde tutulur.

Örnekler:

- Cassandra
- HBase

Avantajları:

- Çok büyük veri kümelerinde güçlü performans
- Write-heavy sistemlerde avantaj

---

#### 1.3.4 Graph Databases

Veriler düğümler ve ilişkiler üzerinden modellenir.

Örnekler:

- Neo4j
- ArangoDB

Avantajları:

- Sosyal ağlar
- Recommendation system
- Relationship-heavy domainler

---

## 1.4 CAP Theorem

NoSQL sistemlerinin temel teorilerinden biridir.

Bir dağıtık sistem aynı anda aşağıdaki üç özelliğin tamamını garanti edemez:

## 4.1 Consistency

Tüm node’ların aynı veriyi görmesi.

## 4.2 Availability

Sistemin sürekli erişilebilir olması.

## 4.3 Partition Tolerance

Ağ bölünmelerine rağmen sistemin çalışmaya devam etmesi.

Modern dağıtık sistemlerde Partition Tolerance zorunlu olduğundan genellikle:

- CP sistemleri
- AP sistemleri

şeklinde seçim yapılır.

---

# 5. ACID ve BASE Karşılaştırması

## 5.1 ACID

Relational database yaklaşımı.

### Atomicity
İşlem tamamen başarılı olur veya tamamen geri alınır.

### Consistency
Veri bütünlüğü korunur.

### Isolation
İşlemler birbirini etkilemez.

### Durability
Commit edilen veri kalıcıdır.

---

## 5.2 BASE

NoSQL sistemlerinde yaygın yaklaşım.

### Basically Available
Sistem erişilebilir kalır.

### Soft State
Sistem durumu zamanla değişebilir.

### Eventual Consistency
Veri zaman içinde tutarlı hale gelir.

---

# 6. Relational Database’in Temel Özellikleri

İlişkisel veritabanlarının temelinde Codd’un relational model yaklaşımı bulunmaktadır.

Genellikle aşağıdaki kavramlar relational sistemlerin temelini oluşturur:

1. Tablo yapısı
2. Primary key
3. Foreign key
4. Normalization
5. SQL standardı
6. Transaction desteği
7. ACID garantileri
8. Referential integrity

Bir sistemin relational database mantığına yaklaşabilmesi için en azından aşağıdaki temel özelliklerin önemli kısmını sağlaması beklenir:

- Tablolar
- Anahtar ilişkileri
- Veri bütünlüğü
- Transaction mantığı

---

# 7. Document Database Veri Modelleme

Document database sistemlerinde veri çoğunlukla JSON formatında saklanır.

Örnek kullanıcı dokümanı:

```json
{
  "id": "user-1",
  "name": "Melih",
  "age": 23,
  "addresses": [
    {
      "city": "Istanbul",
      "district": "Kadikoy"
    }
  ]
}
```

---

# 8. Embedding vs Referencing

Document database modellemesinin en kritik noktalarından biridir.

## 8.1 Embedding

İlişkili veri aynı document içinde tutulur.

Avantajlar:

- Tek query ile veri çekilir.
- Performans avantajı sağlar.
- Read-heavy sistemlerde uygundur.

Dezavantajlar:

- Veri tekrarına neden olabilir.
- Büyük document problemi oluşabilir.

Örnek:

```json
{
  "orderId": "1",
  "customer": {
    "name": "Ahmet"
  }
}
```

---

## 8.2 Referencing

İlişkili veri başka document referansı ile tutulur.

Avantajlar:

- Veri tekrarını azaltır.
- Güncelleme kolaylığı sağlar.

Dezavantajlar:

- Ek query gerektirebilir.
- Join benzeri operasyon ihtiyacı oluşur.

---

# 9. Ne Zaman NoSQL Kullanılır?

NoSQL tercih edilmesi gereken durumlar:

- Çok büyük veri hacmi
- Dinamik şema
- Mikroservis mimarisi
- Horizontal scaling ihtiyacı
- High throughput gereksinimi
- Gerçek zamanlı sistemler

Relational database tercih edilmesi gereken durumlar:

- Güçlü transaction ihtiyacı
- Karmaşık ilişkiler
- Finansal sistemler
- Kritik veri bütünlüğü

---

# 10. Normalization ve Denormalization

## 10.1 Normalization

Veri tekrarını azaltmak için verinin bölünmesidir.

Avantaj:

- Veri bütünlüğü
- Güncelleme kolaylığı

Dezavantaj:

- Fazla join ihtiyacı

---

## 10.2 Denormalization

Performans için veri tekrarına izin verilmesidir.

NoSQL sistemlerinde yaygındır.

Avantaj:

- Hızlı okuma

Dezavantaj:

- Veri tutarsızlığı riski

---

# 11. Join Kavramı

Relational database’lerde tablolar foreign key üzerinden ilişkilendirilir.

Örnek:

```sql
SELECT *
FROM orders o
JOIN customers c ON o.customer_id = c.id;
```

NoSQL sistemlerinde klasik relational join yaklaşımı çoğunlukla tercih edilmez.

Sebep:

- Dağıtık sistem maliyeti
- Ağ maliyeti
- Performans düşüşü

Bu nedenle veri çoğunlukla query pattern’e göre modellenir.

---

# 12. Veri Modelleme Yaklaşımı

Veri modelleme çoğunlukla aşağıdaki süreçlerden geçer:

1. Requirement analysis
2. Domain analysis
3. Entity identification
4. Relationship analysis
5. Access pattern analysis
6. Normalization/denormalization kararı
7. Performance optimization
8. Index strategy
9. Scalability planning
10. Refactoring

Gerçek kurumsal sistemlerde veri modeli genellikle tek seferde oluşmaz.

Süreç iterative ilerler:

- İlk model
- Test verisi
- Query analizi
- Bottleneck analizi
- Refactoring
- Yeni requirement

Bu nedenle büyük sistemlerde veri modeli defalarca revize edilir.

---

# 13. Entity Kavramı

Entity:

- Domain’de anlam ifade eden nesnedir.
- Persistent olabilir.
- Kimliği vardır.

Örnek:

- User
- Order
- Product

JPA entity örneği:

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
}
```

---

# 14. Entity Lifecycle

JPA entity lifecycle oldukça önemlidir.

Entity aşağıdaki state’lerde bulunabilir:

## 14.1 Transient

Henüz persistence context içinde değildir.

```java
User user = new User();
```

---

## 14.2 Managed

Persistence context tarafından yönetilir.

```java
entityManager.persist(user);
```

---

## 14.3 Detached

Persistence context dışına çıkmıştır.

```java
entityManager.detach(user);
```

---

## 14.4 Removed

Silinmek üzere işaretlenmiştir.

```java
entityManager.remove(user);
```

---

# 15. JPA Nedir?

JPA (Java Persistence API):

Java dünyasında ORM standardıdır.

Amaç:

- Object-relational mapping
- Boilerplate azaltma
- Veri erişimini soyutlama

Hibernate en yaygın JPA implementasyonudur.

---

# 16. ORM (Object Relational Mapping)

ORM:

Nesne yönelimli modeller ile relational tablolar arasındaki dönüşümü sağlar.

Örnek:

| Java Object | Database |
|---|---|
| User class | users table |
| field | column |
| object | row |

---

# 17. Composition Over Inheritance

Modern yazılım tasarımında inheritance yerine composition çoğunlukla önerilir.

Sebep:

- Daha düşük coupling
- Daha yüksek esneklik
- Daha kolay test edilebilirlik
- Runtime behavior değişimi

Kötü örnek:

```java
class Animal {}
class Dog extends Animal {}
```

Daha iyi yaklaşım:

```java
class Engine {}

class Car {
    private Engine engine;
}
```

---

# 18. Inheritance Mapping Strategies

JPA inheritance mapping kritik konulardan biridir.

## 18.1 Single Table Inheritance

Tüm class’lar tek tabloda tutulur.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Vehicle {
}
```

Avantaj:

- Hızlı query
- Join yok

Dezavantaj:

- Null column fazlalığı
- Veri israfı

---

## 18.2 Joined Strategy

Her entity kendi tablosunda tutulur.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {
}
```

Avantaj:

- Normalize yapı
- Daha temiz model

Dezavantaj:

- Fazla join
- Performans maliyeti

---

## 18.3 Table Per Class

Her subclass bağımsız tabloya sahip olur.

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Vehicle {
}
```

Genellikle önerilmez.

Sebep:

- Complex union query
- Performans sorunları
- Polymorphic query maliyeti
- Maintainability problemleri

Kurumsal sistemlerde çoğunlukla:

- SINGLE_TABLE
veya
- JOINED

tercih edilir.

---

# 19. Unidirectional vs Bidirectional Relationships

## 19.1 Unidirectional

İlişki tek taraftan bilinir.

```java
@OneToMany
private List<Order> orders;
```

Avantaj:

- Daha basit model
- Daha düşük complexity

---

## 19.2 Bidirectional

İki taraf birbirini bilir.

```java
@OneToMany(mappedBy = "user")
private List<Order> orders;

@ManyToOne
private User user;
```

Avantaj:

- Daha güçlü navigation

Dezavantaj:

- Infinite recursion riski
- Serialization problemi
- Daha yüksek complexity

---

# 20. Fetch Types

## 20.1 Eager Loading

İlişkili veri hemen yüklenir.

```java
@OneToMany(fetch = FetchType.EAGER)
```

Avantaj:

- Veri hazır gelir.

Dezavantaj:

- Gereksiz veri yüklenebilir.
- N+1 problemi oluşabilir.

---

## 20.2 Lazy Loading

Veri ihtiyaç olduğunda yüklenir.

```java
@OneToMany(fetch = FetchType.LAZY)
```

Avantaj:

- Daha iyi performans
- Daha az memory kullanımı

Dezavantaj:

- LazyInitializationException riski

Modern backend uygulamalarında çoğunlukla LAZY önerilir.

---

# F2 — Couchbase Basics

# 21. Couchbase Nedir?

Couchbase distributed document-oriented NoSQL veritabanıdır.

Özellikleri:

- JSON document storage
- High availability
- Distributed architecture
- Memory-first yaklaşımı
- N1QL query language
- Full-text search
- Eventing
- Analytics

---

# 22. Couchbase Architecture

Temel bileşenler:

1. Cluster
2. Node
3. Bucket
4. Scope
5. Collection
5. Index
6. Document

---

# 23. Bucket Kavramı

Bucket relational database’teki database kavramına benzer.

Bucket:

- Veri container’ıdır.
- Memory quota içerir.
- Replication yönetir.

Bucket türleri:

- Couchbase bucket
- Ephemeral bucket
- Memcached bucket

---

# 24. Scope Kavramı

Scope logical grouping sağlar.

Relational database’de schema kavramına benzer.

Örnek:

- ecommerce.inventory
- ecommerce.orders

---

# 25. Collection Kavramı

Collection document gruplarını temsil eder.

Relational database’de table kavramına benzer.

Örnek:

- users
- products
- orders

---

# 26. Couchbase Document Yapısı

```json
{
  "type": "user",
  "username": "melih",
  "email": "test@test.com"
}
```

---

# 27. Spring Boot Couchbase Integration

Dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-couchbase</artifactId>
</dependency>
```

Configuration:

```yaml
spring:
  couchbase:
    connection-string: couchbase://localhost
    username: admin
    password: password
  data:
    couchbase:
      bucket-name: ecommerce
```

---

# 28. Couchbase Entity Örneği

```java
@Document
public class Product {

    @Id
    private String id;

    private String name;

    private Double price;
}
```

---

# F3 — Querying, N1QL, Indexing

# 29. N1QL Nedir?

N1QL:

SQL benzeri Couchbase query language’dir.

Avantaj:

- SQL syntax yakınlığı
- JSON query desteği
- Nested object query

---

# 30. Temel N1QL Query

```sql
SELECT *
FROM ecommerce.inventory.products
WHERE price > 100;
```

---

# 31. Projection

```sql
SELECT name, price
FROM ecommerce.inventory.products;
```

---

# 32. Filtering

```sql
SELECT *
FROM users
WHERE age > 18;
```

---

# 33. ORDER BY

```sql
SELECT *
FROM products
ORDER BY price DESC;
```

---

# 34. LIMIT

```sql
SELECT *
FROM products
LIMIT 10;
```

---

# 35. JOIN in Couchbase

```sql
SELECT *
FROM orders o
JOIN customers c ON KEYS o.customerId;
```

NoSQL sistemlerde join maliyetlidir.

Bu nedenle:

- Embedding
- Query-based modeling

çoğunlukla daha avantajlıdır.

---

# 36. Indexing

Index performans için kritik öneme sahiptir.

Index olmadan query full scan yapabilir.

---

# 37. Primary Index

```sql
CREATE PRIMARY INDEX idx_primary
ON ecommerce.inventory.products;
```

Genellikle production ortamında önerilmez.

Sebep:

- Full bucket scan
- Yüksek maliyet

---

# 38. Secondary Index

```sql
CREATE INDEX idx_price
ON products(price);
```

---

# 39. Composite Index

```sql
CREATE INDEX idx_category_price
ON products(category, price);
```

---

# 40. Covering Index

Query’nin tüm alanları index içinde bulunur.

Avantaj:

- Data fetch azalır.
- Daha yüksek performans sağlar.

---

# 41. Query Optimization

Optimizasyon teknikleri:

1. Proper indexing
2. Query simplification
3. Projection kullanımı
4. Avoid SELECT *
5. Pagination
6. Denormalization
7. Cache usage
8. Covering index

---

# F4 — Spring Data, Repository, Custom Query

# 42. Spring Data Nedir?

Spring Data:

Persistence abstraction sağlar.

Amaç:

- Boilerplate azaltmak
- Repository pattern standardizasyonu
- Query abstraction

---

# 43. Repository Pattern

Repository:

Data access logic’i soyutlar.

```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```

---

# 44. CrudRepository

```java
public interface UserRepository
        extends CrudRepository<User, Long> {
}
```

Temel CRUD operasyonları sağlar.

---

# 45. JpaRepository

Ek özellikler:

- Paging
- Sorting
- Batch operations

---

# 46. Query Method

Spring method isminden query üretir.

```java
List<User> findByUsername(String username);
```

---

# 47. Custom Query

```java
@Query("SELECT u FROM User u WHERE u.age > :age")
List<User> findAdults(@Param("age") int age);
```

Avantaj:

- Daha kontrollü query
- Complex query desteği

---

# 48. Native Query

```java
@Query(value = "SELECT * FROM users", nativeQuery = true)
List<User> findAllNative();
```

Avantaj:

- Vendor-specific optimization

Dezavantaj:

- Database bağımlılığı

---

# 49. Criteria Query

Dynamic query oluşturmak için kullanılır.

```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<User> query = cb.createQuery(User.class);
Root<User> root = query.from(User.class);

query.select(root)
     .where(cb.equal(root.get("username"), "melih"));
```

Avantaj:

- Dynamic filtering
- Type-safe yaklaşım

Dezavantaj:

- Verbose syntax

---

# 50. Specification Pattern

Complex dynamic filtering için yaygın kullanılır.

```java
public class UserSpecification {

    public static Specification<User> hasAge(int age) {
        return (root, query, cb) ->
                cb.equal(root.get("age"), age);
    }
}
```

---

# 51. Pagination

```java
Page<User> findAll(Pageable pageable);
```

Avantaj:

- Memory optimization
- Large dataset handling

---

# 52. Sorting

```java
Sort.by("username").ascending();
```

---

# 53. Persistence Context

Persistence context:

Managed entity cache’idir.

Hibernate:

- Dirty checking
- Change tracking
- First-level cache

sağlar.

---

# 54. Dirty Checking

Managed entity değişirse otomatik update edilir.

```java
user.setUsername("newName");
```

Explicit save çağrısı gerekmeyebilir.

---

# 55. Flush

Persistence context değişikliklerini database’e gönderir.

```java
entityManager.flush();
```

---

# F5 — Data Consistency & Optimistic Locking

# 56. Data Consistency

Data consistency kritik sistemlerde çok önemlidir.

Özellikle:

- Bankacılık
- Finans
- Sipariş yönetimi
- Envanter yönetimi

---

# 57. Concurrency Problems

Aynı veri üzerinde eş zamanlı işlem:

- Lost update
- Dirty read
- Non-repeatable read
- Phantom read

problemlerine yol açabilir.

---

# 58. Locking Yaklaşımları

## 58.1 Pessimistic Locking

Kaynak önceden kilitlenir.

Avantaj:

- Güçlü tutarlılık

Dezavantaj:

- Deadlock riski
- Performans düşüşü

---

## 58.2 Optimistic Locking

Çakışma ihtimali düşük varsayılır.

Modern sistemlerde oldukça yaygındır.

---

# 59. Optimistic Locking Kullanımı

```java
@Version
private Long version;
```

Hibernate update sırasında version kontrol eder.

Örnek:

1. User A version=1 okur
2. User B version=1 okur
3. User A update yapar → version=2
4. User B update yaparsa exception oluşur

---

# 60. Transaction Management

```java
@Transactional
public void updateUser() {
}
```

Transaction:

- Atomic operation sağlar.
- Rollback yönetir.

---

# 61. Isolation Levels

## READ UNCOMMITTED

En düşük isolation.

---

## READ COMMITTED

Dirty read engellenir.

---

## REPEATABLE READ

Aynı transaction içinde aynı veri korunur.

---

## SERIALIZABLE

En yüksek isolation.

Performans maliyeti yüksektir.

---

# 62. N+1 Query Problemi

ORM dünyasının en kritik performans problemlerinden biridir.

Örnek:

1 query parent için
N query child için

Toplam:

N+1 query

---

# 63. Çözüm Yöntemleri

- Fetch join
- Entity graph
- Batch fetching
- DTO projection

Örnek:

```java
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

---

# 64. DTO Projection

Entity yerine yalnızca gerekli veri taşınır.

```java
public record UserDto(
        String username,
        String email
) {}
```

Avantaj:

- Daha düşük memory usage
- Daha hızlı response

---

# 65. Database Design Best Practices

## Tablo neye göre oluşturulur?

Tablo tasarımı aşağıdaki faktörlere göre belirlenir:

1. Domain modeli
2. Business requirement
3. Access pattern
4. Transaction ihtiyacı
5. Query sıklığı
6. Veri büyüklüğü
7. Performans gereksinimi
8. Scaling ihtiyacı
9. Reporting ihtiyaçları
10. Consistency gereksinimi

---

# 66. Veri Modeli Kaç Iterasyonda Oluşur?

Kurumsal projelerde veri modeli:

- İlk taslak
- Proof of concept
- Test verisi
- Load test
- Query optimization
- Production feedback

süreçlerinden geçer.

Bu nedenle veri modeli sürekli evolve eder.

---

# 67. Join Kararı Neye Göre Verilir?

Join kullanımı aşağıdakilere bağlıdır:

1. Veri büyüklüğü
2. Query sıklığı
3. Transaction gereksinimi
4. Read/write ratio
5. Performans hedefi
6. Normalization seviyesi
7. Cache stratejisi

Çok yoğun distributed sistemlerde excessive join performans problemi oluşturabilir.

---

# 68. Mikroservis ve Database Yaklaşımı

Modern mikroservis mimarilerinde:

- Database per service
- Polyglot persistence

yaklaşımı yaygındır.

Örnek:

- Payment → PostgreSQL
- Analytics → Cassandra
- Cache → Redis
- Search → Elasticsearch

---

# 69. Polyglot Persistence

Her problem için farklı database seçilmesidir.

Avantaj:

- Problem-specific optimization

Dezavantaj:

- Operational complexity
- Data synchronization problemi

---

# 70. Sonuç

Modern backend geliştirme süreçlerinde:

- Relational database
- NoSQL
- ORM
- Distributed systems
- Query optimization
- Consistency management

birbirleriyle doğrudan bağlantılıdır.

Başarılı bir sistem tasarımı için:

- Veri modeli
- Access pattern
- Transaction ihtiyacı
- Performans hedefi
- Scalability planı

birlikte değerlendirilmelidir.

Özellikle büyük ölçekli sistemlerde:

- Doğru indexing
- Lazy loading kullanımı
- Composition over inheritance
- Query optimization
- Proper transaction management

kritik öneme sahiptir.

Bu nedenle modern yazılım mühendisliği yalnızca kod yazma süreci değil, aynı zamanda veri mimarisi, performans mühendisliği ve dağıtık sistem tasarımı disiplinlerini de kapsamaktadır.

