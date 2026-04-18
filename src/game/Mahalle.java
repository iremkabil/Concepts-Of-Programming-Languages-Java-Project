/**
 * @author İrem Kabil ve irem.kabil@ogr.sakarya.edu.tr
 * @since 2026-03-28
 * <p>
 * Mahalle sinifi: Bir mahalleyi ve icerisinde yasayan kisileri yonetir.
 * Nufus artisi sirasinda yeni kisi nesneleri olusturulur ve
 * her tur sonunda tum kisilerin yaslari bir artirilir.
 * </p>
 */

package game;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

public class Mahalle {
    private String ad;
    private ArrayList<Kisi> kisiler;

    public Mahalle(String ad) {
        this.ad = ad;
        this.kisiler = new ArrayList<>();
    }

    // Baslangicta belirli sayida kisi ekler
    public void baslangicKisileriniOlustur(int kisiSayisi, Faker faker, Random random) {
        for (int i = 0; i < kisiSayisi; i++) {
            String isim = faker.name().firstName();
            String soyisim = faker.name().lastName();
            int yas = random.nextInt(51); // 0-50 arasi
            kisiler.add(new Kisi(isim, soyisim, yas));
        }
    }

    // Tur sonunda nufus artisi: mevcut kisi sayisi x carpan
    public void nufusArtir(int carpan, Faker faker, Random random) {
        if (carpan == 0) {
            // Carpan 0 ise sadece 1 kisi ekle
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

    // Her tur sonunda tum kisilerin yasini 1 arttir
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