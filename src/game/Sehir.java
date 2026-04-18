/**
 * @author İrem Kabil ve irem.kabil@ogr.sakarya.edu.tr
 * @since 2026-03-29
 * <p>
 * Sehir sinifi: Bir sehri temsil eder. Ilceleri bunyesinde barindirir.
 * Nufus artis carpanini hesaplar, bolunme kosullarini kontrol eder
 * ve gerektiginde sehri ikiye bolerek yeni bir sehir olusturur.
 * </p>
 */

package game;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

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

    // Nufus artis carpanini hesapla: nufusun birler + onlar basamagi
    public int getCarpan() {
        int nufus = getNufus();
        int birler = nufus % 10;
        int onlar = (nufus / 10) % 10;
        return birler + onlar;
    }

    // Tur sonunda nufus artisi
    public void nufusArtir(Faker faker, Random random) {
        int carpan = getCarpan();
        for (Ilce ilce : ilceler) {
            ilce.nufusArtir(carpan, faker, random);
        }
    }

    // Her tur sonunda yaslari artir
    public void yaslariArtir() {
        for (Ilce ilce : ilceler) {
            ilce.yaslariArtir();
        }
    }

    // Sehrin nufusu 4 basamakli mi (>= 1000)?
    public boolean bolunmeliMi() {
        return getNufus() >= 1000 && ilceler.size() > 1;
    }
    
    // Sehri bol: ilcelerin yarisini yeni sehre tasi
    public Sehir bolun(Faker faker) {
        Sehir yeniSehir = new Sehir(faker.address().city());

        int toplamIlce = ilceler.size();
        int tasinacak = toplamIlce / 2; // tek sayiysa yeni sehirde 1 eksik

        // Listenin sonundan tasi
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

    // Detayli yazdirma (oyun sonu icin)
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