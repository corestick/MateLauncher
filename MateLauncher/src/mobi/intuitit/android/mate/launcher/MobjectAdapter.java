package mobi.intuitit.android.mate.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MobjectAdapter extends ArrayAdapter<MObject> {

	private final LayoutInflater mInflater;

	public MobjectAdapter(Context context, ArrayList<MObject> apps) {
		super(context, 0, apps);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MObject info = getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.application_boxed, parent,
					false);
		}
		info.icon = Utilities.createIconThumbnail(info.icon, getContext());

		final TextView textView = (TextView) convertView;
		textView.setCompoundDrawablesWithIntrinsicBounds(null, info.icon, null,
				null);
		textView.setText(info.title);

		return convertView;
	}
}
