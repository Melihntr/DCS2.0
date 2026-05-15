# Spring Data Dokümantasyonu

Spring Data, veri erişim katmanını (Data Access Layer) geliştirmeyi inanılmaz derecede kolaylaştıran, Spring Framework üzerine inşa edilmiş kapsamlı bir projedir.

JPA ve Hibernate gibi teknolojileri kullanırken yazdığınız tekrarlı kodları (Boilerplate code) ortadan kaldırmayı amaçlar.

#### Temel Amacı Nedir?
Geleneksel yöntemde her bir tablo (Entity) için; veriyi kaydetme, silme, güncelleme ve ID ile bulma gibi metotları (save, delete, findById) tek tek yazmanız gerekir. Spring Data, bu standart işlemleri sizin yerinize halleder.

Bunun yanı sıra sadece ilişkisel veritabanları (SQL) değil; NoSQL, Graph, Map-Reduce gibi farklı veri depolama teknolojilerine de aynı programlama modelini kullanarak erişmenizi sağlar.


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

### 1.2 ORM (Object-Relational Mapping) Kavramı
JPA'nın temelinde ORM yatar. Nesne yönelimli programlama (Java) ile ilişkisel veritabanları (SQL) arasındaki "doku uyuşmazlığını" giderir.

Java tarafında: Sınıflar, nesneler ve kalıtım vardır.

Veritabanı tarafında: Tablolar, satırlar ve yabancı anahtarlar (foreign keys) vardır.
ORM, bu iki farklı dünya arasında bir köprü kurarak, geliştiricinin SQL kodu yazmadan veritabanı işlemi yapmasına olanak tanır.

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

## 2. Entity Lifecycle

## 2.1 New (Transient)
Nesnenin bellekte (RAM) oluşturulduğu ancak henüz bir veritabanı satırı ile ilişkilendirilmediği durumdur.

Nesne new anahtar kelimesiyle oluşturulmuştur.

Henüz bir Primary Key (ID) değerine sahip değildir (veya atanmamıştır).

EntityManager bu nesneden haberdar değildir; uygulama kapanırsa veri kaybolur.
```
User user = new User();
```


### 2.2 Managed (Persistent)
Nesnenin EntityManager tarafından takip edildiği ve veritabanındaki bir satırla eşleştiği durumdur.

persist(), find() veya merge() metotları çağrıldığında nesne bu duruma geçer.

Kirli Kontrol (Dirty Checking): Bu durumdaki bir nesnenin herhangi bir alanını (field) değiştirdiğinizde, işlem (transaction) sonunda JPA bunu fark eder ve otomatik olarak bir UPDATE sorgusu gönderir. Manuel update() çağırmanıza gerek kalmaz.

```
entityManager.persist(user);
```

### 2.3 Detached
Nesnenin veritabanında bir karşılığı vardır ancak artık EntityManager tarafından takip edilmediği durumdur.

EntityManager kapatıldığında (close()) veya clear() metodu çağrıldığında tüm nesneler "detached" olur.

Bu durumdaki bir nesne üzerinde yapılan değişiklikler veritabanına yansımaz. Tekrar yönetilmesini isterseniz merge() metodunu kullanmanız gerekir.

```
entityManager.detach(user);
```


### 2.4 Removed

Nesnenin silinmek üzere işaretlendiği durumdur.

remove() metodu çağrıldığında nesne bu duruma geçer.

İşlem (transaction) commit edildiğinde veritabanından ilgili satır fiziksel olarak silinir.

```
entityManager.remove(user);
```
#### Lifecycle Olayları ve Callback Metotları

JPA, bu geçişler sırasında belirli işlemleri otomatik olarak tetiklemenize izin veren Callback anotasyonları sunar. Kodunuzun temiz kalması ve mantıksal işlemlerin (loglama, tarih güncelleme vb.) otomatize edilmesi için kritiktir:

