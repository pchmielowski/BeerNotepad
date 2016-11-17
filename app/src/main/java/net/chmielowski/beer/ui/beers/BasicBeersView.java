package net.chmielowski.beer.ui.beers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import net.chmielowski.beer.R;
import net.chmielowski.beer.model.Beer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

final class BasicBeersView implements BeersView {

    private final BeersActivity mActivity;

    private final BeersAdapter mAdapter;
    @BindView(R.id.beers_fab_add)
    FloatingActionButton mAddNew;
    @BindView(R.id.beers_sp_sort)
    Spinner mSortBySpinner;
    @BindView(R.id.beers_switch_sort)
    Switch mSortOrderSwitch;

    BasicBeersView(final BeersActivity activity) {
        this.mActivity = activity;
        this.mAdapter = new BeersAdapter();
        ButterKnife.bind(this, mActivity);
        this.configureBeerListView();
    }

    @Override
    public Observable<Integer> sortingMethodNumber() {
        return RxAdapterView.itemSelections(mSortBySpinner);
    }

    @Override
    public Observable<Boolean> sortingAscending() {
        BehaviorSubject<Boolean> subject = BehaviorSubject.create(true);
        RxView.clicks(mSortOrderSwitch).map(
                new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(final Void aVoid) {
                        return mSortOrderSwitch.isChecked();
                    }
                })
              .subscribe(subject);
        return subject.asObservable();

    }

    @Override
    public Observable fabClicked() {
        return RxView.clicks(mAddNew);
    }

    @Override
    public void add(final List<Beer> beers) {
        this.mAdapter.add(beers);
    }

    @Override
    public void showLoading(final boolean b) {
        // CHECKSTYLE:OFF
        this.mActivity.findViewById(R.id.beers_pb_loading)
                      .setVisibility(b ? View.VISIBLE : View.GONE);
        // CHECKSTYLE:ON
    }

    @Override
    public void showError(final Throwable e) {
        e.printStackTrace();
    }

    private void configureBeerListView() {
        RecyclerView recyclerView =
                (RecyclerView) mActivity.findViewById(R.id.beers_rv_list);
        try {
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        } catch (NullPointerException e) {
            throw new IllegalStateException(
                    "recycler view not found in the view", e);
        }
    }

}

