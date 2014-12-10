package unam.mobi.kanji.dialogos;

import unam.mobi.kanji.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class Dialogo_Porcen extends SherlockDialogFragment implements
		OnClickListener {

	private View view;
	private int pocentaje;
	private Escuchador_Lienzo_Kanji lienzo_Kanjii;
	

	private int tipo_botones;
	private LinearLayout layout;

	private final int ID_ACEPTAR = 1;
	private final int ID_REACER = 2;

	public Dialogo_Porcen(int pocentaje, int tipo_botones) {
		this.pocentaje = pocentaje;
		this.tipo_botones = tipo_botones;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			lienzo_Kanjii = (Escuchador_Lienzo_Kanji) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	public interface Escuchador_Lienzo_Kanji {
		public void siguiente_Kanji();
		public void reacer_lienzo(); 
			

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.dialogo_porcen, null);

		layout = (LinearLayout) view.findViewById(R.id.layout_boto_dialo);

		TextView textView = (TextView) view.findViewById(R.id.text_porce);
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progress_porce);
		textView.setText(pocentaje + " %");
		progressBar.setProgress(pocentaje);

		if (tipo_botones == 1) {
			crear_acep_reac();
		}
		else {
			crear_reacer(); 
		}
		builder.setView(view);

		return builder.create();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case ID_ACEPTAR:
			lienzo_Kanjii.siguiente_Kanji();
			break;

		case ID_REACER:
			lienzo_Kanjii.reacer_lienzo(); 
			break;
		}

	}

	private void crear_reacer() {

		layout.removeAllViews();

		ImageButton button = new ImageButton(getSherlockActivity());
		button.setId(ID_REACER);
		button.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		button.setAdjustViewBounds(true);
		button.setBackgroundColor(Color.TRANSPARENT);
		button.setPadding(0, 0, 0, 0);
		button.setScaleType(ScaleType.FIT_XY);
		button.setImageResource(R.drawable.boton_reacer_com);
		button.setOnClickListener(this);

		layout.addView(button);
	}

	private void crear_acep_reac() {

		layout.removeAllViews();

		ImageButton borrar = new ImageButton(getSherlockActivity());
		borrar.setId(ID_REACER);
		borrar.setLayoutParams(new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1));
		borrar.setAdjustViewBounds(true);
		borrar.setBackgroundColor(Color.TRANSPARENT);
		borrar.setPadding(0, 0, 0, 0);
		borrar.setScaleType(ScaleType.FIT_XY);
		borrar.setImageResource(R.drawable.boton_reacer_mit);
		borrar.setOnClickListener(this);

		layout.addView(borrar);

		ImageButton aceptar = new ImageButton(getSherlockActivity());
		aceptar.setId(ID_ACEPTAR);
		aceptar.setLayoutParams(new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1));
		aceptar.setAdjustViewBounds(true);
		aceptar.setBackgroundColor(Color.TRANSPARENT);
		aceptar.setPadding(0, 0, 0, 0);
		aceptar.setScaleType(ScaleType.FIT_XY);
		aceptar.setImageResource(R.drawable.boton_ok);
		aceptar.setOnClickListener(this);

		layout.addView(aceptar);

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
