package net.chmielowski.beer.ui.addbeer;

import android.widget.EditText;
import android.widget.RatingBar;

import com.jakewharton.rxbinding.view.RxView;

import net.chmielowski.beer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

final class BasicAddBeerView implements AddBeerView {
    private final AddBeerActivity mActivity;

    @BindView(R.id.add_et_name)
    EditText mName;
    @BindView(R.id.add_rb_rating)
    RatingBar mRatingBar;
    @BindView(R.id.add_et_country)
    EditText mCountry;
    @BindView(R.id.add_et_style)
    EditText mStyle;

    BasicAddBeerView(final AddBeerActivity activity) {
        this.mActivity = activity;
        ButterKnife.bind(this, mActivity);
    }

    @Override
    public Observable okClicked() {
        return RxView.clicks(mActivity.findViewById(R.id.add_btn_ok));
    }

    @Override
    public String name() {
        return String.valueOf(mName.getText());
    }

    @Override
    public String country() {
        return String.valueOf(mCountry.getText());
    }

    @Override
    public float rating() {
        return mRatingBar.getRating();
    }

    @Override
    public String style() {
        return String.valueOf(mStyle.getText());
    }
}
