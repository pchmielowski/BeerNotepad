package net.chmielowski.beer.ui.beers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Func1;

final class BeersAdapter
        extends RecyclerView.Adapter<BeersAdapter.BeerViewHolder> {
    private final List<Beer> mDataset = new ArrayList<>();
    private int mExpanded = -1;

    @Override
    public BeerViewHolder onCreateViewHolder(final ViewGroup parent,
            final int viewType) {
        return new BeerViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout(viewType), parent, false)
        );
    }

    private int layout(final int viewType) {
        int layout;
        if (viewType == 0) {
            layout = R.layout.beer;
        } else {
            layout = R.layout.beer_expanded;
        }
        return layout;
    }

    @Override
    public int getItemViewType(final int position) {
        if (hashCodeOf(position) == mExpanded) {
            return 1;
        } else {
            return 0;
        }
    }

    private int hashCodeOf(final int position) {
        return mDataset.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(final BeerViewHolder holder,
            final int position) {
        mDataset.get(position).showOn(holder);
        RxView.clicks(holder.itemView).subscribe(new Action1<Void>() {
            @Override
            public void call(final Void aVoid) {
                updateExpandedIndex();
                notifyItemChanged(position);
            }

            private void updateExpandedIndex() {
                if (mExpanded == hashCodeOf(position)) {
                    mExpanded = -1;
                } else {
                    mExpanded = hashCodeOf(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(final List<Beer> beers) {
        this.mDataset.clear();
        int i = 0;
        for (final Beer b : beers) {
            this.mDataset.add(b);
            notifyItemChanged(i++);
        }
    }

    class BeerViewHolder extends RecyclerView.ViewHolder implements BeerView {
        @BindView(R.id.beer_tv_name)
        TextView mName;
        @BindView(R.id.beer_tv_rating)
        TextView mRating;
        @BindView(R.id.beer_tv_style)
        TextView mStyle;
        @BindView(R.id.beer_tv_country)
        TextView mCountry;
        @BindView(R.id.beer_pb_photo_loads)
        ProgressBar mProgressBar;
        @BindView(R.id.beer_iv_photo)
        ImageView mImage;

        BeerViewHolder(final View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @Override
        public void show(
                final String name,
                final float rating,
                final String style,
                final String country,
                final Photo photo) {
            this.mName.setText(name);
            this.mRating.setText(String.valueOf(rating));
            this.mStyle.setText(style);
            this.mCountry.setText(country);
            photo.bytes()
                 .map(new Func1<byte[], Bitmap>() {
                     @Override
                     public Bitmap call(final byte[] bytes) {
                         return BitmapFactory.decodeByteArray(
                                 bytes, 0, bytes.length);
                     }
                 })
                 .subscribe(
                         new Action1<Bitmap>() {
                             @Override
                             public void call(final Bitmap bmp) {
                                 mImage.setImageBitmap(bmp);
                                 mProgressBar.setVisibility(View.GONE);
                                 mImage.setVisibility(View.VISIBLE);
                             }
                         }
                 );
        }

    }
}
