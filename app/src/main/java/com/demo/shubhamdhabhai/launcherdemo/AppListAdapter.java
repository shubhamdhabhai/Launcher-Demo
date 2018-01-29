package com.demo.shubhamdhabhai.launcherdemo;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shubhamdhabhai on 29/01/18.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {

    private List<App> appList;

    @Nullable
    private OnAppClickedListener appClickedListener;

    public interface OnAppClickedListener {
        void onAppCLicked(App app);
    }

    public void addAppList(List<App> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    public void setAppClickedListener(@Nullable OnAppClickedListener appClickedListener) {
        this.appClickedListener = appClickedListener;
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_app_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        holder.bind(appList.get(position));
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {

        private ImageView appIcon;
        private TextView appName;


        public AppListViewHolder(View view) {
            super(view);
            appIcon = view.findViewById(R.id.iv_app);
            appName = view.findViewById(R.id.tv_app_name);
        }

        public void bind(final App app) {
            appIcon.setImageDrawable(app.getIcon());
            appName.setText(app.getAppName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (appClickedListener != null) {
                        appClickedListener.onAppCLicked(app);
                    }
                }
            });
        }
    }
}
