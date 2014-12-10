package unam.mobi.kanji;

import unam.mobi.kanji.dialogos.Dialogo_Juega;
import unam.mobi.kanji.extras.Acerca_de;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Menu_Inicial extends SherlockFragmentActivity implements
		OnClickListener, Dialogo_Juega.EligeActividad {

	private final int ID_MENU = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);
		// LinearLayout layout = (LinearLayout) findViewById(R.id.acti_menu);
				
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		Button inicio = (Button) findViewById(R.id.inicio);
		inicio.setOnClickListener(this);
		Button avances = (Button) findViewById(R.id.avances);
		avances.setOnClickListener(this);
		Button guia = (Button) findViewById(R.id.guia);
		guia.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, ID_MENU, 0, "Acerca de");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == ID_MENU) {
			Intent intent = new Intent(this, Acerca_de.class);
			startActivity(intent);
		}


		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		Intent intent;

		switch (view.getId()) {
		case R.id.inicio:
			lanza_Dialogo();
			break;
		case R.id.avances:
			intent = new Intent(this, Avances.class);
			intent.putExtra("escu", false);
			startActivity(intent);
			break;
		case R.id.guia:
			intent = new Intent(this, Guia.class);
			startActivity(intent);
			break;
		}
	}

	private void lanza_Dialogo() {
		SherlockDialogFragment dialogFragment = new Dialogo_Juega();
		dialogFragment.show(getSupportFragmentManager(), "dia");

	}

	@Override
	public void lanzarActividad(int activ) {

//		Log.e("dialog", "Regrso de Dialog con" + String.valueOf(activ));
		Intent intent;

		Dialogo_Juega fragment = (Dialogo_Juega) getSupportFragmentManager()
				.findFragmentByTag("dia");

		if (fragment != null) {
			fragment.dismiss();
		}

		if (activ == 1) {
			intent = new Intent(this, Avances.class);
			intent.putExtra("escu", true);
			startActivity(intent);
		} else {
			intent = new Intent(this, Lista_Practica.class);
			startActivity(intent);
		}

	}

	// private void liberaMemoria(View view) {
	// if (view.getBackground() != null) {
	// Log.d("Hay memoria Menu", "libera");
	// view.getBackground().setCallback(null);
	// }
	//
	// if (view instanceof ViewGroup) {
	// for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	// liberaMemoria(((ViewGroup) view).getChildAt(i));
	// }
	// ((ViewGroup) view).removeAllViews();
	// }
	// }

}
