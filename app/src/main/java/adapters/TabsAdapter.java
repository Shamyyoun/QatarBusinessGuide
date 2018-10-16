package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.R;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHolder> {
    private String[] data;
    private int selectedTab;

    private TextView[] tabTitles;
    private OnItemClickListener onItemClickListener;

    public TabsAdapter(String[] data, int selectedTab) {
        this.data = data;
        this.selectedTab = selectedTab;

        tabTitles = new TextView[data.length];
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = data[position];

        holder.textTitle.setText(title);

        // --check item index--
        if (position == selectedTab)
            // if first item, select tab
            holder.textTitle.setSelected(true);

        if (position == (data.length - 1))
            // if last item, hide seperator
            holder.viewSeperator.setVisibility(View.GONE);

        tabTitles[position] = holder.textTitle;
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tabs_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textTitle;
        public View viewSeperator;

        public ViewHolder(View v) {
            super(v);
            textTitle = (TextView) v.findViewById(R.id.text_title);
            viewSeperator = v.findViewById(R.id.view_seperator);

            textTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void selectTab(int position) {
        // deselect all first
        for (TextView tabTitle : tabTitles) {
            if (tabTitle != null)
                tabTitle.setSelected(false);
        }

        // select desired tab
        tabTitles[position].setSelected(true);
        selectedTab = position;
    }

}