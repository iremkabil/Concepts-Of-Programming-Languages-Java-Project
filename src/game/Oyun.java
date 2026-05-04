/**
 * @author İrem Kabil ve irem.kabil@ogr.sakarya.edu.tr
 * @since 2026-03-30
 * <p>
 * Oyun sinifi: Tum simulasyonun kontrol merkezidir.
 * Kullanicidan alinan kodlarla sehirler decode edilir,
 * tur dongusu icerisinde nufus artisi ve sehir bolunmesi
 * gerceklestirilir. Konsol arayuzu ile kullaniciya
 * gorsel bir deneyim sunulur.
 * </p>
 */

package game;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Locale;


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

    // Oyunu baslat
    public void basla() {
        hosGeldinMesaji();
        turSayisiAl();
        sayilariAl();
        baslangicGoster();

        for (int tur = 1; tur <= turSayisi; tur++) {
            turCalistir();
            bolunmeKontrol();
            ekranTemizle();
            turBilgisiGoster(tur);
            nufuslariGoster();
        }

        oyunSonu();
    }

    // Hos geldin mesaji
    private void hosGeldinMesaji() {
        System.out.println("+--------------------------------------------+");
        System.out.println("|     SEHIR NUFUS SIMULASYONU                |");
        System.out.println("|     Programlama Dillerinin Prensipleri      |");
        System.out.println("+--------------------------------------------+");
        System.out.println();
    }

    // Kullanicidan tur sayisini al
    private void turSayisiAl() {
        System.out.print(">> Kac tur oynanacak? : ");
        turSayisi = scanner.nextInt();
        scanner.nextLine();
    }

    // Kullanicidan sayilari al ve sehirleri olustur
    private void sayilariAl() {
        System.out.print(">> Sehir kodlarini giriniz (boslukla ayrilmis): ");
        String satir = scanner.nextLine();
        String[] parcalar = satir.split(" ");

        System.out.println("\n--- Sehirler olusturuluyor ---");
        for (String parca : parcalar) {
            try {
                int sayi = Integer.parseInt(parca.trim());
                sehirOlustur(sayi);
            } catch (NumberFormatException e) {
                System.out.println("  [!] '" + parca + "' sayi degil! Atlaniyor.");
            }
        }

        if (sehirler.isEmpty()) {
            System.out.println("  [!] Hicbir gecerli sehir olusturulamadi! Program sonlaniyor.");
            System.exit(0);
        }

        System.out.println("--- " + sehirler.size() + " sehir basariyla olusturuldu ---\n");
    }
    // Bir sayidan sehir olustur (decode islemi)
    private void sehirOlustur(int sayi) {
        if (sayi < 10 || sayi > 99) {
            System.out.println("  [!] " + sayi + " gecersiz! (10-99 arasi olmali) Atlaniyor.");
            return;
        }
        int ilceSayisi = sayi / 10;
        int mahalleSayisi = sayi % 10;

        mahalleSayisi = mahalleDuzelt(ilceSayisi, mahalleSayisi);

        int duzeltilmisSayi = ilceSayisi * 10 + mahalleSayisi;
        int toplamMahalle = mahalleSayisi;
        int nufus = nufusDuzelt(duzeltilmisSayi, toplamMahalle);
        int kisiPerMahalle = nufus / toplamMahalle;
        int mahallePerIlce = toplamMahalle / ilceSayisi;

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
        System.out.println("  [+] " + sehir.getAd() + " olusturuldu (Kod: " + sayi + " -> Nufus: " + nufus + ", " + ilceSayisi + " ilce, " + toplamMahalle + " mahalle)");
    }

    // Mahalle sayisi duzeltmesi
    private int mahalleDuzelt(int ilceSayisi, int mahalleSayisi) {
        if (mahalleSayisi != 0 && mahalleSayisi % ilceSayisi == 0) {
            return mahalleSayisi;
        }

        for (int y = mahalleSayisi + 1; y <= 9; y++) {
            if (y % ilceSayisi == 0) {
                return y;
            }
        }

        for (int y = 1; y < mahalleSayisi; y++) {
            if (y % ilceSayisi == 0) {
                return y;
            }
        }

        return ilceSayisi;
    }

    // Nufus duzeltmesi
    private int nufusDuzelt(int nufus, int toplamMahalle) {
        if (nufus % toplamMahalle == 0) {
            return nufus;
        }

        int sonuc = ((nufus / toplamMahalle) + 1) * toplamMahalle;
        return sonuc;
    }

    // Bir turu calistir
    private void turCalistir() {
        for (Sehir sehir : sehirler) {
            sehir.nufusArtir(faker, random);
            sehir.yaslariArtir();
        }
    }

    // Bolunme kontrolu
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

    // Tur bilgisi goster
    private void turBilgisiGoster(int tur) {
        System.out.println("============================================");
        System.out.println("  Tur " + tur + "/" + turSayisi + " tamamlandi");
        System.out.println("  Toplam sehir: " + sehirler.size());
        System.out.println("============================================");
        System.out.println();
    }
    // Nufus gosterimi (satirlari ve sutunlari numarali)
    private void nufuslariGoster() {
        // Sutun basliklarini yazdir
        System.out.print("        ");
        int toplamSutun = Math.min(sehirler.size(), 5);
        for (int s = 0; s < toplamSutun; s++) {
            System.out.print("  Sutun " + s + "  ");
            if (s < toplamSutun - 1) System.out.print(" ");
        }
        System.out.println();

        System.out.print("        ");
        for (int s = 0; s < toplamSutun; s++) {
            System.out.print("---------");
            if (s < toplamSutun - 1) System.out.print("-");
        }
        System.out.println();

        // Satirlari yazdir
        int satirSayisi = (sehirler.size() + 4) / 5;
        for (int i = 0; i < satirSayisi; i++) {
            System.out.print("Satir " + i + " ");
            for (int j = 0; j < 5; j++) {
                int index = i * 5 + j;
                if (index < sehirler.size()) {
                    String nufusStr = String.valueOf(sehirler.get(index).getNufus());
                    int bosluk = 9 - nufusStr.length() - 2;
                    int sol = bosluk / 2;
                    int sag = bosluk - sol;
                    System.out.print(tekrarla(" ", sol) + "[" + nufusStr + "]" + tekrarla(" ", sag));
                    if (j < 4 && index + 1 < sehirler.size()) {
                        System.out.print("-");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Baslangic nufuslarini goster
    private void baslangicGoster() {
        System.out.println("============ BASLANGIC DURUMU ============");
        nufuslariGoster();
    }

    // Ekran temizleme
    private void ekranTemizle() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Oyun sonu
    private void oyunSonu() {
        System.out.println("============================================");
        System.out.println("            OYUN SONA ERDI                  ");
        System.out.println("============================================");
        System.out.println();

        // Toplam nufus hesapla
        int toplamNufus = 0;
        for (Sehir sehir : sehirler) {
            toplamNufus += sehir.getNufus();
        }
        System.out.println("  Toplam sehir sayisi : " + sehirler.size());
        System.out.println("  Toplam nufus        : " + toplamNufus);
        System.out.println("  Oynanan tur sayisi  : " + turSayisi);
        System.out.println();

        System.out.println("--- Son Nufus Tablosu ---");
        System.out.println("(Detay gormek istediginiz sehrin satir ve sutun numarasini giriniz)");
        System.out.println();
        nufuslariGoster();

        System.out.print(">> Satir numarasi giriniz : ");
        int satir = scanner.nextInt();
        System.out.print(">> Sutun numarasi giriniz : ");
        int sutun = scanner.nextInt();

        int index = satir * 5 + sutun;

        if (index >= 0 && index < sehirler.size()) {
            System.out.println();
            sehirler.get(index).detayliYazdir();
        } else {
            System.out.println("\n[!] Gecersiz satir/sutun! Tablodaki numaralari kullaniniz.");
            System.out.println("    Satir: 0-" + ((sehirler.size() - 1) / 5) + ", Sutun: 0-4");
        }

        System.out.println("\nCikmak icin Enter'a basin...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
        System.exit(0);
    }

    // Yardimci metod: belirli karakteri n kez tekrarla
    private String tekrarla(String karakter, int adet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adet; i++) {
            sb.append(karakter);
        }
        return sb.toString();
    }
}