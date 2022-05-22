package com.example.monopoly;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.monopoly.game.GameFragment;
import com.example.monopoly.util.DateFormatter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.monopoly.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }




    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host_fragment_content_main).getCurrentDestination().getId() == R.id.gameFragment) {
//            ((GameFragment)getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().getFragments().get(0)).stopRolling();
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure you want to quit?")
                    .setNegativeButton("no",null)
                    .setPositiveButton("yes", (dialog, which) -> {
                        ((GameFragment)getSupportFragmentManager().getFragments().get(0).getChildFragmentManager().getFragments().get(0)).deleteGameFromDatabase();
                        MainActivity.super.onBackPressed();

                    })
                    .show();
        }
        else
            super.onBackPressed();
    }
}