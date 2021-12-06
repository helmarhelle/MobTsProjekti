package fi.helmarhelle.mobtsprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Kayttajatietokanta extends SQLiteOpenHelper {

    //Vakiot
    private final String TIETOKANNAN_NIMI = "KAYTTAJA";
    private final String ID_SARAKE = "ID";
    private final String IKA_SARAKE = "IKA";
    private final String PITUUS_SARAKE = "PITUUS";
    private final String PAINO_SARAKE = "PAINO";

    public Kayttajatietokanta (@Nullable Context context) {
        super(context, "kayttaja.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String taulunLuontiLause_SQL = "CREATE TABLE " + TIETOKANNAN_NIMI + " (" + ID_SARAKE +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + IKA_SARAKE + " TEXT, " + PITUUS_SARAKE + " INT, " + PAINO_SARAKE + " REAL)";

        db.execSQL(taulunLuontiLause_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean lisaaTiedot (Kayttaja kayttaja) {

        SQLiteDatabase tietokanta = this.getWritableDatabase();
        ContentValues sisalto = new ContentValues();

        sisalto.put(IKA_SARAKE, kayttaja.getIka());
        sisalto.put(PITUUS_SARAKE, kayttaja.getPituusCM());
        sisalto.put(PAINO_SARAKE, kayttaja.getPainoKG());

        long insert = tietokanta.insert(TIETOKANNAN_NIMI, null, sisalto);
        if (insert == -1) {
            return false;
        }else {
            return true;
        }
    }

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
}