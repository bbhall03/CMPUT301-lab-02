package com.example.listycity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declaring city list variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    String selectedCity;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Toronto", "Lethbridge", "Calgary"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // initialize component variables
        Button addButton = (Button) findViewById(R.id.add_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        TextInputEditText newCityInput = (TextInputEditText) findViewById(R.id.new_city_text);

        // Set listener to get value of last selected city list item
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);
        });

        // Focus on
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newCityInput.requestFocus();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(selectedCity != null) {
                    dataList.remove(selectedCity);
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addCity = String.valueOf(newCityInput.getText());
                if(!addCity.isEmpty()) {
                    dataList.add(addCity);
                    cityAdapter.notifyDataSetChanged();
                    newCityInput.setText("");
                }
            }
        });
    }
}