package com.egorovsoft.myrain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DataWheatherFragment extends Fragment implements Observer {

    public DataWheatherFragment() {
        // Required empty public constructor
    }

    public static DataWheatherFragment newInstance(String param1, String param2) {
        DataWheatherFragment fragment = new DataWheatherFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Publisher.getInstance().subscribe(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_wheather, container, false);
    }

    @Override
    public void onDestroyView() {
        Publisher.getInstance().unsubscribe(this);

        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateCity(String name) {

    }

    @Override
    public void setVisibleWindSpeed(boolean isVisible) {
        ((TextView)getActivity().findViewById(R.id.textView_Speed)).setVisibility(isVisible? View.VISIBLE : View.GONE);
    }

    @Override
    public void setVisiblePressure(boolean isVisible) {
        ((TextView)getActivity().findViewById(R.id.textViewPressure)).setVisibility(isVisible? View.VISIBLE : View.GONE);
    }
}
