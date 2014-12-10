package unam.mobi.kanji.listas;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Adaptador_Avance extends ArrayAdapter<Integer> {

	private ArrayList<Integer> porcentajes;
	private Context context;
	private boolean habilitado;

	public Adaptador_Avance(Context context, ArrayList<Integer> porcentajes) {
		super(context, R.layout.elemento_avance, porcentajes);
		this.context = context;
		this.porcentajes = porcentajes;
		habilitado = true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.elemento_avance, parent, false);

		ImageView imageView = (ImageView) view.findViewById(R.id.imagen_ava);
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progress_bar);
		TextView textView = (TextView) view.findViewById(R.id.porcent);

		switch (position) {
		case 0:
			imageView.setImageResource(R.drawable.uno);
			progressBar.setProgressDrawable(context.getResources().getDrawable(
					R.drawable.progre_uno));
			break;
		case 1:
			imageView.setImageResource(R.drawable.dos);
			progressBar.setProgressDrawable(context.getResources().getDrawable(
					R.drawable.progre_dos));

			break;
		case 2:
			imageView.setImageResource(R.drawable.tres);
			progressBar.setProgressDrawable(context.getResources().getDrawable(
					R.drawable.progre_tres));

			break;
		case 3:
			imageView.setImageResource(R.drawable.cuatro);
			progressBar.setProgressDrawable(context.getResources().getDrawable(
					R.drawable.progre_cuatro));

			break;
		case 4:
			imageView.setImageResource(R.drawable.cinco);
			progressBar.setProgressDrawable(context.getResources().getDrawable(
					R.drawable.progre_cinco));

			break;
		}

		progressBar.setProgress(porcentajes.get(position));
		textView.setText(porcentajes.get(position) + " %");

		return view;
	}

	@Override
	public boolean isEnabled(int position) {
		if (!habilitado) {
			return false;
		}
		return true;
	}

	public void set_habilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
}
