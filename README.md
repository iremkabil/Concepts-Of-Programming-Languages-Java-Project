# Şehir Nüfus Simülasyonu

Bu proje, **Sakarya Üniversitesi Bilgisayar Mühendisliği Bölümü** kapsamında alınan **Programlama Dillerinin Prensipleri** dersi için Java dili ile geliştirilmiş konsol tabanlı bir şehir nüfus simülasyonudur.

Projede kullanıcıdan alınan iki basamaklı şehir kodları decode edilerek şehir, ilçe, mahalle ve kişi yapıları oluşturulmaktadır. Her tur sonunda şehirlerin nüfusu belirli bir çarpana göre artırılmakta, nüfusu 1000 ve üzerine çıkan şehirler ise belirlenen kurallara göre ikiye bölünmektedir.

## Projenin Amacı

Bu projenin temel amacı, Java programlama dili kullanılarak nesne yönelimli programlama prensiplerini uygulamak ve hiyerarşik yapıya sahip bir simülasyon sistemi geliştirmektir.

Proje kapsamında aşağıdaki konular uygulanmıştır:

- Nesne yönelimli programlama
- Sınıf ve nesne yapısı
- Encapsulation
- Tek sorumluluk prensibi
- Hiyerarşik veri modelleme
- Konsol tabanlı kullanıcı etkileşimi
- Harici kütüphane kullanımı
- JAR dosyası oluşturma
- Simülasyon mantığı

## Kullanılan Teknolojiler

- Java 17
- Eclipse IDE
- Java Faker Kütüphanesi
- JAR bağımlılıkları:
  - java-faker
  - snakeyaml
  - generex
  - automaton
  - commons-lang3

## Proje Yapısı

Proje toplam 6 temel sınıftan oluşmaktadır.

```text
src/
│
├── Main.java
├── Oyun.java
├── Sehir.java
├── Ilce.java
├── Mahalle.java
└── Kisi.java

lib/
├── java-faker.jar
├── snakeyaml.jar
├── generex.jar
├── automaton.jar
└── commons-lang3.jar
```

## Sınıfların Görevleri

### Main

Programın başlangıç sınıfıdır. Simülasyonu başlatır.

### Oyun

Simülasyonun ana kontrol sınıfıdır. Kullanıcıdan girişleri alır, şehirleri oluşturur, turları yönetir ve sonuçları ekrana yazdırır.

### Sehir

Bir şehri temsil eder. İlçeleri barındırır, toplam nüfusu hesaplar, nüfus artış işlemlerini gerçekleştirir ve gerekli durumlarda şehir bölünmesini yönetir.

### Ilce

Bir ilçeyi temsil eder. Mahalleleri tutar ve ilçeye ait nüfus bilgisinin yönetilmesini sağlar.

### Mahalle

Bir mahalleyi temsil eder. Mahalle içerisindeki kişileri saklar.

### Kisi

Her bireyi temsil eder. Kişiye ait ID, ad, soyad ve yaş bilgilerini tutar. Statik sayaç kullanılarak her kişi için benzersiz ID üretilir.

## Decode Mekanizması

Kullanıcıdan alınan iki basamaklı sayılar şehir kodu olarak kullanılır.

Örneğin:

```text
25
```

Bu kod şu şekilde yorumlanır:

```text
İlçe sayısı: 2
Mahalle sayısı: 5
```

Onlar basamağı ilçe sayısını, birler basamağı ise mahalle sayısını temsil eder.

Ancak mahalle sayısının ilçe sayısına tam bölünebilmesi gerekir. Eğer mahalle sayısı ilçe sayısına tam bölünemiyorsa, birler basamağı yukarı doğru en yakın bölünebilen değere çıkarılır. Uygun değer bulunamazsa aşağı doğru arama yapılır.

Ayrıca başlangıç nüfusu da toplam mahalle sayısına eşit şekilde dağıtılabilecek biçimde yukarı yuvarlanır. Bu sayede her mahalleye eşit sayıda kişi yerleştirilebilir.

## Nüfus Artışı

Her tur sonunda şehirlerin nüfusu belirli bir çarpana göre artırılır.

Çarpan, şehrin mevcut nüfusunun onlar ve birler basamağındaki rakamların toplamı alınarak hesaplanır.

Örneğin:

```text
Nüfus: 24
Çarpan: 2 + 4 = 6
```

