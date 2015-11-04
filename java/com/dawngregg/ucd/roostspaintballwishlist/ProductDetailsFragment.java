package com.dawngregg.ucd.roostspaintballwishlist;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Configuration;


public class ProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String data = "";
    private int pos = 0;
    private boolean visible = false;

    private OnFragmentInteractionListener mListener;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            data = getArguments().getString(FirstActivity.dataStr);
            pos = getArguments().getInt(FirstActivity.posStr);
            visible = getArguments().getBoolean(FirstActivity.page2);
        }

        getActivity().setTitle(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View current;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            current = inflater.inflate(R.layout.fragment_product_details, container, false);
        }
        else
        {
            // Set layout to landscape mode
            current = inflater.inflate(R.layout.product_details_landscape, container, false);
        }


        TextView textView = (TextView) current.findViewById(R.id.textView);
        ImageView imageView = (ImageView) current.findViewById(R.id.paintball_image);
        Button buttonView = (Button) current.findViewById(R.id.action_add);

        if (visible)
        {
            textView.setText(R.string.statusIn);
            buttonView.setText(R.string.remove);
        }

        return current;
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
