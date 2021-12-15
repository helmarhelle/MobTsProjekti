package fi.helmarhelle.mobtsprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <p>Viikkotavoitetietokanta -luokka toimii rajapintana SQLite-tietokannalle johon tallennetaan jokaisen viikon tavoitteet.</p>
 * @author Reima
 * @since 9.12.2021
 * @version 14.12.2021
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

    /**
     * <p>Konstruktori Paivalomaketietokantaoliolle.</p>
     * @param context   aktiviteetti, jossa luokkaa kaytetaan.
     */
    public Paivalomaketietokanta (@Nullable Context context) {
        super(context, "paivalomake.db", null, 1);
    }

    /**
     * <p>Varsinainen tietokannan luontimetodi - luo taulun ja sarakkeet.</p>
     * @param db    Tietokanta
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String taulunLuontiLause_SQL = "CREATE TABLE " + TIETOKANNAN_NIMI + " (" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PAIVA_SARAKE + " INTEGER, " + VUODENPAIVA_SARAKE + " INTEGER, " + KUUKAUSI_SARAKE + " INTEGER, " + VUOSI_SARAKE + " INTEGER, " + UNI_SARAKE + " INTEGER, " + LIIKUNTA_SARAKE + " REAL, " + ULKONASYONNIT_SARAKE + " INTEGER, " + LENKKI_SARAKE + " REAL, " + SALI_SARAKE + " INTEGER)";

        db.execSQL(taulunLuontiLause_SQL);
    }

    /**
     * <p>Tietokannan paivitysmetodi - ei tarvita tassa ohjelmassa</p>
     * @param db    tietokannan nimi
     * @param oldVersion    vanha versionumero
     * @param newVersion    uusi versionumero
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * <p>Ottaa paivalomakeosion sisaansa ja tallentaa sen parametrit seka tallentamisajankohdan tietokantaan.</p>
     * <p>Hyodyntaa Viikkotavoitetietokantaa, joten sen pitaa olla ajan tasalla ennen Paivalomaketietokannan kayttoa.</p>
     * @param paivaLomake Taytetty paivalomake.
     * @param context Se aktiviteetti jossa lisaaTiedot-metodia kutsutaan.
     * @return Onnistuiko tietojen tallentaminen vai ei.
     */
    public boolean lisaaTiedot (PaivaLomake paivaLomake, Context context) {

        Viikkotavoitetietokanta viikkotavoitetietokanta = new Viikkotavoitetietokanta (context);
        SQLiteDatabase tietokanta = this.getWritableDatabase();
        ContentValues sisalto = new ContentValues();

        sisalto.put(PAIVA_SARAKE, LocalDateTime.now().getDayOfMonth());
        sisalto.put(VUODENPAIVA_SARAKE, LocalDateTime.now().getDayOfYear());
        sisalto.put(KUUKAUSI_SARAKE, LocalDateTime.now().getMonth().getValue());
        sisalto.put(VUOSI_SARAKE, LocalDateTime.now().getYear());
        //Jos lomakkeeseen on syötetty käyttäjän nukkuneen tarpeeksi, haetaan tuntimäärä tietokannasta. Muutoin otetaan käyttäjän syöttämä määrä.
        if (paivaLomake.isNukuttuTarpeeksi()) {
            int unenKestoTavoite = viikkotavoitetietokanta.haeTamanViikonUniTavoite();
            sisalto.put(UNI_SARAKE, unenKestoTavoite);
        } else {
            sisalto.put(UNI_SARAKE, paivaLomake.getUniH());
        }

        //Jos lomakkeeseen on syötetty käyttäjän liikkuneen tarpeeksi, haetaan kilometrimäärä tietokannasta viikolle ja jaetaan se 7:llä. Muutoin otetaan käyttäjän syöttämä määrä.
        if (paivaLomake.isKaveltyTarpeeksi()) {
            //Muutetaan viikkotavoitetietokantaan tallennettu kokonaisluku liukuluvuksi.
            float tavoiteKilometrit = viikkotavoitetietokanta.haeTamanViikonLiikuntaTavoite();
            float kavelynMaaraPaivalle = tavoiteKilometrit/7;
            sisalto.put(LIIKUNTA_SARAKE, kavelynMaaraPaivalle);
        } else {
            sisalto.put(LIIKUNTA_SARAKE, paivaLomake.getLiikuntaKM());
        }

        //Jos lomakkeeseen on syötetty käyttäjän syöneen ulkona, merkataan hänen syöneen kerran ulkona. Muutoin otetaan käyttäjän syöttämä määrä.
        if (paivaLomake.isSyotyUlkona()) {
            sisalto.put(ULKONASYONNIT_SARAKE, 1);
        } else {
            sisalto.put(ULKONASYONNIT_SARAKE, paivaLomake.getUlkonaSyonnitKPL());
        }

        sisalto.put(LENKKI_SARAKE, paivaLomake.getLenkkiKilometritKM());
        sisalto.put(SALI_SARAKE, paivaLomake.getSaliKaynnitKPL());

        long insert = tietokanta.insert(TIETOKANNAN_NIMI, null, sisalto);
        return insert != -1;
    }

    /**
     * <p>Tarkistaa, onko tietokannassa lomake talle paivalle.</p>
     * <p>Tekee sen valitsemalla SQL-hakulausekkeella tietokannasta vuodenpaiva-sarakkeen ja vertaamalla sita tahan paivaan siten, etta tiedon tulee olla merkattu samana paivana.</p>
     * @return Onko lomaketta talle paivalle vai ei.
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
     * <p>Getteri valitun kuukauden valituille saavutuksille.</p>
     * @param kuukausi haluttu kuukausi kokonaislukuna.
     * @param saavutukset joko uni, liikunta, syonti, lenkki tai sali.
     * @return Listan, jossa jokainen loytynyt saavutus on oma alkionsa (indeksit valilla 0-30).
     */
    public ArrayList<Float> haeKuukaudenSaavutukset(int kuukausi, String saavutukset) {

        String hakuLauseke_SQL;
        //Palautettava saavutukset valitaan merkkijonon perusteella
        if (saavutukset.contains("uni")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, UNI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (saavutukset.contains("liikunta")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LIIKUNTA FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (saavutukset.contains("syonti")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, ULKONASYONNIT FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (saavutukset.contains("lenkki")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LENKKI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else if (saavutukset.contains("sali")){
            hakuLauseke_SQL = "SELECT KUUKAUSI, SALI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".KUUKAUSI =" + kuukausi;
        } else  {
            hakuLauseke_SQL = "";
        }

        ArrayList <Float> haetutTavoitteet = new ArrayList<>();

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        if (kursori.moveToFirst()) {

            haetutTavoitteet.add(kursori.getFloat(1));

            while (kursori.moveToNext()) {
                haetutTavoitteet.add(kursori.getFloat(1));
            }
        }
        kursori.close();
        tietokanta.close();
        return haetutTavoitteet;
    }
    /**
     * <p>Getteri kuluvan viikon saavutuksille.</p>
     * @param saavutukset joko uni, liikunta, syonti, lenkki tai sali.
     * @param context Se aktiviteetti jossa haeTamanViikonSaavutukset-metodia kutsutaan.
     * @return listan, jossa jokainen loytynyt saavutus on oma alkionsa (indeksit valilla 0-6).
     */
    public ArrayList<Float> haeTamanViikonSaavutukset(String saavutukset, Context context) {

        //Haetaan viikkotavoitteen tallennuspäivä. Haetaan ainoastaan saman päivän tietoja tai uudempia.
        Viikkotavoitetietokanta viikkotavoitetietokanta = new Viikkotavoitetietokanta (context);
        int viikkotavoitteenTallennusPaiva = viikkotavoitetietokanta.haeTamanViikonViikkotavoitteenTallennusPaiva();

        String hakuLauseke_SQL;
        //Palautettava saavutukset valitaan merkkijonon perusteella
        if (saavutukset.contains("uni")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, UNI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".VUODENPAIVA >=" + viikkotavoitteenTallennusPaiva;
        } else if (saavutukset.contains("liikunta")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LIIKUNTA FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".VUODENPAIVA >=" + viikkotavoitteenTallennusPaiva;
        } else if (saavutukset.contains("syonti")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, ULKONASYONNIT FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".VUODENPAIVA >=" + viikkotavoitteenTallennusPaiva;
        } else if (saavutukset.contains("lenkki")) {
            hakuLauseke_SQL = "SELECT KUUKAUSI, LENKKI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".VUODENPAIVA >=" + viikkotavoitteenTallennusPaiva;
        } else if (saavutukset.contains("sali")){
            hakuLauseke_SQL = "SELECT KUUKAUSI, SALI FROM " + TIETOKANNAN_NIMI + " WHERE " + TIETOKANNAN_NIMI + ".VUODENPAIVA >=" + viikkotavoitteenTallennusPaiva;
        } else  {
            hakuLauseke_SQL = "";
        }

        ArrayList <Float> haetutTavoitteet = new ArrayList<>();

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        if (kursori.moveToFirst()) {

            haetutTavoitteet.add(kursori.getFloat(1));

            while (kursori.moveToNext()) {
                haetutTavoitteet.add(kursori.getFloat(1));
            }
        }
        kursori.close();
        tietokanta.close();
        return haetutTavoitteet;
    }
    /**
     * <p>Getteri tanaan merkatulle unen kestolle.</p>
     * @return Talle paivalle merkatun unen keston tunteina.
     */
    public int haeTamanPaivanUnenKesto() {

        String hakuLauseke_SQL = "SELECT UNI FROM " + TIETOKANNAN_NIMI;
        int haettuTieto;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTieto = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTieto;
    }
    /**
     * <p>Getteri tanaan merkatun liikunnan pituudelle.</p>
     * @return Talle paivalle merkatun liikunnan kilometreina.
     */
    public float haeTamanPaivanLiikunnanPituus() {

        String hakuLauseke_SQL = "SELECT LIIKUNTA FROM " + TIETOKANNAN_NIMI;
        float haettuTieto;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTieto = kursori.getFloat(0);
        kursori.close();
        tietokanta.close();
        return haettuTieto;
    }
    /**
     * <p>Getteri tanaan merkatuille ulkonasyonneille.</p>
     * @return Talle paivalle merkatut ulkonasyonnit kokonaislukuna.
     */
    public int haeTamanPaivanUlkonaSyonnit() {

        String hakuLauseke_SQL = "SELECT ULKONASYONNIT FROM " + TIETOKANNAN_NIMI;
        int haettuTieto;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTieto = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuTieto;
    }

    /**
     * <p>Getteri tanaan merkatun lenkin pituudelle.</p>
     * @return Talle paivalle merkatun lenkin kilometreina.
     */
    public float haeTamanPaivanLenkinpituus() {

        String hakuLauseke_SQL = "SELECT LENKKI FROM " + TIETOKANNAN_NIMI;
        float haettuTavoite;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuTavoite = kursori.getFloat(0);
        kursori.close();
        tietokanta.close();
        return haettuTavoite;
    }


    /**
     * <p>Getteri tanaan merkatuille salikaynneille.</p>
     * @return Talle paivalle merkatut salikaynnit kokonaislukuna.
     */
    public int haeTamanPaivanSaliKaynnit() {

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
