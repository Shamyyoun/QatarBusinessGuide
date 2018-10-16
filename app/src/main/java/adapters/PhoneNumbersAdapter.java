package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.R;

import java.util.List;

public class PhoneNumbersAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layoutResourceId;
    private List<String> data = null;

    public PhoneNumbersAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.textPhoneNumber = (TextView) row.findViewById(R.id.text_phoneNumber);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final String mobileNumber = data.get(position);

        // set data
        holder.textPhoneNumber.setText(mobileNumber);

        return row;
    }

    static class ViewHolder {
        TextView textPhoneNumber;
    }
}
