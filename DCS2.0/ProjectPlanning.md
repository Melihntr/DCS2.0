# DispatchSim – Proje Öncesi Analiz Dokümanı

## 1. Proje Özeti

DispatchSim, bir şehirdeki acil çağrı merkezini simüle eden, backend tarafında concurrency ve thread yönetimini gerçek zamanlı olarak çalıştıran ve frontend tarafında bu süreçleri görselleştiren bir sistemdir.

Proje aşağıdaki teknik konuları gerçek bir operasyonel senaryo üzerinden göstermeyi amaçlamaktadır:

- Thread lifecycle
- Concurrency
- Parallelism
- Blocking vs Non-blocking
- Garbage Collector (GC) davranışı

Backend teknolojisi: **Spring Boot**  
Frontend teknolojisi: **React + React Flow**  
Veritabanı: **H2 (in-memory database)**

---

## 2. Problem Tanımı

Concurrency ve JVM davranışı genellikle:

- Log çıktıları üzerinden
- Console print’leriyle
- Soyut teorik anlatımlarla

öğretilmektedir.

Bu yaklaşım:

- Thread lifecycle’ı görselleştirmez
- Blocking etkisini sezdirmez
- GC'nin latency üzerindeki etkisini somutlaştırmaz
- Parallelism farkını deneyimletmez

Bu proje, bu eksikliği görsel ve interaktif bir sistem ile gidermeyi hedefler.

---

## 3. Senaryo Tanımı (Gerçek Dünya Bağlamı)

Sistem bir şehirdeki 112 Acil Çağrı Merkezi’nin simülasyonudur.

Vatandaşlar sisteme acil çağrı bırakır.

Her çağrı:

- Öncelik seviyesi içerir (LOW, MEDIUM, HIGH, CRITICAL)
- İşlem süresi içerir
- CPU-bound veya IO-bound olabilir
- Bellek tüketebilir (memory allocation)

Çağrılar bir thread pool tarafından işlenir.

Sistem gerçek zamanlı olarak:

- Thread state değişimlerini
- Kuyruk durumunu
- Çalışan task’leri
- Memory kullanımını
- GC event’lerini

görselleştirir.

---

## 4. Amaç ve Hedefler

### 4.1 Fonksiyonel Hedefler

- Gerçek zamanlı task oluşturma
- Öncelik bazlı kuyruk yönetimi
- Thread lifecycle görselleştirme
- GC olaylarını izleme
- CPU-bound ve IO-bound işlerin farkını gösterme
- Execution time ölçümü
- Timeout ve cancellation senaryoları

### 4.2 Akademik / Teknik Hedefler

- Concurrency vs Parallelism farkını deneyimletmek
- Thread state geçişlerini gözlemlemek
- Synchronized ve unsynchronized senaryoları karşılaştırmak
- Thread starvation senaryosu üretmek
- Deadlock simülasyonu yapmak
- GC'nin latency üzerindeki etkisini göstermek

---

## 5. Kapsam

### Dahil Olanlar

- Spring Boot backend
- Custom ThreadPoolExecutor
- PriorityBlockingQueue
- JVM Memory & GC monitoring (MXBean)
- WebSocket ile canlı veri akışı
- React Flow ile görselleştirme
- H2 in-memory relational database

### Dahil Olmayanlar

- Gerçek 3rd party entegrasyon
- Gerçek network IO
- Production dağıtımı
- Gerçek dağıtık sistem mimarisi

---

## 6. Sistem Mimarisi

### 6.1 Backend – Spring Boot

Temel bileşenler:

- Task Service
- Custom Executor Service
- Queue Manager
- GC Monitor Service
- Metrics Collector
- WebSocket Event Publisher

Thread yönetimi için:

- ThreadPoolExecutor
- PriorityBlockingQueue
- CompletableFuture (async senaryo için)

Memory ve GC metrikleri için:

- ManagementFactory
- MemoryMXBean
- GarbageCollectorMXBean

---

### 6.2 Frontend – React + React Flow

Görselleştirilecek bileşenler:

- Waiting Queue
- Thread Nodes
- Running Tasks
- Completed Tasks
- Memory Usage Bar
- GC Event Animation

Thread state renk kodlaması:

- NEW → Gri
- RUNNABLE → Yeşil
- BLOCKED → Kırmızı
- WAITING → Turuncu
- TERMINATED → Siyah

---

## 7. Veri Modeli (H2 - Minimal DB)

Sistem H2 in-memory database kullanacaktır.

Amaç kalıcı runtime log tutmak ve analiz yapılabilmesini sağlamaktır.

### Task Entity

- id
- type (CPU / IO)
- priority
- createdAt
- startedAt
- finishedAt
- executionTime
- status (SUCCESS / TIMEOUT / FAILED)
- memoryAllocatedSnapshot

Not:
Veritabanı concurrency mekanizmasının ana parçası değildir. Sadece log ve analiz amaçlıdır.

---

## 8. Teknik Risk Analizi

| Risk | Açıklama | Çözüm |
|------|----------|--------|
| WebSocket yoğunluğu | Çok sık event publish edilmesi | Event batching |
| UI performansı | Fazla node animasyonu | Virtualized rendering |
| Thread state takibi | Java’da state geçişini gerçek zamanlı yakalama zorluğu | Custom executor wrapper |
| GC event gecikmesi | GC event polling interval | Scheduled monitoring |

---

## 9. Teknik Derinlik Noktaları

Bu proje aşağıdaki konuları pratikte gösterir:

- Thread pool sizing stratejisi
- CPU-bound vs IO-bound workload farkı
- Blocking etkisi
- Backpressure
- Priority scheduling
- GC pause latency etkisi
- Heap doluluğu ile performans ilişkisi
- Contention ve lock etkisi

---

## 10. Genişletilebilirlik

İleri aşamada:

- Deadlock simülasyonu
- Starvation senaryosu
- Virtual Threads (Project Loom) karşılaştırması
- Executor strateji karşılaştırması
- Metrics dashboard entegrasyonu
- Multi-core scaling analizi

---

## 11. Beklenen Çıktı

Proje sonunda sistem:

- JVM davranışını görsel olarak anlatabilen
- Akademik sunumlarda kullanılabilir
- Thread lifecycle’ı canlı gösterebilen
- Concurrency kavramlarını deneyimsel hale getiren
- GC etkisini somutlaştıran

bir eğitim ve analiz platformu olacaktır.

---

## 12. Başarı Kriterleri

- Thread state değişimlerinin doğru yansıtılması
- Concurrency senaryolarının gözlemlenebilir olması
- GC event’lerinin görsel olarak anlaşılır sunulması
- Execution time farklarının ölçülebilir olması
- Sistem stabil ve deterministik çalışması
