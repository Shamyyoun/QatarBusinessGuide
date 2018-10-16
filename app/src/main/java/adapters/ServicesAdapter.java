package adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;

import java.util.ArrayList;
import java.util.List;

import datamodels.Service;
import views.SlideExpandableListView;

public class ServicesAdapter extends SlideExpandableListView.AnimatedExpandableListAdapter {
    private Activity activity;
    private int groupLayoutResourceId;
    private int childLayoutResourceId;
    private List<Service> data;

    private LayoutInflater inflater;
    private List<GroupHolder> groupHolders;

    public ServicesAdapter(Activity activity, int groupLayoutResourceId,
                           int childLayoutResourceId, List<Service> data) {
        this.activity = activity;
        this.groupLayoutResourceId = groupLayoutResourceId;
        this.childLayoutResourceId = childLayoutResourceId;
        this.data = data;

        inflater = LayoutInflater.from(activity);
        groupHolders = new ArrayList<GroupHolder>(data.size());
    }

    @Override
    public Service getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;
        final Service service = getChild(groupPosition, childPosition);

        if (convertView == null) {
            holder = new ChildHolder();

            convertView = inflater.inflate(childLayoutResourceId, parent, false);
            holder.layoutAddress = convertView.findViewById(R.id.layout_cairo_address);
            holder.textAddress = (TextView) convertView.findViewById(R.id.text_address);
            holder.layoutPhone = convertView.findViewById(R.id.layout_phone);
            holder.textPhone = (TextView) convertView.findViewById(R.id.text_phone);

            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        // set data
        String address = AppController.getInstance(activity.getApplicationContext()).getLang().equals("ar") ? service.getAddressAr() : service.getAddressEn();

        if (address == null) {
            holder.layoutAddress.setVisibility(View.GONE);
        } else {
            holder.layoutAddress.setVisibility(View.VISIBLE);
            holder.textAddress.setText(address);
        }
        holder.textPhone.setText(service.getPhone());

        // add listeners
        holder.layoutPhone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + service.getPhone()));
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Service getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final GroupHolder holder;
        final Service service = getGroup(groupPosition);

        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(groupLayoutResourceId, parent, false);

            holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.imageExpand = (ImageView) convertView.findViewById(R.id.image_expand);

            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        // set data
        String name = AppController.getInstance(activity.getApplicationContext()).getLang().equals("ar") ? service.getNameAr() : service.getNameEn();
        holder.textTitle.setText(name);

        // set expand button image
        if (service.isExpanded()) {
            holder.imageExpand.setImageResource(R.drawable.ex_list_arrow_expanded);
        } else {
            holder.imageExpand.setImageResource(R.drawable.ex_list_arrow_collapsed);
        }

        // save reference to this group row
        groupHolders.add(groupPosition, holder);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    /**
     * method, used to change icon and state when expand/collapse group
     */
    public void expandItem(int position, final boolean expand) {
        final GroupHolder holder = groupHolders.get(position);

        // change expand icon
        holder.imageExpand.setImageResource(expand ? R.drawable.ex_list_arrow_expanded : R.drawable.ex_list_arrow_collapsed);

        // change project object state
        data.get(position).setExpanded(expand);
    }

    private static class GroupHolder {
        TextView textTitle;
        ImageView imageExpand;
    }

    private static class ChildHolder {
        View layoutAddress;
        TextView textAddress;
        View layoutPhone;
        TextView textPhone;
    }
}
