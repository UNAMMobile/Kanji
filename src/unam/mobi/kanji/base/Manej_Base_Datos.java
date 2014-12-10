package unam.mobi.kanji.base;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Manej_Base_Datos {

	private NivelSQLiteHelper helper;

	public Manej_Base_Datos(Context context) {
		helper = new NivelSQLiteHelper(context, "niveles", null, 1);
	}

	public ArrayList<Integer> get_Pocentajes() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		SQLiteDatabase database = helper.getWritableDatabase();

		Cursor cursor = database.query(helper.get_NomTabla(),
				new String[] { "Porcentaje" }, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				arrayList.add(cursor.getInt(0));
			} while (cursor.moveToNext());

		}

		database.close();
		return arrayList;
	}

	public void editar_Nivel(int nivel, int porcentaje) {

		SQLiteDatabase database = helper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("Porcentaje", porcentaje);

		database.update(helper.get_NomTabla(), contentValues, "Nivel=" + nivel,
				null);
		database.close();
	}

	public int obtener_Porcentaje(int nivel) {
		int porcentaje = 0;
		SQLiteDatabase database = helper.getWritableDatabase();

		Cursor cursor = database.query(helper.get_NomTabla(),
				new String[] { "Porcentaje" },"Nivel="+nivel, null, null, null, null);
		if (cursor.moveToFirst()) {
			porcentaje = cursor.getInt(0);
		}
		database.close(); 

		return porcentaje;
	}

}
