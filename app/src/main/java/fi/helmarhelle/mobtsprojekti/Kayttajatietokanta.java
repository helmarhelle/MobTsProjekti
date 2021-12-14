package fi.helmarhelle.mobtsprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * @author  Reima
 * @since 7.12.2021
 * @version 14.12.2021
 * <p>Käyttäjätietokanta -luokka toimii rajapintana SQLite-tietokannalle johon tallennetaan Käyttäjän tiedot.</p>
 */

public class Kayttajatietokanta extends SQLiteOpenHelper {

    //Vakiot
    private final String TIETOKANNAN_NIMI = "KAYTTAJA";
    private final String ID = "ID";

    private final String PAIVA_SARAKE = "PAIVA";
    private final String KUUKAUSI_SARAKE = "KUUKAUSI";
    private final String VUOSI_SARAKE = "VUOSI";
    private final String IKA_SARAKE = "IKA";
    private final String PITUUS_SARAKE = "PITUUS";
    private final String PAINO_SARAKE = "PAINO";

    /**
     * <p>Konstruktori Kayttajatietokantaoliolle</p>
     * @param context   aktiviteetti, jossa luokkaa kaytetaan.
     */
    public Kayttajatietokanta (@Nullable Context context) {
        super(context, "kayttaja.db", null, 1);
    }

    /**
     * <p>Varsinainen tietokannan luontimetodi - luo taulun ja sarakkeet.</p>
     * @param db    Tietokanta
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String taulunLuontiLause_SQL = "CREATE TABLE " + TIETOKANNAN_NIMI + " (" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PAIVA_SARAKE + " INTEGER, " + KUUKAUSI_SARAKE + " INTEGER, " + VUOSI_SARAKE + " INTEGER, " + IKA_SARAKE + " INTEGER, " + PITUUS_SARAKE + " INTEGER, " + PAINO_SARAKE + " REAL)";

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
     * <p>Tallettaa ajankohdan ja kayttajaolion tiedot tietokantaan.</p>
     * @param kayttaja  Alustettu kayttajaolio, josta tiedot luetaan.
     * @return  Onnistuiko tietojen tallentaminen vai ei.
     */
    public boolean lisaaTiedot (Kayttaja kayttaja) {

        SQLiteDatabase tietokanta = this.getWritableDatabase();
        ContentValues sisalto = new ContentValues();

        sisalto.put(PAIVA_SARAKE, LocalDateTime.now().getDayOfMonth());
        sisalto.put(KUUKAUSI_SARAKE, LocalDateTime.now().getMonth().getValue());
        sisalto.put(VUOSI_SARAKE, LocalDateTime.now().getYear());
        sisalto.put(IKA_SARAKE, kayttaja.getIka());
        sisalto.put(PITUUS_SARAKE, kayttaja.getPituusCM());
        sisalto.put(PAINO_SARAKE, kayttaja.getPainoKG());

        long insert = tietokanta.insert(TIETOKANNAN_NIMI, null, sisalto);
        return insert != -1;
    }

    /**
     * <p>Tarkistaa, onko tietokannassa vähintään 1 rivi tietoa.</p>
     * @return Onko tietokantaa luotu vai ei.
     */
    public boolean onkoTietokantaa () {

        String hakuLauseke_SQL = "SELECT * FROM " + TIETOKANNAN_NIMI;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        if(kursori.moveToFirst()) {
            kursori.close();
            tietokanta.close();
            return true;
        }else {
            kursori.close();
            tietokanta.close();
            return false;
        }
    }

    /**
     * <p>Getteri kayttajan ialle</p>
     * @return Ian kokonaislukuna (vuosina).
     */
    public int haeIka() {

        String hakuLauseke_SQL = "SELECT IKA FROM " + TIETOKANNAN_NIMI;
        int haettuIka;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuIka = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuIka;
    }
    /**
     * <p>Getteri kayttajan pituudelle</p>
     * @return Pituuden kokonaislukuna (cm).
     */
    public int haePituus () {

        String hakuLauseke_SQL = "SELECT PITUUS FROM " + TIETOKANNAN_NIMI;
        int haettuPituus;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuPituus = kursori.getInt(0);
        kursori.close();
        tietokanta.close();
        return haettuPituus;
    }
    /**
     * <p>Getteri kayttajan painolle.</p>
     * @return Kayttajan painon liukulukuna (Kg).
     */
    public float haePaino () {

        String hakuLauseke_SQL = "SELECT PAINO FROM " + TIETOKANNAN_NIMI;
        float haettuPaino;

        SQLiteDatabase tietokanta = this.getReadableDatabase();

        Cursor kursori = tietokanta.rawQuery(hakuLauseke_SQL, null);

        kursori.moveToLast();
        haettuPaino = kursori.getFloat(0);
        kursori.close();
        tietokanta.close();
        return haettuPaino;
    }
}
