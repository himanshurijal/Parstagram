package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.fragments.ComposeFragment;
import com.example.parstagram.fragments.PostsFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.example.parstagram.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // VIEW
    FrameLayout flContainer;
    BottomNavigationView bnvBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FrameManager is responsible for changing
        // the fragment which is show in a FrameLayout.
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment composeFragment = new ComposeFragment();
        final Fragment postsFragment = new PostsFragment();
        final Fragment profileFragment = new ProfileFragment();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flContainer = findViewById(R.id.flContainer);
        bnvBottomNavigation = findViewById(R.id.bnvBottomNavigation);

        bnvBottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = new Fragment();
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = postsFragment;
                                break;
                            case R.id.action_compose:
                                fragment = composeFragment;
                                break;
                            case R.id.action_profile:
                                fragment = profileFragment;
                                break;
                            default:
                                fragment = postsFragment;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
        });
        // Set default selection
        bnvBottomNavigation.setSelectedItemId(R.id.action_home);
    }
}