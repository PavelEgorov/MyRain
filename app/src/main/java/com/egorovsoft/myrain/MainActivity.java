package com.egorovsoft.myrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /// Включаем чтобы стартавала страница с выбором города
        setContentView(R.layout.activity_city_change);

        findViewById(R.id.buttonKrasnodar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick("Krasnodar");
            }
        });
        findViewById(R.id.buttonMoscow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick("Moscow");
            }
        });
            findViewById(R.id.buttonNovosibirsk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick("Novosibirsk");
            }
        });
        findViewById(R.id.buttonPetersburg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick("Petersburg");
            }
        });
    }

    protected void buttonClick(String msg){
        // Активити создавал вручную, по этому не переключается, возможно что-то не так сделал.
        // Пока закомментировал код. В будущем перепишу.
        Intent intent = new Intent(MainActivity.this, Main.class);
        startActivity(intent);
    }
}
