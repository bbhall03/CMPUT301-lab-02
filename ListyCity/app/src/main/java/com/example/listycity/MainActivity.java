package com.example.listycity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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
    int selectedPosition = -1;

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

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Change background of current selected cityList item
                if (position == selectedPosition) {
                    view.setBackgroundColor(
                            ContextCompat.getColor(getContext(), R.color.selected_item)
                    );
                } else {
                    view.setBackgroundColor(
                            ContextCompat.getColor(getContext(), R.color.white)
                    );
                }
                return view;
            }
        };
        cityList.setAdapter(cityAdapter);

        // initialize component variables
        Button addButton = (Button) findViewById(R.id.add_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        TextInputEditText newCityInput = (TextInputEditText) findViewById(R.id.new_city_text);

        // Set newCityInput and confirmButton as initially invisible
        newCityInput.setVisibility(GONE);
        confirmButton.setVisibility(GONE);

        // Set listener to get value of last selected city list item
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            selectedCity = dataList.get(position);
            cityAdapter.notifyDataSetChanged();
        });

        // Focus on input box when add button is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newCityInput.setVisibility(VISIBLE);
                confirmButton.setVisibility(VISIBLE);
                newCityInput.requestFocus();
            }
        });
        // Delete selected list item from list when delete button is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(selectedCity != null) {
                    dataList.remove(selectedCity);
                    selectedPosition = -1;
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });
        // Add city to list when confirm button is clicked if the input is not empty
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addCity = String.valueOf(newCityInput.getText());
                if(!addCity.isEmpty()) {
                    dataList.add(addCity);
                    cityAdapter.notifyDataSetChanged();
                    newCityInput.setText("");
                }
                confirmButton.setVisibility(GONE);
                newCityInput.setVisibility(GONE);
            }
        });
    }
}