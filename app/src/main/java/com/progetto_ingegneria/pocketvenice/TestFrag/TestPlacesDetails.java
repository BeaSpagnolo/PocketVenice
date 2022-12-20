package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.R;


public class TestPlacesDetails extends Fragment implements View.OnClickListener {


    private static final String DETAILS = "param1";
    protected TextView title, district, address, description;
    protected ImageView imgPlace, shareBtn;
    protected RatingBar ratingBar;
    protected String mTitle, mAddress, mDistrict, mDescription;
    protected FirebaseUser user;
    protected boolean isLogged;
    protected float mRatingBar;
    protected Place place;
    protected View view;


    public TestPlacesDetails() {
        // Required empty public constructor
    }

    public static TestPlacesDetails newInstance(Place place) {
        TestPlacesDetails fragment = new TestPlacesDetails();
        Bundle args = new Bundle();
        args.putSerializable(DETAILS, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            place = (Place) getArguments().getSerializable(DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_places_details, container, false);

        checkAuth();
        initView();
        loadPlaceData();


        return view;
    }

    private void loadPlaceData() {

        mTitle = place.getTitle();
        mAddress = place.getAddress();
        mDistrict = place.getDistrict();
        mDescription = place.getDescription();
        mRatingBar = place.getRating();

        title.setText(mTitle);
        address.setText(mAddress);
        district.setText(mDistrict);
        description.setText(mDescription);
        ratingBar.setRating(mRatingBar);

        Glide.with(this)
                .load(place.getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imgPlace);
    }

    private void initView() {
        imgPlace = view.findViewById(R.id.item_place_img);
        title = view.findViewById(R.id.item_place_title);
        address = view.findViewById(R.id.item_place_address);
        district = view.findViewById(R.id.item_place_district);
        description = view.findViewById(R.id.details_places_description);
        ratingBar = view.findViewById(R.id.item_place_ratingbar);
        shareBtn = view.findViewById(R.id.item_place_share);
        shareBtn.setOnClickListener(this);
    }

    private void checkAuth() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            isLogged = true;
        }
    }

    @Override
    public void onClick(View v) {
        if (isLogged) {

            if (v.getId() == R.id.item_event_share) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plan");
                    i.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                    String body = mTitle + "\n" + mAddress + "\n" + "Sestiere:" + " " + mDistrict + "\n" + "Rating:" + " " + mRatingBar + "\n" + "Shared from PocketVenice App" + "\n";
                    i.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(i, "Share with: "));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong. Cannot share at this moment. Try again", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(getActivity(), "You have to be logged to share this place", Toast.LENGTH_SHORT).show();
        }
    }
}