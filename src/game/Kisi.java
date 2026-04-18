/**
 * @author İrem Kabil ve irem.kabil@ogr.sakarya.edu.tr
 * @since 2026-03-28
 * <p>
 * Kisi sinifi: Simulasyondaki her bir bireyi temsil eder.
 * Her kisinin oyun genelinde benzersiz bir ID numarasi,
 * Faker ile uretilmis isim-soyisim bilgisi ve yasi bulunur.
 * </p>
 */

package game;

public class Kisi {
    private static int sayac = 0; // unique ID uretmek icin sayac

    private int id;
    private String isim;
    private String soyisim;
    private int yas;

    public Kisi(String isim, String soyisim, int yas) {
        this.id = ++sayac; // her yeni kisi benzersiz ID alir
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