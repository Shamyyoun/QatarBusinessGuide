package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import datamodels.SearchableItem;
import utils.ViewUtil;

public class SearchAdapter extends ArrayAdapter<SearchableItem> {
    private Activity activity;
    private int layoutResourceId;
    private List<SearchableItem> data = null;

    public SearchAdapter(Activity activity, int layoutResourceId, List<SearchableItem> data) {
        super(activity, layoutResourceId, data);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.textName = (TextView) row.findViewById(R.id.text_name);
            holder.layoutThumbnailRoot = row.findViewById(R.id.layout_thumbnailRoot);
            holder.imageThumbnail = (ImageView) row.findViewById(R.id.image_thumbnail);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        // set data
        SearchableItem searchableItem = data.get(position);
        holder.textName.setText(AppController.getInstance(activity.getApplicationContext()).getLang().equals("en") ? searchableItem.getNameEn() : searchableItem.getName());

        // check if has thumbnail
        if (searchableItem.getThumbnail() == null)
            // doesn't have >> hide it
            holder.layoutThumbnailRoot.setVisibility(View.GONE);
        else if (!searchableItem.getThumbnail().isEmpty()) {
            // load image
            final ViewHolder finalHolder = holder;
            Picasso.with(activity).load(searchableItem.getThumbnail()).into(holder.imageThumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    ViewUtil.showView(finalHolder.imageThumbnail, true);
                }

                @Override
                public void onError() {
                }
            });
        }

        return row;
    }

    /**
     * method, used to add items to adapter
     */
    public void addMore(List<SearchableItem> searchableItems) {
        data.addAll(searchableItems);
        notifyDataSetChanged();
    }

    /**
     * method, used to get last searchable item id
     */
    public String getLastItemId() {
        return data.get(data.size() - 1).getId();
    }

    static class ViewHolder {
        TextView textName;
        View layoutThumbnailRoot;
        ImageView imageThumbnail;
    }
}