@PrePersist: Veritabanına ilk kez kaydedilmeden hemen önce çalışır (Örn: createdAt tarihini atamak için).

@PostPersist: Kayıt işlemi tamamlandıktan sonra çalışır.

@PreUpdate: Mevcut bir veri güncellenmeden hemen önce tetiklenir (Örn: updatedAt bilgisini güncellemek için).

@PostUpdate: Güncelleme başarılı olduktan sonra çalışır.

@PreRemove: Veri silinmeden hemen önce çalışır.

@PostRemove: Veri silindikten sonra çalışır.

@PostLoad: Veri veritabanından çekilip nesneye dönüştürüldükten hemen sonra çalışır.

Özet Akış Şeması


## 3. Spring Data Repository Yapısı
### Repository Hiyerarşisi
Repository<T, ID>: En tepedeki boş interface'dir. Sadece bir sınıfın repository olduğunu işaretlemek (marker interface) için kullanılır. Hiçbir metot içermez.

CrudRepository<T, ID>: Temel Create, Read, Update, Delete (CRUD) işlemlerini sağlar. save(), findById(), findAll(), deleteById() gibi metotlar burada tanımlıdır.

PagingAndSortingRepository<T, ID>: CrudRepository'den türer. CRUD işlemlerine ek olarak verileri sayfalara bölmek (Pagination) ve belirli kriterlere göre sıralamak (Sorting) için gerekli metotları ekler.

JpaRepository<T, ID>: En sık kullanılan arayüzdür. Yukarıdakilerin hepsine ek olarak JPA'ya özgü flush() (değişiklikleri veritabanına zorla yazma), saveAndFlush() ve toplu silme (deleteInBatch()) gibi gelişmiş özellikleri sunar.


### 3.2 Standart Bir Repository Tanımlama
Spring Data ile çalışırken genellikle JpaRepository tercih edilir. Bu sayede tüm yeteneklere tek seferde sahip olursunuz.

```Java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 1. Query Methods: Metot isminden sorgu türetme
    List<Product> findByNameContaining(String keyword);
    
    // 2. Custom JPQL Query: Daha karmaşık mantıklar için
    @Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    List<Product> findExpensiveProducts(@Param("minPrice") BigDecimal minPrice);
}
```
### 3.3 Neden Bu Yapı Kullanılır?
Kod Tekrarını Önler: Her entity için aynı SQL sorgularını (insert, update vb.) tekrar yazmanıza gerek kalmaz.

Okunabilirlik: findByEmailAndStatus(String email, Status status) gibi bir metot ismi, kodun ne yaptığını SQL bilmeyen biri için bile anlaşılır kılar.

Tip Güvenliği (Type Safety): Doğrudan Java nesneleriyle (Entity) çalıştığınız için çalışma zamanı hataları minimize edilir.

Esneklik: Eğer SQL yerine Couchbase veya MongoDB gibi bir NoSQL yapısına geçerseniz, sadece JpaRepository yerine CouchbaseRepository yazarak kodun geri kalanını (Business Logic) neredeyse hiç değiştirmeden kullanmaya devam edebilirsiniz.


### 3.4 Persistence Abstraction

Spring Data, alttaki ORM implementasyonunu soyutlar.

Uygulama:

Hibernate,
EclipseLink,
başka bir provider

kullansa bile repository kodu değişmeden kalabilir.

Bu yaklaşım:

loose coupling,
maintainability,
testability

sağlar.


## 4.  Query Mekanizmaları

### 4.1 Derived Query

Query Methods (Metot İsminden Sorgu Türetme)En popüler yöntemdir. Spring Data, yazdığınız metot ismini analiz eder ve arka planda otomatik olarak bir SQL sorgusu oluşturur.

Kural: Metot ismi find...By, read...By, query...By, count...By veya get...By ile başlamalıdır.

