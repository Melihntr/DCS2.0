# SOLID PRINCIPLES
## 1. CLEAN CODE (TEMİZ KOD) NEDİR?

Usta yazılımcı Robert C. Martin’in kitabı “**Clean Code**” şu cümlelerle başlar: “**Bu kitabı iki sebepten ötürü okursunuz, ilki yazılımcısınız, ikincisiyse daha iyi bir yazılımcı olmak istiyorsunuz. Güzel, çünkü iyi yazılımcılara ihtiyacımız var**.”

Bu cümleden de anlaşılacağı üzerine clean code/ temiz kod bir yazılım projesinin geleceği açısından kilit noktasıdır. Clean code/ temiz kodun tercih edilmediği yazılım geliştirme aşamalarının projenin ileriki adımları için bir tehdit olduğunu söyleyebiliriz. Çünkü yazdığımız kod gün geçtikçe karmaşıklaşır, okunamaz ve işin içinden çıkılmaz bir hale gelir ki bu durum projenin iptaline kadar gidebilir. Yani hüsranka sonuçlanabilir. Buna verilebilecek örneklerden birisi;

80’lerde bir şirket müthiş ve popüler bir uygulama yazmıştır. Fakat bir süre sonra yeni sürüm çıkma dönemleri (release) uzamaya başlar. Bir sonraki sürümde hatalar ortaya çıkar, yüklenme süresi artar ve çökmeler artar. Zaten sonrasında bu sirket piyasadan çekilir. Olaydan 20 yıl sonra şirketin ilk çalışanlarından biriyle karşılaşılır ve o zaman ne olduğu ve şirketin neden bir anda ortadan kaybolduğu sorulur. Cevap ise şudur: “Ürünü markete erkenden sürebilmek için çok fazla acele etmişlerdi ve kodda çok büyük bir kargaşaya sebep olmuşlardı. Daha fazla özellik ekledikçe kod daha kötü bir hal almış ve o kadar kötü bir hale gelmişti ki artık kodu yönetemiyorlardı. Böylece kötü, kontrol edilemez kod, şirketin kapanmasına sebep olmuştu.” Peki bu kod ne oldu da bu denli bir hızla kötü koda dönüştü? Bunun için pek çok sebep vardır. Bunlardan bazıları; gereksinimlerin çok fazla değişmesi, teslim tarihlerinin çok sık olması ve şimdi yazalım sonra refactor ederiz düşüncesidir. Fakat “**Later equals never (Sonra asla demektir)**”.

**CLEAN CODE (TEMİZ KOD)**

