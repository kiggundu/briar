package net.sf.briar.android.groups;

import static android.view.Gravity.CENTER;
import static android.view.View.INVISIBLE;
import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static net.sf.briar.android.groups.ManageGroupsItem.NONE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.briar.R;
import net.sf.briar.api.messaging.GroupStatus;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class ManageGroupsAdapter extends BaseAdapter {

	private final Context ctx;
	private final List<ManageGroupsItem> list =
			new ArrayList<ManageGroupsItem>();

	ManageGroupsAdapter(Context ctx) {
		this.ctx = ctx;
	}

	public void add(ManageGroupsItem item) {
		list.add(item);
	}

	public void clear() {
		list.clear();
	}

	public int getCount() {
		return list.isEmpty() ? 1 : list.size();
	}

	public ManageGroupsItem getItem(int position) {
		return list.isEmpty() ? NONE : list.get(position);
	}

	public long getItemId(int position) {
		return android.R.layout.simple_expandable_list_item_1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ManageGroupsItem item = getItem(position);

		if(item == NONE) {
			TextView none = new TextView(ctx);
			none.setGravity(CENTER);
			none.setTextSize(18);
			none.setPadding(10, 10, 10, 10);
			none.setText(R.string.no_groups_available);
			return none;
		}

		GroupStatus s = item.getGroupStatus();
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(HORIZONTAL);

		ImageView subscribed = new ImageView(ctx);
		subscribed.setPadding(5, 5, 5, 5);
		subscribed.setImageResource(R.drawable.navigation_accept);
		if(!s.isSubscribed()) subscribed.setVisibility(INVISIBLE);
		layout.addView(subscribed);

		LinearLayout innerLayout = new LinearLayout(ctx);
		innerLayout.setOrientation(VERTICAL);

		TextView name = new TextView(ctx);
		name.setTextSize(18);
		name.setMaxLines(1);
		name.setPadding(0, 10, 10, 10);
		name.setText(s.getGroup().getName());
		innerLayout.addView(name);

		TextView status = new TextView(ctx);
		status.setTextSize(14);
		status.setPadding(0, 0, 10, 10);
		if(s.isSubscribed()) {
			if(s.isVisibleToAll()) status.setText(R.string.subscribed_all);
			else status.setText(R.string.subscribed_some);
		} else {
			status.setText(R.string.not_subscribed);
		}
		innerLayout.addView(status);
		layout.addView(innerLayout);

		return layout;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	public void remove(ManageGroupsItem item) {
		list.remove(item);
	}

	public void sort(Comparator<ManageGroupsItem> comparator) {
		Collections.sort(list, comparator);
	}
}
