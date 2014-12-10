package unam.mobi.kanji.extras;

import unam.mobi.kanji.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class Acerca_de extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.info);
			crear_oyente(); 
		} else {
			getFragmentManager().beginTransaction()
					.replace(android.R.id.content, new Acerca_de_Frag())
					.commit();
		}
	}

	@SuppressWarnings("deprecation")
	public void crear_oyente() {
		OnPreferenceClickListener clickListener = new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				
				Intent intent = null;
				
				if (preference.getKey().compareTo("face") == 0) {
					try {
						getPackageManager().getPackageInfo(
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
		};

		Preference face = findPreference("face");
		Preference twit = findPreference("twit");
		Preference web = findPreference("web");

		face.setOnPreferenceClickListener(clickListener);
		twit.setOnPreferenceClickListener(clickListener);
		web.setOnPreferenceClickListener(clickListener);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
