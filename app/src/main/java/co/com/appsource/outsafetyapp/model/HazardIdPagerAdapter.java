package co.com.appsource.outsafetyapp.model;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.HazardIdPortadaFragment;

/**
 * Created by JANUS on 24/02/2016.
 */
public class HazardIdPagerAdapter extends FragmentPagerAdapter {

    public static int pos = 0;

    private List<android.support.v4.app.Fragment> myFragments;
    private ArrayList<String> categories;
    private Context context;

    public HazardIdPagerAdapter(Context c, android.support.v4.app.FragmentManager fragmentManager, List<android.support.v4.app.Fragment> myFrags, ArrayList<String> cats) {
        super(fragmentManager);
        myFragments = myFrags;
        this.categories = cats;
        this.context = c;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getCount() {

        return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);
        return categories.get(position);
    }

    public static int getPos() {
        return pos;
    }

    public void add(Class<HazardIdPortadaFragment> c, String title, Bundle b) {
        myFragments.add(android.support.v4.app.Fragment.instantiate(context, c.getName(), b));
        categories.add(title);
    }

    public static void setPos(int pos) {
        HazardIdPagerAdapter.pos = pos;
    }
}