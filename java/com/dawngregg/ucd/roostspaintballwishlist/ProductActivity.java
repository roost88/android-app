package com.dawngregg.ucd.roostspaintballwishlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;

public class ProductActivity extends Activity
        implements ProductDetailsFragment.OnFragmentInteractionListener {

    private FragmentManager fragManager;
    private FragmentTransaction fragTrans;
    private ProductDetailsFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Get reference to Frame

        // Reference FragmentManager
        fragManager = getFragmentManager();

        // Begin new FragmentTransaction
        fragTrans = fragManager.beginTransaction();

        // Create Fragment
        frag = new ProductDetailsFragment();

        // Create Bundle
        Bundle args = new Bundle();
        args = getIntent().getExtras();

        // Set arguments of Fragment
        frag.setArguments(args);

        // Add Fragment to FragmentTransaction
        fragTrans.add(R.id.product_frame, frag);

        // Commit FragmentTransaction
        fragTrans.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

    public void onFragmentInteraction(String str, int pos) {
        finish();
    }
}
