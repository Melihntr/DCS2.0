# Spring Data Dokumantasyonu

## 1. Giriş

Modern yazılım sistemlerinde veri miktarının, çeşitliliğinin ve erişim hızının artması; geleneksel ilişkisel veritabanı sistemlerinin (RDBMS) bazı senaryolarda yetersiz kalmasına neden olmuştur. Özellikle büyük ölçekli dağıtık sistemler, gerçek zamanlı uygulamalar, IoT çözümleri, sosyal medya platformları ve yüksek trafikli web uygulamaları; esnek, yatay ölçeklenebilir ve düşük gecikmeli veri yönetimi ihtiyaçlarını ortaya çıkarmıştır. Bu ihtiyaçların sonucu olarak NoSQL veritabanı sistemleri geliştirilmiştir.

Bu dokümanda NoSQL kavramları, Document Database yaklaşımı, veri modelleme prensipleri ve Couchbase altyapısının temel bileşenleri detaylı biçimde ele alınacaktır. Ayrıca akademik bütünlüğü artırmak amacıyla veri tutarlılığı, indeksleme, dağıtık mimari ve performans optimizasyonu gibi önemli ek başlıklara da yer verilmiştir.


### 1.1 NoSQL Kavramı

#### NoSQL Nedir?

NoSQL (“Not Only SQL”) veritabanları, ilişkisel olmayan veri depolama yaklaşımlarını ifade eden modern veritabanı sistemleridir. NoSQL sistemleri; yüksek hacimli, yarı yapılandırılmış veya yapılandırılmamış verilerin yönetimi için geliştirilmiştir.

Geleneksel ilişkisel veritabanlarından farklı olarak:

Katı şema zorunluluğu bulunmaz.
Yatay ölçeklenme desteklenir.
Dağıtık sistem mimarilerine uygundur.
Büyük veri işleme süreçlerinde yüksek performans sağlar.

NoSQL sistemleri genellikle aşağıdaki problemleri çözmek amacıyla kullanılır:

Büyük veri hacmi (Big Data)
Yüksek eşzamanlı kullanıcı sayısı
Düşük gecikme süresi ihtiyacı
Esnek veri yapıları
Mikroservis mimarileri


### 1.2 NoSQL Veritabanı Türleri

NoSQL sistemleri dört temel kategoriye ayrılır:


### 1.3 Key-Value Database

Veriler anahtar-değer (key-value) çiftleri şeklinde tutulur.

Örnek sistemler:

Redis
Riak
Amazon DynamoDB

Avantajları:

Çok hızlı erişim
Basit veri yapısı
Cache sistemleri için ideal kullanım


### 1.4 Document Database

Veriler JSON benzeri dokümanlar halinde tutulur.

Örnek sistemler:

Couchbase
MongoDB
CouchDB

Avantajları:

Esnek şema yapısı
İç içe veri modelleri
API tabanlı uygulamalara uygunluk


### 1.5 Column Family Database

Veriler sütun aileleri şeklinde organize edilir.

Örnek sistemler:

Cassandra
HBase

Avantajları:

Büyük veri analitiği
Yüksek yazma performansı


### 1.6 Graph Database

Veriler düğüm (node) ve bağlantılar (edge) şeklinde tutulur.

Örnek sistemler:

Neo4j
ArangoDB

Avantajları:

İlişkisel bağlantıların hızlı analizi
Sosyal ağ uygulamaları

### 1.7 Document Database Yaklaşımı

#### Document-Oriented Veri Yapısı

Document Database sistemlerinde veriler genellikle JSON formatına benzer dokümanlar olarak tutulur.

Örnek kullanıcı dokümanı:

{
  "id": "user_101",
  "name": "Ahmet Yılmaz",
  "email": "ahmet@example.com",
  "addresses": [
    {
      "city": "Ankara",
      "district": "Çankaya"
    }
  ]
}

Bu yaklaşım sayesinde:

İç içe veri yapıları desteklenir.
İlişkisel JOIN işlemlerine olan ihtiyaç azalır.
Veri okuma performansı artar.


