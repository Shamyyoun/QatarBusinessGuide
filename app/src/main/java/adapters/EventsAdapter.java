package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;

import java.util.ArrayList;
import java.util.List;

import datamodels.Event;
import views.SlideExpandableListView;

public class EventsAdapter extends SlideExpandableListView.AnimatedExpandableListAdapter {
    private Context context;
    private int groupLayoutResourceId;
    private int childLayoutResourceId;
    private Event[] data;

    private LayoutInflater inflater;
    private List<GroupHolder> groupHolders;

    public EventsAdapter(Context context, int groupLayoutResourceId,
                         int childLayoutResourceId, Event[] data) {
        this.context = context;
        this.groupLayoutResourceId = groupLayoutResourceId;
        this.childLayoutResourceId = childLayoutResourceId;
        this.data = data;

        inflater = LayoutInflater.from(context);
        groupHolders = new ArrayList<GroupHolder>(data.length);
    }

    @Override
    public Event getChild(int groupPosition, int childPosition) {
        return data[groupPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;
        final Event event = getChild(groupPosition, childPosition);

        if (convertView == null) {
            holder = new ChildHolder();

            convertView = inflater.inflate(childLayoutResourceId, parent, false);
            holder.textDate = (TextView) convertView.findViewById(R.id.text_date);
            holder.textPlace = (TextView) convertView.findViewById(R.id.text_place);
            holder.textDesc = (TextView) convertView.findViewById(R.id.text_desc);

            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        // set data
        holder.textDate.setText(event.getDate());
        holder.textPlace.setText(AppController.getInstance(context).getLang().equals("en") ? event.getPlaceEn() : event.getPlace());

        // check language to decide to hide desc or not
        if (AppController.getInstance(context).equals("en")) {
            // english
            if (event.getDesc1En().isEmpty()) {
                holder.textDesc.setVisibility(View.GONE);
            } else {
                holder.textDesc.setText(event.getDesc1En());
                holder.textDesc.setVisibility(View.VISIBLE);
            }
        } else {
            // arabic
            if (event.getDesc1().isEmpty()) {
                holder.textDesc.setVisibility(View.GONE);
            } else {
                holder.textDesc.setText(event.getDesc1());
                holder.textDesc.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Event getGroup(int groupPosition) {
        return data[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return data.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final GroupHolder holder;
        final Event event = getGroup(groupPosition);

        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(groupLayoutResourceId, parent, false);

            holder.textName = (TextView) convertView.findViewById(R.id.text_name);
            holder.imageExpand = (ImageView) convertView.findViewById(R.id.image_expand);

            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        // set data
        holder.textName.setText(AppController.getInstance(context).getLang().equals("en") ? event.getNameEn() : event.getName());

        // set expand button image
        if (event.isExpanded()) {
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
        data[position].setExpanded(expand);
    }

    private static class GroupHolder {
        TextView textName;
        ImageView imageExpand;
    }

    private static class ChildHolder {
        TextView textDate;
        TextView textPlace;
        TextView textDesc;
    }
}
