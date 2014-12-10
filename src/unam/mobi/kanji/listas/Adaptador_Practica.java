package unam.mobi.kanji.listas;

import java.util.ArrayList;

import unam.mobi.kanji.R;
import unam.mobi.kanji.extras.Diccionario;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class Adaptador_Practica extends ArrayAdapter<Diccionario> {

	private ArrayList<Diccionario> list;
	private SherlockActivity context;

	public Adaptador_Practica(SherlockActivity context,
			ArrayList<Diccionario> list) {
		super(context, R.layout.elemento_practica, list);
		this.context = context;
		this.list = new ArrayList<Diccionario>(list);

	}

	private static class ViewHolder {
		public ImageView imagen;
		public TextView textEspan;
		public TextView textJapon;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView; 
		ViewHolder holder; 
		
		if (view== null)
		{
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			LayoutInflater inflater = context.getLayoutInflater(); 
			view = inflater.inflate(R.layout.elemento_practica, parent, false);
			
		holder = new ViewHolder(); 
			
			holder.imagen = (ImageView) view.findViewById(R.id.imagen_prac);
			holder.textEspan = (TextView) view.findViewById(R.id.text_espan);
			holder.textJapon = (TextView) view.findViewById(R.id.text_japon);
			
			view.setTag(holder); 
		}
		else {
			holder = (ViewHolder)view.getTag(); 
		}
		holder.textEspan.setText(list.get(position).get_Espa());
		holder.textJapon.setText(list.get(position).get_Japo());
		
		int id = context.getResources().getIdentifier("kanprac_"+(position+1), "drawable",
				"unam.mobi.kanji");

		holder.imagen.setImageResource(id);
		
		return view;
	}
}
