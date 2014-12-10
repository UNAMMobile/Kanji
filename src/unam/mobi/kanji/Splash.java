package unam.mobi.kanji;

import unam.mobi.kanji.base.Manej_Base_Datos;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;

public class Splash extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_nivel);

		int nivel = getIntent().getIntExtra("nivel", -1);

		if (nivel == -1) {
			splash_Principal();
			return;
		}

		ImageView imageView = (ImageView) findViewById(R.id.splasnivel);

		switch (nivel) {
		case 0:
			imageView.setBackgroundResource(R.drawable.nivel1);
			break;
		case 1:
			imageView.setBackgroundResource(R.drawable.nivel2);
			break;
		case 2:
			imageView.setBackgroundResource(R.drawable.nivel3);
			break;
		case 3:
			imageView.setBackgroundResource(R.drawable.nivel4);
			break;
		case 4:
			imageView.setBackgroundResource(R.drawable.nivel5);
			break;

		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				setResult(RESULT_OK);
				finish();
			}
		}, 1000);

	}

	private void splash_Principal() {

		ImageView imageView = (ImageView) findViewById(R.id.splasnivel);
		imageView.setBackgroundResource(R.drawable.splash);

		new Manej_Base_Datos(this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Splash.this, Menu_Inicial.class);
				finish();
				startActivity(intent);

			}
		}, 1000);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		liberaMemoria(findViewById(R.id.splasnivel));
		//Log.d("Entra", "onDestroy");

	}

	private void liberaMemoria(View view) {
		if (view.getBackground() != null) {
		//	Log.d("Hay memoria", "libera");
			view.getBackground().setCallback(null);
		}

		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				liberaMemoria(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}
}
