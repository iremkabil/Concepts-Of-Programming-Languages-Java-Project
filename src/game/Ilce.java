/**
 * @author İrem Kabil ve irem.kabil@ogr.sakarya.edu.tr
 * @since 2026-03-28
 * <p>
 * Ilce sinifi: Bir ilceyi ve ona bagli mahalleleri yonetir.
 * Ilce nufusu, bagli mahallelerdeki toplam kisi sayisindan hesaplanir.
 * </p>
 */


package game;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

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

    // Tur sonunda tum mahallelerde nufus artisi
    public void nufusArtir(int carpan, Faker faker, Random random) {
        for (Mahalle mahalle : mahalleler) {
            mahalle.nufusArtir(carpan, faker, random);
        }
    }

    // Her tur sonunda tum kisilerin yasini attir
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