package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.R;

import datamodels.ServiceCategory;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class ServicesCategoriesAdapter extends RecyclerView.Adapter<ServicesCategoriesAdapter.ViewHolder> {
    private Context context;
    private ServiceCategory[] data;
    private int layoutResId;

    private OnItemClickListener onItemClickListener;

    public ServicesCategoriesAdapter(Context context, ServiceCategory[] data, int layoutResId) {
        this.context = context;
        this.data = data;
        this.layoutResId = layoutResId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ServiceCategory serviceCategory = data[position];

        holder.imageThumbnail.setImageResource(serviceCategory.getIconResId());
        holder.textTitle.setText(serviceCategory.getName());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageThumbnail;
        public TextView textTitle;

        public ViewHolder(View v) {
            super(v);
            imageThumbnail = (ImageView) v.findViewById(R.id.image_thumbnail);
            textTitle = (TextView) v.findViewById(R.id.text_title);

            v.setOnClickListener(this);
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

}