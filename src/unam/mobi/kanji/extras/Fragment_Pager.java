package unam.mobi.kanji.extras;

import unam.mobi.kanji.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class Fragment_Pager extends SherlockFragment {

	private int id_guia;

	public Fragment_Pager(int id_guia) {
		this.id_guia = id_guia;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewGroup = (View) inflater.inflate(R.layout.pager,
				container, false);

		ImageView imageView = (ImageView) viewGroup
				.findViewById(R.id.elem_pager);
		imageView.setBackgroundResource(id_guia);

		return viewGroup;
	}

}
