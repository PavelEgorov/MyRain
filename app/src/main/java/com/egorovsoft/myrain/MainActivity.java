package com.egorovsoft.myrain;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    private static final int RESULT_CODE_MAIN = 1;
    private static final int RESULT_CODE_SETTINGS = 2;

    private static final String TAG = "MainActivity";

    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /// Если приложение впервые запущено, то загрузим данные.
        if (savedInstanceState == null) MainPresenter.getInstance().loadPreference(this);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = getResources().getStringArray(R.array.items);
        initRecyclerView(data);

        findViewById(R.id.buttonChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ChangeCity.class), RESULT_CODE_MAIN);
            }
        });
        findViewById(R.id.buttonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, Settings.class), RESULT_CODE_SETTINGS);
            }
        });

        findViewById(R.id.button_brows).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Без локализации. просто вызываю погоду в яндексе на неделю по названию города.
                // В дальнейшем нужно доработать этот вариант. Проверить на кирилицу и привести её к латинице.
                String uriStr = String.format("https://yandex.ru/pogoda/" + cityName.getText().toString() + "?from=serp_title");
                Uri uri = Uri.parse(uriStr);
                Intent runEchoIntent = new Intent(Intent.ACTION_VIEW, uri);
                ActivityInfo activityInfo =
                        runEchoIntent.resolveActivityInfo(getPackageManager(),
                                runEchoIntent.getFlags());
                if (activityInfo != null) {
                    startActivity(runEchoIntent);
                }
            }
        });

        cityName = findViewById(R.id.textView_cityName);

        ///{{ Тест кнопка для тестирования запроса на сайт
        findViewById(R.id.button_UpdateTemperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPresenter.getInstance().updateData();
            }
        });
        ///}}

        sendMessage("onCreate()");

        ///{{ Запускаем слушатель сенсора температуры
        MainPresenter.getInstance().registerTemperatureListener(this);
        ///}}
        ///{{ Запускаем слушатель сенсора влажности
        MainPresenter.getInstance().registerHumidityListener(this);
        ///}}
    }

    private void initRecyclerView(String[] data) {
        Weekend[] weekends = new Weekend[data.length];
        for (int i=0; i<data.length; i++){
            weekends[i] =   new Weekend(data[i]);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView_weekend);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        WeekendAdapter adapter = new WeekendAdapter(weekends);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,  LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setInterface();

        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // С учетом того, что Bundle у меня пустой данный метод не вызывается. По этому я поместил обновление города
        // в метом onStart() т.к. onRestoreInstanceState вызывается после него.

        sendMessage("onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        sendMessage("onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        sendMessage("onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        ///{{ Останавливаем слушатель сенсора температуры
        MainPresenter.getInstance().unRegisterTemperatureListener(this);
        ///}}
        ///{{ Останавливаем слушатель сенсора влажности
        MainPresenter.getInstance().unRegisterHumidityListener(this);
        ///}}

        sendMessage("onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        sendMessage("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        sendMessage("onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        sendMessage("onDestroy()");
    }

    private void setInterface() {
        cityName.setText(MainPresenter.getInstance().getCityName());

        if (MainPresenter.getInstance().isNeedPressure()){
            ((TextView)findViewById(R.id.textViewPressure)).setVisibility(View.VISIBLE);
        }else{
            ((TextView)findViewById(R.id.textViewPressure)).setVisibility(View.INVISIBLE);
        }

        if (MainPresenter.getInstance().isNeedSpeed()){
            ((TextView)findViewById(R.id.textView_Speed)).setVisibility(View.VISIBLE);
        }else{
            ((TextView)findViewById(R.id.textView_Speed)).setVisibility(View.INVISIBLE);
        }
    }

    public void sendMessage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == RESULT_CODE_MAIN ){
            ((TextView)findViewById(R.id.textView_cityName)).setText(data.getStringExtra(MainPresenter.CITY_NAME));
        }
        if (requestCode == RESULT_CODE_SETTINGS){
            if (data.getBooleanExtra(MainPresenter.THEME_INSTALLED, true)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                restartActivity();
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                restartActivity();
            }
        }
    }

    private void restartActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
