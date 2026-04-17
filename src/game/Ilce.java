
package game;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author [Ad Soyad] ve [email]
 * @since 2026-04-14
 * <p>
 * Ilce sinifi: Bir ilceyi temsil eder.
 * Ilceye bagli mahalleleri tutar ve yonetir.
 * </p>
 */
public class Ilce {
    private String ad;
    private ArrayList<Mahalle> mahalleler;

    public Ilce(String ad) {
        this.ad = ad;
        this.mahalleler = new ArrayList<>();
    }

    public void mahalleEkle(Mahalle mahalle) {
        mahalleler.add(mahalle);
    }

    // Tur sonunda tüm mahallelerde nüfus artışı
    public void nufusArtir(int carpan, Faker faker, Random random) {
        for (Mahalle mahalle : mahalleler) {
            mahalle.nufusArtir(carpan, faker, random);
        }
    }

    // Her tur sonunda tüm kişilerin yaşını artır
    public void yaslariArtir() {
        for (Mahalle mahalle : mahalleler) {
            mahalle.yaslariArtir();
        }
    }

    public int getNufus() {
        int toplam = 0;
        for (Mahalle mahalle : mahalleler) {
            toplam += mahalle.getNufus();
        }
        return toplam;
    }

    public String getAd() {
        return ad;
    }

    public ArrayList<Mahalle> getMahalleler() {
        return mahalleler;
    }
}