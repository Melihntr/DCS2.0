# Spring Data, Modern Veri Yönetimi ve Kalıcılık Mimarileri Dokümantasyonu

## 1. NoSQL Kavramları ve Döküman Tabanlı Veritabanlarında Veri Modelleme
İlişkisel veritabanlarının katı şema (rigid schema) yapısına bir alternatif olarak doğan NoSQL (Not Only SQL) veritabanları, yatay ölçeklenebilirlik (horizontal scalability) ve esnek veri modelleme imkanı sunar. Döküman tabanlı (Document DB) veritabanları, veriyi genellikle JSON veya BSON formatında saklar.
Veri Modelleme Yaklaşımları
Döküman tabanlı veritabanlarında veri modelleme, ilişkisel veritabanlarının aksine "uygulamanın veriye nasıl erişeceğine" (query-driven design) göre şekillenir. İki temel modelleme stratejisi vardır:
Embedding (İçe Gömme - Composition): Birlikte okunan ve yazılan veriler tek bir JSON dökümanı içinde tutulur. Composition over inheritance felsefesinin veri modellemedeki karşılığıdır. İşlem maliyetini (I/O) düşürür.
⁠
Referencing (Referans Verme): Çok büyük alt dökümanlar veya çoktan-çoğa (many-to-many) ilişkiler söz konusu olduğunda, dökümanlar birbirlerinin ID'lerini tutarak ayrılır. 

## 2. Couchbase Temelleri ve Altyapı Mimarisi
Couchbase, bellek öncelikli (memory-first) mimarisi ile yüksek performans sunan, dağıtık bir NoSQL döküman veritabanıdır. Mantıksal veri hiyerarşisi, RDBMS standartlarına yaklaşacak şekilde yeniden tasarlanmıştır.
Hiyerarşik Altyapı
Cluster (Küme): Couchbase düğümlerinin (nodes) oluşturduğu bütün.
⁠
Bucket (Kova): İlişkisel veritabanlarındaki "Veritabanı" (Database) kavramına denk gelir. Verilerin fiziksel olarak tutulduğu, bellek ve replikasyon ayarlarının yapıldığı en üst düzey mantıksal birimdir.
⁠
Scope: İlişkisel veritabanlarındaki "Şema" (Schema) kavramına eşdeğerdir. Microservis mimarilerinde her servisin kendi verisini izole etmesi için kullanılır.
⁠
Collection: İlişkisel veritabanlarındaki "Tablo" (Table) kavramına denk gelir. Benzer yapıdaki dökümanları gruplar (Örn: users collection'ı, orders collection'ı).
⁠
Document: Tablodaki bir satıra (Row) denk gelen, JSON formatındaki veri birimidir. 



## 3. Sorgulama: N1QL, İndeksleme ve Performans
N1QL (SQL for JSON)
N1QL, JSON verilerini sorgulamak için SQL'in genişletilmiş halidir. İlişkisel veritabanlarından gelen geliştiricilerin adaptasyonunu hızlandırır ve JOIN işlemlerini NoSQL dünyasında mümkün kılar.
Indexing (İndeksleme) ve Performanslı Sorgu
Couchbase'de verimli sorgular atmak için Global Secondary Index (GSI) yapısı kullanılır. İndeksler, N1QL sorgularının tüm veri setini taramasını (Full Bucket Scan) engeller.
SQL
-- Kullanıcı e-postasına göre arama yapmak için indeks oluşturmaCREATE INDEX idx_user_email ON `my_bucket`.`my_scope`.`users`(email);
 
-- Performanslı bir N1QL sorgusuSELECT id, email, fullName
FROM `my_bucket`.`my_scope`.`users`
WHERE email = 'ornek@email.com';
Performanslı bir sorgu için, WHERE koşulundaki alanların indekslenmiş olması ve sadece ihtiyaç duyulan alanların (SELECT * yerine) çekilmesi kritiktir.


## 4. Spring Data, JPA ve Kalıcılık Soyutlaması (Persistence Abstraction)
Spring Data, veritabanı işlemlerini soyutlayarak (abstraction) altyapı bağımsız bir geliştirme deneyimi sunar. İster NoSQL (Couchbase) ister RDBMS (PostgreSQL) kullanılsın, geliştirici Repository arayüzü ile muhatap olur.
Repository Pattern ve Persistence Abstraction
Domain odaklı tasarımın (DDD) kalbinde yer alan Repository deseni, veri erişim kodlarını (DAO) gizler. @Repository anotasyonu ile veri erişim katmanı standardize edilir.
Entity Lifecycle (JPA Varlık Yaşam Döngüsü)
JPA (Java Persistence API) bağlamında bir nesnenin (Entity) veritabanı ile olan ilişkisi 4 aşamadan oluşur:
New / Transient: Nesne yeni new anahtar kelimesi ile oluşturulmuştur. Henüz JPA EntityManager tarafından bilinmez, ID'si yoktur.
⁠
Managed (Kalıcı/Yönetilen): Nesne EntityManager'a eklenmiştir (persist()). Nesnedeki her değişiklik, transaction sonunda veritabanına otomatik yansır (Dirty Checking).
⁠
Detached (Koparılmış): Nesne önceden yönetiliyordur ancak EntityManager kapatılmış veya nesne detach() edilmiştir. Değişiklikler veritabanına yansımaz.
⁠
Removed (Silinmiş): Nesne silinmek üzere işaretlenmiştir (remove()). Transaction bitiminde veritabanından silinir. Custom Query (Özel Sorgular)
Spring Data'nın türettiği metodlar (örn: findByLastName) yetersiz kaldığında özel sorgular yazılır.
Java
// Spring Data JPA örneği@Query("SELECT u FROM User u WHERE u.status = :status AND u.age > :age")List<User> findActiveUsersOlderThan(@Param("status") String status, @Param("age") int age);
 
