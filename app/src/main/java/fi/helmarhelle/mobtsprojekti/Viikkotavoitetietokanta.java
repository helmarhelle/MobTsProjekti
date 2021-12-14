package fi.helmarhelle.mobtsprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Reima
 * @version 14.12.2021
 * @since 7.12.2021
 * <p>Viikkotavoitetietokanta -luokka toimii rajapintana SQLite-tietokannalle johon tallennetaan jokaisen viikon tavoitteet.</p>
 *
 */

public class Viikkotavoitetietokanta extends SQLiteOpenHelper {

    //Vakiot
    private final String TIETOKANNAN_NIMI = "TAVOITE";
    private final String ID = "ID";

    private final String PAIVA_SARAKE = "PAIVA";
    private final String VUODENPAIVA_SARAKE = "VUODENPAIVA";
    private final String KUUKAUSI_SARAKE = "KUUKAUSI";
    private final String VUOSI_SARAKE = "VUOSI";
    private final String UNI_SARAKE = "UNI";
    private final String LIIKUNTA_SARAKE = "LIIKUNTA";
    private final String ULKONASYONNIT_SARAKE = "ULKONASYONNIT";
    private final String LENKKI_SARAKE = "LENKKI";
    private final String SALI_SARAKE = "SALI";

    public Viikkotavoitetietokanta (@Nullable Context context) {
        super(context, "tavoite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String taulunLuontiLause_SQL = "CREATE TABLE " + TIETOKANNAN_NIMI + " (" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PAIVA_SARAKE + " INTEGER, " + VUODENPAIVA_SARAKE + " INTEGER, " + KUUKAUSI_SARAKE + " INTEGER, " + VUOSI_SARAKE + " INTEGER, " + UNI_SARAKE + " INTEGER, " + LIIKUNTA_SARAKE + " INTEGER, " + ULKONASYONNIT_SARAKE + " INTEGER, " + LENKKI_SARAKE + " INTEGER, " + SALI_SARAKE + " INTEGER)";

        db.execSQL(taulunLuontiLause_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean lisaaTiedot (Viikkotavoite viikkotavoite) {

        SQLiteDatabase tietokanta = this.getWritableDatabase();
        ContentValues sisalto = new ContentValues();

        sisalto.put(PAIVA_SARAKE, LocalDateTime.now().getDayOfMonth());
        sisalto.put(VUODENPAIVA_SARAKE, LocalDateTime.now().getDayOfYear());
        sisalto.put(KUUKAUSI_SARAKE, LocalDateTime.now().getMonth().getValue());
        sisalto.put(VUOSI_SARAKE, LocalDateTime.now().getYear());
        sisalto.put(UNI_SARAKE, viikkotavoite.getUniH());
        sisalto.put(LIIKUNTA_SARAKE, viikkotavoite.getLiikuntaKM());
        sisalto.put(ULKONASYONNIT_SARAKE, viikkotavoite.getUlkonaSyonnitKPL());
        sisalto.put(LENKKI_SARAKE, viikkotavoite.getLenkitKM());
        sisalto.put(SALI_SARAKE, viikkotavoite.getSaliKaynnitKPL());

        long insert = tietokanta.insert(TIETOKANNAN_NIMI, null, sisalto);
        return insert != -1;
    }

    /**
     * <p>tarkistaa, onko tietokannassa tavoitetta kuluvalle viikolle.</p>>
     * <p>Tekee sen valitsemalla SQL-hakulausekkeella tietokannasta vuodenpäivä-sarakkeen ja vertaamalla sitä tähän päivään siten, että tiedon tulee olla max. 6 päivää vanhaa.</p>
     * @return Onko tavoitetta tälle viikolle vai ei.
     */
    public boolean onkoTavoitettaKuluvalleViikolle () {

        String hakuLauseke_SQL = "SELECT VUODENPAIVA FROM " + TIETOKANNAN_NIMI;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        //Jos tietokannassa on dataa eli moveToFirst palauttaa true, siirrytään viimeiselle riville ja tarkistetaan, että tieto on alle 7 päivää vanha.
        if(kursori.moveToFirst()) {
            kursori.moveToLast();
            int tamaPaiva = LocalDateTime.now().getDayOfYear();
            int viimeiseimmanRivinPaiva = kursori.getInt(0);
            int erotus = tamaPaiva - viimeiseimmanRivinPaiva;
            //Jos tämä päivä on seuraavan vuoden puolella, erotuksesta tulee negatiivinen vastaus, joka muutetaan järkeväksi luvuksi
            if (erotus < 0) {
                erotus = (365 + tamaPaiva) - viimeiseimmanRivinPaiva;
            }
            if (erotus < 7) {
                kursori.close();
                tietokanta.close();
                return true;
            }
        }
        kursori.close();
        tietokanta.close();
        return false;
    }

    /**
     * Getteri tämän viikon unitavoitteelle
     * @return unitavoitteen kokonaislukuna
     */
    public int haeTamanViikonUniTavoite() {

        String hakuLauseke_SQL = "SELECT UNI FROM " + TIETOKANNAN_NIMI;
        int haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }

    /**
     * Getteri valitun kuukauden valituille tavoitteille.
     * @param kuukausi haluttu kuukausi kokonaislukuna.
     * @param tavoite joko uni, liikunta, syonti, lenkki tai sali.
     * @return listan, jossa jokaisen loytyneen viikon tavoite on oma alkionsa (indeksit valilla 0-3).
     */
    public ArrayList<Integer> haeKuukaudenTavoitteet(int kuukausi, String tavoite) {

        String hakuLauseke_SQL;
        //Palautettava tavoite valitaan merkkijonon perusteella
        if (tavoite.contains("uni")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, UNI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (tavoite.contains("liikunta")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LIIKUNTA FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (tavoite.contains("syonti")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, ULKONASYONNIT FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (tavoite.contains("lenkki")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LENKKI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else {
            hakuLauseke_SQL = "SELECT KUUKAUSI, SALI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        }

        ArrayList <Integer> haetutTavoitteet = new ArrayList<>();

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        if (kursori.moveToFirst()) {
            haetutTavoitteet.add(kursori.getInt(1));
            while (kursori.moveToNext()) {
                haetutTavoitteet.add(kursori.getInt(1));
            }
        }
        kursori.close();
        tietokanta.close();
        return haetutTavoitteet;
    }

    /**
     * Getteri tämän viikon liikuntatavoitteelle
     * @return palauttaa kokonaisluvun
     */
    public int haeTamanViikonLiikuntaTavoite() {

        String hakuLauseke_SQL = "SELECT LIIKUNTA FROM " + TIETOKANNAN_NIMI;
        int haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }
    /**
     * Getteri tämän viikon ulkonasyömistavoitteelle
     * @return palauttaa kokonaisluvun
     */
    public int haeTamanViikonUlkonasyonnitTavoite() {

        String hakuLauseke_SQL = "SELECT ULKONASYONNIT FROM " + TIETOKANNAN_NIMI;
        int haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }

    /**
     * Getteri tämän viikon lenkkien pituustavoitteelle
     * @return palauttaa kokonaisluvun
     */
    public int haeTamanViikonLenkkiTavoite() {

        String hakuLauseke_SQL = "SELECT LENKKI FROM " + TIETOKANNAN_NIMI;
        int haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }
    /**
     * Getteri tämän viikon salitreenien tavoitemäärälle
     * @return palauttaa kokonaisluvun
     */
    public int haeTamanViikonSaliTavoite() {

        String hakuLauseke_SQL = "SELECT SALI FROM " + TIETOKANNAN_NIMI;
        int haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }
}
