package com.egorovsoft.myrain;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeCity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);
        restore();

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackBar = Snackbar.make(v, R.string.saveChange, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.yes, snackbarOnClickListenerYes);
                //onBackPressed();
                snackBar.show();
            }
        });
    }

    View.OnClickListener snackbarOnClickListenerYes = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveData();
            onBackPressed();
        }
    };

    private void restore() {
        ((TextInputLayout)findViewById(R.id.editText_enter_city)).getEditText().setText(MainPresenter.getInstance().getCityName());
        ((CheckBox)findViewById(R.id.checkBoxPressure)).setChecked(MainPresenter.getInstance().isNeedPressure());
        ((CheckBox)findViewById(R.id.checkBoxSpeed)).setChecked(MainPresenter.getInstance().isNeedSpeed());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        //saveData();
    }

    @Override
    public void onBackPressed() {
        //saveData();

        super.onBackPressed();
    }

    private void saveData() {
        String cName = ((TextInputLayout)findViewById(R.id.editText_enter_city)).getEditText().getText().toString();
        boolean isVisibleS = ((CheckBox)findViewById(R.id.checkBoxSpeed)).isChecked();
        boolean isVisibleP = ((CheckBox)findViewById(R.id.checkBoxPressure)).isChecked();

        MainPresenter.getInstance().setCityName(cName);
        MainPresenter.getInstance().setNeedPressure(isVisibleS);
        MainPresenter.getInstance().setNeedSpeed(isVisibleP);

        MainPresenter.getInstance().savePreference(this);

        Intent intent = new Intent();
        intent.putExtra(MainPresenter.CITY_NAME, cName);
        intent.putExtra(MainPresenter.SPEED_VISIBLE, isVisibleS);
        intent.putExtra(MainPresenter.PRESSURE_VISIBLE, isVisibleP);
        setResult(RESULT_OK, intent);

        Publisher.getInstance().notifyCity(cName);
        Publisher.getInstance().notifyVisibleWindSpeed(isVisibleS);
        Publisher.getInstance().notifyVisiblePressure(isVisibleP);
    }
}
