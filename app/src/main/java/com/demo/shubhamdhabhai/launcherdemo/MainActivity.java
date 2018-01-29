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
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppListAdapter.OnAppClickedListener {


    private RecyclerView appListRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        FrameLayout mainLayout = findViewById(R.id.fl_main_layout);//Substitute with your layout
        mainLayout.setBackground(wallpaperDrawable);
        appListRv = findViewById(R.id.rv_app_list);
        populateAppList();
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
        AppListAdapter adapter = new AppListAdapter();
        adapter.addAppList(apps);
        adapter.setAppClickedListener(this);
        appListRv.setAdapter(adapter);

    }

    @Override
    public void onAppCLicked(App app) {
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(app.getAppPackageName());
        startActivity(launchIntentForPackage);
    }
}