// Spring Data Couchbase örneği@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND status = $1")List<User> findCustomUsers(String status);
Criteria Query
Sorguların string olarak değil, nesne yönelimli ve tip güvenli (type-safe) olarak çalışma zamanında (dynamic) oluşturulmasını sağlar. Dinamik filtreleme ekranları (örn: e-ticaret sitelerindeki gelişmiş filtreler) için idealdir.
Java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<User> cq = cb.createQuery(User.class);
Root<User> user = cq.from(User.class);
Predicate statusPredicate = cb.equal(user.get("status"), "ACTIVE");
cq.where(statusPredicate);
List<User> result = entityManager.createQuery(cq).getResultList();


## 5. Veri Tutarlılığı (Data Consistency) ve Veri Güvenliği
Optimistic Locking (İyimser Kilitleme)
Eşzamanlı (concurrent) işlemlerin aynı veriyi bozmasını engellemek için kullanılır. Satırı fiziksel olarak kilitlemek (Pessimistic Locking) yerine, bir "sürüm" (version) kontrolü yapılır. İki kullanıcı aynı veriyi çeker, biri güncelleyip kaydettiğinde sürüm numarası artar. İkinci kullanıcı kaydetmeye çalıştığında sürüm numaraları uyuşmadığı için OptimisticLockException fırlatılır.

```Java
@Entitypublic class Product {
    @Idprivate Long id;
    private String name;
    
    @Version // Optimistic locking için JPA anotasyonu (Couchbase'de de CAS için kullanılır)private Long version;
}
```

Couchbase bu işlemi CAS (Compare and Swap) değeri ile donanımsal düzeyde yöneterek veri güvenliğini ve tutarlılığını garanti altına alır.


## 6. İlişkisel Veritabanı Tasarımı ve İlişki Yönetimi
Veritabanı "8 Maddesi" ve En Az 4'ünün Sağlanması (Normalizasyon)
Veritabanı literatüründe "sağlıklı bir tasarım için uyulması gereken kurallar" genellikle Normalizasyon Formları (Normal Forms - NF) veya E.F. Codd'un kuralları olarak geçer. Toplamda 8 civarı temel normal form (1NF, 2NF, 3NF, BCNF, 4NF, 5NF, 6NF, DKNF) bulunmaktadır.
"En az 4'ünün sağlanması gerekir" yaklaşımı endüstri standardıdır. Bir ilişkisel veritabanının anormalliklerden (update/delete/insert anomalies) kurtulması için veri modelinin sırasıyla şu 4 aşamadan geçmesi (en az Boyce-Codd Normal Form - BCNF seviyesine getirilmesi) zorunlu kabul edilir:
1. Normal Form (1NF): Her kolon atomik olmalıdır (örneğin "Telefonlar" kolonunda iki telefon numarası virgülle ayrılarak yazılamaz).
⁠
2. Normal Form (2NF): Tablodaki veri, birincil anahtara (primary key) tam bağımlı olmalıdır. Kısmi bağımlılıklar ayrılmalıdır.
⁠
3. Normal Form (3NF): Geçişli (transitive) bağımlılık olmamalıdır. Bir kolon, primary key olmayan başka bir kolona bağımlı olamaz (Örn: Zip koduna bağlı Şehir bilgisinin aynı tabloda olması kural ihlalidir).
⁠
BCNF (Boyce-Codd NF): 3NF'nin daha sıkı halidir. Tüm belirleyiciler (determinants) aday anahtar (candidate key) olmak zorundadır. Tablo Neye Göre Oluşturulur ve İterasyonlar
Veritabanı tasarımı tek seferde yapılmaz, 3 aşamalı (tur) bir iterasyondan geçer:
Kavramsal Tasarım (Conceptual - 1. Tur): Varlıklar (Entities) ve aralarındaki ilişkiler belirlenir (ER Diyagramı oluşturulur). Veri tipleri düşünülmez.
⁠
Mantıksal Tasarım (Logical - 2. Tur): Normalizasyon kuralları (yukarıdaki 4 kural) uygulanır. Yabancı anahtarlar (Foreign Keys) belirlenir.
⁠
Fiziksel Tasarım (Physical - 3. Tur): Hedef veritabanına (PostgreSQL, Oracle vs.) özgü veri tipleri, indeksler ve performans optimizasyonları kurgulanır. Neye göre Join yapılır? Normalizasyon sürecinde veri tekrarını önlemek için parçalanan ve farklı tablolara ayrılan verileri, okuma esnasında mantıksal bir bütün olarak geri birleştirmek (sorgulamak) için JOIN yapılır. Yabancı anahtarlar (Foreign Keys) kullanılarak tablolar eşleştirilir.