### 1.8 Schema Flexibility (Şema Esnekliği)

Document Database sistemlerinde her dokümanın aynı alanlara sahip olması zorunlu değildir.

Örneğin:

{
  "name": "Ali"
}

ve

{
  "name": "Ayşe",
  "phone": "5551234567"
}

aynı koleksiyon içerisinde bulunabilir.

Bu özellik:

Agile geliştirme süreçlerini destekler.
Veri modelinde hızlı değişiklik yapılmasını sağlar.
Mikroservis mimarileri için avantaj oluşturur.


### 1.9 NoSQL Veri Modelleme
#### Veri Modelleme Kavramı

Veri modelleme; verilerin sistem içerisinde nasıl organize edileceğini belirleyen süreçtir.

İlişkisel sistemlerde normalizasyon ön plandayken, NoSQL sistemlerinde erişim örüntüleri (access patterns) önceliklidir.

NoSQL modellemede temel amaç:

En az sorguyla veri erişimi sağlamak
JOIN ihtiyacını azaltmak
Okuma performansını artırmak
4.2 Embedding ve Referencing Yaklaşımları
4.2.1 Embedding

İlişkili verilerin aynı doküman içerisine gömülmesi yaklaşımıdır.

Örnek:

{
  "customer": "Mehmet",
  "orders": [
    {
      "product": "Laptop",
      "price": 45000
    }
  ]
}

Avantajları:

Tek sorguda veri erişimi
Yüksek okuma performansı

Dezavantajları:

Veri tekrarına neden olabilir
Büyük doküman boyutları oluşabilir


### 1.10 Referencing

İlişkili verilerin ayrı dokümanlarda tutulmasıdır.

Örnek:

{
  "customerId": "cust_1001",
  "orderId": "ord_2001"
}

Avantajları:

Veri tekrarını azaltır
Güncelleme maliyetini düşürür

Dezavantajları:

Ek sorgu ihtiyacı doğabilir


#### 1.10.1 Denormalization

NoSQL sistemlerde performans amacıyla veri tekrarına bilinçli şekilde izin verilmesine denormalization denir.

Amaç:

Okuma işlemlerini hızlandırmak
Karmaşık JOIN işlemlerini ortadan kaldırmak

Bu yaklaşım özellikle:

E-ticaret sistemleri
Sosyal medya uygulamaları
Gerçek zamanlı veri erişimi gereken platformlar

için önemlidir.


## 2. Couchbase Temelleri
#### Couchbase Nedir?

Couchbase, dağıtık mimariye sahip, yüksek performanslı bir NoSQL Document Database sistemidir.

Temel özellikleri:

JSON document desteği
Bellek öncelikli mimari
Yatay ölçeklenebilirlik
Yüksek erişilebilirlik
N1QL sorgu dili desteği

Couchbase özellikle:

Gerçek zamanlı uygulamalar
Mobil uygulamalar
Cache + Database hibrit sistemleri

için yaygın şekilde kullanılmaktadır.


### 2.2Couchbase Mimari Yapısı
#### 2.2.1 Bucket

Bucket, Couchbase içerisindeki en üst veri konteyneridir.

Görevleri:

Veri depolama
RAM yönetimi
Replikasyon kontrolü

Bucket türleri:

Couchbase Bucket
Ephemeral Bucket
Memcached Bucket

Bucket yapısı ilişkisel sistemlerdeki “database” kavramına benzetilebilir.


### 2.3 Scope

Scope, bucket altında mantıksal gruplama sağlar.

Amaç:

Veri organizasyonu
Mikroservis ayrımı
Yetkilendirme kolaylığı

Örnek:

Bucket: ecommerce

  Scope: users
  Scope: orders
  Scope: products


### 2.4 Collection

Collection, dokümanların tutulduğu en küçük mantıksal veri grubudur.

İlişkisel sistemlerdeki tablo kavramına benzetilebilir.

Örnek yapı:

Bucket: ecommerce
   Scope: sales
      Collection: invoices
      Collection: payments

Avantajları:

