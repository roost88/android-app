package com.dawngregg.ucd.roostspaintballwishlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dawngregg.ucd.roostspaintballwishlist.dummy.DummyContent;

public class ProductFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title1";
    private static final String ARG_PARAM2 = "title2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static ProductFragment newInstance(String title1, String title2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title1);
        args.putString(ARG_PARAM2, title2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Set title
        getActivity().setTitle(mParam1);

//        // TODO: Change Adapter to display your content
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));

        new HttpGetTask().execute();

        // Retain instance
        setRetainInstance(true);
    }

    private class HttpGetTask extends AsyncTask<Void, Void, List> {

        private static final String TAG = "HttpGetTask ";
        private static final String URL = "http://ucd.dawngregg.com/dlang/a6/data.json.php";

        @Override
        protected List doInBackground(Void... params) {
            List data = null;
            HttpURLConnection httpUrlConnection = null;

            try
            {
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

                JSONResponseHandler mClient = new JSONResponseHandler();

                String jdata = readStream(in);

                data = mClient.handleResponse(jdata);
            }
            catch (MalformedURLException exception)
            {
                Log.e(TAG, "MalformedURLException");
            }
            catch (IOException exception)
            {
                Log.e(TAG, "IOException");
            }
            finally
            {
                if (null != httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List result) {
            // Create a ListAdapter from the JSON List data
            ArrayAdapter listAdapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, result);
            setListAdapter(listAdapter);
        }

        private String readStream(InputStream in) {
            String TAG = "readStream ";
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");

            try
            {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    data.append(line);
                }
            }
            catch (IOException e)
            {
                Log.e(TAG, "IOException");
            }
            finally
            {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }
    }

    private class JSONResponseHandler {

//        private static final String ID_TAG = "ItemID";
        private static final String NAME_TAG = "Name";
        private static final String PRODUCTS_TAG = "PRODUCTS";
        private static final String TAG = "JSONResponseHandler ";

        public List handleResponse(String JSONResponse) throws IOException
        {
            List result = new ArrayList();

            try
            {
                // Get top-level JSON Object - a Map
                JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

                // Extract value of "PRODUCTS" key -- a List
                JSONArray allProducts = responseObject.getJSONArray(PRODUCTS_TAG);

                // Iterate over products list
                for (int i = 0; i < allProducts.length(); i++)
                {
                    // Get single product - a Map
                    JSONObject singleProduct = (JSONObject) allProducts.get(i);

                    // Summarize product data as a string and add it to
                    // result
                    result.add(singleProduct.get(NAME_TAG));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return result;
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id, position);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String data, int position);
    }

}
