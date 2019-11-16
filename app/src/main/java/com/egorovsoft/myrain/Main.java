package com.egorovsoft.myrain;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);
        restore();

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void restore() {
        ((EditText)findViewById(R.id.editText_enter_city)).setText(MainPresenter.getInstance().getCityName());
        ((CheckBox)findViewById(R.id.checkBoxPressure)).setChecked(MainPresenter.getInstance().isNeedPressure());
        ((CheckBox)findViewById(R.id.checkBoxSpeed)).setChecked(MainPresenter.getInstance().isNeedSpeed());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        saveData();
    }

    @Override
    public void onBackPressed() {
        saveData();

        super.onBackPressed();
    }

    private void saveData() {
        MainPresenter.getInstance().setCityName(((EditText)findViewById(R.id.editText_enter_city)).getText().toString());
        MainPresenter.getInstance().setNeedPressure(((CheckBox)findViewById(R.id.checkBoxPressure)).isChecked());
        MainPresenter.getInstance().setNeedSpeed(((CheckBox)findViewById(R.id.checkBoxSpeed)).isChecked());

        Intent intent = new Intent();
        intent.putExtra(MainPresenter.CITY_NAME, ((EditText)findViewById(R.id.editText_enter_city)).getText().toString());
        intent.putExtra(MainPresenter.SPEED_VISIBLE, ((CheckBox)findViewById(R.id.checkBoxSpeed)).isChecked());
        intent.putExtra(MainPresenter.PRESSURE_VISIBLE, ((CheckBox)findViewById(R.id.checkBoxPressure)).isChecked());
        setResult(RESULT_OK, intent);
    }
}
