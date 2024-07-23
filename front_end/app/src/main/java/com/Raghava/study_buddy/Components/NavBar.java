package com.Raghava.study_buddy.Components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.Raghava.study_buddy.R;
import com.google.android.material.navigation.NavigationView;

public class NavBarFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ImageView menuIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nav_bar, container, false);

        menuIcon = view.findViewById(R.id.menuIcon);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title = item.getTitle().toString();
                switch (title) {
                    case "Study Rooms":
                        // Handle study rooms click
                        break;
                    case "Chat Rooms":
                        // Handle chat rooms click
                        break;
                    case "Leaderboard":
                        // Handle leaderboard click
                        break;
                    case "Events":
                        // Handle events click
                        break;
                    case "Resources":
                        // Handle resources click
                        break;
                    case "Notifications":
                        // Handle notifications click
                        break;
                    case "Profile":
                        // Handle profile click
                        break;
                    case "Logout":
                        // Handle logout click
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
