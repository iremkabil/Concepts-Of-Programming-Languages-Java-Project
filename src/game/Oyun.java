package game;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Locale;

/**
 * @author [İrem Kabil] ve []
 * @since 2026-04-14
 * <p>
 * Oyun sinifi: Tum simulasyonun kontrol merkezidir.
 * Kullanicidan alinan verilerle sehirler olusturulur,
 * tur dongusu icerisinde nufus artisi ve sehir bolunmesi
 * islemleri gerceklestirilir. Her tur sonunda guncel
 * nufus bilgileri ekranda gosterilir.
 * </p>
 */
public class Oyun {
    private ArrayList<Sehir> sehirler;
    private Faker faker;
    private Random random;
    private Scanner scanner;
    private int turSayisi;

    public Oyun() {
        this.sehirler = new ArrayList<>();
        this.faker = new Faker(new Locale("tr"));
        this.random = new Random();
        this.scanner = new Scanner(System.in);
    }

    // Oyunu başlat
    public void basla() {
        turSayisiAl();
        sayilariAl();
        baslangicGoster();

        for (int tur = 1; tur <= turSayisi; tur++) {
            turCalistir();
            ekranTemizle();
            nufuslariGoster();
            bolunmeKontrol();
            ekranTemizle();
            nufuslariGoster();
        }

        oyunSonu();
    }

    // Kullanıcıdan tur sayısını al
    private void turSayisiAl() {
        System.out.print("Tur sayisini giriniz: ");
        turSayisi = scanner.nextInt();
        scanner.nextLine(); // satır sonu temizle
    }

    // Kullanıcıdan sayıları al ve şehirleri oluştur
    private void sayilariAl() {
        System.out.print("Sayilari giriniz (boslukla ayrilmis): ");
        String satir = scanner.nextLine();
        String[] parcalar = satir.split(" ");

        for (String parca : parcalar) {
            int sayi = Integer.parseInt(parca.trim());
            sehirOlustur(sayi);
        }
    }

    // Bir sayıdan şehir oluştur (decode işlemi)
    private void sehirOlustur(int sayi) {
        if (sayi < 10 || sayi > 99) {
            System.out.println("Uyari: " + sayi + " gecersiz! Sayilar iki basamakli (10-99) olmalidir. Atlaniyor.");
            return;
        }
        int ilceSayisi = sayi / 10;        // onlar basamağı
        int mahalleSayisi = sayi % 10;      // birler basamağı

        // Mahalle sayısı düzeltmesi
        mahalleSayisi = mahalleDuzelt(ilceSayisi, mahalleSayisi);

        // Düzeltilmiş sayı = onlar basamağı * 10 + yeni birler basamağı
        int duzeltilmisSayi = ilceSayisi * 10 + mahalleSayisi;

        // Toplam mahalle sayısı
        int toplamMahalle = mahalleSayisi;

        // Nüfus düzeltmesi: düzeltilmiş sayı üzerinden
        int nufus = nufusDuzelt(duzeltilmisSayi, toplamMahalle);

        // Her mahalledeki kişi sayısı
        int kisiPerMahalle = nufus / toplamMahalle;

        // Her ilçedeki mahalle sayısı
        int mahallePerIlce = toplamMahalle / ilceSayisi;

        // Şehri oluştur
        Sehir sehir = new Sehir(faker.address().city());

        for (int i = 0; i < ilceSayisi; i++) {
        	Ilce ilce = new Ilce(faker.address().state());

            for (int j = 0; j < mahallePerIlce; j++) {
                Mahalle mahalle = new Mahalle(faker.address().streetName());
                mahalle.baslangicKisileriniOlustur(kisiPerMahalle, faker, random);
                ilce.mahalleEkle(mahalle);
            }

            sehir.ilceEkle(ilce);
        }

        sehirler.add(sehir);
    }
    
    // Mahalle sayısı düzeltmesi: ilçe sayısına tam bölünebilmeli
    private int mahalleDuzelt(int ilceSayisi, int mahalleSayisi) {
        // 0 veya bölünemiyorsa düzeltme gerekli
        if (mahalleSayisi != 0 && mahalleSayisi % ilceSayisi == 0) {
            return mahalleSayisi;
        }

        // Önce Y'den büyük veya eşit bölünebilen ara (Y+1'den 9'a)
        for (int y = mahalleSayisi + 1; y <= 9; y++) {
            if (y % ilceSayisi == 0) {
                return y;
            }
        }

        // Bulunamazsa 1'den Y-1'e kadar ara
        for (int y = 1; y < mahalleSayisi; y++) {
            if (y % ilceSayisi == 0) {
                return y;
            }
        }

        return ilceSayisi; // en kötü ihtimal
    }

    // Nüfus düzeltmesi: toplam mahalleye bölünebilmeli (yukarı yuvarla)
    private int nufusDuzelt(int nufus, int toplamMahalle) {
        if (nufus % toplamMahalle == 0) {
            return nufus;
        }

        // Yukarı yuvarlayarak bölünebilen en yakın sayı
        int sonuc = ((nufus / toplamMahalle) + 1) * toplamMahalle;
        return sonuc;
    }

    // Bir turu çalıştır
    private void turCalistir() {
        for (Sehir sehir : sehirler) {
            sehir.nufusArtir(faker, random);
            sehir.yaslariArtir();
        }
    }

    // Bölünme kontrolü
    private void bolunmeKontrol() {
        ArrayList<Sehir> yeniSehirler = new ArrayList<>();

        for (Sehir sehir : sehirler) {
            if (sehir.bolunmeliMi()) {
                Sehir yeniSehir = sehir.bolun(faker);
                yeniSehirler.add(yeniSehir);
            }
        }

        sehirler.addAll(yeniSehirler);
    }

    // Ekrandaki nüfus gösterimi (satır başına 5 şehir)
    private void nufuslariGoster() {
        for (int i = 0; i < sehirler.size(); i++) {
            System.out.print("[" + sehirler.get(i).getNufus() + "]");
            if ((i + 1) % 5 == 0) {
                System.out.println();
            } else if (i < sehirler.size() - 1) {
                System.out.print("-");
            }
        }
        System.out.println();
    }

    // Başlangıç nüfuslarını göster
    private void baslangicGoster() {
        System.out.println("Baslangic nufuslari:");
        nufuslariGoster();
        System.out.println();
    }

    // Ekran temizleme
    private void ekranTemizle() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Oyun sonu: şehir detay gösterimi
    private void oyunSonu() {
        System.out.println("\nOyun bitti! Son nufuslar:");
        nufuslariGoster();

        System.out.print("\nSatir giriniz: ");
        int satir = scanner.nextInt();
        System.out.print("Sutun giriniz: ");
        int sutun = scanner.nextInt();

        int index = satir * 5 + sutun;

        if (index >= 0 && index < sehirler.size()) {
            System.out.println();
            sehirler.get(index).detayliYazdir();
        } else {
            System.out.println("Gecersiz satir/sutun!");
        }

        System.out.println("\nCikmak icin herhangi bir tusa basin...");
        scanner.nextLine(); // buffer temizle
        scanner.nextLine(); // tuş bekle
    }
}