![](https://miro.medium.com/v2/resize:fit:194/1*UIFOgROMPJge-D4lZKnrjg.jpeg)

Yazılan kodların spagetti kod dediğimiz karmaşık ve içinden çıkması kolay olmayan ve tekrar edilen kodlar yerine neyin nerede olduğunu kolayca ayırt edebildiğimiz modüler kodlar hayat kurtarır ve yazdığımız kodun daha önce kodumuzu hiç görmeyen bir developer tarafından kolayca anlaşılabilir olması önemlidir. Bu noktada clean koddan bahsetmeliyiz. Clean kod, yazılan kodun anlaşılması ve üzerinde geliştirmeler yapılması kolay olan okuyucu odaklı bir biçimdir.

“**Programların basit görünmesini sağlayan dil değildir, dilin basit görünmesini sağlayan yazılımcıdır.”** Rober Cecil Martin’in Clean Code kitabından..

**a.**  **Clean code Özellikleri (Clean code features):**

1- Basit ve açıktır. Yazılımcının niyetini gizlemez.

2- İyi yazılmış bir düz yazı gibidir. Okunabilirlik optimale yakındır.

3- Tüm istisnai durumlar ele alınmıştır.

4- Çok az bağlılığı vardır ve temiz bir API sağlar.

5- Temiz kodda bir problemin çözülebilmesi için tek bir alternatif vardır.

6- Kolay anlaşılabilir ve geliştirilebilir.

7- En önemlisi de temiz kod her zaman ona değer veren biri tarafıından yazılmış gibi görünür.


![](https://miro.medium.com/v2/resize:fit:700/1*Rm4HjbWYE65r6R8lsx3p2g.png)

**b.**  **Clean Code Principles (Temiz Kod Prensipleri):**

Kodlama yaparken okunabilirlik (readability), değiştirilebilirlik (changeability), genişletilebilirlik (extensibility) ve sürdürülebilirlik (maintability) koşullarının sağlanması o kodun anlaşılırlığını arttırır. Kod yazarken bu koşulları sağlamak adına birtakım standartlar vardır:

**1.1**  **İSİMLENDİRME (NAMING)**

Yazılım alanında değişkenler, sabitler, fonksiyonlar, sınıflar, paketler gibi tüm nesnelerin isimleri vardır. Clean code yazabilmemiz için clean code prensiplerine uygun olarak açık ve anlaşılır kodlar yazmalıyız. Doğru isimlendirme, yorum satırlarına ve detaylı kod incelemelerine gerek duymadan ilgili kodun anlaşılmasını sağlayacaktır. Tüm nesnelerin isimlendirilirken belirlenmiş bazı kuralları vardır.


![](https://miro.medium.com/v2/resize:fit:700/1*gjFjJhqFgquD2GlX90Q3DQ.jpeg)

**a.**  **Değişken ve Sabitlerin İsimlendirilmesi:**

Variables (değişkenler) ve constants (sabitler) gibi içerisinde veri barındıran yapılar isimlendirilirken içerisinde barındırdığı verinin ne olduğuyla ilgili net ve açıklayıcı sıfatlarla isim ön eklerinden oluşan kısa ifadeler kullanılmalıdır. İsimlendirme yapılırken daha sonra okunmasını zorlaştıracak gereksiz kısaltmalardan kaçınılmalı ve telaffuz edilmesi kolay isimler kullanılmalıdır.


![](https://miro.medium.com/v2/resize:fit:700/1*h_vj_XbYpcs-WknJFWd1Cw.png)

Mesela burada gün cinsinden geçen süre int tipinde bir değişkenle tutulmak istenmiş fakat “d” harfi, günle ya da süreyle ilgili en ufak bir ipucu bile vermiyor. Ayrıca doküman içerisinde “d” değişkenini aradığımızda sonucun birçok satırda bulunma olasılığı vardır bu da aranabilirlik özelliğinin azalmasına neden olur.


![](https://miro.medium.com/v2/resize:fit:700/1*EmlZqGxbvItj_O7MAwtEGA.png)

Fakat burada koda baktığımızda yapılan her bir isimlendirme gayet açık ve yazılımcının niyetini belli ettiği için okunabilirliği de maximumdur ve ekibe (team) sonradan katılan bir developer bu kodu kolaylıkla anlayabilir ve isterse zorlanmadan geliştirebilir de.

Ayrıca isimlendirme yapılırken belli başlı kod standartları vardır. Bunlar Camel Case ve Pascal Case’dir.

·  **Camel Case:** İsimdeki ilk kelimenin ilk harfi her zaman küçük harfle başlar, ikinci kelimenin baş harfi büyüktür.


![](https://miro.medium.com/v2/resize:fit:700/1*s95_9X6F_v5eOcpISVLUkw.png)

·  **Pascal Case:** İsimdeki tüm kelimlerin ilk harfleri büyük harfle başlar.


![](https://miro.medium.com/v2/resize:fit:700/1*g1BNAb0ENGujcHspN9Uk-A.png)

Bir diğer örnek;


![](https://miro.medium.com/v2/resize:fit:700/1*7AmuTyvyKsgKS0ZesxplFQ.png)

Burada koda bakıldığında da akılda canlanan tek şey;

1- “realList” ne içeriyor?

2- “list1” ne için kullanılıyor?

3- “70” değeri ne için önemli?

Fakat yazdığımız kodlar neye karşılık geldiklerini açık ve net bir şekilde belli etmelidirler. Bu yüzden bu kodu şu şekilde yeniden düzenleyebiliriz  **refactor (yeniden yapılandırma).**


![](https://miro.medium.com/v2/resize:fit:700/1*AG1xdE9uUGqbyn9j4qKpdw.png)

Burada ise “realList” dizisinin aslında öğrenci notlarını tuttuğunu, 70’in geçme notu olduğunu ve 70’e eşit veya daha yüksek alan notların ise “temporaryNotes” adlı dizide tutulduğunu rahat bir şekilde anlayabiliyorum.

· Kötü isim örneklerinden biri de küçük “l” ve büyük “O” kullanmaktır. Çünkü küçük “l”, 1’e; büyük “O”, 0’a benzer ve kodda karmaşa yaratabilir.


![](https://miro.medium.com/v2/resize:fit:700/1*mLj2j5b8mgbEGUyc0H4zIQ.png)

· Kısaltma kullanmaktan olabildiğince kaçınmalıyız. Çünkü kısaltmalar kodumuzda kullandığımız değişkenlerin işlevini tam olarak yansıtamayabilir. Fakat bu durumun istisnası döngülerde görülebilir. Döngülerde alışılagelmiş bir durum olaral döngü değişkenleri tek karakterden oluşur.

![](https://miro.medium.com/v2/resize:fit:635/1*NEA8TooXTrzg-e1S4gjaaw.png)

· İsimlendirme yaparken birbirine yakın çağrışımlı kelimeler kullanmaktan kaçınmalıyız çünkü aynı scope(kapsam) içerisinde belirsizlik yaratabilir. Mesela aşağıdaki kodda hangi methodu kullanacağımız konusunda kafa karışıklığı yaşarız.


![](https://miro.medium.com/v2/resize:fit:700/1*qUW09_UnknXFcQxvBsqQgA.png)

**b.**  **Fonksiyonların İsimlendirilmesi:**

Fonksiyonlar isimlendirilirken tutarlı olunmalıdır. Örnek olarak veri getirmek için “get” ön eki kullanılıyorsa başka yerde “fetch” kullanılması tutarlılığa aykırıdır. Aynı konseptteki işler için aynı ön ekler getirilmelidir.

![](https://miro.medium.com/v2/resize:fit:515/1*LwKIFaMhi6xKqi2ObO1XQg.png)

Mesela bu örnekte aynı konseptte yapılan işler için farklı eylemler kullanılmış. Bu clean code prensiplerine aykırı bir durumdur.


![](https://miro.medium.com/v2/resize:fit:700/1*chb8mEquwc3t31yzsMC-Fg.png)

Fakat burada iki method için de aynı konseptte eylemler, ön ekler kullanırsak kodumuzu “refactor” ederek okunurluğunu artırabiliriz.

· Method, fonksiyonlar isimlendirilirken fiiller kullanılmalıdır. Çünkü method ve fonksiyonlar belli işlevleri yerine getiren kod parçacıklarıdır. Sınıfları (class) isimlendirirken ise isimler veya isim tamlamaları kullanılmalıdır.

![](https://miro.medium.com/v2/resize:fit:539/1*YjuU4FH4D4VJ5HlhXfYpNQ.png)

**1.2**  **KOŞULLAR (CONDITIONS):**

Yazdığımız kodun hiç koşulu ya da kontol akışı olmasaydı dallanma ve zıplamalar olmazdı böylece kodumuzun okunması daha kolay olurdu. Fakat böyle bir şey mümkün olmadığı için koşullarımızı olabildiğince doğal bir şekilde kullanmalıyız ki okuyan kişi kodumuzu tekrar tekrar okumak zorunda kalmasın.


· Koşul cümlelerinde koşul kullanmak kodu okuyanlar için kafa karışıklığına sebep olabilir. Bu yüzden elimizden geldiğince pozitif anlamlı koşullar üzerinde ilerlemeliyiz.

Mesela aşağıdaki koda baktığımızda çoğu yazılımcıya ikinci koşul daha okunabilir gelecektir. Çünkü konuşma dilindeki doğallık, programlama dillerinde de geçerlidir. Çünkü yaş sınırı öğrenci yaşından küçükse demeyiz, öğrenci yaşı yaş sınırından büyükse deriz.


![](https://miro.medium.com/v2/resize:fit:700/1*FiGefFrnVcp8087C6TzRmA.png)

· Boolean bir değeri condition içerisinde tekrar “true” ya da “false”a eşitlemek gereksiz bir kalabalık yaratacaktır. Bu yüzden şu kullanım tercih edilebilir:


![](https://miro.medium.com/v2/resize:fit:700/1*R8hpeH9FJn2RhI94WieDoQ.png)

· Bir grup seçenek arasından seçim yapacaksak bunu static olarak vermektense enumlara bağlayıp kullanmak daha yerinde olacaktır. Böylece kodda ilgili bir opsiyonu değiştirmek istediğimizde o opsiyonun kullanıldığı yerleri tek tek bulup değiştirmek yerine ilgili enum opsiyonunu değiştirmemiz yeterli olacaktır.


![](https://miro.medium.com/v2/resize:fit:700/1*zPz3BJ9ETLQTSJfDWy8BCg.png)

· Üçlü (Ternary) operatörü 4–5 satırda yazdığımız kodu daha az hatta tek satırda yazmamızı sağlar. Fakat okunabilirlik açısından yazılımcılar arasında farklılık gösterebilir.


![](https://miro.medium.com/v2/resize:fit:700/1*s8SffK9RczhXTmceQSmklQ.png)

Yukarıdaki kodda çok kısa bir işlem 11 satırda yazılmıştır ve okuması zaman alabilir.


![](https://miro.medium.com/v2/resize:fit:700/1*d73FvOzpEy3q10p0w5Oh_A.png)

Yukarıdaki kodda ise aynı işlem tek satırda yazılmıştır.

· Kısa devre mantığında ise her iki koşulu da kontrol etmek istediğimizde “&” operatörü kullanabiliriz. Yalnızca tek bir koşulun doğruluğunun yeterli olması durumunda ise “||” operatörünü kullanabiliriz.


![](https://miro.medium.com/v2/resize:fit:700/1*7CR2BiaFTCMdUK96PZ11Ug.png)

· Validasyonları iç içe “if” kullanarak yapmak çoğu yazılımcının düştüğü bir yazılım yanlışıdır. Bu şekilde bir validasyon zincirinin okunması ve aynı zamanda hangi “if”in hangi “else”e denk geldiğinin anlaşılması zordur. Bunun yerine “if”leri alt alta sıralayıp ilk yakalanan validasyondan itibaren kodu return etmek daha doğrudur.


![](https://miro.medium.com/v2/resize:fit:700/1*RES19Ox08uo1pOjCjLUa9g.png)

**1.3**  **DÖNGÜLER (LOOPS)**

· Çoğu kodlama dilinde yaygın olarak kullanılan döngüler for, for..each ve while’dır . Ancaj bunların yanı sıra do-while döngüsünden de bahsedebiliriz. Do-while döngüsü, belirtilen koşul doğru ya da yanlış olsun bir kez kesinlikle calışır. Yani ilk önce kod bloğu çalışır sonra koşul kontrol edilir. Bu yüzden clean code yazmak isteyen yazılımcıların çok da tercih ettiği bir yöntem değildir. Do-while’ın muadili olarak for, for…each veya while döngülerinin kullanılması kodun hem organizasyonu hem de performansı açısından daha etkilidir.

![](https://miro.medium.com/v2/resize:fit:654/1*P6IvC9YzxDkX3nQn-nlwxw.png)

Yukarıdaki kodda koşul sağlanmamasına rağmen sonuç ekrana “16.5” olarak yazdırılacaktır.

![](https://miro.medium.com/v2/resize:fit:608/1*3bntMwn_BLkfQ3YeLTIPYg.png)

Yukarıdaki kodda ilk önce koşul kontrol edilir ve koşul sağlanmadığı için ekrana sonuç yazdırılmaz. Bu yüzden do-while döngülerinin kullanımı kodda yanlış sonuçlar verebilir.

**1.4**  **YORUM SATIRI (COMMENT LINE)**

Yorum satırlarını kodda kendimizi ifade edebilmek için kullanırız. Fakat kodumuzdaki yorum satırları her zaman kusurlarımızdır. Çünkü onlarsız ne yapacağımızı bilmez ve kötü kodlarımızı perdelemek için kullanırız. Kötü ve ne yaptığı belli olmayan kod parçacıklarını açıklamak için yorum satırı yazmaya yöneliriz. Fakat bu kodumuz için çok tehlikelidir. Çünkü yazdığımız kodlar değişir, gelişir ancak yorum satırları maintain edilmez (düzeltilmez). Bu yüzden kodumuz yorum satırlarına ihtiyaç duymadan kendi kendini açıklayabilmelidir. Yazılımcının niyeti yorum satırları olmadan da anlaşılabilmelidir.

· Ayrıca zombie kod denilen kullanılmayan ve yorum satırları içerisine alınan kod blokları da kafa karışıklığına yol açar ve okunurluğu azaltır. Bu yüzden zombie kod kullanımından da kaçınılmalıdır.


![](https://miro.medium.com/v2/resize:fit:700/1*uhFPoXCwfG6hqIhuWCXKLQ.png)

Yukarıdaki kodda yorum satırı içinde bir sürü zombie kod bulunmaktadır ve bu yüzden kod karmaşık görünmektedir.

**1.5**  **FONKSİYONLAR (FUNCTIONS)**

![](https://miro.medium.com/v2/resize:fit:275/1*eT3-rBdbTfvukTcfab0xVg.png)

Dry (Don’t repeat yourself), çoğu yazılımcının duyduğu bir kodlama prensibidir. Bir fonksiyonelliği kodun birden fazla kısmında tekrar edecek şekilde yazmışsak ve daha da kullanmamız gerekiyorsa kod tekrarından kaçınmak için bu kod parçacığını metotlaştırmalıyız.

Örneğin aşağıdaki kodda 3 farklı yerde isim değişkeninin de içinde olduğu bir cümle yazdırılmak isteniyor. Fakat bu 3 ile sınırlı kalmak zorunda değil daha karmaşık , binlerce satır kod yazdığımızda bu durum başımızı ağrıtabilir. Bunun “Greet” fonksiyonu oluşturarak her seferinde bu fonksiyonu çağırıp işlemlerimizi daha basit, kafa karışıklığı yaratmadan halledebiliriz.


![](https://miro.medium.com/v2/resize:fit:700/1*OW82SYhwei652mGfN7LXug.png)

· Fonksiyonlarımızı yazarken göz önünde bulundurulması gereken en önemli noktalardan birisi uzunluklarıdır. Bir fonksiyon çokça satırdan oluşuyorsa orada durup bu kodu nasıl daha küçük parçalara bölebiliriz diye düşünmeliyiz ve mümkün olduğu kadar parçalamalıyız. İdeal olan fonksiyon uzunluğu maximum 20 satırdır ve bir satırda en fazla 150 karakter bulunmalıdır.


![](https://miro.medium.com/v2/resize:fit:700/1*C7MZOswhincJ9ZxLwL2WbA.png)

Bu kodda görüldüğü üzere fonksiyonun satır sayısı 20’yi geçmiştir ve okunabilirliği büyük oranda azalmıştır. Eğer fonksiyonumuzu daha kısa tutsaydık hem okunabilirliği artar hem de geliştirilmesi kolaylaşabilirdi.

· Her fonksiyon yalnızca tek bir işlevi yerine getirmeli ve bunu da en efektif şekilde gerçekleştirmelidir. Fonksiyon yazan yazılımcı da ileride onu çağıracak olan yazılımcı da fonksiyonu tek ve spesifik bir amaç için çağırmalıdır.

Örneğin aşağıdaki kodda toplam ve ortalama aynı fonksiyon içerisinde hesaplanmaktadır fakat bu ileride sorun yaratabilir bu yüzden her zaman fonksiyonlarımızı yalnızca tek bir işlevi yerine getirecek şekilde yazmalıyız.

![](https://miro.medium.com/v2/resize:fit:700/1*xouOTDBRHbRjSTzsGHBH-Q.png)

Bu kodu düzelterek (refactor)şu şekilde yazarsak daha sağlıklı bir kodlama yapmış oluruz.

![](https://miro.medium.com/v2/resize:fit:700/1*ldrVNAJZ_kbEF8RDSosLHw.png)

· Bir fonksiyonun çok fazla parametre alması ileride kodun çalışabilirliği açısından sıkıntı yaratabilecekken o fonksiyonun birden fazla iş yaptığının da göstergesi olabilir. Fonksiyonlar için en uygunu hiç parametre almamalarıdır.

![](https://miro.medium.com/v2/resize:fit:700/1*TRspBT_w61IQy4PUJAwYRg.png)

**1.6**  **HATA/İSTİSNA YÖNETİMİ (ERROR HANDLING)**

Hata işleme, kod yazarken yapmak zorunda olduğumuz şeylerden sadece bir tanesidir ve biz yazılımcılar olarak kodumuzu bu tarz durumlara karşı hazırlamakla sorumluyuz.

Eskiden hata ayıklarken try-cath fonksiyonları yokken hata kodlarını return eden fonksiyonlar yazılırdı. Bu kullanımın tercih edilmemesi gerekir çünkü o kullanımda çokça if veya switch ifadeleri kullanılır. Bunların okunabilmesi ve kontrol edilebilmesi çok zahmetlidir. Bu yüzden try-catch metotlarını kullanmalıyız.

· Try-catch yazmak programnıza bir kapsam sağlar ve uygulamanın try bloğunda bir yerlerde hata olabileceğini ve catchlerde duracağını belirtirsiniz.

· Catch blokları, programınızı tutarlı bir durumda bırakmak zorundadırlar.

## 2.  SOLID Tasarım Prensipleri
SOLID prensipleri, nesne yönelimli programlamada (OOP) esnek, bakımı kolay ve genişletilebilir bir mimari kurmanın anayasası gibidir. Akademik düzeyde bir inceleme yaparken, her prensibi sadece "ne olduğuyla" değil, **"neyi engellediğiyle" (ihlaller)** ve **"nasıl düzelttiğiyle"** ele almak gerekir.

Aşağıda Java dili ile hazırlanmış, detaylı SOLID rehberi yer almaktadır.

#### 2.1 Single Responsibility Principle (SRP) - Tek Sorumluluk Prensibi

**Tanım:** Bir sınıfın değişmek için tek bir nedeni olmalıdır. Bir sınıf, sistemin yalnızca tek bir işlevinden sorumlu olmalıdır.

-   **İhlal Durumu (God Object):** Bir `Employee` sınıfının hem çalışan verilerini tutması, hem maaş hesaplaması, hem de bu verileri veritabanına kaydetmesi. Veritabanı yapısı değiştiğinde veya maaş politikası değiştiğinde aynı sınıf modifiye edilir. Bu durum "Rigidity" (Sertlik) yaratır.
    

**Java Örneği:**


```
// KÖTÜ ÖRNEK (İhlal)
public class Employee {
    public void calculatePay() { /* Muhasebe mantığı */ }
    public void saveToDatabase() { /* DB mantığı */ }
    public void reportHours() { /* Raporlama mantığı */ }
}

// İYİ ÖRNEK (SRP Uygun)
public class EmployeeData { /* Sadece veri */ }
public class PayCalculator { public void calculate(EmployeeData emp) { ... } }
public class EmployeeRepository { public void save(EmployeeData emp) { ... } }

```

----------

#### 2.2 Open/Closed Principle (OCP) - Açık/Kapalı Prensibi

**Tanım:** Yazılım birimleri (sınıflar, modüller) genişletilmeye **açık**, ancak değiştirilmeye **kapalı** olmalıdır. Yeni bir özellik eklemek için mevcut kodu bozmamalısınız.

-   **İhlal Durumu:** Yeni bir ödeme yöntemi (örneğin PayPal) eklemek için mevcut `PaymentProcessor` sınıfındaki bir `switch-case` bloğuna yeni bir satır eklemek. Bu, mevcut çalışan kodu riske atar.
    

**Java Örneği:**


```
// İYİ ÖRNEK (Interface ve Polimorfizm ile OCP)
interface PaymentMethod {
    void process(double amount);
}

public class CreditCardPayment implements PaymentMethod {
    public void process(double amount) { /* Kredi kartı işlemleri */ }
}

public class PaymentProcessor {
    public void checkout(PaymentMethod method, double amount) {
        method.process(amount); // Yeni bir yöntem gelirse buraya dokunmaya gerek kalmaz!
    }
}

```

----------

#### 2.3 Liskov Substitution Principle (LSP) - Liskov'un Yerine Geçme Prensibi

**Tanım:** Alt sınıflar, türetildikleri üst sınıfların tüm özelliklerini sergilemeli ve onların yerine kullanıldığında sistemin davranışını bozmamalıdır.

-   **İhlal Durumu:** Klasik "Kare-Dikdörtgen" problemi. Eğer `Square` sınıfı `Rectangle` sınıfından kalıtım alıyorsa ve `setHeight` metodunu çağırdığınızda genişlik de değişiyorsa, bu LSP ihlalidir. Çünkü bir dikdörtgen bekleyen kod, karenin bu özel davranışıyla karşılaşınca hata verebilir.
    

**Java Örneği:**


```
// KÖTÜ ÖRNEK (İhlal)
public class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Deve kuşları uçamaz!");
    }
}
// Çözüm: Uçabilen kuşlar ve yürüyen kuşlar için arayüzleri ayırmaktır.

```

----------

#### 2.4 Interface Segregation Principle (ISP) - Arayüz Ayrıştırma Prensibi

**Tanım:** İstemciler, kullanmadıkları metodları içeren arayüzlere zorlanmamalıdır. Büyük ve "şişkin" arayüzler yerine, daha spesifik ve küçük arayüzler tercih edilmelidir.

-   **İhlal Durumu:** Bir `Worker` arayüzünde hem `work()` hem de `eat()` metodu varsa; yemek yemeye ihtiyaç duymayan bir "RobotWorker" sınıfı bu metodu boş bırakmak (dummy implementation) zorunda kalır.
    

**Java Örneği:**


```
// İYİ ÖRNEK (ISP Uygun)
interface Workable { void work(); }
interface Feedable { void eat(); }

class HumanWorker implements Workable, Feedable {
    public void work() { ... }
    public void eat() { ... }
}

class RobotWorker implements Workable {
    public void work() { ... }
}

```

----------

#### 2.5 Dependency Inversion Principle (DIP) - Bağımlılığın Ters Çevrilmesi

**Tanım:** Yüksek seviyeli modüller, düşük seviyeli modüllere bağımlı olmamalıdır; her ikisi de soyutlamalara (interface/abstract class) bağımlı olmalıdır.

-   **İhlal Durumu:** Bir `Notification` sınıfının doğrudan bir `EmailSender` sınıfını "new" anahtar kelimesiyle oluşturması. Yarın SMS ile bildirim göndermek isterseniz `Notification` sınıfını baştan aşağı değiştirmeniz gerekir.
    

**Java Örneği:**


```
// İYİ ÖRNEK (DIP Uygun)
interface MessageService { void send(String msg); }

class EmailService implements MessageService {
    public void send(String msg) { /* Email gönder */ }
}

class Notification {
    private final MessageService service;

    // Bağımlılık dışarıdan enjekte edilir (Dependency Injection)
    public Notification(MessageService service) {
        this.service = service;
    }
    
    public void notifyUser(String message) {
        service.send(message);
    }
}
```

## 3. Coupling & Cohesion Loose coupling Bağımlılık kontrolü

Tasarım kalıpları ile ilgili süreçleri ve sorumlulukları anlayabilmek için cohesion (birliktelik) ve coupling (bağımlılık) kavramlarını iyi anlıyor olmak gerekiyor.  _Coupling_  ve  _Cohesion_  kavramlari iyi bir Object Oriented (OO) dizayna sahip bir yazılım ürünü ortaya çıkartmak için olmazsa olmazdır ve kalitesiyle doğrudan ilgilidir. Genel olarak kaliteyi arttırabilmek için nesneler arasında oldukça  **gevşek bağımlı ve yüksek uyumlu bir tasarım**  izlenmelidir. (Loose coupling and high cohesion)


#### 3.1 Cohesion

**Cohesion**, bir sınıfın alt bileşenlerinin ne kadar uyuma veya birlikteliğe (togetherness) sahip olduğunun derecesini belirtmek için kullanılır. Sınıfın yapısı, içerisinde yer alan bileşenler (tüm alanlar veya metotlar) benzer bir sorumluluğa hizmet etmesi beklenir. Bütünlük ne kadar artarsa sınıfın birlikteliği yani cohesion’ı doğru orantılı olarak artar.  
Burada karşımıza SOLID prensiplerinde “S” harfini temsil eden prensip çıkıyor “**Single Responsibility**”**.**  Aksi durumlarda farklı süreçleri yöneten parçaların ayrı olarak konumlandırılması farklı sınıflara taşınması gerekmektedir (**Seperation of concern (SoC)**).

#### Yüksek Uyumun Faydaları (High Cohesion)

-   Son derece uyumlu sınıfların bakımı çok daha kolaydır ve daha az sıklıkla değiştirilir.
-   Bu tür sınıflar, iyi odaklanmış bir amaca göre tasarlandıkları için diğerlerinden daha kullanışlıdır ve tekrar kullanıma (reusability) daha yatkındırlar.

#### 3.2 Cohesion Çeşitleri

Yazılımcıların sınıflar üzerinde yapmış olduğu modellemelere göre farklı birliktelik tipleri zamanla ortaya çıkmıştır. Bunları kötü senaryodan iyiye gidecek şekilde tanımlayalım.

-   **Coincidental Cohesion (Tesadüfi):** Sınıfta yer alan süreçlerin gelişi güzel olarak bir araya gelmesi durumudur. Nesneler arası soyutlama yok denecek kadar azdır ve aynı zamanda nesne organizasyonu ile hiçbir ilgisi yoktur gelişi güzel yazılmıştır. Bu tarz yapılara daha çok Utils, Helpers gibi isimlendirilen sınıflarda rastlarız.  **Karşılaşılabilecek en kötü durum budur.**


![](https://miro.medium.com/v2/resize:fit:700/1*4q6BjVzG9jrCqDwj0LFzmQ.png)

**Coincidental Cohesion’a örnek**

-   **Logical Cohesion (Mantıksal):** Modülün içerisinde ki parçalar doğaları gereği farklı olsalar bile mantıksal olarak aynı şeyi yapmak üzere kategorize edilirler. Örnek olarak çıktı alınan bir raporun tüm süreçlerini bir modül altında toparlamak verilebilir (db access, readfile, print file gibi tüm süreçlerin bir yerde toplanması gibi).


![](https://miro.medium.com/v2/resize:fit:700/1*tSSZXaA9eNifz2pZvOYMog.png)

**Logical Cohesion’a örnek**

-   **Temporal Cohesion (Zamansal):** Bir modülün içerisinde ki parçaların çalışma zamanına göre gruplandırılmasıdır. Örneğin açık dosyaları loglayan, loglama yaparken hata günlüğü oluşturan ve bunların yanında senaryolara göre kullanıcıyı bilgilendiren bir modül.


![](https://miro.medium.com/v2/resize:fit:700/1*GVURg9KsOZWy7mMgeEgD9A.png)

**Temporal Cohesion’a örnek olarak;** sistem çalışma zamanında burada ki tüm methodların çalıştırılması

-   **Procedural Cohesion (Prosedürel):** Bir süreç ile ilgili işleyişin yukarıdan aşağıya doğru fonksiyonel olarak ayrılması ve hepsinin bir modülde veya bir metotta bir araya getirilmesidir. Örneğin dosya izinlerini kontrol eden ve ardından dosyayı açan bir fonksiyon şeklinde düşünebiliriz.
-   **Communicational Cohesion (İletişimsel):** Aynı veriler/girdiler/çıktılar üzerinde çalışan işlemlerin bir modülde bir araya getirilmesidir. Veri işleme süreçlerinin yürütüldüğü durumlarda sık görülürler.
-   **Sequential Cohesion (Sıralı):** Modülün parçalarının gruplanmasıdır. Bir parçanın çıktısı, başka bir parçanın beslediği girdi olabilir. Burada akla montaj hattı gelebilir. (örneğin, bir dosyadan veri okuyan ve verileri işleyen bir metot). Prosedürel olan tipe oldukça benziyor farkı burada girdi-çıktı ilişkisi olmasıdır. Ardıl işlemlerin çok artması bir noktadan sonra cohesion’u azaltacaktır.
-   **Fonksiyonel Cohesion (Functional):** Çok iyi bir şekilde tanımlanmış, işlevselliğe ulaşmak için mümkün olan en iyi şekilde birlikte çalışan, olabildiğince küçük bir işe yada sorumluluğa yönelik olarak bir araya getirilmiş yapılardır. Diğer türlerde detay arttıkça birliktelik azalır, fakat fonksiyonel birliktelikte bölüp parçalama yoluyla odağın korunması amaçlanır.  **En iyi birliktelik durumudur.**

#### 3.3 Coupling

**Coupling (bağımlılık),**  modelleme sırasında sistemde bulunan varlıkların ilişkilerini belirlemeye yarayan terimdir. Cohesion yani birlikteliğin tersi olarak değerlendirilebilir. Geliştirme sırasında birlikteliği yüksek tutmaya çalışırken bağımlılığı minimize etmeye çalışmalıyız, yani olabildiğince nesnelerin izole tutulması gerekmektedir. İzole olarak tutulan nesne çiftlerinde bağımlılık azalır ve birinde yapılan değişiklik diğer bir nesnenin değişmesini veya güncellenmesini gerektirmez.

#### 3.4  Coupling Çeşitleri

Modellemeler yapılırken bağımlılık türlerine göre türler oraya çıkmıştır bunları kötü senaryodan iyiye göre sıralayalım ve tanımlayalım.

![](https://miro.medium.com/v2/resize:fit:652/0*p9x7JkNa_ldOoogU.png)

-   **Content Coupling (İçerik):** Modellerin iç yapılarına, gerçekleştirmelerine (implementation) göre bağımlı olma durumudur. Bu bağımlılık sebebiyle bir modülde yapılan değişiklik sonrası diğer modüller de aynı anda etkilenir. Bu etkilenmenin en temel sebebi yanlış soyutlamadır. Bunun yanında alan erişimlerinin doğrudan olması veya get/set metotları ile veri alışverişi en sık karşılaşılan durumlardandır.
-   **Common Coupling (Ortak):** Modüller arasında ortak veya global verilerin/değişkenlerin kullanılması anlamına gelir. Global olarak tanımlı bilgilerde bir değişiklik bağımlı olan her yerde hissedilir ve bunun sonucunda hatalar ve tanımlanamayan sorunlara neden olabilir.

-   **External Coupling (Dışsal):** Modüller arasında kullanılan ortak yapıların dışarıdan bir sistem tarafından format veya veri yapısının dayatmasından kaynaklanan bağımlılıktır. Örnek olarak üçüncü servisler verilebilir.
-   **Control Coupling (Kontrol):** Modüller arasındaki veri paylaşımının kontrol edilmesi anlamına gelir. Aralarında birbirlerinin akışını etkileyecek şekilde veri paylaşıyor veya etkileşime giriyorsa bu coupling tipine bir örnektir. Özetle, bir modülün veri veya bilgi akışını diğer modüller tarafından ne yapılacağına ilişkin bilgileri kontrol etmesi anlamına gelir. Örnek olarak modüller arasında bir flag yoluyla akışın kontrol edilmesi gibi.
-   **Data-Structured Coupling (Veri-Yapısı Bağımlılığı):** Modüller arası geçiş sırasında modüllerin farklı tipte  **veri yapıları** geçerek (ki bu genelde nesnedir) oluşturduğu bağımlılıktır. Geçilen şey veri yapısıdır. Tamamıyla veri yapısı geçildiği için kullanılmayan veriler ilgisiz veri demetleri oluşturur ve yeniden kullanılabilirliği azaltır.
-   **Data Coupling (Veri Bağımlılığı):** Modüller birbirlerine basit ve ilkel veri geçerek oluşturdukları bağımlılıklardır yani bir modülün verilerini başka bir modüle aktarılma veya birleştirme olarak değerlendirilir. Anlaşılması adına bir metot’a çok sayıda veri içeren parametrelerin geçilmesi örnek olarak verilebilir.
-   **Message Coupling (Mesaj Bağımlılığı):** Arayüzü (interface) bilgisi dışında başka hiç bir bilgi gerektirmeyen bağımlılık şeklidir. Metotlar, modüller, sistemler ve sınıflar arasında uygulanabilir. Olabilecek en sağlıklı bağımlılıktır.
-   **No Coupling (Sıfır bağımlılık):** Hiçbir bağımlılığın olmaması durumudur. Bir sistem olabilmesi mümkün değildir.

#### 3.5 OOP merkezi dillerde farklı bağımlılık tipleri

-   **Inheritance Coupling (Miras Bağımlılığı):** Kalıtım ile birlikte sınıflar arasında oluşan sıkı bağı temsil eder. Child sınıf parent öğesinden türetilmişse, parent öğesine sıkıca bağlanmıştır**.** Parent sınıf üzerinde herhangi bir değişiklik yaparsanız, child sınıfta da değiştirmeniz gerekebilir.
-   **Abstract Coupling (Soyut Bağımlılık) :** Soyut üst sınıfa olan bağımlılıktır. Soyut bağımlılık diğer bağımlılık türlerine göre daha iyidir. Soyut bağımlılıkta nesneler sadece üst sınıfın tipini bilirler fakat gerçek tipini bilmezler (Polimorfizm). SOLID yazılım prensiplerinden Dependency Inversion (DI) ile elde edilir. Bunu bir sonraki yazımda ele alıyor olacağız.


İyi bir tasarım, düşük bağımlılığa sahip olandır. Modüller arası bağlılık artarsa hata yapılma olasılığı artar, test edilebilme durumu da gittikçe zorlaşır. Bunun önüne geçebilmek için işlerin uygun bir şekilde alt parçalara hatta atomik denebilecek küçük parçalara bölünmesi gerekir. Bölünen küçük parça bir süreci yönetsin ve bu süreci tam anlamıyla tamamlasın ki başka bir parça ile bağımlılığı en az seviyede olsun.

#### 3.6 Complexity (Değişim)

Yazılım, doğası gereği değişen bir yapıya sahiptir. Değişimin en temel sebebi var olan ihtiyaçların üzerine yeni bir takım ihtiyaçların eklenmesidir. Değişimde maliyetlerinin azaltılabilmesi için birlikteliği yüksek (highly-cohesion) ve bağımlılığın düşük (lowly-coupled) olması gerekir. Aksi taktirde hem aynı yerde farklı işleri yapan yapılar hem de iş parçacıkları etrafa saçılmış olacaktır ve sonucunda gerekli bir değişiklik çok fazla yeri çok fazla bileşeni etkiliyor olacaktır.


## 4. Refactoring ve Code Smells (Kod Kokuları)

#### 4.1  Refactoring (Kod İyileştirme) Nedir?

**Refactoring**, bir yazılımın **dış davranışını değiştirmeden**, iç yapısını daha anlaşılır, daha temiz ve bakımı kolay hale getirmek için yapılan sistematik bir süreçtir.

-   **Temel Amaç:** Teknik borcu (Technical Debt) azaltmak, kodun okunabilirliğini artırmak ve hata payını minimize etmektir.
    
-   **Ne Zaman Yapılır?** Bir özellik eklemeden önce (hazırlık için), bir hata ayıklarken (kodu anlamak için) veya kod incelemesi (Code Review) sırasında.
    
-   **Altın Kural:** Refactoring yaparken yeni bir fonksiyonellik eklenmez. Önce kod iyileştirilir, testler geçer, sonra yeni özellik eklenir.
    

----------

#### 4.2 Code Smells (Kod Kokuları)

"Kod kokusu", kodda teknik bir hata olduğu anlamına gelmez; ancak tasarımda derin bir problemin habercisidir. Tıpkı mutfaktaki kötü bir kokunun bozulan bir yemeğe işaret etmesi gibi, kod kokusu da gelecekte sistemin kırılmasına (Fragility) neden olacak zayıf noktaları gösterir.

#### A. Bloaters (Şişkinler)

Kodun zamanla devasa boyutlara ulaşması ve yönetilemez hale gelmesidir.

-   **Long Method:** Bir metodun 20-25 satırdan fazla olması. Genelde içinde birden fazla iş (SRP ihlali) barındırır.
    
-   **Large Class:** Bir sınıfın yüzlerce satır ve onlarca metoda sahip olması (God Object).
    
-   **Data Clumps:** Sürekli beraber gezen değişken grupları (örneğin: `startDate`, `endDate`). Bunlar bir nesne (`DateRange`) haline getirilmelidir.
    

#### B. Object-Orientation Abusers (Nesne Yönelim İstismarcıları)

OOP prensiplerinin yanlış veya eksik uygulanmasıdır.

-   **Switch Statements:** Çok fazla `switch-case` veya `if-else` bloğu varsa, orada polimorfizm (OCP) eksik demektir.
    
-   **Temporary Field:** Sınıf içinde sadece belirli durumlarda kullanılan geçici değişkenlerin bulunması.
    

#### C. Change Preventers (Değişim Engelleyiciler)

Kodda bir yeri değiştirmek istediğinizde sizi engelleyen durumlardır.

-   **Divergent Change:** Tek bir sınıfta farklı nedenlerle (örneğin hem veritabanı hem UI için) sürekli değişiklik yapmak zorunda kalmanız.
    
-   **Shotgun Surgery:** Bir özelliği değiştirmek için 10 farklı sınıfa dokunmanız gerekmesi (Yüksek Coupling/Bağımlılık göstergesi).
    

----------

#### 4.3 Önemli Refactoring Teknikleri ve Java Örnekleri

#### I. Extract Method (Metodu Dışarı Çıkarma)

Karmaşık ve uzun bir metodun içindeki mantıklı parçaları yeni küçük metodlara bölmektir.

```
// KÖTÜ ÖRNEK (Koku: Long Method)
void printReceipt() {
    // Toplamı hesapla
    double total = 0;
    for (Item i : items) total += i.price;
    
    // Detayları yazdır
    System.out.println("Customer: " + name);
    System.out.println("Total: " + total);
}

// İYİ ÖRNEK (Refactored)
void printReceipt() {
    double total = calculateTotal();
    printDetails(total);
}

private double calculateTotal() { /* ... */ }
private void printDetails(double total) { /* ... */ }

```

#### II. Replace Temp with Query

Geçici değişkenleri bir metod çağrısıyla değiştirerek kodun tekrar kullanılabilirliğini artırmaktır.

#### III. Preserve Whole Object

Bir metoda bir nesnenin 5 farklı parametresini göndermek yerine, nesnenin kendisini göndermektir.


#### 4.4  Sürdürülebilirlik (Sustainability) İlişkisi

Sürdürülebilirlik, bir yazılım projesinin yıllar sonra bile makul maliyetlerle geliştirilmeye devam edilebilmesidir.

1.  **Bakım Kolaylığı:** Refactoring yapılmış kodda hata bulmak (Debugging) çok daha hızlıdır.
    
2.  **Hız (Velocity):** Temiz kodda yeni özellikler eklemek, "spagetti kod" içinde yol bulmaya çalışmaktan çok daha az zaman alır.
    
3.  **Ekip Adaptasyonu:** Yeni katılan bir geliştirici, temiz yazılmış ve iyi dökümante edilmiş (README) bir projeye günler içinde adapte olabilir.

## 5. Ekstralar

### 5.1 TDD (Test Driven Development)

TDD bir test yöntemi değil, bir **tasarım disiplinidir**. Kodun nasıl yazılacağını testler belirler.

### 5.2 TDD Döngüsü: Red-Green-Refactor

Bu döngü, yazılımın her adımda çalıştığından emin olmanızı sağlar.

1.  **🔴 Red (Başarısız Test):** Henüz kodun kendisi yokken, beklenen davranışı tanımlayan bir test yazılır. Test çalıştırılır ve başarısız olur.
    
2.  **🟢 Green (Geçen Test):** Testi geçirecek **en basit ve en hızlı** kod yazılır. Şık olması önemli değildir, sadece çalışması yeterlidir.
    
3.  **🔵 Refactor (Düzenleme):** Test hala geçerken, kod Clean Code ve SOLID prensiplerine göre iyileştirilir.

### 5.3 Temel Yazılım Prensipleri (KISS, DRY, YAGNI)

Bu prensipler, mühendislikte "Over-engineering" (Aşırı Mühendislik) dediğimiz, basit bir sorunu devasa bir mimariyle çözme hatasını engeller.

### 5.4 DRY (Don't Repeat Yourself)

**Tanım:** Bir sistemdeki her bilgi parçasının tek, kesin ve yetkili bir temsili olmalıdır.

-   **İhlal (WET - Write Everything Twice):** Aynı doğrulama (validation) mantığının hem `UserService` hem de `AdminService` içinde kopyalanmış olması. Bir kural değiştiğinde iki yeri de güncellemeniz gerekir; birini unutursanız sistem tutarsızlaşır.
    

**Java Örneği:**

Java

```
// KÖTÜ (Tekrar var)
if (user.getAge() < 18) throw new Exception("Yaş küçük"); // UserService
if (admin.getAge() < 18) throw new Exception("Yaş küçük"); // AdminService

// İYİ (DRY Uygun)
public class Validator {
    public static void validateAge(int age) {
        if (age < 18) throw new Exception("Yaş küçük");
    }
}

```
###  5.5 KISS (Keep It Simple, Stupid)

**Tanım:** Bir sistemi olabildiğince basit tutmak, karmaşıklıktan kaçınmak temel amaçtır.

-   **İhlal:** Sadece iki sayıyı toplayacak bir fonksiyona; Generic tipler, Reflection ve Factory Pattern ekleyerek "gelecekte belki her şeyi toplarız" diye düşünmek.
    

### 5.6 YAGNI (You Ain't Gonna Need It)

**Tanım:** Bir özelliği gerçekten ihtiyaç duyulana kadar eklemeyin.

-   **İhlal:** "İleride belki kullanıcılar profil resmini siyah beyaz yapmak ister" deyip, henüz ortada profil resmi yükleme özelliği bile yokken görüntü işleme kütüphanesi eklemek.

