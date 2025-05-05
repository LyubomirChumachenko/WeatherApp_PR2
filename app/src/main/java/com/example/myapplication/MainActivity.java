package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView buttonRu;
    private TextView buttonEn;
    private String currentLanguage = "RU";
    private LinearLayout cityList;
    private TextView textPlaceholder;

    // Лаунчер для получения результата от MapActivity
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRu = findViewById(R.id.tvRu);
        buttonEn = findViewById(R.id.tvEn);
        ImageButton btnNext = findViewById(R.id.addCityButton);
        cityList = findViewById(R.id.cityList);
        textPlaceholder = findViewById(R.id.textPlaceholder);

        // Инициализируем лаунчер для получения результата
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (result.getData().getBooleanExtra("ADD_CITY", false)) {
                    addCityToLayout();
                }
            }
        });

        if (btnNext == null) {
            Toast.makeText(this, "Кнопка не найдена", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonRu.setOnClickListener(v -> switchLanguage("RU"));
        buttonEn.setOnClickListener(v -> switchLanguage("EN"));
        btnNext.setOnClickListener(v -> goToNextActivity());

        updateLanguageUI();
    }

    private void addCityToLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cityView = inflater.inflate(R.layout.city_item, cityList, false);

        // Настройка данных города
        TextView tvCity = cityView.findViewById(R.id.tvCity);
        TextView tvTemperature = cityView.findViewById(R.id.tvTemperature);
        // ... другие элементы

        cityList.addView(cityView);

        // Скрываем плейсхолдер после добавления первого города
        if (textPlaceholder.getVisibility() == View.VISIBLE) {
            textPlaceholder.setVisibility(View.GONE);
        }
    }

    private void switchLanguage(String language) {
        if (!language.equals(currentLanguage)) {
            currentLanguage = language;
            Toast.makeText(this, "Выбран язык: " + language, Toast.LENGTH_SHORT).show();
            updateLanguageUI();
        }
    }

    private void updateLanguageUI() {
        if (currentLanguage.equals("RU")) {
            buttonRu.setBackgroundResource(R.drawable.selected_language_background);
            buttonEn.setBackgroundResource(R.drawable.unselected_language_background);
        } else {
            buttonEn.setBackgroundResource(R.drawable.selected_language_background);
            buttonRu.setBackgroundResource(R.drawable.unselected_language_background);
        }
    }

    private void goToNextActivity() {
        try {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("LANGUAGE", currentLanguage);
            // Используем новый ActivityResultLauncher вместо устаревшего startActivityForResult
            activityResultLauncher.launch(intent);
            // Убираем устаревший метод для анимации
            // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Убираем эту строку
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка открытия карты", Toast.LENGTH_SHORT).show();
        }
    }
}