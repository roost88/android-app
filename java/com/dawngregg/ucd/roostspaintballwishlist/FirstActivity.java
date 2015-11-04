package com.dawngregg.ucd.roostspaintballwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

public class FirstActivity extends Activity
        implements ProductFragment.OnFragmentInteractionListener {

    private View view;
    private FragmentManager fragManager;
    private FragmentTransaction fragTrans;
    private ProductFragment frag1, frag2;
    private static final String TAG = "WishlistActivity";
    public final static String dataStr = "com.dawngregg.ucd.roostspaintballwishlist.DATA";
    public final static String posStr = "com.dawngregg.ucd.roostspaintballwishlist.POS";
    public final static String page2 = "com.dawngregg.ucd.roostspaintballwishlist.PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Log.i(TAG, "Some message");

        // Get reference to Frame
        int frame1 = R.id.list_frame;

        // Get a reference to the FragmentManager
        fragManager = getFragmentManager();

        // Begin a new FragmentTransaction
        fragTrans = fragManager.beginTransaction();

        // Create two new instances of ProductFragment
        frag1 = ProductFragment.newInstance("Roost's Paintball Products", "");
        frag2 = ProductFragment.newInstance("WishList", "");

        // Add fragments to frame
        fragTrans.add(frame1, frag1);

        // Commit the FragmentTransaction
        fragTrans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_products)
        {
            return true;
        }
        else if (id == R.id.action_wishlist) {
            return true;
        }

        if (frag1 != null && frag1.isVisible())
        {
            fragManager.beginTransaction().remove(frag1).commit();
        }

        if (frag2 != null && !frag2.isVisible())
        {
            fragManager.beginTransaction().add(R.id.list_frame, frag2).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(String data, int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(dataStr, data);
        intent.putExtra(posStr, position);
        intent.putExtra(page2, frag2.isVisible());
        startActivity(intent);
    }
}
