package com.egorovsoft.myrain;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity implements Observer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Publisher.getInstance().subscribe(this);

        ((TextView)findViewById(R.id.textView_error)).setText(MainPresenter.getInstance().getError());
    }

    @Override
    public void updateError(String err) {
        ((TextView)findViewById(R.id.textView_error)).setText(err);
    }

    @Override
    public void updateCity(String name) {

    }

    @Override
    public void updateTemp(String name) {

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

    @Override
    protected void onStop() {
        Publisher.getInstance().unsubscribe(this);

        super.onStop();
    }
}
