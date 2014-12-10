package unam.mobi.kanji;

import java.util.ArrayList;

import unam.mobi.kanji.dibujado.Actividad_Dibujo_Practica;
import unam.mobi.kanji.listas.Adaptador_Practica;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class Lista_Practica extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_practica);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ArrayList<String> espa = new ArrayList<String>();
		ArrayList<String> japo = new ArrayList<String>();

		espa.add("Uno");
		espa.add("Dos");
		espa.add("Tres");
		espa.add("Cuatro");
		espa.add("Cinco");

		japo.add("Ichi");
		japo.add("Ni");
		japo.add("San");
		japo.add("Yon, Shi");
		japo.add("Go");

		Adaptador_Practica adaptador_Practica = new Adaptador_Practica(this,
				espa, japo);
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

}
