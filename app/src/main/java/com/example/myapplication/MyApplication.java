package com.example.myapplication;

import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class MyApplication extends Application {
    private static volatile boolean isMapKitInitialized = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeMapKit();
    }

    private void initializeMapKit() {
        if (!isMapKitInitialized) {
            try {
                MapKitFactory.setApiKey("b504e178-2074-47b2-9a81-2dfdae6fb539");
                MapKitFactory.initialize(this);
                isMapKitInitialized = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isMapKitInitialized() {
        return isMapKitInitialized;
    }

    public static void forceSetInitialized() {
        isMapKitInitialized = true;
    }
}