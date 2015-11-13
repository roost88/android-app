package com.dawngregg.ucd.roostspaintballwishlist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


public class ProductDetailsFragment extends Fragment {

    // JSON data variables
    private static final String PRODUCTS_TAG = "PRODUCTS";
    private static final String ID_TAG = "ItemID";
    private static final String NAME_TAG = "Name";
    private static final String DESC_TAG = "Description";
    private static final String IMG_TAG = "Image";

    // File name variable
    private static final String FILE_NAME = "data.json";

    // Other member variables
    private String item_number = "";
    private int pos = 0;
    private boolean visible = false;

    private OnFragmentInteractionListener mListener;

    public ProductDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            item_number = getArguments().getString(FirstActivity.dataStr);
            pos = getArguments().getInt(FirstActivity.posStr);
            visible = getArguments().getBoolean(FirstActivity.page2);
        }

//        getActivity().setTitle("Item " + item_number);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Exception tag
        String TAG = "ProductDetailsOnCreateView";

        // View variable
        View current;

        // Check screen configuration
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // Set layout to portait mode
            current = inflater.inflate(R.layout.fragment_product_details, container, false);
        }
        else
        {
            // Set layout to landscape mode
            current = inflater.inflate(R.layout.product_details_landscape, container, false);
        }

        // Get references to widgets
        TextView status = (TextView) current.findViewById(R.id.textView);
        TextView description = (TextView) current.findViewById(R.id.product_description);
        ImageView imageView = (ImageView) current.findViewById(R.id.paintball_image);
        Button buttonView = (Button) current.findViewById(R.id.action_add);

        // If page2 is visible, change text on page
        if (visible)
        {
            status.setText(R.string.statusIn);
            buttonView.setText(R.string.remove);
        }

        try
        {
            // Create new read object
            FileReadWrite readObj = new FileReadWrite();

            // Read JSON data
            String JSONdata = readObj.readFile(getActivity(), FILE_NAME);

            // Get top-level JSON Object - a Map
            JSONObject responseObject = (JSONObject) new JSONTokener(JSONdata).nextValue();

            // Extract value of "PRODUCTS" key -- a List
            JSONArray allProducts = responseObject.getJSONArray(PRODUCTS_TAG);

            // Extract current Product by item ID
            JSONObject currProduct = (JSONObject) allProducts.get(pos);

            // Set title of fragment to name of current product
            getActivity().setTitle(currProduct.get(NAME_TAG).toString());

            // Reference current product image
            String currImage = currProduct.get(IMG_TAG).toString();

            // Set image
            new DownloadImageTask(imageView).execute
                    ("http://ucd.dawngregg.com/dlang/a6/custom/images/markers/" + currImage);

            // Get and set description
            String currDesc = currProduct.get(DESC_TAG).toString();
            description.setText(currDesc);
        }
        catch (JSONException e)
        {
            Log.e(TAG, "JSONException");
        }

        return current;
    }

    // Class that will download images from web page
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        private static final String TAG = "DownloadImageTask ";
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            Log.i(TAG, urlDisplay);

            try
            {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();

            }
            catch (MalformedURLException e)
            {
                Log.e(TAG, "MalformedURLException");
            }
            catch (IOException e)
            {
                Log.e(TAG, "IOException");
            }
            Bitmap resized = Bitmap.createScaledBitmap(mIcon11, 800, 380, false);
            return resized;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
            Log.i(TAG, "Bitmap set");
        }
    }
    public void onButtonPressed(String string, int pos) {
        if (mListener != null) {
            mListener.onFragmentInteraction(string, pos);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(String string, int position);
    }

}
