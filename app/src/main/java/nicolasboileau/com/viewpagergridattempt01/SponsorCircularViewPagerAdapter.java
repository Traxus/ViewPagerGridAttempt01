package nicolasboileau.com.viewpagergridattempt01;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * App specific circular pager adapter
 * <p>
 * Created by Nicolas Boileau on 12/12/2017.
 */
public class SponsorCircularViewPagerAdapter extends BaseCircularViewPagerAdapter<Sponsor>
{
    private final Context mContext;

    public SponsorCircularViewPagerAdapter(final Context context, final FragmentManager fragmentManager, final List<Sponsor> sponsors)
    {
        super(fragmentManager, sponsors);
        mContext = context;
    }

    @Override
    protected Fragment getFragmentForItem(final Sponsor sponsor)
    {
        return SponsorViewPagerItemFragment.instantiateWithArgs(mContext, sponsor);
    }
}
