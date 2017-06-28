package com.artiomlevchuk.yourbandsintown;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class App extends Application {

    public static final String API_KEY = "09901063-740b-4e9d-a7c4-982760a81565";

    @Override public void onCreate() {
        super.onCreate();

        Log.d("TAG_APPLICATION", "init");

        YandexMetricaConfig.Builder configBuilder = YandexMetricaConfig.newConfigBuilder(API_KEY);
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("PREF_IS_FIRST", true))
            configBuilder.handleFirstActivationAsUpdate(true);
        YandexMetricaConfig extendedConfig = configBuilder.build();

        YandexMetrica.activate(getApplicationContext(), extendedConfig);
        YandexMetrica.enableActivityAutoTracking(this);
    }

}
