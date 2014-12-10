package unam.mobi.kanji.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NivelSQLiteHelper extends SQLiteOpenHelper {

	private String nombre_tabla;  
	
	public NivelSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		nombre_tabla = "Porce_Niveles";  
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String crea_tabla = "CREATE TABLE "+nombre_tabla+" (Nivel INTEGER PRIMARY KEY UNIQUE, Porcentaje INTEGER)"; 
		ContentValues contentValues = new ContentValues(); 
		
		db.execSQL(crea_tabla); 
		
		for (int i =1; i<6; i++)
		{
			contentValues.put("Nivel", i); 
			contentValues.put("Porcentaje", 0); 
			db.insert(nombre_tabla, null, contentValues); 
			contentValues.clear(); 
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public String get_NomTabla ()
	{
		return nombre_tabla; 
	}

}
