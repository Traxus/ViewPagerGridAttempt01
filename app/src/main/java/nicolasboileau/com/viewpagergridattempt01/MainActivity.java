package nicolasboileau.com.viewpagergridattempt01;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    List sourceSponsorList = new ArrayList<Sponsor>();

    //----- gets set via JSON call to config
    private int gridRowCount = 2;
    private int gridColumnCount = 3;

    private int lastActiveGridCell;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onResume() {
        hideUi();

        sourceSponsorList = Sponsor.createSampleSponsors();

        buildGrid();
        //initValuesFromDeviceSettings();

        super.onResume();
    }

    public void buildGrid() {
        Context mContext = this;
        //-----init internally after data from server
        String[] cellLabels = new String[gridColumnCount * gridRowCount];

        ArrayList<ViewPagerCell> gridCellList = new ArrayList<ViewPagerCell>();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        //-----https://stackoverflow.com/questions/7067449/android-how-to-stretch-rows-in-the-gridview-to-fill-screen
        int rowHeight = metrics.heightPixels/gridRowCount;


        for(int yPos=0; yPos < gridRowCount; yPos++) {
            for (int xPos = 0; xPos < gridColumnCount; xPos++) {
                int gridViewCellId  = yPos * gridColumnCount + xPos;
                int viewPagerCellId = (yPos + 1) + (xPos * gridRowCount);
                cellLabels[gridViewCellId] = Integer.toString(viewPagerCellId);
                gridCellList.add(new ViewPagerCell(
                        viewPagerCellId,
                        xPos + 1,
                        yPos + 1,
                        gridColumnCount,
                        gridRowCount,
                        rowHeight,
                        sourceSponsorList
                ));
            }
        }

        if (gridView != null) {
            //gridView.invalidateViews();
        }

        //-----build grid from data
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(gridColumnCount);

        gridView.setAdapter(new ViewPagerCellGridAdapter(this, gridCellList));
        //gridView.setAdapter(new TextViewCellGridAdapter(this, cellLabels, rowHeight));

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int cellColId = (position%gridColumnCount) + 1;
                int cellRowId = (position/gridColumnCount) + 1;
                //int gridViewCellId = position + 1;
                int deviceCellId = cellRowId + ((cellColId - 1) * gridRowCount);

                String toastMessage = " Cell Id = " + Integer.toString(deviceCellId) +
                        " Row Id = " +  Integer.toString(cellRowId) +
                        " Col Id = " +  Integer.toString(cellColId);
                //Log.d("IDENTIFY", "~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                //Log.d("IDENTIFY", toastMessage);

                toggleCellDisplay(position, true);
                toggleCellDisplay(lastActiveGridCell , false);
                lastActiveGridCell = position;
            }
        });

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        toggleCellDisplay(lastActiveGridCell , true);
                        // unregister listener (this is important)
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });*/
    }

    /**
     * set system flags to hide device ui elements
     */
    private void hideUi() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        /* Set the app into full screen mode */
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    public void toggleCellDisplay(int position, Boolean active) {

        final int size = gridView.getChildCount();
        if (position < 0 || position >= size) {
            //return false;
            //Log.d("INIT","~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //Log.d("INIT","FAIL " + Integer.toString(position));
            //Log.d("INIT","SIZE " + Integer.toString(size));
        } else {
            TextView cell = (TextView) gridView.getChildAt(position);
            if (active == true) {
                cell.setBackgroundColor(Color.RED);;
            } else {
                cell.setBackgroundColor(Color.GRAY);;
            }
            //return true;
            //Log.d("INIT","~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //Log.d("INIT","SUCC " + Integer.toString(position));
        }
    }

}