Örnekler:
```findByUsername(String username) $\rightarrow$ WHERE username = ?findByAgeGreaterThan(int age) $\rightarrow$ WHERE age > ?findByCategoryAndPriceLessThan(String cat, Double price) $\rightarrow$ WHERE category = ? AND price < ?findFirst3ByOrderByCreatedAtDesc() ```

En yeni 3 kaydı getirir.

### 4.2 Custom Query
Özel sorgular için kullanılır.

#### @Query Anotasyonu (JPQL)

Metot isimleri çok uzadığında veya karmaşık JOIN işlemleri gerektiğinde kullanılır. Burada tablo isimleri değil, Entity (Sınıf) isimleri kullanılır.

```Java
@Query("SELECT u FROM User u WHERE u.status = :status AND u.email LIKE %:suffix%")
List<User> findActiveUsersWithEmailDomain(@Param("status") Status status, @Param("suffix") String suffix);```
```
Avantajı: Veritabanı bağımsızdır (H2, MySQL veya PostgreSQL fark etmez).

#### Native Queries (Saf SQL)
Bazen veritabanına özgü fonksiyonları (Örn: PostgreSQL'e özel JSON işlemleri) kullanmanız gerekir. Bu durumda nativeQuery = true parametresi kullanılır.

```Java
@Query(value = "SELECT * FROM users WHERE last_login > NOW() - INTERVAL '1 day'", nativeQuery = true)
List<User> findRecentLogins();
```
Dikkat: Bu yöntem veritabanı bağımlıdır. Veritabanı değiştirirseniz sorguyu güncellemeniz gerekebilir.


### 4.3 Criteria Query

Sorgunun çalışma zamanında (runtime), kullanıcıdan gelen filtrelere göre dinamik olarak oluşturulması gereken durumlarda kullanılır. Tip güvenlidir (Type-safe), yani yanlış bir alan adı yazarsanız kod derlenmez.

Özellikle "Gelişmiş Arama" sayfalarında (fiyat aralığı, kategori, tarih filtresi gibi 10 farklı opsiyonun olduğu yerler) tercih edilir.
```
CriteriaBuilder cb = entityManager.getCriteriaBuilder();

CriteriaQuery<User> cq = cb.createQuery(User.class);

Root<User> root = cq.from(User.class);

cq.select(root)
  .where(cb.equal(root.get("username"), "melih"));

List<User> result =
    entityManager.createQuery(cq).getResultList();
```


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

### 6.1 Unidirectional (Tek Yönlü) İlişki
Bu modelde sadece bir sınıf diğerini tanır. İlişki tek taraflı bir referans üzerinden yürür.

Mantık: A sınıfı B sınıfını bir alan (field) olarak tutar, ancak B sınıfının A'dan haberi yoktur.

Örnek: Bir User (Kullanıcı) nesnesinin bir Address (Adres) nesnesi tutması. Adres nesnesine gidip "Bu adres hangi kullanıcıya ait?" diye sorduğunuzda cevap alamazsınız.

```Java
@Entity
public class User {
    @Id
    private Long id;

    @OneToOne // Sadece User üzerinden Address'e erişim var
    @JoinColumn(name = "address_id")
    private Address address;
}
@Entity
public class Address {
    @Id
    private Long id;
    private String street;
    // User referansı yok!
}
```

### 6.2 Bidirectional (Çift Yönlü) İlişki
Her iki sınıf da birbirini referans olarak tutar. İki taraftan da birbirine erişmek mümkündür.

Mantık: A nesnesi üzerinden B'ye, B nesnesi üzerinden de A'ya ulaşılabilir.

mappedBy: Çift yönlü ilişkilerde en kritik kavram budur. İlişkinin "sahibini" (owning side) belirlemek için kullanılır. Veritabanında dış anahtar (Foreign Key) hangi tabloda duruyorsa, ilişki sahibi odur. Diğer taraf mappedBy ile "ilişki bende değil, karşı tarafta" mesajını verir.

```Java
@Entity
public class Post {
    @Id
    private Long id;

