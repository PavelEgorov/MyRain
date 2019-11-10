package com.egorovsoft.myrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        cityName = findViewById(R.id.textView_cityName);

        Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: ");
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

        Toast.makeText(getApplicationContext(), "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy: ");
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
}
