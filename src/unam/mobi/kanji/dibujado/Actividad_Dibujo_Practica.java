package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.dialogos.Dialogo_Porcen;
import unam.mobi.kanji.proce_info.Manejador_Json;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class Actividad_Dibujo_Practica extends SherlockFragmentActivity
		implements Fragment_Kanji.ListenerActividad,
		Fragment_Lienzo.ListenerDialogo, Dialogo_Porcen.Escuchador_Lienzo_Kanji {

	private Manejador_Json manejador_Json;
	private int indi_niv;
	private int num_kan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_dibujo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		int kanji = getIntent().getIntExtra("kanji", 0);

		indi_niv = kanji / 10;
		num_kan = kanji % 10;

		switch (indi_niv) {
		case 0:
			manejador_Json = new Manejador_Json(R.raw.nivel_1, this);
			break;
		case 1:
			manejador_Json = new Manejador_Json(R.raw.nivel_2, this);
			break;
		case 2:
			manejador_Json = new Manejador_Json(R.raw.nivel_3, this);
			break;
		case 3:
			manejador_Json = new Manejador_Json(R.raw.nivel_4, this);
			break;
		case 4:
			manejador_Json = new Manejador_Json(R.raw.nivel_5, this);
			break;
		}

		for (int i = 0; i <= num_kan; i++) {
			manejador_Json.siguiente_Json();
		}

		crea_Kanji();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void crea_Kanji() {

		String num_kanji = (indi_niv+1) + "_" + (num_kan + 1);

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

		Fragment_Lienzo frag_lienzo = new Fragment_Lienzo(guias, puntos, false);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		transaction.replace(R.id.fragmen_dibujo, frag_lienzo, "frag_lienzo");
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

	@Override
	public void crea_Dialogo_Porcen(int porcentaje) {

		Dialogo_Porcen dialogo_Porcen = new Dialogo_Porcen(porcentaje, 1);
//		dialogo_Porcen.setCancelable(false);
		dialogo_Porcen.show(getSupportFragmentManager(), "porcen");

	}

	@Override
	public void siguiente_Kanji() {
		finish();
	}

	@Override
	public void reacer_lienzo() {
		FragmentManager manager = getSupportFragmentManager();

		Dialogo_Porcen dialogo = (Dialogo_Porcen) manager
				.findFragmentByTag("porcen");
		dialogo.dismiss();

		Fragment_Lienzo lienzo = (Fragment_Lienzo) manager
				.findFragmentByTag("frag_lienzo");

		if (lienzo != null) {
			lienzo.limpiar_Lienzo();
		}
	}
}