    @OneToMany(mappedBy = "post") // İlişki sahibi Comment sınıfıdır
    private List<Comment> comments;
}

@Entity
public class Comment {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id") // Foreign Key burada tutulur
    private Post post;
}
```

### 6.3 Aralarındaki Farklar ve Seçim Kriterleri

#### Neden Unidirectional Tercih Edilir?

Kodun daha temiz ve bağımsız (loosely coupled) kalmasını sağlar. Eğer bir Product nesnesinin hangi Category'ye ait olduğunu bilmeniz yetiyorsa, Category sınıfının içine binlerce Product listesi ekleyip belleği yormanıza gerek kalmaz.

#### Neden Bidirectional Tercih Edilir?

Erişim kolaylığı sağlar. Örneğin bir Order (Sipariş) nesnesini çekerken içindeki OrderItem (Sipariş Kalemleri) listesine de sık sık erişiyorsanız, çift yönlü yapı işleri kolaylaştırır.Kritik 

Uyarı: Infinite Recursion (Sonsuz Döngü)Bidirectional ilişkilerde nesneleri JSON formatına çevirirken (örneğin bir REST API hazırlarken), nesneler birbirini çağırdığı için sonsuz bir döngüye girip StackOverflowError hatası alabilirsiniz. Bunu önlemek için @JsonManagedReference ve @JsonBackReference gibi anotasyonlar kullanmanız gerekir.Bu konu, veritabanı tasarımı yaparken tablolar arası coupling (bağımlılık) seviyesini belirlediği için mimari açıdan çok değerlidir. Genellikle "olabildiğince unidirectional başla, ihtiyaç duyarsan bidirectional yap" prensibi uygulanır.

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

bir ana kaydı çekerken, o kayda bağlı ilişkili verilerin (örneğin bir postun yorumları) her biri için veritabanına ayrı ayrı gereksiz sorgu gönderilmesi durumudur.

Sorun Nasıl Oluşur?
1 Sorgu: Tüm "Post"ları çekmek için atılır. (SELECT * FROM Post)

N Sorgu: Gelen her bir (N tane) postun "Yorumlar"ını çekmek için tek tek sorgu atılır. (SELECT * FROM Comment WHERE post_id = ?)

Sonuçta 1 + N kadar sorgu oluşur. Bu da veritabanını yorar ve performansı ciddi şekilde düşürür.

Çözüm Yöntemleri
Bu sorunu çözmek için Eager Loading yerine, veriyi tek bir seferde (veya akıllıca) çeken yöntemler kullanılır:

Join Fetch (En Yaygın): Sorguda JOIN FETCH kullanarak ilişkili veriyi tek bir SQL sorgusuyla (JOIN ile) getirir.

```Java
@Query("SELECT p FROM Post p JOIN FETCH p.comments")
List<Post> findAllWithComments();
```
Entity Graph: Hangi alanların çekileceğini belirleyen bir plan sunar.

Batch Size: Hibernate'e nesneleri tek tek değil, 10'arlı veya 50'şerli gruplar halinde çekmesini söyler (Sorgu sayısını azaltır ama tamamen bitirmez).


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

Codd'un 13 kuralı (0-12) teoriktir. Gerçek dünyada bir veritabanı motorunun "ilişkisel" kabul edilmesi için bu kuralların hepsini sağlaması neredeyse imkansızdır. Piyasada "Eğer şu temel 5-6 kuralı sağlıyorsa o bir RDBMS'dir" gibi sektörel kabuller vardır.

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
Veritabanı tasarımında Normalizasyon, veri tekrarını (redundancy) önlemek ve veri bütünlüğünü (integrity) korumak için tabloları organize etme sürecidir. Toplamda 6-7 seviye olsa da, endüstride ilk 3 formun (ve bazen BCNF) uygulanması genellikle yeterli kabul edilir.

### 10.1 Birinci Normal Form (1NF)

Bir tablonun 1NF olması için temel kural: Her hücrede tek bir değer (atomic value) olmalıdır.

Sorun: Bir "Hobiler" sütununda "Yüzme, Kitap Okuma" şeklinde virgülle ayrılmış birden fazla veri olması.

Çözüm: Çoklu değer içeren sütunlar parçalanır ve her satır tek bir değer içerir. Ayrıca her tablonun bir Primary Key'i olmalıdır.


### 10.2 İkinci Normal Form (2NF)

2NF olması için tablo önce 1NF olmalı ve Kısmi Bağımlılık (Partial Dependency) olmamalıdır.

Sorun: Bileşik anahtarın (Composite Key) olduğu bir tabloda, bir sütunun anahtarın tamamına değil de sadece bir kısmına bağlı olması.

Örnek: (ÖğrenciID, DersID) anahtarı olan tabloda "Öğrenci Adı" sütunu sadece ÖğrenciID'ye bağlıdır. DersID ile ilgisi yoktur.

Çözüm: Bu sütunlar ayrılır ve yeni bir tablo oluşturulur.

### 10.3 Üçüncü Normal Form (3NF)

3NF olması için tablo önce 2NF olmalı ve Geçişli Bağımlılık (Transitive Dependency) olmamalıdır.

Sorun: Anahtar olmayan bir sütunun, anahtar olmayan başka bir sütuna bağlı olması. (Yani: A $\rightarrow$ B $\rightarrow$ C durumu).

Örnek: Öğrenci tablosunda "Okul Kodu" anahtara bağlıdır, "Okul Adı" ise "Okul Kodu"na bağlıdır. Okul adı dolaylı olarak anahtara bağlı kalır.

Çözüm: Geçişli bağımlılık yaratan sütunlar (Okul Kodu ve Okul Adı) ayrı bir tabloya (Okul tablosu) taşınır.

### 10.4 Boyce-Codd Normal Form (BCNF / 3.5NF)

3NF'in daha güçlü bir versiyonudur.

Kural: Her belirleyici (determinant) bir Aday Anahtar (Candidate Key) olmalıdır.

Genellikle birden fazla bileşik anahtarın birbiriyle çakıştığı durumlarda ortaya çıkan karmaşık anomalileri çözer.


## 11. Join Mekanizmaları

İlişkisel veritabanlarında Join, iki veya daha fazla tabloyu aralarındaki bir sütun (genellikle Foreign Key) üzerinden birleştirerek tek bir sonuç kümesi elde etmektir.

### 11.1 Inner Join

Her iki tabloda da eşleşen kaydı olan satırları getirir. Kesişim kümesidir.

Örnek: Sadece siparişi olan müşterileri listeler.

```sql
SELECT *
FROM users u
INNER JOIN orders o
ON u.id = o.user_id
```

### 11.2 Left (Outer) Join

Sol (birinci) tablodaki tüm kayıtları ve sağ tablodaki eşleşen kayıtları getirir. Sağ tarafta eşleşme yoksa o alanlar NULL döner.

Örnek: Hiç sipariş vermemiş olsa bile tüm müşterileri ve (varsa) siparişlerini listeler.

### 11.3ight (Outer) Join

Sağ (ikinci) tablodaki tüm kayıtları ve sol tablodaki eşleşen kayıtları getirir. Sol tarafta eşleşme yoksa o alanlar NULL döner.

Örnek: Sistemdeki tüm siparişleri ve bu siparişleri veren müşterileri listeler (Müşterisi silinmiş siparişler dahil).


### 11.4 Full (Outer) Join

Her iki tablodaki tüm kayıtları getirir. Eşleşme olanları birleştirir, olmayan yerleri her iki taraf için de NULL ile doldurur.

Örnek: Tüm müşterileri ve tüm siparişleri, birbirleriyle eşleşip eşleşmediklerine bakmaksızın listeler.


### 11.5 Fetch Join

JPA (Java Persistence API) ve Hibernate'de N+1 problemini çözmek için kullanılan en etkili yöntemdir. Standart bir SQL Join'inden farklı olarak, ilişkili nesneleri sadece sorgu sonuç kümesine dahil etmekle kalmaz, aynı zamanda bu nesnelerin Java tarafındaki referanslarını (Entity) tek bir sorguda doldurur.

Neden Kullanılır?

Standart bir JOIN kullandığınızda, veritabanı seviyesinde tablolar birleşir ancak Hibernate, ilişkili nesneleri (örneğin bir Post'un Comment'lerini) hala Lazy (Tembel) olarak işaretlenmişse çekmez. Bu verilere erişmek istediğinizde her biri için yeni bir sorgu atar (N+1 Sorunu). FETCH JOIN ise bu ilişkiyi tek bir SELECT içinde "yükleyerek" getirir.

Örnek Kullanım (JPQL)

Aşağıdaki sorgu, tüm yazarları ve o yazarlara ait kitapları tek bir veritabanı turunda (round-trip) çeker:

```Java
@Query("SELECT a FROM Author a JOIN FETCH a.books")
List<Author> findAllAuthorsWithBooks();
```
Normal JOIN olsaydı: 1 sorgu yazarlar için, ardından her yazarı kitapları için N tane ek sorgu.

FETCH JOIN ile: Tek bir SQL sorgusu ile tüm yazarlar ve kitapları belleğe yüklenmiş (managed state) olarak gelir.


## 12. Inheritance Mapping

JPA'da Inheritance Mapping (Kalıtım Eşleme), Java'daki nesne yönelimli kalıtım yapısının (Inheritance), ilişkisel veritabanındaki (RDBMS) tablolara nasıl aktarılacağını belirleyen yöntemdir. Veritabanı tabloları doğal olarak kalıtımı desteklemediği için JPA bize 3 ana strateji sunar.

### 12.1 Single Table Strategy (Tek Tablo Stratejisi)

Tüm sınıf hiyerarşisi (Ata ve Çocuk sınıflar) veritabanında tek bir tabloda tutulur.

Nasıl Çalışır: Tabloda, hangi satırın hangi sınıfa ait olduğunu belirten bir Discriminator Column (ayırıcı sütun, genellikle DTYPE) bulunur.

Avantajı: Çok hızlıdır çünkü JOIN işlemi gerektirmez. Sorgular basittir.

Dezavantajı: Çocuk sınıflara özgü alanlar veritabanında NULL olabilir. Bu durum veri bütünlüğü kısıtlarını (NOT NULL) zorlaştırır.

### 12.2 Joined Table Strategy (Birleştirilmiş Tablo Stratejisi)
Her sınıf (Ata ve her bir Çocuk) için ayrı bir tablo oluşturulur.

Nasıl Çalışır: Çocuk tabloları, ata tablonun Primary Key'ini kullanarak ona bağlanır.

Avantajı: Veritabanı tasarımı en temiz ve "normalizasyon" kurallarına en uygun olandır. NOT NULL kısıtları rahatça kullanılabilir.

Dezavantajı: Veri çekmek için çok sayıda JOIN yapılması gerekir, bu da büyük hiyerarşilerde performansı düşürür.

### 12.3 Table Per Class Strategy (Sınıf Başına Tablo Stratejisi)

Sadece her bir somut (concrete) çocuk sınıf için ayrı bir tablo oluşturulur. Ata sınıf için tablo oluşturulmaz.

Nasıl Çalışır: Ata sınıftaki tüm alanlar, her bir çocuk tablosunda tekrarlanır.

Avantajı: Bir çocuk sınıfı sorgularken sadece kendi tablosuna bakılır, hızlıdır.

Dezavantajı: Ata sınıf üzerinden bir sorgu yapıldığında (örneğin "tüm çalışanları getir") veritabanı tüm tabloları UNION ile birleştirmek zorunda kalır, bu çok maliyetlidir.

### 12.4 Hangisi Tercih Edilmeli?

Seçim yaparken performans ve veri bütünlüğü dengesini gözetmen gerekir

Single TableVarsayılan tercihtir. Hiyerarşi küçükse ve performans en öncelikli kriterse (Örn: User -> Admin, Customer).

Joined TableVeri bütünlüğü (NOT NULL gibi) çok kritikse ve hiyerarşi çok karmaşıksa. Profesyonel projelerde "temiz tasarım" için sıkça tercih edilir.

Table Per ClassAta sınıf üzerinden sorgu yapmaya neredeyse hiç ihtiyaç duyulmayan, her alt sınıfın tamamen bağımsız çalıştığı nadir durumlarda.


## 13. Composition Over Inheritance

Composition over Inheritance (Kalıtım yerine Kompozisyon), yazılım tasarımında "is-a" (bir ...-dır) ilişkisi yerine "has-a" (bir ...-ya sahiptir) ilişkisini tercih etmeyi öneren temel bir prensiptir.

Bu prensip, sınıfların birbirine sıkı sıkıya bağlanmasını (tight coupling) önlemek ve kodun esnekliğini artırmak için kullanılır.

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

### 13.1 Örnek Senaryo: Robot Sistemi
Diyelim ki farklı yetenekleri olan robotlar tasarlıyorsunuz.

Kalıtım ile (Kötü Yaklaşım):
UcanRobot ve YuruyenRobot diye iki sınıf yaptınız. Peki hem uçan hem yürüyen bir robot gerekirse? Java'da iki sınıfı birden extend edemezsiniz. Kod tekrarına düşersiniz.

Kompozisyon ile (İyi Yaklaşım):
Robotun yeteneklerini ayrı sınıflar veya arayüzler (interface) olarak tanımlarsınız ve robot nesnesine enjekte edersiniz.

```Java
public class Robot {
    private MoveStrategy moveStrategy; // Kompozisyon burası

