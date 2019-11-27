package com.egorovsoft.myrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class TemperatureFragment extends Fragment implements Observer{

    public TemperatureFragment() {
        // Required empty public constructor
    }

   // TODO: Rename and change types and number of parameters
    public static TemperatureFragment newInstance(String param1, String param2) {
        TemperatureFragment fragment = new TemperatureFragment();
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
        return inflater.inflate(R.layout.fragment_temperature, container, false);
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void updateError(String err) {

    }

    @Override
    public void updateCity(String name) {

    }

    @Override
    public void updateTemp(String t) {
        ((TextView)getActivity().findViewById(R.id.textView_temp)).setText(t);
        if (MainPresenter.getInstance().isError()){
            Intent intent = new Intent(getContext(), ErrorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void updatePressure(String name) {

    }

    @Override
    public void updateWind(String name) {

    }

    @Override
    public void setVisibleWindSpeed(boolean isVisible) {

    }

    @Override
    public void setVisiblePressure(boolean isVisible) {

    }
}
