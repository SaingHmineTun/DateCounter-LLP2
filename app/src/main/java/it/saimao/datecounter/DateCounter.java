package it.saimao.datecounter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import it.saimao.datecounter.databinding.ActivityDateCounterBinding;
import it.saimao.datecounter.databinding.DialogInputBinding;

public class DateCounter extends AppCompatActivity {

    private ActivityDateCounterBinding binding;
    private DatePickerDialog datePickerDialog;
    private AlertDialog alertDialog;
    private String dialogName;
    private static final String TOM = "Tom";
    private static final String JERRY = "Jerry";

    private SharedPreferences sp;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();

        initDatePickerDialog();
        initInputDialog();
        initListeners();
    }

    private void initUi() {
        sp = getSharedPreferences("date_counter", MODE_PRIVATE);
        binding.tvTom.setText(read(TOM));
        binding.tvJerry.setText(read(JERRY));
    }

    private void initInputDialog() {
        var dialogBinding = DialogInputBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder
                .setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.btConfirm.setOnClickListener(v -> {
            String text = dialogBinding.etInput.getText().toString();
            switch (dialogName) {
                case TOM -> {
                    binding.tvTom.setText(text);
                    save(TOM, text);
                }
                case JERRY -> {
                    binding.tvJerry.setText(text);
                    save(JERRY, text);
                }
            }
            dialogBinding.etInput.getText().clear();
            alertDialog.cancel();
        });

        dialogBinding.btCancel.setOnClickListener(v -> alertDialog.cancel());
    }

    private void initDatePickerDialog() {
        // dd/MM/yyyy
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            var selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            binding.btDate.setText(formatter.format(selectedDate));
            updateDate();
        });
        updateDate();
    }

    private void updateDate() {
        var date = LocalDate.parse(binding.btDate.getText().toString(), formatter);
        var todayDate = LocalDate.now();
        var difference = todayDate.toEpochDay() - date.toEpochDay();
        binding.tvDate.setText(difference + " Days");
    }

    private void initListeners() {

        binding.btDate.setOnClickListener(v -> datePickerDialog.show());
        binding.tvTom.setOnClickListener(v -> showCustomDialog(TOM));
        binding.tvJerry.setOnClickListener(v -> showCustomDialog(JERRY));
    }

    private void showCustomDialog(String name) {
        dialogName = name;
        alertDialog.show();
    }

    // Save data in shared preference
    private void save(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Read data from shared preference
    private String read(String key) {
        return sp.getString(key, key);
    }

}