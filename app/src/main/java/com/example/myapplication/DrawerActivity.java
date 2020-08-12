package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drawer);
        fragmentManager = getSupportFragmentManager();
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DrawerActivity.this,drawerLayout,toolbar,
                0,0);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setItemIconPadding(dpTopix(30));
        fragmentManager.beginTransaction().replace(R.id.frameL,Fragment1.getInstance("这是Fragmnet1")).commit();
        View head = navigationView.getHeaderView(0);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this,"点击了头部",Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.fragmen1:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().replace(R.id.frameL,Fragment1.getInstance("这是Fragmnet1")).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.fragmen2:
                        fragmentManager.beginTransaction().replace(R.id.frameL,Fragment2.getInstance("这是Fragmnet2")).commit();
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.fragmen3:
                        fragmentManager.beginTransaction().replace(R.id.frameL,Fragment3.getInstance("这是Fragmnet3")).commit();
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.fragmen4:
                        fragmentManager.beginTransaction().replace(R.id.frameL,Fragment4.getInstance("这是Fragmnet4")).commit();
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.fragmen5:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen6:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen7:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen8:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen9:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen10:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen11:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen13:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen14:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fragmen15:
                        Toast.makeText(DrawerActivity.this,"点击了"+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    private int dpTopix(int pix){
        DisplayMetrics display = getResources().getDisplayMetrics();
        return (int)(display.density*pix+0.5);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
