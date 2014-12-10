package unam.mobi.kanji.dibujado;

import unam.mobi.kanji.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class Fragment_Kanji extends SherlockFragment {

	private View view;
	private String kanji;
	private ListenerActividad listenerActividad;

	public Fragment_Kanji(String kanji) {
		this.kanji = kanji;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		try {
			listenerActividad = (ListenerActividad) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}
	
	public interface ListenerActividad {
		public void lanza_Lienzo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.kanji, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.mos_kanji);

		int id = getResources().getIdentifier("kanji_"+kanji, "drawable",
				"unam.mobi.kanji");
		imageView.setImageResource(id);

		ImageButton button = (ImageButton) view.findViewById(R.id.acepta_kanji);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listenerActividad.lanza_Lienzo(); 
			}
		});
		return view;
	}


}