Her mahalledeki mevcut kişi sayısı bu çarpana göre artırılır. Yeni kişiler `Kisi` nesnesi olarak oluşturulur.

Eğer çarpan sıfır olursa, her mahalleye yalnızca bir kişi eklenir.

## Şehir Bölünme Mantığı

Bir şehrin nüfusu 1000 veya üzerine çıktığında şehir ikiye bölünür.

Bölünme kuralları şu şekildedir:

- İlçe sayısı çift ise ilçeler iki şehir arasında eşit şekilde bölünür.
- İlçe sayısı tek ise eski şehirde bir ilçe fazla kalır.
- Tek ilçeli şehirler bölünemez.

Örnek:

```text
7 ilçeli ve 1078 nüfuslu şehir:

Eski şehir: 4 ilçe
Yeni şehir: 3 ilçe
```

Başka bir örnek:

```text
8 ilçeli ve 1408 nüfuslu şehir:

Eski şehir: 4 ilçe
Yeni şehir: 4 ilçe
```

## Programın Çalışma Mantığı

Program çalıştırıldığında kullanıcıdan önce kaç tur oynanacağı alınır.

Ardından şehir kodları girilir.

Örnek giriş:

```text
Kaç tur oynanacak?: 1
Şehir kodlarını giriniz: 18 25 79 37 62 86 17 50
```

Program bu kodları decode ederek şehirleri oluşturur.

Her tur sonunda:

- Şehir nüfusları artırılır.
- Nüfusu 1000 ve üzeri olan şehirler bölünür.
- Güncel nüfus tablosu ekrana yazdırılır.

Oyun sonunda kullanıcı satır ve sütun numarası girerek istediği şehrin detaylarını görüntüleyebilir.

## Örnek Giriş

```text
1
18 25 79 37 62 86 17 50
```

## Örnek Decode ve Tur Sonuçları

| Giriş | İlçe | Mahalle | Başlangıç Nüfusu | 1. Tur Sonrası |
|------|------|---------|------------------|----------------|
| 18   | 1    | 8       | 24               | 144            |
| 25   | 2    | 6       | 30               | 90             |
| 79   | 7    | 7       | 77               | 1078           |
| 37   | 3    | 9       | 45               | 405            |
| 62   | 6    | 6       | 66               | 792            |
| 86   | 8    | 8       | 88               | 1408           |
| 17   | 1    | 7       | 21               | 63             |
| 50   | 5    | 5       | 55               | 550            |

## Örnek Çıktı

Program çalıştırıldığında şehirler oluşturulur ve her şehir için decode edilen bilgiler ekrana yazdırılır.

Örnek çıktı:

```text
SEHIR NUFUS SIMULASYONU
Programlama Dillerinin Prensipleri

Kaç tur oynanacak?: 1
Şehir kodlarını giriniz: 18 25 79 37 62 86 17 50

Şehirler oluşturuluyor...

[+] İstanbul oluşturuldu (Kod: 18 -> Nüfus: 24, 1 ilçe, 8 mahalle)
[+] Eskişehir oluşturuldu (Kod: 25 -> Nüfus: 30, 2 ilçe, 6 mahalle)
[+] İzmir oluşturuldu (Kod: 79 -> Nüfus: 77, 7 ilçe, 7 mahalle)
[+] Van oluşturuldu (Kod: 37 -> Nüfus: 45, 3 ilçe, 9 mahalle)
[+] Edirne oluşturuldu (Kod: 62 -> Nüfus: 66, 6 ilçe, 6 mahalle)
[+] Şırnak oluşturuldu (Kod: 86 -> Nüfus: 88, 8 ilçe, 8 mahalle)
[+] Edirne oluşturuldu (Kod: 17 -> Nüfus: 21, 1 ilçe, 7 mahalle)
[+] Edirne oluşturuldu (Kod: 50 -> Nüfus: 55, 5 ilçe, 5 mahalle)

8 şehir başarıyla oluşturuldu.
```

Tur tamamlandıktan sonra nüfus tablosu ekrana yazdırılır.

Örnek:

```text
Tur 1/1 tamamlandı
Toplam şehir: 10

OYUN SONA ERDİ

Toplam şehir sayısı : 10
Toplam nüfus        : 4530
Oynanan tur sayısı  : 1
```

## Şehir Detay Görüntüleme

Oyun sonunda kullanıcıdan görüntülemek istediği şehrin satır ve sütun numarası istenir.

Örnek:

