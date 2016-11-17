package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import static org.mockito.Mockito.times;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class BeersPresenterTest {

    @Mock
    BeersView mockedView;
    @Mock
    Beers mockedBeers;
    private List<Beer> beers;

    private static Observable<List<Beer>> noCompletingJust(
            final List<Beer> twoBeers) {
        Subject<List<Beer>, List<Beer>> twoBeersSource = ReplaySubject.create();
        twoBeersSource.onNext(twoBeers);
        return twoBeersSource.<List<Beer>>asObservable();
    }

    private static ReplaySubject<Integer> nonCompletingFrom(
            final List<Integer> list) {
        final ReplaySubject<Integer> subject =
                ReplaySubject.create();
        for (Integer i : list) {
            subject.onNext(i);
        }
        return subject;
    }

    @Before
    public void setUp() throws Exception {
        beers = Arrays.asList(
                new Beer(
                        "first name",
                        "aaa country",
                        "first style",
                        2.0f
                ),
                new Beer(
                        "second name",
                        "bbb country",
                        "second style",
                        1.0f
                )
        );
    }

    @Test
    public void no_data_no_sorting_method_changes() {
        Mockito.when(mockedBeers.list()).thenReturn(
                Observable.<List<Beer>>never());
        Mockito.when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.<Integer>never());

        new BeersPresenter(mockedView, mockedBeers);

        verifyInteractionsWithViewOnlyDuringSetup();
    }

    @Test
    public void no_data_sorting_method_changes() {
        Mockito.when(mockedBeers.list()).thenReturn(
                Observable.<List<Beer>>never());
        Mockito.when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.just(1));

        new BeersPresenter(mockedView, mockedBeers);

        verifyInteractionsWithViewOnlyDuringSetup();
    }

    @Test
    public void list_of_few_beers() {
        Mockito.when(mockedBeers.list())
               .thenReturn(Observable.just(beers));
        Mockito.when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.<Integer>never());

        new BeersPresenter(mockedView, mockedBeers);
        verifyInteractionsWithViewOnlyDuringSetup();
    }

    @Test
    public void list_of_few_beers_sorting_method_change_once() {
        Mockito.when(mockedBeers.list())
               .thenReturn(noCompletingJust(beers));
        Mockito.when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.just(0));

        new BeersPresenter(mockedView, mockedBeers);

        Mockito.verify(mockedView, times(1))
               .add((List<Beer>) argThat(
                       IsCollectionWithSize.hasSize(beers.size())));
    }

    @Test
    public void list_of_few_beers_sorting_method_change_few_times() {
        Mockito.when(mockedBeers.list())
               .thenReturn(noCompletingJust(beers));
        final List<Integer> sortingMethods = Arrays.asList(0, 1, 0);
        Mockito.when(mockedView.sortingMethodNumber()).thenReturn(
                nonCompletingFrom(sortingMethods));

        new BeersPresenter(mockedView, mockedBeers);

        Mockito.verify(mockedView, times(sortingMethods.size()))
               .add((List<Beer>) argThat(
                       IsCollectionWithSize.hasSize(beers.size())));
    }

    private void verifyInteractionsWithViewOnlyDuringSetup() {
        Mockito.verify(mockedView, times(1)).showLoading(true);
        Mockito.verify(mockedView, times(1)).sortingMethodNumber();
        Mockito.verifyNoMoreInteractions(mockedView);
    }
}
