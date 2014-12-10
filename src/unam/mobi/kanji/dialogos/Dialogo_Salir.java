package unam.mobi.kanji.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class Dialogo_Salir extends SherlockDialogFragment {

	Escuchador_Salir eschuchado_Salir;

	public interface Escuchador_Salir {
		public void salir();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			eschuchado_Salir = (Escuchador_Salir) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new Builder(getSherlockActivity());

		builder.setTitle("Salir")
				.setMessage(
						"¿Está seguro de que desea salir?\nTodos sus progresos se perderán")
				.setNegativeButton("Cancelar", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).setPositiveButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stubs
						eschuchado_Salir.salir();
					}
				});

		return builder.create();
	}

}
