package unam.mobi.kanji.proce_info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Manejador_Json {

	private JSONObject objeto;

	private InputStreamReader streamReader;
	private BufferedReader reader;

	private int tamaño_guias;
	private int tamaño_puntos;

	public Manejador_Json(int archivo, Context context) {
		tamaño_guias = 0;
		tamaño_puntos = 0;
		streamReader = new InputStreamReader(context.getResources()
				.openRawResource(archivo));
		reader = new BufferedReader(streamReader);
	}

	public boolean siguiente_Json() {

		String linea;
		StringBuilder builder = new StringBuilder();

		try {

			for (int i = 0; i < 4; i++) {
				if ((linea = reader.readLine()) != null) {
					builder.append(linea + "\n");
				} else {
					close_Buffered();
					return false;
				}
			}
			
//			Log.d("Obtenido", builder.toString());
		} catch (IOException e) {
			close_Buffered();
			Log.d("Error al obtener el Json", e.getMessage());
			return false;
		}

		try {
			objeto = new JSONObject(builder.toString());
			tamaño_guias = objeto.getJSONArray("guias").length();
			tamaño_puntos = objeto.getJSONArray("puntos").length();
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			close_Buffered();
			Log.d("Error al generar el Json", e.getMessage());
			return false;
		}
	}

	public void close_Buffered() {
		try {
			reader.close();
			streamReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Error Cerrando el Buffered", e.getMessage());
		}
	}

	public ArrayList<Integer> get_Guias() {
		ArrayList<Integer> guias = new ArrayList<Integer>();

		try {
			JSONArray array = objeto.getJSONArray("guias");
			for (int i = 0; i < tamaño_guias; i++) {
				guias.add(array.getInt(i));
			}
		} catch (JSONException e) {
			Log.d("Error en en guias", e.getMessage());
		}

		return guias;
	}

	public ArrayList<Integer> get_Puntos() {
		ArrayList<Integer> puntos = new ArrayList<Integer>();

		try {
			JSONArray array = objeto.getJSONArray("puntos");
			for (int i = 0; i < tamaño_puntos; i++) {
				puntos.add(array.getInt(i));
			}
		} catch (JSONException e) {
			Log.d("Error en en guias", e.getMessage());
		}

		return puntos;
	}
}
