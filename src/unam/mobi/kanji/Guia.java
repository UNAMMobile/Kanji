package unam.mobi.kanji;

import unam.mobi.kanji.extras.Fragment_Pager;
import unam.mobi.kanji.extras.MiFrag_PagerAdapter;
import unam.mobi.kanji.extras.ZoomPager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.CirclePageIndicator;

public class Guia extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guia);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		MiFrag_PagerAdapter adapter = new MiFrag_PagerAdapter(
				getSupportFragmentManager());

		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

		adapter.addFragment(new Fragment_Pager(R.drawable.guia1));
		adapter.addFragment(new Fragment_Pager(R.drawable.guia2));
		adapter.addFragment(new Fragment_Pager(R.drawable.guia3));
		adapter.addFragment(new Fragment_Pager(R.drawable.guia4));
		adapter.addFragment(new Fragment_Pager(R.drawable.guia5));
		adapter.addFragment(new Fragment_Pager(R.drawable.guia6));

		viewPager.setAdapter(adapter);
		viewPager.setPageTransformer(true, new ZoomPager());

		indicator.setViewPager(viewPager);

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