    public Robot(MoveStrategy strategy) {
        this.moveStrategy = strategy;
    }

    public void move() {
        moveStrategy.move();
    }
}
```

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

Hibernate’de DDL (Data Definition Language) yönetimi, Java entity sınıflarınızdaki değişikliklerin veritabanı şemasına (tablolar, kolonlar, kısıtlamalar) nasıl yansıtılacağını belirleyen mekanizmadır. Bu yönetim, hibernate.hbm2ddl.auto (veya Spring Boot kullanıyorsan spring.jpa.hibernate.ddl-auto) özelliği ile kontrol edilir.

### 15.1 Tablo Oluşturma Süreci

Hibernate entityleri analiz eder:

annotation okuma
metadata üretimi
ilişki çözümleme
foreign key oluşturma
DDL üretimi


### 15.2 ddl-auto Modları
spring.jpa.hibernate.ddl-auto=create

Seçenekler:

create	Baştan oluşturur

update	Eksikleri günceller

create-drop	Açılışta oluşturur kapanışta siler

validate	Şemayı doğrular

none	İşlem yapmaz


### 15.3 Join Kararı Neye Göre Verilir?

Özetle, Hibernate'in JOIN kararı şu 4 kritere dayanır:

1. İlişkinin Varlığı (Relationship)
Tablolar arasında @ManyToOne veya @OneToMany gibi bir bağ yoksa join yapılamaz. @ManyToOne gibi tekil ilişkiler join yapmaya daha meyillidir.

2. Yükleme Stratejisi (Fetch Strategy)
Eager: "Hemen getir" demektir. Hibernate bunu genellikle ana sorguya bir Left Outer Join ekleyerek çözer.

Lazy: "İhtiyaç olunca getir" demektir. İlk sorguda join yapılmaz, veriye dokunulursa ek sorgu atılır.

3. JPQL İçindeki Komut (Explicit Join)
Siz sorguda açıkça JOIN FETCH yazarsanız, entity ayarları ne olursa olsun Hibernate veritabanında Join yapar ve veriyi tek seferde çeker.

4. Boş Olabilirlik (Optionality)
optional = false: Veri mutlaka vardır, Inner Join yapılır (Hızlıdır).

optional = true: Veri olmayabilir, veri kaybını önlemek için Left Outer Join yapılır.

## 16. Transaction Yönetimi

### 16.1 @Transactional Anotasyonu

Spring'de @Transactional kullanıldığında, Spring bu metodu bir Proxy nesnesi ile sarmalar. Arka planda şu süreçleri otomatik yönetir:

Transaction Başlatır: Metoda girildiği an veritabanı bağlantısı üzerinden bir işlem başlatılır.

Commit Eder: Metot başarıyla (hatasız) tamamlandığında tüm değişiklikler veritabanına kalıcı olarak yazılır.

Rollback Yapar: Eğer metot içinde bir RuntimeException (Unchecked Exception) fırlatılırsa, o ana kadar yapılmış tüm işlemler geri alınır ve veritabanı eski haline döner.

```
@Transactional
public void transfer() {
}
```


### 16.2 Isolation Levels (İzolasyon Seviyeleri)

Aynı anda çalışan (concurrent) transaction'ların birbirlerinin yaptığı değişiklikleri ne kadar görebileceğini belirler.

Seviye,Açıklama,Risk / Yan Etki

READ_UNCOMMITTED,Henüz commit edilmemiş (geçici) verileri bile okur.,Dirty Read: Hiç var olmamış bir veriyi okuma riski.

READ_COMMITTED,Sadece commit edilmiş verileri okur. En yaygın varsayılandır.,Non-repeatable Read: Aynı sorgu aynı işlemde farklı sonuç dönebilir.

REPEATABLE_READ,"Bir veri okunduğunda, transaction bitene kadar o verinin değişmeyeceğini garanti eder.",Phantom Read: Yeni eklenen (insert) satırları görebilir.

SERIALIZABLE,İşlemleri sıraya koyar (en katı seviye). Tam izolasyon sağlar.,Düşük Performans: Veritabanı kilitlemeleri (locking) yüzünden yavaştır.

### 16.3 Transaction Propagation (Yayılma Tipleri)

REQUIRED (Varsayılan): Mevcut bir transaction varsa ona dahil olur, yoksa yeni açar.

REQUIRES_NEW: Her zaman yeni bir transaction açar, varsa mevcut olanı bekletir.

## 17. Veri Güvenliği
### 17.1 SQL Injection

Saldırganın girdi alanlarına SQL komutları enjekte ederek veritabanını ele geçirmesidir.

Yanlış:
```
String sql =
 "SELECT * FROM users WHERE username='"
 + username + "'";
```

String birleştirme (Concatenation) kullanmak. Veri, kodun bir parçası haline gelir.

Doğru:
```
@Query("SELECT u FROM User u WHERE u.username=:username")
```

### 17.2 Password Güvenliği

Şifreler asla düz metin (plain text) olarak saklanmamalıdır. Şifreler Salted Hash yöntemiyle geri döndürülemez şekilde şifrelenmelidir.

Önerilen:

BCrypt
BCryptPasswordEncoder


### 17.3 Least Privilege Principle

Uygulamanın veritabanı kullanıcısı (DB User) bir "root" veya "admin" olmamalıdır.

Database user:

yalnızca gerekli yetkilere sahip olmalıdır


### 17.4 Audit Logging

Veri üzerinde yapılan her kritik değişikliğin izi sürülmelidir.

Kim:

ne zaman
hangi veriyi değiştirdi

takip edilmelidir.

