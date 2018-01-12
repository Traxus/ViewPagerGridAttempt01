package nicolasboileau.com.viewpagergridattempt01;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nicolas Boileau on 1/11/2018.
 */

public class ViewPagerCell {
    public int mCellId;
    public int mCellColumnId;
    public int mCellRowId;
    public int mGridColumnCount;
    public int mGridRowCount;
    public int mRowHeight;
    public List mSourceSponsorList;
    public List mCellSponsorList;

    public ViewPagerCell (
            int cellId,
            int cellColumnId,
            int cellRowId,
            int gridColumnCount,
            int gridRowCount,
            int rowHeight,
            List sourceSponsorList
    ) {
        mCellId = cellId;
        mCellColumnId =  cellColumnId;
        mCellRowId = cellRowId;
        mGridColumnCount = gridColumnCount;
        mGridRowCount = gridRowCount;
        mRowHeight = rowHeight;
        mSourceSponsorList = sourceSponsorList;
        mCellSponsorList = mSourceSponsorList;

        Collections.sort(mSourceSponsorList);
        mCellSponsorList = getCellSlidesFromSourceList();

        Log.d("ViewPagerCell Class", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Log.d("ViewPagerCell Class", Integer.toString(mCellId));
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


}
