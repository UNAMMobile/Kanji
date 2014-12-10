package unam.mobi.kanji.dialogos;

import unam.mobi.kanji.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class Dialogo_Juega extends SherlockDialogFragment implements
		OnClickListener {

	private EligeActividad eligeActividad;
	private View view;

	public interface EligeActividad {
		public void lanzarActividad(int activ);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			eligeActividad = (EligeActividad) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.dialogo_juega, null);
		builder.setView(view);

		ImageButton juega = (ImageButton) view.findViewById(R.id.juega);
		ImageButton practica = (ImageButton) view.findViewById(R.id.practica);

		juega.setOnClickListener(this);
		practica.setOnClickListener(this);

		return builder.create();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.juega:
			eligeActividad.lanzarActividad(1);
			break;
		case R.id.practica:
			eligeActividad.lanzarActividad(2);
			break;
		}

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		liberaMemoria(view);
		//Log.d("entro", "onDestroyView");
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
