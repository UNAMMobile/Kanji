package unam.mobi.kanji.listas;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador_Practica extends ArrayAdapter<String> {

	private ArrayList<String> espan;
	private ArrayList<String> japon;
	private Context context;

	public Adaptador_Practica(Context context, ArrayList<String> espan,
			ArrayList<String> japon) {
		super(context, R.layout.elemento_practica, espan);
		this.context = context;
		this.espan = espan;
		this.japon = japon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.elemento_practica, parent, false);

		ImageView imageView = (ImageView) view.findViewById(R.id.imagen_prac);
		TextView textEspan = (TextView) view.findViewById(R.id.text_espan);
		TextView textJapon = (TextView) view.findViewById(R.id.text_japon);

		textEspan.setText(espan.get(position)); 
		textJapon.setText(japon.get(position)); 
		
		switch (position) {
		case 0:
			imageView.setImageResource(R.drawable.uno_pru);

			break;
		case 1:
			imageView.setImageResource(R.drawable.dos_pru);

			break;
		case 2:
			imageView.setImageResource(R.drawable.tres_pru);

			break;
		case 3:
			imageView.setImageResource(R.drawable.cuatro_pru);

			break;
		case 4:
			imageView.setImageResource(R.drawable.cinco_pru);

			break;
		}

		return view;
	}
}
