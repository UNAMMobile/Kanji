package unam.mobi.kanji.extras;

import unam.mobi.kanji.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Acerca_de_Frag extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.info); 
	}

}
