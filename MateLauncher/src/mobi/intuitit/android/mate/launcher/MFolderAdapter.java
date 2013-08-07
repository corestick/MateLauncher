package mobi.intuitit.android.mate.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MFolderAdapter extends ArrayAdapter<MFolder> {
	private final LayoutInflater mInflater;

	public MFolderAdapter(Context context, ArrayList<MFolder> objects) {
		super(context, 0, objects);
		mInflater = LayoutInflater.from(context);
		init();
	}

	public void init() {

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MFolder info = getItem(position);		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.application_boxed, parent,
					false);
		}

		info.mInfo.icon = Utilities.createIconThumbnail(info.mInfo.icon, getContext());

		final TextView textView = (TextView) convertView;
		textView.setCompoundDrawablesWithIntrinsicBounds(null, info.mInfo.icon, null,
				null);
		textView.setText(info.mInfo.title);

		return convertView;
	}

}