#### JPA'da İlişki Yönleri: Unidirectional vs Bidirectional
Unidirectional (Tek Yönlü): Sadece bir taraf diğerini bilir. Örneğin, bir Order nesnesi OrderItem listesini tutar, ancak OrderItem'ın hangi siparişe ait olduğundan haberi yoktur. Kod karmaşasını azaltır, tercih edilmelidir.
⁠
Bidirectional (Çift Yönlü): İki nesne de birbirini bilir. Order OrderItem'ı bilir, OrderItem da Order nesnesini referans olarak tutar. Bellek yönetimi ve dairesel referans (circular reference) hatalarına (özellikle JSON'a dönüştürürken) dikkat edilmelidir. Java
// Bidirectional Örnek@Entitypublic class Order {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // mappedBy, ilişkinin sahibinin OrderItem olduğunu belirtirprivate List<OrderItem> items;
}
 
@Entitypublic class OrderItem {
    @ManyToOne@JoinColumn(name = "order_id")private Order order; // Ters referans
}
Fetching Stratejileri: Lazy vs Eager
Eager Loading (Hevesli Yükleme): Bir entity çekildiğinde, ona bağlı tüm ilişkili veriler veritabanından anında (genellikle JOIN atarak) çekilir. Gereksiz veri çekilmesine ve bellek (RAM) şişmesine yol açar.
⁠
Lazy Loading (Tembel Yükleme): İlişkili veri sadece kodun içinde çağrıldığında (örn: order.getItems().size()) veritabanına bir sorgu daha atılarak çekilir. Performans için her zaman tercih edilmelidir. Ancak dikkat edilmezse ünlü "N+1 Sorgu Problemine" yol açabilir (Bunu çözmek için Custom Query ile JOIN FETCH kullanılır). 

## 7. Nesne Yönelimli Yaklaşımlar ve Kalıtım Stratejileri
Composition over Inheritance (Kalıtım Yerine Kompozisyon)
Yazılım mühendisliğinin temel kurallarından biridir. Sınıfları "IS-A" (Kalıtım) ilişkisi yerine "HAS-A" (Kompozisyon) ilişkisiyle bağlamayı önerir. JPA'da bu, bir nesneyi tablo yapmak yerine başka bir tablonun kolonları olarak gömmekle sağlanır.

```Java
@Embeddable // Ayrı tablo olmazpublic class Address {
    private String street;
    private String city;
}
 
@Entitypublic class User {
    @Id private Long id;
    
    @Embedded // Address nesnesinin kolonları (street, city) User tablosuna kolon olarak eklenirprivate Address address;
}
```

## 8. JPA Kalıtım Mimarileri (Inheritance Strategies)
Eğer nesneler arasında mutlaka bir kalıtım varsa (Örn: Payment üst sınıfı; CreditCardPayment ve CryptoPayment alt sınıfları), bunu ilişkisel veritabanına 3 şekilde yansıtabiliriz:
Single Table Inheritance (Tek Tablo Kalıtımı - Önerilen Performans Seçeneği): Tüm alt ve üst sınıflar veritabanında tek bir devasa tabloya yazılır. Hangi satırın hangi sınıfa ait olduğunu anlamak için bir DTYPE (Discriminator) kolonu eklenir. Sorgular çok hızlıdır (JOIN yoktur), ancak kullanılmayan alt sınıf kolonları veritabanında NULL olarak kalmak zorundadır (Veri bütünlüğü / Not Null kısıtlamaları zayıflar).
⁠
Joined Table Inheritance (Birleştirilmiş Tablo Kalıtımı - Önerilen Normalizasyon Seçeneği):
Üst sınıfın (Payment) kendi tablosu olur. Alt sınıfların (CreditCard) sadece kendilerine has kolonlarını içeren kendi tabloları olur. Bir alt sınıf çekilmek istendiğinde veritabanında JOIN atılması gerekir. Normalizasyona %100 uygundur, ancak okuma performansı yavaştır.
 
Table per Class (Sınıf Başına Tablo - ÖNERİLMEZ):
Sadece alt sınıflar için (CreditCardPayment, CryptoPayment) tablolar oluşturulur ve üst sınıftaki kolonlar (amount, date) bu alt tabloların hepsine tekrar tekrar kopyalanır.
 
Neden Önerilmez? Bir Payment arandığında polymorphik sorgu atılır. Veritabanı tüm alt tabloları UNION (birleştirme) işlemi ile bir araya getirip taramak zorunda kalır. Bu performans açısından bir felakettir ve Yabancı Anahtar (Foreign Key) bütünlüğünü sağlamak imkansızlaşır. Kurumsal projelerde genellikle anti-pattern (kaçınılması gereken yöntem) olarak kabul edilir.
