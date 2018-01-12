package nicolasboileau.com.viewpagergridattempt01;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.List;

/**
 * Created by Nicolas Boileau on 12/12/2017.
 */

public class Sponsor implements Comparable<Sponsor> {
    public String mHeadline;
    public int mId;
    public String mImageUrl;
    public String mLogoUrl;
    public String mCareersUrl;
    public String mLongDescription;
    public String mName;
    public int mOrder;
    public String mShortDescription;

    public Sponsor (
            final String headline,
            final int id,
            final String imageUrl,
            final String logoUrl,
            final String careersUrl,
            final String longDescription,
            final String name,
            final int order,
            final String shortDescription
    ) {
        mHeadline = headline;
        mId = id;
        mImageUrl = imageUrl;
        mLogoUrl = logoUrl;
        mCareersUrl = careersUrl;
        mLongDescription = longDescription;
        mName = name;
        mOrder = order;
        mShortDescription = shortDescription;
    }

    public int getOrder() {
        return mOrder;
    }

    @Override
    public int compareTo(Sponsor compareSponsor) {
        //-----thanks:
        //-------https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/

        int compareOrder = ((Sponsor) compareSponsor).getOrder();

        //ascending order
        return this.mOrder - compareOrder;

        //descending order
        //return compareQuantity - this.quantity;

    }

    public static List<Sponsor> createSampleSponsors() {
        final List<Sponsor> sponsors = new ArrayList<Sponsor>();
        sponsors.add(new Sponsor(
                "AAAAA",
                1,
                "",
                "",
                "",
                "aa aaa aaaa",
                "Aaaa Aaaa",
                1,
                "aaa"
        ));
        sponsors.add(new Sponsor(
                "BBBBB",
                2,
                "",
                "",
                "",
                "bb bbb bbbb",
                "Bbbb Bbbb",
                2,
                "bbb"
        ));
        sponsors.add(new Sponsor(
                "CCCCC",
                3,
                "",
                "",
                "",
                "cc ccc cccc",
                "Cccc Cccc",
                3,
                "ccc"
        ));
        sponsors.add(new Sponsor(
                "DDDDD",
                4,
                "",
                "",
                "",
                "dd ddd dddd",
                "Dddd Dddd",
                4,
                "ddd"
        ));
        sponsors.add(new Sponsor(
                "EEEEE",
                5,
                "",
                "",
                "",
                "ee eee eeee",
                "Eeee Eeee",
                5,
                "eee"
        ));
        sponsors.add(new Sponsor(
                "FFFFF",
                6,
                "",
                "",
                "",
                "ff fff ffff",
                "Ffff Ffff",
                6,
                "fff"
        ));
        sponsors.add(new Sponsor(
                "GGGGG",
                7,
                "",
                "",
                "",
                "gg ggg gggg",
                "Gggg Gggg",
                7,
                "ggg"
        ));
        sponsors.add(new Sponsor(
                "HHHHH",
                8,
                "",
                "",
                "",
                "hh hhh hhhh",
                "Hhhh Hhhh",
                8,
                "hhh"
        ));
        sponsors.add(new Sponsor(
                "IIIII",
                9,
                "",
                "",
                "",
                "ii iii iiii",
                "Iiii Iiii",
                9,
                "iii"
        ));


        return sponsors;
    }


}

