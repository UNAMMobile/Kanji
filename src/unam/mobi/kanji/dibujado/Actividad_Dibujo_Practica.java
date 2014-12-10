package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.dialogos.Dialogo_Porcen;
import unam.mobi.kanji.proce_info.Manejador_Json;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class Actividad_Dibujo_Practica extends SherlockFragmentActivity
		implements Fragment_Kanji.ListenerActividad,
		Fragment_Lienzo.ListenerDialogo, Dialogo_Porcen.Escuchador_Lienzo_Kanji {
	private Manejador_Json manejador_Json;

//	private RelativeLayout layout;
//	private ImageView imagen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_dibujo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		layout = (RelativeLayout) findViewById(R.layout.lista_practica);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		int kanji = getIntent().getIntExtra("kanji", 0);
		manejador_Json = new Manejador_Json(R.raw.prueba, this);

		for (int i = 0; i <= kanji; i++) {
			manejador_Json.siguiente_Json();
		}

		crea_Kanji(kanji + 1);

	}

//	private void crea_lienzo() {
//
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		params.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
//		params.addRule(RelativeLayout.ABOVE, R.id.lienzo); 
//
//		imagen = new ImageView(this);
//		imagen.setLayoutParams(params);
//		imagen.setAdjustViewBounds(true);
//		imagen.setBackgroundColor(Color.TRANSPARENT);
//		imagen.setPadding(0, 0, 0, 0);
//		imagen.setScaleType(ScaleType.FIT_CENTER);
//		imagen.setImageResource(R.drawable.uno_pru);
//
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void crea_Kanji(int kanji) {
		
		String num_kanji = "prac_" + kanji;

		Fragment_Kanji frag_kanji = new Fragment_Kanji(num_kanji);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		transaction.replace(R.id.fragmen_dibujo, frag_kanji);
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
		transaction.commit();
	}

	@Override
	public void crea_Dialogo(int porcentaje) {

		Dialogo_Porcen dialogo_Porcen = new Dialogo_Porcen(porcentaje, 1);
		dialogo_Porcen.setCancelable(false);
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
