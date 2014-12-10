package unam.mobi.kanji.dialogos;

import unam.mobi.kanji.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class Dialogo_Porcen_Total extends SherlockDialogFragment  {

	private View view;
	private int pocentaje;
	private Escuchador_Avance lienzo_Kanjii;
	


	public Dialogo_Porcen_Total(int pocentaje) {
		this.pocentaje = pocentaje;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			lienzo_Kanjii = (Escuchador_Avance) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	public interface Escuchador_Avance {
		public void terminar_juego();
			

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
		
		view = inflater.inflate(R.layout.dialogo_total, null);

		TextView textView = (TextView) view.findViewById(R.id.text_porce_total);
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progress_total);
		textView.setText(pocentaje + " %");
		progressBar.setProgress(pocentaje);

		
		ImageButton button = (ImageButton)view.findViewById(R.id.boto_total); 
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lienzo_Kanjii.terminar_juego();
			}
		});
		

		builder.setView(view);

		return builder.create();
	}




	

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		liberaMemoria(view);
		Log.d("entro", "onDestroyView");
	}

	private void liberaMemoria(View view) {
		if (view.getBackground() != null) {
			Log.d("Hay memoria", "libera");
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
