package adapters;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;

import datamodels.SubSector;

public class SubSectorsAdapter extends ArrayAdapter<SubSector> {
    private ActionBarActivity activity;
    private int layoutResourceId;
    private SubSector[] data = null;

    public SubSectorsAdapter(ActionBarActivity activity, int layoutResourceId, SubSector[] data) {
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

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        SubSector subSector = data[position];

        // set data
        holder.textName.setText(AppController.getInstance(activity.getApplicationContext()).getLang().equals("en") ? subSector.getNameEn() : subSector.getName());

        return row;
    }

    static class ViewHolder {
        TextView textName;
    }
}
