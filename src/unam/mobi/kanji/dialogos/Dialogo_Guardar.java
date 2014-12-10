package unam.mobi.kanji.dialogos;

import java.io.File;
import java.util.Calendar;

import unam.mobi.kanji.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class Dialogo_Guardar extends SherlockDialogFragment {

	private View view;
	private String path;
	private String nombre;
	private String nombre_fin;

	private EditText editText;
	private TextView textView;
	private AlertDialog alertDialog;

	private OnGuardar miEscuchador;

	private File imagen;

	public interface OnGuardar {
		public void guardar(File file);
	}

	public void setOnMiEscuchar(OnGuardar escuchador) {
		miEscuchador = escuchador;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSherlockActivity());

		LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.dialgo_guardar, null);
		builder.setView(view);

		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Kanjis/";

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		textView = (TextView) view.findViewById(R.id.text_guardar);
		editText = (EditText) view.findViewById(R.id.edit_guardar);

		nombre_Kanji();

		textView.setText(path + nombre + ".jpg");
		editText.setHint(nombre);

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				nombre_fin = s.toString();
				if (nombre_fin.isEmpty()) {
					nombre_fin = nombre;
 
				} else {
					while (nombre_fin.startsWith(" ")) {
						nombre_fin = nombre_fin.substring(1);
					}

					if (nombre_fin.isEmpty()) {
						nombre_fin = nombre;
						textView.setText(path + nombre_fin + ".jpg");
						establece_Archivo();
						return;
					}

					while (nombre_fin.endsWith(" ")) {
						nombre_fin = nombre_fin.substring(0,
								nombre_fin.length() - 1);
					}

					if (nombre_fin.contains("/") || nombre_fin.contains("\\")
							|| nombre_fin.contains(":")
							|| nombre_fin.contains("*")
							|| nombre_fin.contains("?")
							|| nombre_fin.contains("\"")
							|| nombre_fin.contains("<")
							|| nombre_fin.contains(">")
							|| nombre_fin.contains("|")) {
						alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
								.setEnabled(false);

						Toast.makeText(getSherlockActivity(),
								"No se permiten los caracteres: /\\:?<>|",
								Toast.LENGTH_SHORT).show();
						textView.setText(path + nombre_fin + ".jpg");
						return;
					} else {
						alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
								.setEnabled(true);
					}
				}
				
				textView.setText(path + nombre_fin + ".jpg");
				establece_Archivo(); 

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		builder.setCancelable(true);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			builder.setIcon(R.drawable.guardar_light);
		} else {
			builder.setIcon(R.drawable.guardar);
		}

		builder.setTitle("Guardar: ")
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						miEscuchador.guardar(imagen);

						alertDialog.setCancelable(false);
					}
				}).setNegativeButton("Cancelar", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});

		imagen = new File(path, nombre_fin + ".jpg");
		alertDialog = builder.create();
		
		return alertDialog;
	}

	private void nombre_Kanji() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minuto = calendar.get(Calendar.MINUTE);
		int segundo = calendar.get(Calendar.SECOND);

		nombre_fin = nombre = "Kanji_" + day + "_" + month + "_" + year + "_"
				+ hora + "_" + minuto + "_" + segundo;

	}

	private void establece_Archivo() {

		alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
				.setEnabled(false);

		imagen = new File(path, nombre_fin + ".jpg");

		if (imagen.exists()) {
			Toast.makeText(getSherlockActivity(), "Ya existe la imagen",
					Toast.LENGTH_SHORT).show();
		} else {
			alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(
					true);
		}
		
	}

}
