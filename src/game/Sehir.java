package game;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author [Ad Soyad] ve [email]
 * @since 2026-04-14
 * <p>
 * Sehir sinifi: Bir sehri temsil eder.
 * Ilceleri yonetir, nufus artisini ve sehir bolunmesini destekler.
 * </p>
 */
public class Sehir {
    private String ad;
    private ArrayList<Ilce> ilceler;

    public Sehir(String ad) {
        this.ad = ad;
        this.ilceler = new ArrayList<>();
    }

    public void ilceEkle(Ilce ilce) {
        ilceler.add(ilce);
    }

    // Nüfus artış çarpanını hesapla: nüfusun birler + onlar basamağı
    public int getCarpan() {
        int nufus = getNufus();
        int birler = nufus % 10;
        int onlar = (nufus / 10) % 10;
        return birler + onlar;
    }

    // Tur sonunda nüfus artışı
    public void nufusArtir(Faker faker, Random random) {
        int carpan = getCarpan();
        for (Ilce ilce : ilceler) {
            ilce.nufusArtir(carpan, faker, random);
        }
    }

    // Her tur sonunda yaşları artır
    public void yaslariArtir() {
        for (Ilce ilce : ilceler) {
            ilce.yaslariArtir();
        }
    }

    // Şehrin nüfusu 4 basamaklı mı (>= 1000)?
    public boolean bolunmeliMi() {
        return getNufus() >= 1000 && ilceler.size() > 1;
    }
    
    // Şehri böl: ilçelerin yarısını yeni şehre taşı
    public Sehir bolun(Faker faker) {
        Sehir yeniSehir = new Sehir(faker.address().city());

        int toplamIlce = ilceler.size();
        int tasinacak = toplamIlce / 2; // tek sayıysa yeni şehirde 1 eksik

        // Listenin sonundan taşı
        for (int i = 0; i < tasinacak; i++) {
            Ilce tasinanIlce = ilceler.remove(ilceler.size() - 1);
            yeniSehir.ilceEkle(tasinanIlce);
        }

        return yeniSehir;
    }

    public int getNufus() {
        int toplam = 0;
        for (Ilce ilce : ilceler) {
            toplam += ilce.getNufus();
        }
        return toplam;
    }

    public String getAd() {
        return ad;
    }

    public ArrayList<Ilce> getIlceler() {
        return ilceler;
    }

    // Detaylı yazdırma (oyun sonu için)
    public void detayliYazdir() {
        String ayrac = "========================================";
        String altAyrac = "----------------------------------------";

        System.out.println(ayrac);
        System.out.println("  SEHIR: " + ad);
        System.out.println("  TOPLAM NUFUS: " + getNufus());
        System.out.println(ayrac);

        for (int i = 0; i < ilceler.size(); i++) {
            Ilce ilce = ilceler.get(i);
            System.out.println("  +-- Ilce " + (i + 1) + ": " + ilce.getAd());
            System.out.println("  |   Nufus: " + ilce.getNufus());

            for (int j = 0; j < ilce.getMahalleler().size(); j++) {
                Mahalle mah = ilce.getMahalleler().get(j);
                System.out.println("  |   +-- Mahalle " + (j + 1) + ": " + mah.getAd());
                System.out.println("  |   |   Nufus: " + mah.getNufus());
                System.out.println("  |   |   Kisiler:");

                for (Kisi kisi : mah.getKisiler()) {
                    System.out.println("  |   |     " + kisi.toString());
                }

                if (j < ilce.getMahalleler().size() - 1) {
                    System.out.println("  |   |");
                }
            }

            if (i < ilceler.size() - 1) {
                System.out.println("  |");
                System.out.println("  " + altAyrac);
            }
        }

        System.out.println(ayrac);
    }
}