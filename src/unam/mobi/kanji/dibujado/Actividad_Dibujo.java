package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.base.Manej_Base_Datos;
import unam.mobi.kanji.dialogos.Dialogo_Porcen;
import unam.mobi.kanji.dialogos.Dialogo_Porcen_Total;
import unam.mobi.kanji.dialogos.Dialogo_Salir;
import unam.mobi.kanji.proce_info.Manejador_Json;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class Actividad_Dibujo extends SherlockFragmentActivity implements
		Fragment_Kanji.ListenerActividad, Fragment_Lienzo.ListenerDialogo,
		Dialogo_Porcen.Escuchador_Lienzo_Kanji, Dialogo_Salir.Escuchador_Salir,
		Dialogo_Porcen_Total.Escuchador_Avance {

	private Manejador_Json manejador_Json;
	private int conta;
	private int nivel;
	private int porce_niveles;
	private int porce_actu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d("ciclo", "OnCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_dibujo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		porce_actu = 0;
		porce_niveles = 0;

		int archivo = getIntent().getIntExtra("tipoarchi", 0);
		Log.d("archivo", String.valueOf(archivo)); 
		
		if (archivo == 0)
		{
			Toast.makeText(this, "Poco memoria en el dispostivo", Toast.LENGTH_SHORT).show(); 
			finish();
		}
		nivel = getIntent().getIntExtra("kanjis", 0);
		
		Log.d("nivel", String.valueOf(nivel)); 

		conta = 0;

		manejador_Json = new Manejador_Json(escoge_archivo(archivo), this);
		if (manejador_Json.siguiente_Json()) {
			crea_Kanji();
		}

	}
	
	
	private int escoge_archivo(int archivo) {
		int id = 0;
		switch (archivo) {
		case 1:
			id = R.raw.nivel_1;
			break;
		case 2:
			id = R.raw.nivel_2;
			break;
		case 3:
			id = R.raw.nivel_3;
			break;
		case 4:
			id = R.raw.nivel_4;
			break;
		case 5:
			id = R.raw.nivel_5;
			break;
		}

		return id;
	}

	private void crea_Kanji() {

		conta++;
		String num_kanji = nivel + "_" + conta;
		Fragment_Kanji frag_kanji = new Fragment_Kanji(num_kanji);

		FragmentManager manager = getSupportFragmentManager();

		FragmentTransaction transaction = manager.beginTransaction();

		transaction.replace(R.id.fragmen_dibujo, frag_kanji);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
		transaction.commit();

	}

	@Override
	public void lanza_Lienzo() {
		crea_Lienzo();
	}

	private void crea_Lienzo() {

		ArrayList<Integer> guias = manejador_Json.get_Guias();
		ArrayList<Integer> puntos = manejador_Json.get_Puntos();

		Fragment_Lienzo frag_lienzo = new Fragment_Lienzo(guias, puntos, true);

		FragmentManager manager = getSupportFragmentManager();

		FragmentTransaction transaction = manager.beginTransaction();

		transaction.replace(R.id.fragmen_dibujo, frag_lienzo, "frag_lienzo");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); 
		transaction.commit();
	}

	@Override
	public void crea_Dialogo_Porcen(int porcentaje) {

		porce_actu = porcentaje;
		Dialogo_Porcen dialogo_Porcen;

		if (porcentaje >= 75) {
			dialogo_Porcen = new Dialogo_Porcen(porcentaje, 1);
		} else {
			dialogo_Porcen = new Dialogo_Porcen(porcentaje, 2);
		}

		// dialogo_Porcen.setCancelable(false);
		dialogo_Porcen.show(getSupportFragmentManager(), "porcen");

	}

	@Override
	public void siguiente_Kanji() {

		porce_niveles += porce_actu;

		FragmentManager manager = getSupportFragmentManager();

		Dialogo_Porcen dialogo = (Dialogo_Porcen) manager
				.findFragmentByTag("porcen");

		if (dialogo != null) {
			dialogo.dismiss();
		}

		if (manejador_Json.siguiente_Json()) {
			crea_Kanji();
		} else {
			porce_niveles /= 10;
			Dialogo_Porcen_Total total = new Dialogo_Porcen_Total(porce_niveles);
			total.setCancelable(false);
			total.show(getSupportFragmentManager(), "porcen_total");
		}
	}

	@Override
	public void reacer_lienzo() {
		FragmentManager manager = getSupportFragmentManager();

		Dialogo_Porcen dialogo = (Dialogo_Porcen) manager
				.findFragmentByTag("porcen");

		if (dialogo != null) {
			dialogo.dismiss();
		}

		Fragment_Lienzo lienzo = (Fragment_Lienzo) manager
				.findFragmentByTag("frag_lienzo");

		if (lienzo != null) {
			lienzo.limpiar_Lienzo();
		} else {
			crea_Lienzo();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Dialogo_Salir dialogo_Salir = new Dialogo_Salir();
			dialogo_Salir.show(getSupportFragmentManager(), "dia_salir");
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			Dialogo_Salir dialogo_Salir = new Dialogo_Salir();
			dialogo_Salir.show(getSupportFragmentManager(), "dia_salir");
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void terminar_juego() {

		FragmentManager manager = getSupportFragmentManager();

		Dialogo_Porcen_Total dialogo = (Dialogo_Porcen_Total) manager
				.findFragmentByTag("porcen_total");
		if (dialogo != null) {
			dialogo.dismiss();
		}
		Manej_Base_Datos base_Datos = new Manej_Base_Datos(this);
		base_Datos.editar_Nivel(nivel, porce_niveles);
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void salir() {
		finish();

	}

//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		Log.d("ciclo", "OnStart");
//
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		Log.d("ciclo", "OnResume");
//
//	}
//
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		Log.d("ciclo", "OnPause");
//
//	}
//
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//		Log.d("ciclo", "OnRestart");
//	}
//
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		Log.d("ciclo", "OnStop");
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		Log.d("ciclo", "OnDestroy");
//
//	}

}
