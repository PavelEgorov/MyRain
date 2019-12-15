package com.egorovsoft.myrain.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.egorovsoft.myrain.MainPresenter;
import com.egorovsoft.myrain.connections.ConnectionToWheatherServer;

import androidx.annotation.Nullable;

public class UpdateWheatherService extends Service {
    private static final String TAG = "UPDATE_WHEATHER_SERVICE";
    private static final String EXTRA_CITY = MainPresenter.getInstance().CITY_NAME;

    private Thread thread;
    private String city;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        city = intent.getStringExtra(EXTRA_CITY);

        if (!thread.isInterrupted()) thread.interrupt(); // Если поток уже запущен, то остановим его.

        thread.setDaemon(true);
        thread.start();

        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!thread.isInterrupted()) {
                    try {
                        ConnectionToWheatherServer conn = new ConnectionToWheatherServer(city);
                        conn.refreshDataRetrofit();
                        conn.close();

                        MainPresenter.getInstance().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                MainPresenter.getInstance().updateActivity();
                            }
                        });

                        thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (thread != null && !thread.isInterrupted()) thread.interrupt();

        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
