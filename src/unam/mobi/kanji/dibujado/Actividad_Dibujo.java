package unam.mobi.kanji.dibujado;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.base.Manej_Base_Datos;
import unam.mobi.kanji.dialogos.Dialogo_Porcen;
import unam.mobi.kanji.dialogos.Dialogo_Salir;
import unam.mobi.kanji.proce_info.Manejador_Json;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class Actividad_Dibujo extends SherlockFragmentActivity implements
		Fragment_Kanji.ListenerActividad, Fragment_Lienzo.ListenerDialogo,
		Dialogo_Porcen.Escuchador_Lienzo_Kanji, Dialogo_Salir.Escuchador_Salir{

	private Manejador_Json manejador_Json;
	private int conta;
	private int nivel;
	private int porce_niveles;
	private int porce_actu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_dibujo);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 

		porce_actu = 0;
		porce_niveles = 0;

		int archivo = getIntent().getIntExtra("archivo", 0);
		nivel = getIntent().getIntExtra("kanjis", 1);
		conta = 0;

		manejador_Json = new Manejador_Json(archivo, this);
		if (manejador_Json.siguiente_Json()) {
			crea_Kanji();
		} else {
			Toast.makeText(this, "Termino el Nivel", Toast.LENGTH_SHORT).show();
		}

	}

	private void crea_Kanji() {

		conta++;
		String num_kanji = nivel + "_" + conta;
		// String num_kanji = "prueba_kanji";
		Fragment_Kanji frag_kanji = new Fragment_Kanji(num_kanji);

		FragmentManager manager = getSupportFragmentManager();
		// Fragment fragment = manager.findFragmentByTag("frag_lienzo");

		FragmentTransaction transaction = manager.beginTransaction();

		// if (fragment != null) {
		// transaction.remove(fragment);
		// }

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

		Fragment_Lienzo frag_lienzo = new Fragment_Lienzo(guias, puntos, true);

		FragmentManager manager = getSupportFragmentManager();

		FragmentTransaction transaction = manager.beginTransaction();

		transaction.replace(R.id.fragmen_dibujo, frag_lienzo, "frag_lienzo");
		transaction.commit();
	}

	@Override
	public void crea_Dialogo(int porcentaje) {

		porce_actu = porcentaje;
		Dialogo_Porcen dialogo_Porcen;

		if (porcentaje >= 75) {
			dialogo_Porcen = new Dialogo_Porcen(porcentaje, 1);
		} else {
			dialogo_Porcen = new Dialogo_Porcen(porcentaje, 2);
		}

//		dialogo_Porcen.setCancelable(false);
		dialogo_Porcen.show(getSupportFragmentManager(), "porcen");

	}

	@Override
	public void siguiente_Kanji() {

		porce_niveles += porce_actu;

		FragmentManager manager = getSupportFragmentManager();

		Dialogo_Porcen dialogo = (Dialogo_Porcen) manager
				.findFragmentByTag("porcen");
		dialogo.dismiss();

		if (manejador_Json.siguiente_Json()) {
			crea_Kanji();
		} else {
			Manej_Base_Datos base_Datos = new Manej_Base_Datos(this);
			base_Datos.editar_Nivel(nivel, porce_niveles / 10);
			setResult(RESULT_OK);
			finish();
		}
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Dialogo_Salir dialogo_Salir = new Dialogo_Salir(); 
			dialogo_Salir.show(getSupportFragmentManager(), "dia_salir"); 
			return true; 
		}
		
		return super.onKeyDown(keyCode, event);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home)
		{
			Dialogo_Salir dialogo_Salir = new Dialogo_Salir(); 
			dialogo_Salir.show(getSupportFragmentManager(), "dia_salir"); 
			return true; 
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void salir() {

		finish(); 
	}
	


}
