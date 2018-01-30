package com.demo.shubhamdhabhai.launcherdemo;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppListAdapter.OnAppClickedListener {


    private RecyclerView appListRv;
    private SearchView searchAppSv;
    private AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        LinearLayout mainLayout = findViewById(R.id.ll_main_layout);
        mainLayout.setBackground(wallpaperDrawable);
        appListRv = findViewById(R.id.rv_app_list);
        searchAppSv = findViewById(R.id.sv_app_list);
        populateAppList();
        setupSearchView();
    }

    private void setupSearchView() {
        searchAppSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void populateAppList() {
        PackageManager manager = getPackageManager();
        List<App> apps = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo resolveInfo : availableActivities) {
            App app = new App();
            app.setAppName(resolveInfo.loadLabel(manager).toString());
            app.setAppPackageName(resolveInfo.activityInfo.packageName);
            app.setIcon(resolveInfo.activityInfo.loadIcon(manager));
            apps.add(app);
        }
        appListAdapter = new AppListAdapter();
        appListAdapter.addAppList(apps);
        appListAdapter.setAppClickedListener(this);
        appListRv.setAdapter(appListAdapter);

    }

    @Override
    public void onAppCLicked(App app) {
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(app.getAppPackageName());
        startActivity(launchIntentForPackage);
    }
}
