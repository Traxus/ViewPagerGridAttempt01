package nicolasboileau.com.viewpagergridattempt01;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nicolas Boileau on 1/11/2018.
 */

public class ViewPagerCellGridAdapter extends BaseAdapter {
    private Context mContext;
    private ViewPager mViewPager;

    private int mCellId;
    private int mCellRowId;
    private int mCellColumnId;
    private int mGridRowCount;
    private int mGridColumnCount;
    private int mRowHeight;
    private ViewPagerCell mCellData;
    private ArrayList<ViewPagerCell> mViewPagerCellList;
    private List mSourceSponsorList = new ArrayList<Sponsor>();
    private List mCellSponsorList = new ArrayList<Sponsor>();

    public  ViewPagerCellGridAdapter(
            Context c,
            ArrayList<ViewPagerCell> gridCellList) {
        mViewPagerCellList = gridCellList;
        mContext = c;


    }

    public int getCount() {
        return mViewPagerCellList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get the number of items a given cell will interate to...
     * If there is more than one row in the grid, this will be a factor of the total amoung of items
     *
     * @return int
     */
    public int getCellItemsPerRowCount() {
        int gridListTotal = mSourceSponsorList.size();
        int cellItemsPerRow = (int) Math.ceil((double) gridListTotal / mGridRowCount);

        return cellItemsPerRow;
    }

    /**
     * Get a culled List of slides for the cells view pager to iterate through
     * This will be a subset of the total list that is dependant on the cell row
     *
     * @return
     */
    public List getCellSlidesFromSourceList() {
        int sourceListItemId = mCellId;
        int cellItemsPerRow = getCellItemsPerRowCount();
        int gridListTotal = mSourceSponsorList.size();
        List resultList = new ArrayList<Sponsor>();;
        for (int i = 0; i < cellItemsPerRow; i++) {
            sourceListItemId = (mCellRowId + (i * mGridRowCount)) - 1;
            if (sourceListItemId >= gridListTotal || sourceListItemId < 0) {
                resultList.add(new Sponsor(
                        "",
                        1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        1,
                        ""
                ));
            } else {
                resultList.add(mSourceSponsorList.get(sourceListItemId));
            }
        }
        return resultList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        mCellData = mViewPagerCellList.get(position);

        mCellId = mCellData.mCellId;
        mCellColumnId =  mCellData.mCellColumnId;
        mCellRowId = mCellData.mCellRowId;
        mGridColumnCount = mCellData.mGridColumnCount;
        mGridRowCount = mCellData.mGridRowCount;
        mRowHeight = mCellData.mRowHeight;
        mSourceSponsorList = mCellData.mSourceSponsorList;
        mCellSponsorList = mCellData.mCellSponsorList;

        if (convertView == null) {
            mViewPager = new NonSwipeableViewPager(mContext);
            //-----https://stackoverflow.com/questions/8460680/how-can-i-assign-an-id-to-a-view-programmatically
            //mViewPager.generateViewId();
            mViewPager.setId(mCellId);
            mViewPager.setBackgroundColor(Color.GRAY);
            GridView.LayoutParams pagerParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pagerParams.height = mRowHeight;
            mViewPager.setLayoutParams (pagerParams);
            //mViewPager.setAdapter(new SponsorCircularViewPagerAdapter(mContext, ((MainActivity) mContext).getSupportFragmentManager(), mCellSponsorList));
            mViewPager.setAdapter(new SponsorCircularViewPagerAdapter(mContext, ((MainActivity) mContext).getSupportFragmentManager(), mCellSponsorList));
            //maybe?: https://stackoverflow.com/questions/42295470/how-to-call-getsupportfragmentmanager-in-arrayadapter
            final CircularViewPagerHandler circularViewPagerHandler = new CircularViewPagerHandler(mViewPager);
            circularViewPagerHandler.setOnPageChangeListener(createOnPageChangeListener());
            mViewPager.addOnPageChangeListener(circularViewPagerHandler);
        } else {
            mViewPager = (ViewPager) convertView;
        }


        //int slideId = getLocalOffset(0);
        //mViewPager.setCurrentItem(slideId, false);
        moveViaGlobalOffset(0);

        return mViewPager;
    }

    private int getLocalOffset(int globalOffset) {

        int aliasGlobalOffset = globalOffset;
        int cellItemsPerRow = getCellItemsPerRowCount();

        if (aliasGlobalOffset == -1) {
            aliasGlobalOffset = 0;
        }

        int localOffset = mCellColumnId + aliasGlobalOffset;

        if (localOffset > cellItemsPerRow) {
            localOffset = localOffset % cellItemsPerRow;
        }

        //----this looping view pager setup wants indexes starting at 2 and ending at the total slide coune plus one
        if (localOffset == 1) {
            localOffset = cellItemsPerRow + 1;
        }

        return localOffset;
    }

    /**
     * Move to slide at offset
     *
     * @param globalOffset
     */
    public void moveViaGlobalOffset(int globalOffset) {

        int pos = mViewPager.getCurrentItem();
        Boolean smoothScroll = true;
        int cellItemsPerRow = getCellItemsPerRowCount();
        int id = getLocalOffset(globalOffset);
        int distance;

        if (id == cellItemsPerRow + 1 && pos == 2) {
            id = 1;
        } else  if (id == cellItemsPerRow && pos == 1) {
            id = -1;
        }

        distance = Math.abs(pos-id);

        if (distance == 1 || (pos == 1 && id == -1)) {
            //-----only moving left or right by one slide, retain smooth scroll
            //-----the second half of above condition compenstaes for reverse looping from first item to last itme
            smoothScroll = true;
        } else {
            //-----probably a catchup, skip the nasty scroll...
            smoothScroll = false;
        }

        mViewPager.setCurrentItem(id, smoothScroll);
    }


    //-----NB added 20171212-16:30
    //-------part of the ecosystem that allows the view pager to loop at its ends
    private ViewPager.OnPageChangeListener createOnPageChangeListener() {

        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        };
    }

}