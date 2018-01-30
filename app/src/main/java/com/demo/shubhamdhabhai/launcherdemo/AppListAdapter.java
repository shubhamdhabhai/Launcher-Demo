package com.demo.shubhamdhabhai.launcherdemo;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubhamdhabhai on 29/01/18.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> implements Filterable{

    private List<App> appList;
    private List<App> filteredAppList;

    @Nullable
    private OnAppClickedListener appClickedListener;

    public AppListAdapter() {
        appList = new ArrayList<>();
        filteredAppList = new ArrayList<>();
    }

    public interface OnAppClickedListener {
        void onAppCLicked(App app);
    }

    public void addAppList(List<App> appList) {
        this.appList = appList;
        this.filteredAppList = appList;
        notifyDataSetChanged();
    }

    public void setAppClickedListener(@Nullable OnAppClickedListener appClickedListener) {
        this.appClickedListener = appClickedListener;
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_app_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        holder.bind(filteredAppList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredAppList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<App> filteredList = new ArrayList<>();
                if(charSequence.length() == 0) {
                    filteredList = appList;
                } else {
                    for(App app : appList) {
                        if(app.getAppName().toLowerCase().contains(charSequence)) {
                            filteredList.add(app);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredAppList = (List<App>) filterResults.values;
                AppListAdapter.this.notifyDataSetChanged();

            }
        };
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