Daha iyi veri organizasyonu
Yetki yönetimi kolaylığı
Multi-tenant mimari desteği


### 2.5 Veri Tutarlılığı ve CAP Teoremi
#### CAP Teoremi

Dağıtık sistemlerde aynı anda aşağıdaki üç özelliğin tamamı garanti edilemez:

Consistency (Tutarlılık)
Availability (Erişilebilirlik)
Partition Tolerance (Ağ bölünmesine dayanıklılık)

CAP

NoSQL sistemleri genellikle:

Availability
Partition Tolerance

özelliklerine öncelik verir.

### 2.7 Eventual Consistency

Eventual Consistency yaklaşımında sistemdeki tüm düğümler zamanla aynı veriye ulaşır.

Avantajları:

Daha yüksek performans
Daha iyi ölçeklenebilirlik

Dezavantajları:

Kısa süreli veri tutarsızlıkları oluşabilir


### 2.8 İndeksleme (Indexing)
#### İndeks Kavramı

İndeksler, veri erişimini hızlandırmak için kullanılan yapılardır.

Couchbase’de:

Primary Index
Secondary Index
Composite Index

gibi farklı indeks türleri bulunur.

### 2.9 N1QL ve İndeks İlişkisi

Couchbase’in SQL benzeri sorgu dili olan N1QL, indekslerle birlikte yüksek performans sağlar.

Örnek sorgu:

SELECT name, email
FROM users
WHERE city = "Ankara";

İlgili alan üzerinde indeks bulunmaması durumunda:

Full scan işlemi oluşur
Performans ciddi şekilde düşebilir


### 2.10 Dağıtık Mimari ve Replikasyon
#### Sharding

Verilerin farklı sunucular arasında bölünmesine sharding denir.

Amaç:

Yük dağıtımı
Ölçeklenebilirlik
Performans artışı

### 2.11 Replication

Replication, verilerin birden fazla kopyasının tutulmasıdır.

Avantajları:

Veri güvenliği
Yüksek erişilebilirlik
Hata toleransı

Couchbase otomatik replikasyon desteği sunmaktadır.


### 2.12 Performans ve Ölçeklenebilirlik
#### Horizontal Scaling

Yeni sunucular eklenerek sistem kapasitesinin artırılmasına horizontal scaling denir.

NoSQL sistemlerin temel avantajlarından biridir.


#### Caching Mekanizması

Couchbase bellek öncelikli çalıştığı için:

Düşük gecikme süresi sağlar
Sık erişilen verileri RAM üzerinde tutar

Bu yapı özellikle yüksek trafikli sistemlerde büyük avantaj sağlar.


### 2.13 NoSQL Sistemlerin Avantaj ve Dezavantajları

#### Avantajlar

Yüksek performans

Esnek veri modeli

Yatay ölçeklenebilirlik

Büyük veri desteği

Dağıtık mimari uyumluluğu

#### Dezavantajlar
Veri tutarlılığı problemleri

JOIN desteğinin sınırlı olması

Karmaşık transaction yönetimi

Standart eksikliği


#### 12. Sonuç

NoSQL veritabanları, modern yazılım sistemlerinin ölçeklenebilirlik ve performans ihtiyaçlarını karşılamak amacıyla geliştirilmiş önemli veri yönetim çözümleridir. Özellikle Document Database yaklaşımı; esnek veri yapıları ve yüksek erişim performansı sayesinde günümüzde yaygın şekilde kullanılmaktadır.

Couchbase ise Bucket, Scope ve Collection mimarisiyle büyük ölçekli dağıtık sistemlerde etkili veri yönetimi sunmaktadır. Ayrıca indeksleme, replikasyon, sharding ve eventual consistency gibi kavramlar; sistem performansı ve erişilebilirliği açısından kritik öneme sahiptir.

Bu nedenle modern backend mimarilerinde NoSQL sistemlerinin anlaşılması; ölçeklenebilir, esnek ve yüksek performanslı uygulamalar geliştirmek açısından önemli bir gereklilik haline gelmiştir.
