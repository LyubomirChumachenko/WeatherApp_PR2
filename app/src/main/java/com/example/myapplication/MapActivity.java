package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;

public class MapActivity extends AppCompatActivity {
    private static final Point KRASNOYARSK_LOCATION = new Point(56.0153, 92.8932);
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MyApplication.isMapKitInitialized()) {
            try {
                MapKitFactory.setApiKey("b504e178-2074-47b2-9a81-2dfdae6fb539");
                MapKitFactory.initialize(this);
                MyApplication.forceSetInitialized();
            } catch (Exception e) {
                Toast.makeText(this, "Ошибка инициализации карт", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            mapView = findViewById(R.id.mapView);
            if (mapView == null) {
                throw new RuntimeException("MapView not found in layout");
            }

            setupMap();

            ImageButton backButton = findViewById(R.id.backButton);
            ImageButton nextButton = findViewById(R.id.nextButton);

            backButton.setOnClickListener(v -> finishWithTransition());

            nextButton.setOnClickListener(v -> {
                // Создаем Intent для возврата в MainActivity с флагом добавления города
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ADD_CITY", true);
                setResult(RESULT_OK, resultIntent);
                finishWithTransition();
            });

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка загрузки карты", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupMap() {
        Map map = mapView.getMap();
        map.move(
                new CameraPosition(KRASNOYARSK_LOCATION, 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );
        map.setNightModeEnabled(true);
    }

    private void finishWithTransition() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) {
            MapKitFactory.getInstance().onStart();
            mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        if (mapView != null) {
            mapView.onStop();
            MapKitFactory.getInstance().onStop();
        }
        super.onStop();
    }
}