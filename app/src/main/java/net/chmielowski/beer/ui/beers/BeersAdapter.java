package net.chmielowski.beer.ui.beers;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

final class BeersAdapter
        extends RecyclerView.Adapter<BeersAdapter.BeerViewHolder> {
    private final List<Beer> mDataset = new ArrayList<>();
    private BeerView mSelected = new NullBeerView();

    @Override
    public BeerViewHolder onCreateViewHolder(final ViewGroup parent,
            final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.beer, parent, false);
        final BeerViewHolder holder = new BeerViewHolder(view);
        RxView.clicks(view).subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                select();
            }

            private void select() {
                mSelected.collapse();
                mSelected = holder;
                holder.expand();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final BeerViewHolder holder,
            final int position) {
        mDataset.get(position).showOn(holder);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(final List<Beer> beers) {
        this.mDataset.clear();
        for (final Beer b : beers) {
            this.mDataset.add(b);
        }
        this.notifyDataSetChanged();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder implements BeerView {
        private final TextView mName;
        private final TextView mRating;
        private final TextView mStyle;
        private final TextView mCountry;
        @BindView(R.id.beer_iv_photo)
        ImageView mImage;

        BeerViewHolder(final View v) {
            super(v);
            ButterKnife.bind(this, v);
            this.mName = (TextView) v.findViewById(R.id.beer_tv_name);
            this.mRating = (TextView) v.findViewById(R.id.beer_tv_rating);
            this.mStyle = (TextView) v.findViewById(R.id.beer_tv_style);
            this.mCountry = (TextView) v.findViewById(R.id.beer_tv_country);
        }

        @Override
        public void showBeer(final String name, final float rating,
                final String style,
                final String country, final Photo photo) {
            this.mName.setText(name);
            this.mRating.setText(String.valueOf(rating));
            this.mStyle.setText(style);
            this.mCountry.setText(country);
            photo.bytes().subscribe(new Action1<byte[]>() {
                @Override
                public void call(final byte[] bytes) {
                    mImage.setImageBitmap(BitmapFactory.decodeByteArray(
                            bytes, 0, bytes.length));

                }
            });
        }

        @Override
        public void expand() {
            final Observable<Void> observable = Observable.<Void>just(null)
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    mStyle.setVisibility(View.VISIBLE);
                    mCountry.setVisibility(View.VISIBLE);
                    final float expanded = 2.0f;
                    mImage.setScaleX(expanded);
                    mImage.setScaleY(expanded);
                }
            });
        }

        @Override
        public void collapse() {
            final Observable<Void> observable = Observable.<Void>just(null)
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    mStyle.setVisibility(View.GONE);
                    mCountry.setVisibility(View.GONE);
                    final float collapsed = 1.0f;
                    mImage.setScaleX(collapsed);
                    mImage.setScaleY(collapsed);
                }
            });
        }
    }
}