```text
Satır numarası giriniz: 1
Sütun numarası giriniz: 3
```

Seçilen şehrin detayları ağaç yapısında gösterilir.

Örnek:

```text
SEHIR: Edirne
TOPLAM NUFUS: 462

+-- Ilce 1: Louisiana
|   Nüfus: 154
|   +-- Mahalle 1: Burak Glens
|       Nüfus: 154
|       Kişiler:
|       121 - Selim Özkanlı - 47
|       122 - Yiğit Sağdıç - 19
|       123 - İrem Özkanlı - 3
```

## Kurulum

Projeyi çalıştırmak için bilgisayarınızda Java 17 kurulu olmalıdır.

Projeyi GitHub üzerinden klonlayın:

```bash
git clone https://github.com/kullanici-adi/sehir-nufus-simulasyonu.git
```

Proje klasörüne girin:

```bash
cd sehir-nufus-simulasyonu
```

## Eclipse ile Çalıştırma

Projeyi Eclipse üzerinde çalıştırmak için:

1. Eclipse IDE'yi açın.
2. `File > Import` seçeneğine tıklayın.
3. `Existing Projects into Workspace` seçeneğini seçin.
4. Proje klasörünü seçin.
5. `lib` klasöründeki JAR dosyalarının build path'e eklendiğinden emin olun.
6. `Main.java` dosyasını çalıştırın.

## JAR Dosyası ile Çalıştırma

Eğer proje JAR dosyası olarak export edildiyse aşağıdaki komut ile çalıştırılabilir:

```bash
java -jar SehirNufusSimulasyonu.jar
```

## Özellikler

- Konsol tabanlı simülasyon sistemi
- Kullanıcıdan dinamik şehir kodu alma
- İki basamaklı kodları decode ederek şehir oluşturma
- Şehir, ilçe, mahalle ve kişi hiyerarşisi
- Türkçe locale ile rastgele isim üretimi
- Tur bazlı nüfus artışı
- Nüfusu 1000 ve üzeri olan şehirleri bölme
- Oyun sonunda şehir detaylarını görüntüleme
- Ağaç yapısında detaylı çıktı gösterme
- Java Faker kütüphanesi ile kişi verisi üretme

## Test Bilgisi

Program farklı giriş değerleriyle test edilmiştir.

Ödevde verilen örnek giriş için 1. tur sonunda 1078 ve 1408 nüfuslu şehirler bölünerek yeni şehirler oluşmuştur.

Bölünme sonrası:

```text
1078 nüfuslu ve 7 ilçeli şehir:
616 ve 462 nüfuslu iki şehre ayrılmıştır.

1408 nüfuslu ve 8 ilçeli şehir:
704 ve 704 nüfuslu iki şehre ayrılmıştır.
```

Program ayrıca 18 şehir ve 4 tur ile test edilmiş, 90 şehir ve yaklaşık 7.4 milyon nüfusa ulaşılmıştır. Test sürecinde herhangi bir hata veya çökme yaşanmamıştır.

## Öğrenilenler

Bu proje ile Java dilinde nesne yönelimli programlamanın temel prensipleri uygulanmıştır.

Özellikle aşağıdaki konularda deneyim kazanılmıştır:

- Sınıflar arası hiyerarşik ilişki kurma
- Encapsulation kullanımı
- Tek sorumluluk prensibini uygulama
- Harici JAR kütüphanelerini projeye dahil etme
- Java Faker ile rastgele veri üretme
- Simülasyon akışı tasarlama
- Edge case yönetimi
- JAR dosyası oluşturma
- Konsol tabanlı kullanıcı arayüzü geliştirme

Projede en çok dikkat edilmesi gereken kısımlar şehir bölünme mantığının doğru çalışması ve tek ilçeli şehirler gibi özel durumların doğru yönetilmesidir.

## Geliştirici

**İrem Kabil**  
Sakarya Üniversitesi  
Bilgisayar ve Bilişim Bilimleri Fakültesi  
Bilgisayar Mühendisliği Bölümü  

## Ders Bilgisi

**Ders:** Programlama Dillerinin Prensipleri  
**Ödev:** Şehir Nüfus Simülasyonu  
**Dönem:** Nisan 2026

## Referanslar

- Java Faker Kütüphanesi
- Oracle Java SE 17 Documentation
- Eclipse IDE Documentation

## Lisans

Bu proje eğitim amacıyla geliştirilmiştir.
