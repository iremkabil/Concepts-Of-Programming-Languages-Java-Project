package game;
/**
 * @author [Ad Soyad] ve [email]
 * @since 2026-04-14
 * <p>
 * Kisi sinifi: Oyundaki her bir bireyi temsil eder.
 * Her kisinin benzersiz bir ID'si, ismi, soyismi ve yasi vardir.
 * </p>
 */
public class Kisi {
    private static int sayac = 0; // unique ID üretmek için sayaç

    private int id;
    private String isim;
    private String soyisim;
    private int yas;

    public Kisi(String isim, String soyisim, int yas) {
        this.id = ++sayac; // her yeni kişi benzersiz ID alır
        this.isim = isim;
        this.soyisim = soyisim;
        this.yas = yas;
    }

    public void yasArtir() {
        this.yas++;
    }

    public int getId() {
        return id;
    }

    public String getIsim() {
        return isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public int getYas() {
        return yas;
    }

    @Override
    public String toString() {
        return id + " - " + isim + " " + soyisim + " - " + yas;
    }
}