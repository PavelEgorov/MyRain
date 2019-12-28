package com.egorovsoft.myrain.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.egorovsoft.myrain.MainPresenter;
import com.egorovsoft.myrain.R;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private RadioButton rbLight;
    private RadioButton rbDark;

    private RadioButton rbEn;
    private RadioButton rbRu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        restore();

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void restore() {
        rbLight =   findViewById(R.id.radioButton_light);
        rbDark =   findViewById(R.id.radioButton_dark);

        rbEn =   findViewById(R.id.radioButton_english);
        rbRu =   findViewById(R.id.radioButton_Russian);


        if (MainPresenter.getInstance().getLanguage() == MainPresenter.getInstance().LANGUAGE_EN){
            rbEn.setChecked(true);
        }else if (MainPresenter.getInstance().getLanguage() == MainPresenter.getInstance().LANGUAGE_RU){
            rbRu.setChecked(true);
        }else{
            rbEn.setChecked(true);
        }

        if (MainPresenter.getInstance().getTheme() == MainPresenter.getInstance().THEME_DARK){
            rbDark.setChecked(true);
        }else if (MainPresenter.getInstance().getTheme() == MainPresenter.getInstance().THEME_LIGHT){
            rbLight.setChecked(true);
        }else{
            rbLight.setChecked(true);
        }

        CheckBox use_temp_sensor = findViewById(R.id.checkBox_temperature_sensor);
        use_temp_sensor.setChecked(MainPresenter.getInstance().getTemperatureSensorIsActive());

        CheckBox use_hum_sensor = findViewById(R.id.checkBox_humidity_sensor);
        use_hum_sensor.setChecked(MainPresenter.getInstance().getHumiditySensorIsActive());


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
        rbLight =   findViewById(R.id.radioButton_light);
        rbDark =   findViewById(R.id.radioButton_dark);

        rbEn =   findViewById(R.id.radioButton_english);
        rbRu =   findViewById(R.id.radioButton_Russian);

        if (rbEn.isChecked()){
            MainPresenter.getInstance().setLanguage(MainPresenter.getInstance().LANGUAGE_EN);
        }else if (rbRu.isChecked()){
            MainPresenter.getInstance().setLanguage(MainPresenter.getInstance().LANGUAGE_RU);
        }else{
            MainPresenter.getInstance().setLanguage(MainPresenter.getInstance().LANGUAGE_EN);
        }

        if (rbDark.isChecked()){
            MainPresenter.getInstance().setTheme(MainPresenter.getInstance().THEME_DARK);
        }else if (rbLight.isChecked()){
            MainPresenter.getInstance().setTheme(MainPresenter.getInstance().THEME_LIGHT);
        }else{
            MainPresenter.getInstance().setTheme(MainPresenter.getInstance().THEME_LIGHT);
        }

        CheckBox use_temp_sensor = findViewById(R.id.checkBox_temperature_sensor);
        MainPresenter.getInstance().setTemperatureSensorIsActive(use_temp_sensor.isChecked());

        CheckBox use_hum_sensor = findViewById(R.id.checkBox_humidity_sensor);
        MainPresenter.getInstance().setHumiditySensorIsActive(use_hum_sensor.isChecked());

        MainPresenter.getInstance().savePreference(this);

        Intent intent = new Intent();
        intent.putExtra(MainPresenter.THEME_INSTALLED, ((RadioButton)findViewById(R.id.radioButton_light)).isChecked());
        setResult(RESULT_OK, intent);
    }
}
