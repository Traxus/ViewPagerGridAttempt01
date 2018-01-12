package nicolasboileau.com.viewpagergridattempt01;

/**
 * Created by Nicolas Boileau on 12/12/2017.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import static android.view.MotionEvent.BUTTON_PRIMARY;

public class SponsorViewPagerItemFragment extends Fragment {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    View sponsorFragmentView;
    private static final String BUNDLE_KEY_HEADLINE = "bundle_key_headline";
    private static final String BUNDLE_KEY_IMAGE_URL = "bundle_key_image_url";
    private static final String BUNDLE_KEY_LOGO_URL = "bundle_key_logo_url";
    private static final String BUNDLE_KEY_NAME = "bundle_key_name";
    private static final String BUNDLE_KEY_ORDER = "bundle_key_order";
    private static final String BUNDLE_KEY_LONG_DESCRIPTION = "bundle_key_long_description";
    private static final String BUNDLE_KEY_SHORT_DESCRIPTION = "bundle_key_short_description";
    private static final String BUNDLE_KEY_CAREERS_URL = "bundle_key_careers_url";
    public String mHeadline;
    public String mImageUrl;
    public String mLogoUrl;
    public String mCareersUrl;
    public String mLongDescription;
    public String mName;
    public int mOrder;
    public String mShortDescription;
    private MainActivity activity;
    private GestureDetector gestureDetector;
    private View layout;

    public static  SponsorViewPagerItemFragment instantiateWithArgs(final Context context, final Sponsor sponsor) {
        final  SponsorViewPagerItemFragment fragment = (SponsorViewPagerItemFragment) instantiate(context,  SponsorViewPagerItemFragment.class.getName());
        final Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_HEADLINE, sponsor.mHeadline);
        args.putString(BUNDLE_KEY_IMAGE_URL, sponsor.mImageUrl);
        args.putString(BUNDLE_KEY_NAME, sponsor.mName);
        args.putString(BUNDLE_KEY_CAREERS_URL, sponsor.mCareersUrl);
        args.putString(BUNDLE_KEY_LOGO_URL, sponsor.mLogoUrl);
        args.putString(BUNDLE_KEY_LONG_DESCRIPTION, sponsor.mLongDescription);
        args.putString(BUNDLE_KEY_SHORT_DESCRIPTION, sponsor.mShortDescription);
        args.putInt(BUNDLE_KEY_ORDER, sponsor.mOrder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        final Bundle arguments = getArguments();
        if(arguments != null) {
            mImageUrl = arguments.getString(BUNDLE_KEY_IMAGE_URL);
            mLogoUrl = arguments.getString(BUNDLE_KEY_LOGO_URL);
            mHeadline = arguments.getString(BUNDLE_KEY_HEADLINE);
            mName = arguments.getString(BUNDLE_KEY_NAME);
            mCareersUrl = arguments.getString(BUNDLE_KEY_CAREERS_URL);
            mOrder = arguments.getInt(BUNDLE_KEY_ORDER);
            mShortDescription = arguments.getString(BUNDLE_KEY_SHORT_DESCRIPTION);
            mLongDescription = arguments.getString(BUNDLE_KEY_LONG_DESCRIPTION);
        }
        gestureDetector = new GestureDetector(getActivity(), new FragmentGestureDetector());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.sponsor_view_pager_item, container, false);

        TextView sponsorDetailName = (TextView) layout.findViewById(R.id.nameTextView);
        sponsorDetailName.setText(mName);

        //TextView sponsorOrder = (TextView) layout.findViewById(R.id.orderTextView);
        //sponsorOrder.setText(mOrder);

        ViewAnimator viewSwitcher = layout.findViewById(R.id.sponsorViewSwitcher);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        viewSwitcher.setOutAnimation(fadeOut);
        viewSwitcher.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        sponsorFragmentView = layout;

        return sponsorFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dummy", true);
    }

    class FragmentGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        private static final String DEBUG_TAG = "Gestures";
        private static final int NORMAL_TAP = 0;
        private static final int TOP_RIGHT_TAP = 1;
        private static final int TOP_LEFT_TAP = 2;
        private static final int CORNER_TAP_THRESHOLD = 4;

        private boolean imageIsDisplayed = true;
        private int cornerTapCount = 0;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onFling(
                MotionEvent e1,
                MotionEvent e2,
                float velocityX,
                float velocityY
        ) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "Swipe Left");
                }
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "Swipe Right");
                }
            } catch (Exception e) {

            }
            return false;
        }

        /**
         * Detects if a single tap event occurred in either of the hot corners
         * @param e
         * @return
         */
        private int getCornerTap(MotionEvent e) {

            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            float touchX = e.getX();
            float touchY = e.getY();
            // detect top right corner tap
            if (screenWidth - touchX <= 0.1 * screenWidth && screenHeight - touchY >= 0.9 * screenHeight) {
                return TOP_RIGHT_TAP;
            } else if (screenWidth - touchX >= 0.9 * screenWidth && screenHeight - touchY >= 0.9 * screenHeight) {
                return TOP_LEFT_TAP;
            }
            return NORMAL_TAP;
        }
    }
}

