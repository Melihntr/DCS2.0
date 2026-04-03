Singleton Design Pattern ,  **Creational Design Pattern**  grubunda yer alır.

Singleton desgin pattern çalışma zamanında yalnızca 1 object yaratılmasını garanti eden tasarım desenidir.

Kullanımına ihtiyaç duyulan durum şudur :

-   Birden çok sınıfın aynı instance’ı kullanması gerekmektedir.
-   Tüm uygulama için yalnızca bir nesne olması gerekmektedir.
-   Sadece bir nesne olduğu (unique) garanti edilmelidir.

Bu gereklilikler için bir sınıf yaratırız ve sınıfın kendi instance’ını yönetmesini sağlarız.

**Önemli nokta** : Bir sınıfın yalnızca bir instance’ının bulunduğundan emin olmalıyız ve buna global erişim noktası sağlamalıyız.

![](https://miro.medium.com/v2/resize:fit:504/1*n4GFJMzjbIlqu2gv2bkveA.png)

**Singleton Pattern’ın Yapısı**

![](https://miro.medium.com/v2/resize:fit:621/1*lwIJJYxXJTu9Mt4UbSLfcg.png)

createSingleton() methodunu sadece singleton sınıfı kullanır.

getInstance() methodunu diğer sınıflar kullanarak instance’a ulaşırlar.

**Singleton pattern kullanmanın faydaları:**

-   Bir instance‘a kontrollü erişim sağlanır.
-   Global değişkenler yaratmaktan kaçınırız.

Singleton Pattern yalnızca 1 object yaratmak için **test edilmiş**  bir yöntemdir.

Singleton Pattern bize global bir erişim noktası sunar , global değişkenler gibi dezavantajı yoktur. Bu dezavantajlardan biri şudur : objemizi global değişken olarak yarattığımızda uygulama başladığında nesne yaratılacak, bu nesne kaynak yoğunsa ve uygulamanız onu kullanmadan tamamlandıysa ne olacak?  **Singleton Pattern’de bir object ancak ona ihtiyaç duyduğumuzda yaratılır.**

Singleton sınıfımız public olacağı için birden fazla kez başlatabiliriz farklı paketlerden de.

## Get  Buse Tekin Aydın’s stories in your inbox

Join Medium for free to get updates from this writer.

Subscribe

Remember me for faster sign in

**Singleton Sınıf Örneği**

public class Preferences {_// static değişkenimiz singleton class’ımızın instance’ı_private static Preferences instance = null; protected Preferences() {}_// synchronized anahtarını ekleyerek her thread’in bir sonrakini beklemesini sağladık , aynı anda 2 thread bu methodu kullanamaz._private **synchronized** static void createInstance() {if (instance == null)instance = new Preferences();}public static Preferences getInstance() {if (instance == null)createInstance();return instance;}public void helloSingleton() {System.out.println(“Hello i’m a singleton”);}}

Preferences sınıfımızdan kalıtılmış olan MyPreferences sınıfı

**public** **class** MyPreferences **extends** Preferences{}public class Client {public static void main(String [] args){Preferences.getInstance().helloSingleton();MyPreferences.getInstance().helloSingleton();}}

Client sınıfını çalıştırdığımızda çıkan sonuç :

Hello i’m a singleton Hello i’m a singleton (kalıtımdan gelen)

Sınıfımın (örneğin class Preferences) constructor’ı protected da olabilir private’da , burda önemli olan inheriting yapıp yapmayacağınızdır. MyPreferences sınıfımız Preferences’den kalıtıldığı için constructerı  _protected_ yapmamız gerekti. Kalıtım yapılmayacaksa private yapılmalıdır. Bazı kaynaklarda constructer’ın protected yapılması doğru bulunmaz, bu şekilde Singleton pattern’ın düzgün uygulanamadığı söylenmektedir.
