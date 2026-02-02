# Maven Nedir?

Maven, Java tabanlı bir proje yönetim aracıdır. Proje bağımlılıklarını yönetmek, derlemek, test etmek ve dağıtmak için kullanılır.

### 1.1 Maven'ın İşlevleri:

1.  Bağımlılık Yönetimi:  Maven, projelerin bağımlılıklarını yönetmek için kullanılır. Bağımlılıklar, pom.xml dosyasına eklenir ve Maven, bu bağımlılıkları otomatik olarak indirir ve projenin classpath'ine ekler.
2.  Derleme:  Maven, projeleri derlemek için kullanılır. Java kodunu derler, testleri çalıştırır ve deployable bir paket (örneğin, JAR dosyası) oluşturur.
3.  Test:  Maven, projelerin testlerini çalıştırır. JUnit, TestNG gibi test framework'lerini destekler.
4.  Dağıtım:  Maven, projelerin dağıtımını yönetir. Projeyi bir depoya (örneğin, Nexus) yükler ve diğer projelerin kullanımına sunar.

### 1.2  JAR Dosyaları:

JAR (Java ARchive) dosyaları, Java projelerinin deployable paketleridir. JAR dosyaları, derlenmiş Java kodunu, bağımlılıkları ve diğer kaynakları içerir.

### 1.3 Multi-Module Projeler:

Multi-module projeler, birden fazla modülden oluşan projelerdir. Her modül, kendi başına bir proje olarak düşünülebilir. Multi-module projeler, büyük ve karmaşık projelerin yönetimini kolaylaştırır.

Maven'ın Multi-Module Projelerdeki Kullanımı:

Maven, multi-module projelerde kullanılır. Her modül, kendi pom.xml dosyasına sahiptir. Ana pom.xml dosyası, alt modüllerin pom.xml dosyalarını referans gösterir.

Örneğin:

```
<!-- Ana pom.xml dosyası -->
<project>
    <groupId>com.example</groupId>
    <artifactId>multi-module-project</artifactId>
    <version>1.0</version>
    <modules>
        <module>module1</module>
        <module>module2</module>
    </modules>
</project>

```

```
<!-- module1 pom.xml dosyası -->
<project>
    <groupId>com.example</groupId>
    <artifactId>module1</artifactId>
    <version>1.0</version>
    <dependencies>
        <!-- bağımlılıklar -->
    </dependencies>
</project>

```

Maven, multi-module projelerde aşağıdaki işlemleri gerçekleştirir:

-   Her modülün pom.xml dosyasını okur
-   Bağımlılıkları yönetir
-   Derler
-   Testleri çalıştırır
-   Deployable paketler oluşturur

Örnek Kullanım:

```
# Projeyi derle
mvn clean package

# Testleri çalıştır
mvn test

# Deployable paket oluştur
mvn deploy

```
