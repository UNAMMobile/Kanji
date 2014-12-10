package unam.mobi.kanji.extras;

import unam.mobi.kanji.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

public class Acerca_de_Frag extends PreferenceFragment implements
		OnPreferenceClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.info);
		Preference face = findPreference("face");
		Preference twit = findPreference("twit");
		Preference web = findPreference("web");

		face.setOnPreferenceClickListener(this);
		twit.setOnPreferenceClickListener(this);
		web.setOnPreferenceClickListener(this);

	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		
		Intent intent = null;
		
		if (preference.getKey().compareTo("face") == 0) {
			try {
				getActivity().getPackageManager().getPackageInfo(
						"com.facebook.katana", 0);
				intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("fb://profile/134559503283007"));
			} catch (Exception e) {
				intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.facebook.com/UNAM.Mobile"));
			}

		} else if (preference.getKey().compareTo("twit") == 0) {
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://twitter.com/UNAM_Mobile"));
		}
		else
		{
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://unam.mobi"));
		}

		startActivity(intent);
		return false;
	}

}
