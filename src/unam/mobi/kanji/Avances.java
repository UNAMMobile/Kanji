package unam.mobi.kanji;

import java.util.ArrayList;

import unam.mobi.kanji.base.Manej_Base_Datos;
import unam.mobi.kanji.dibujado.Actividad_Dibujo;
import unam.mobi.kanji.listas.Adaptador_Avance;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class Avances extends SherlockActivity {

	private ArrayList<Integer> porcentajes;
	private ListView lista_avances;
	private Adaptador_Avance avance;
	private int nivel;
	private Manej_Base_Datos base_Datos = new Manej_Base_Datos(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avances);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		boolean evalu = getIntent().getBooleanExtra("escu", false);
		porcentajes = base_Datos.get_Pocentajes();

		avance = new Adaptador_Avance(this, porcentajes);
		lista_avances = (ListView) findViewById(R.id.list_avances);

		if (evalu) {
			lista_avances.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					if (arg2 > 0) {
						if (base_Datos.obtener_Porcentaje(arg2) == 0) {
							Toast.makeText(Avances.this,
									"Necesitas pasar el nivel anterior",
									Toast.LENGTH_SHORT).show();
							return;
						}
					} 

					lanza_Nivel(arg2);
				}
			});
		} else {

			avance.set_habilitado(false);
			// lista_avances.setDivider(null);
		}
		lista_avances.setAdapter(avance);

	}

	private void lanza_Nivel(int nivel) {
		this.nivel = nivel + 1;
		Intent intent = new Intent(this, Splash.class);
		intent.putExtra("nivel", nivel);
		startActivityForResult(intent, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) {

			Intent intent = new Intent(this, Actividad_Dibujo.class);
			intent.putExtra("tipoarchi", nivel);
			intent.putExtra("kanjis", nivel);
			startActivityForResult(intent, 2);

		} else if (requestCode == 2 && resultCode == RESULT_OK) {

			porcentajes.set(nivel - 1, base_Datos.obtener_Porcentaje(nivel));
			avance.notifyDataSetChanged();
		}
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
