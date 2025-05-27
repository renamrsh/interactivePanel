package com.example.interactivepanel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView settingsListView;
    TextView editingLabelTextView, seekBarValueTextView;
    SeekBar valueSeekBar;
    int selectedItemPosition = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        settingsListView = findViewById(R.id.settingsListView);
        editingLabelTextView = findViewById(R.id.editingLabelTextView);
        editingLabelTextView.setText("Wybierz opcję z listy powyżej");
        valueSeekBar = findViewById(R.id.valueSeekBar);
        valueSeekBar.setEnabled(false);
        seekBarValueTextView = findViewById(R.id.seekBarValueTextView);

        ArrayList<String> settingNames = new ArrayList<>();
        settingNames.add("Jasność Ekranu");
        settingNames.add("Głośność Dźwięków");
        settingNames.add("Czas Automatycznej Blokady");

        ArrayList<Integer> settingValues = new ArrayList<>();
        settingValues.add(50);
        settingValues.add(80);
        settingValues.add(30);

        ArrayList<String> settingUnits = new ArrayList<>();
        settingUnits.add("%");
        settingUnits.add("%");
        settingUnits.add("s");

        ArrayList<String> displayItemsForListView = new ArrayList<>();
        displayItemsForListView.add(settingNames.get(0)+": "+settingValues.get(0)+settingUnits.get(0));
        displayItemsForListView.add(settingNames.get(1)+": "+settingValues.get(1)+settingUnits.get(1));
        displayItemsForListView.add(settingNames.get(2)+": "+settingValues.get(2)+settingUnits.get(2));

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_setting,
                R.id.itemTextView,
                displayItemsForListView
        );
        settingsListView.setAdapter(listAdapter);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
                editingLabelTextView.setText("Edytujesz: "+settingNames.get(selectedItemPosition));
                valueSeekBar.setProgress(settingValues.get(selectedItemPosition));
                seekBarValueTextView.setText("Wartość: "+valueSeekBar.getProgress());
                valueSeekBar.setEnabled(true);
            }
        });

        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                if(fromUser && selectedItemPosition!=-1){
                    seekBarValueTextView.setText("Wartość: "+progressValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(selectedItemPosition!=-1){
                    settingValues.set(selectedItemPosition,seekBar.getProgress());
                    String sentence = settingNames.get(selectedItemPosition) +": "+settingValues.get(selectedItemPosition)+settingUnits.get(selectedItemPosition);
                    displayItemsForListView.set(selectedItemPosition, sentence);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}