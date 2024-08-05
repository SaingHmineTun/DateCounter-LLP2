package it.saimao.datecounter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.saimao.datecounter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Animal[] animals;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
        intListeners();
    }

    private void intListeners() {

        binding.btPrev.setOnClickListener(v -> {
            if (index == 0) {
                index = animals.length - 1;
            } else {
                index--;
            }
            updateInfo(); // DRY - Don't Repeat Yourself
        });

        binding.btNext.setOnClickListener(v -> {
            if (index == animals.length - 1) {
                index = 0;
            } else {
                index++;
            }
            updateInfo();

        });
    }

    private void updateInfo() {

        binding.iv.setImageResource(animals[index].res());
        binding.tvAnimal.setText(animals[index].name());
    }

    private void initData() {
        animals = new Animal[]{
                new Animal("Bird", R.drawable.bird),
                new Animal("Cat", R.drawable.cat),
                new Animal("Dog", R.drawable.dog),
                new Animal("Mouse", R.drawable.mouse),
                new Animal("Chicken", R.drawable.chicken),
        };
    }
}