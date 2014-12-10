package unam.mobi.kanji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import unam.mobi.kanji.dibujado.Actividad_Dibujo_Practica;
import unam.mobi.kanji.extras.Diccionario;
import unam.mobi.kanji.listas.Adaptador_Practica;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class Lista_Practica extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_practica);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		new Obtiene_Nombres().execute(); 
		
		


	}
	
	private void crear_Lista (ArrayList<Diccionario> list)
	{
		Adaptador_Practica adaptador_Practica = new Adaptador_Practica(this,list);
		ListView list_Prac = (ListView) findViewById(R.id.lista_prac);
		list_Prac.setAdapter(adaptador_Practica);
		
		
		list_Prac.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent (getApplicationContext(), Actividad_Dibujo_Practica.class); 
				intent.putExtra("kanji", arg2); 
				startActivity(intent);
			}
		}); 
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class Obtiene_Nombres extends AsyncTask<Void, Void, Boolean>
	{
		private ArrayList<Diccionario> list;
		private BufferedReader reader; 
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			list = new ArrayList<Diccionario>(); 
				InputStreamReader streamReader;
				try {
					streamReader = new InputStreamReader(getApplicationContext().getResources()
					.openRawResource(R.raw.nom_kan), "UTF-8");
					reader = new BufferedReader(streamReader); 
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {

			String linea; 
			
			try {
				while ((linea=reader.readLine())!=null) {
					list.add(new Diccionario(linea, reader.readLine())); 
				}
			} catch (IOException e) {
				e.printStackTrace();	
				return false; 
			}
			return true;
		}
		

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			if (result)
			{
				crear_Lista(list);
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "Error al obtener la información", Toast.LENGTH_LONG).show(); 
			}
			
		}
	}

}
