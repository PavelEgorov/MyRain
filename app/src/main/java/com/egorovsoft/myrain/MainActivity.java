package com.egorovsoft.myrain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private static final int RESULT_CODE_MAIN = 1;
    private static final int RESULT_CODE_SETTINGS = 2;

    private static final String TAG = "MainActivity";

    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.AppTheme_Dark);
        }else{
            setTheme(R.style.AppTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, Main.class), RESULT_CODE_MAIN);
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

        sendMessage("onCreate()");
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
            /// Переключение темы пока не работает. Что-то не так делаю, на выходных будет больше времени, разберусь.
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
