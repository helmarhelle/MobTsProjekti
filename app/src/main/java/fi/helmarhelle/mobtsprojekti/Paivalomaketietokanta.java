package fi.helmarhelle.mobtsprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author Reima
 * @version 12.12.2021
 * @since 9.12.2021
 * Viikkotavoitetietokanta -luokka toimii rajapintana SQLite-tietokannalle johon tallennetaan jokaisen viikon tavoitteet.
 *
 */

public class Paivalomaketietokanta extends SQLiteOpenHelper {

    //Vakiot
    private final String TIETOKANNAN_NIMI = "PAIVALOMAKE";
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

    public Paivalomaketietokanta (@Nullable Context context) {
        super(context, "Paivalomake.db", null, 1);
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

    /**
     * <p>Ottaa paivalomakeosion sisaansa ja tallentaa sen parametrit tietokantaan. Tarvitsee myos kontekstin</p>
     * <p>Hyodyntaa viikkotavoitetietokantaa, joten sen pitaa olla ajan tasalla ennen paivalomaketietokannan kayttoa.</p>
     * @param paivaLomake
     * @return Onnistuiko tietojen lisays vai ei.
     */
    public boolean lisaaTiedot (PaivaLomake paivaLomake, Context context) {

        Viikkotavoitetietokanta viikkotavoitetietokanta = new Viikkotavoitetietokanta (context);
        SQLiteDatabase tietokanta = this.getWritableDatabase();
        ContentValues sisalto = new ContentValues();

        sisalto.put(PAIVA_SARAKE, LocalDateTime.now().getDayOfMonth());
        sisalto.put(VUODENPAIVA_SARAKE, LocalDateTime.now().getDayOfYear());
        sisalto.put(KUUKAUSI_SARAKE, LocalDateTime.now().getMonth().getValue());
        sisalto.put(VUOSI_SARAKE, LocalDateTime.now().getYear());
        //Jos lomakkeeseen on syötetty käyttäjän nukkuneen tarpeeksi, haetaan tuntimäärä tietokannasta
        if (paivaLomake.isNukuttuTarpeeksi()) {
            int unenKestoTavoite = viikkotavoitetietokanta.haeTamanViikonUniTavoite();
            sisalto.put(UNI_SARAKE, unenKestoTavoite);
        } else {
            sisalto.put(UNI_SARAKE, paivaLomake.getUniH());
        }

        if (paivaLomake.isNukuttuTarpeeksi()) {
            int unenKestoTavoite = viikkotavoitetietokanta.haeTamanViikonUniTavoite();
            sisalto.put(UNI_SARAKE, unenKestoTavoite);
        } else {
            sisalto.put(UNI_SARAKE, paivaLomake.getUniH());
        }

        sisalto.put(LIIKUNTA_SARAKE, paivaLomake.getLiikuntaKM());
        sisalto.put(ULKONASYONNIT_SARAKE, paivaLomake.getUlkonaSyonnitKPL());
        sisalto.put(LENKKI_SARAKE, paivaLomake.getLenkitKM());
        sisalto.put(SALI_SARAKE, paivaLomake.getSaliKaynnitKPL());

        long insert = tietokanta.insert(TIETOKANNAN_NIMI, null, sisalto);
        return insert != -1;
    }

    /**
     * <p>Tarkistaa, onko tietokannassa lomake talle paivalle.</p>
     * <p>Tekee sen valitsemalla SQL-hakulausekkeella tietokannasta vuodenpaiva-sarakkeen ja vertaamalla sita tahan paivaan siten, etta tiedon tulee olla merkattu samana paivana.</p>
     * @return Onko lomaketta tälle paivalle vai ei.
     */
    public boolean onkoLomakettaTallePaivalle () {

        String hakuLauseke_SQL = "SELECT VUODENPAIVA, VUOSI FROM " + TIETOKANNAN_NIMI;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        //Jos tietokannassa on dataa eli moveToFirst palauttaa true, siirrytään viimeiselle riville ja tarkistetaan, että ajankohdat ovat samat.
        if (kursori.moveToFirst()) {
            kursori.moveToLast();
            int tamaPaiva = LocalDateTime.now().getDayOfYear();
            int viimeiseimmanRivinPaiva = kursori.getInt(0);

            int kuluvaVuosi = LocalDateTime.now().getYear();
            int viimeiseimmanRivinVuosi = kursori.getInt(1);

            //Tarkistetaan, että päivät ja vuodet ovat samat.
            if (tamaPaiva == viimeiseimmanRivinPaiva && kuluvaVuosi == viimeiseimmanRivinVuosi) {
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
     * <p>Getteri tanaan merkatulle unen kestolle</p>
     * @return Talle paivalle merkatun unen keston tunteina
     */
    public int haeTamanPaivanUnenKesto() {

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
     * <p>Getteri tanaan merkatun liikunnan pituudelle</p>
     * @return Talle paivalle merkatun liikunnan kilometreina
     */
    public int haeTamanPaivanLiikunnanPituus() {

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
     * <p>Getteri tanaan merkatun lenkin pituudelle</p>
     * @return Talle paivalle merkatun lenkin kilometreina
     */
    public int haeTamanPaivanLenkinpituus() {

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
}
