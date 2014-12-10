package unam.mobi.kanji.extras;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MiFrag_PagerAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> fragments;

	public MiFrag_PagerAdapter(FragmentManager manager) {
		// TODO Auto-generated constructor stub
		super(manager);
		fragments = new ArrayList<Fragment>();
	}

	public void addFragment(Fragment fragment) {
		fragments.add(fragment);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
