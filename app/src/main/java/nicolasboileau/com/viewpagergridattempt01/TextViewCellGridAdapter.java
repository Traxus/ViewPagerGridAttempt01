package nicolasboileau.com.viewpagergridattempt01;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Nicolas Boileau on 1/11/2018.
 */

public class TextViewCellGridAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mCellIds;
    private int mRowHeight;

    public TextViewCellGridAdapter(Context c, String[] cellIds, int rowHeight) {
        mContext = c;
        mCellIds = cellIds;
        mRowHeight = rowHeight;
    }

    public int getCount() {
        return mCellIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        //View view = super.getView(position,convertView,parent);
        //TextView textView = (TextView) view;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            //textView.setTextColor(Color.parseColor("#000000"));
            textView.setBackgroundColor(Color.GRAY);
            textView.setTextSize(80);
            //textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            GridView.LayoutParams titleTextParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setHeight(mRowHeight);
            textView.setLayoutParams (titleTextParams);

        } else {
            textView = (TextView) convertView;
        }

        textView.setText(mCellIds[position]);
        return textView;
    }

}
