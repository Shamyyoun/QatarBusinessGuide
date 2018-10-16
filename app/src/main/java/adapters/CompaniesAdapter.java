package adapters;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpardogo.listbuddies.lib.adapters.CircularLoopAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import datamodels.Company;
import utils.ViewUtil;

public class CompaniesAdapter extends CircularLoopAdapter {
    private ActionBarActivity activity;
    private int layoutResourceId;
    private Company[] data = null;
    private int itemHeight;
    private AbsListView.LayoutParams layoutParams;

    public CompaniesAdapter(ActionBarActivity activity, int layoutResourceId, Company[] data, int itemHeightDimenRes) {
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.data = data;

        itemHeight = (int) activity.getResources().getDimension(itemHeightDimenRes);
        layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemHeight);
    }

    @Override
    public Company getItem(int position) {
        return data[getCircularPosition(position)];
    }

    @Override
    protected int getCircularCount() {
        return data.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder holder = null;

        if (item == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.imageDefault = (ImageView) item.findViewById(R.id.image_default);
            holder.imageImage = (ImageView) item.findViewById(R.id.image_image);
            holder.textName = (TextView) item.findViewById(R.id.text_name);

            item.setTag(holder);

        } else {
            holder = (ViewHolder) item.getTag();
        }

        Company company = getItem(position);

        // set item height
        item.setLayoutParams(layoutParams);

        // set name
        holder.textName.setText(AppController.getInstance(activity.getApplicationContext()).getLang().equals("en") ? company.getNameEn() : company.getName());

        // ensure that image url is not empty
        if (!company.getLogo().isEmpty()) {
            // decode logo url
            try {
                URLDecoder.decode(company.getLogo(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }

            // load image
            final ViewHolder finalHolder = holder;
            Picasso.with(activity).load(company.getLogo()).into(holder.imageImage, new Callback() {
                @Override
                public void onSuccess() {
                    ViewUtil.showView(finalHolder.imageImage, true);
                    ViewUtil.showView(finalHolder.imageDefault, false);
                }

                @Override
                public void onError() {
                }
            });
        }

        return item;
    }

    static class ViewHolder {
        ImageView imageDefault;
        ImageView imageImage;
        TextView textName;
    }
}
