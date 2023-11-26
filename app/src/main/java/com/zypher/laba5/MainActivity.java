package com.zypher.laba5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText filterEditText;
    private DataLoader dataLoader;
    private ArrayList<String> originalCurrencyList;
    private ArrayList<String> filteredCurrencyList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        filterEditText = findViewById(R.id.filterEditText);

        originalCurrencyList = new ArrayList<>();
        filteredCurrencyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredCurrencyList);
        listView.setAdapter(adapter);

        dataLoader = new DataLoader(new DataLoader.DataLoadListener() {
            @Override
            public void onDataLoaded(ArrayList<String> data) {
                originalCurrencyList.clear();
                originalCurrencyList.addAll(data);
                filterData("");
            }
        });

        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String filter = charSequence.toString();
                filterData(filter);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void filterData(String filter) {
        filteredCurrencyList.clear();

        for (String currency : originalCurrencyList) {
            if (currency.toLowerCase().contains(filter.toLowerCase())) {
                filteredCurrencyList.add(currency);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
