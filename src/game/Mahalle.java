package game;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author [Ad Soyad] ve [email]
 * @since 2026-04-14
 * <p>
 * Mahalle sinifi: Bir mahalleyi temsil eder.
 * Mahallede yasayan kisileri tutar ve nufus artisini yonetir.
 * </p>
 */
public class Mahalle {
    private String ad;
    private ArrayList<Kisi> kisiler;

    public Mahalle(String ad) {
        this.ad = ad;
        this.kisiler = new ArrayList<>();
    }

    // Başlangıçta belirli sayıda kişi ekler
    public void baslangicKisileriniOlustur(int kisiSayisi, Faker faker, Random random) {
        for (int i = 0; i < kisiSayisi; i++) {
            String isim = faker.name().firstName();
            String soyisim = faker.name().lastName();
            int yas = random.nextInt(51); // 0-50 arası
            kisiler.add(new Kisi(isim, soyisim, yas));
        }
    }

    // Tur sonunda nüfus artışı: mevcut kişi sayısı x çarpan
    public void nufusArtir(int carpan, Faker faker, Random random) {
        if (carpan == 0) {
            // Çarpan 0 ise sadece 1 kişi ekle
            String isim = faker.name().firstName();
            String soyisim = faker.name().lastName();
            int yas = random.nextInt(51);
            kisiler.add(new Kisi(isim, soyisim, yas));
        } else {
            int mevcutKisiSayisi = kisiler.size();
            int yeniKisiSayisi = mevcutKisiSayisi * carpan - mevcutKisiSayisi;
            for (int i = 0; i < yeniKisiSayisi; i++) {
                String isim = faker.name().firstName();
                String soyisim = faker.name().lastName();
                int yas = random.nextInt(51);
                kisiler.add(new Kisi(isim, soyisim, yas));
            }
        }
    }

    // Her tur sonunda tüm kişilerin yaşını 1 artır
    public void yaslariArtir() {
        for (Kisi kisi : kisiler) {
            kisi.yasArtir();
        }
    }

    public int getNufus() {
        return kisiler.size();
    }

    public String getAd() {
        return ad;
    }

    public ArrayList<Kisi> getKisiler() {
        return kisiler;
    }
}