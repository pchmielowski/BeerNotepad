package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;

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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class BeersPresenterTest {

    @Mock
    BeersView mockedView;
    @Mock
    Beers mockedBeers;
    private List<Beer> beers;

    private static <T> Observable<T> nonCompletingJust(
            final T value) {
        Subject<T, T> subject = ReplaySubject.create();
        subject.onNext(value);
        return subject.<T>asObservable();
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

    private BeersPresenter createBeersPresenter() {
        return new BeersPresenter(mockedView, mockedBeers);
    }

    @Test
    public void no_data_sorting_method_changes() {
        when(mockedBeers.list()).thenReturn(
                Observable.<List<Beer>>never());
        when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.just(1));
//        Mockito.when(mockedView.sortingAscending()).thenReturn(
//                nonCompletingJust(true));

        createBeersPresenter();

        verifyInteractionsWithViewOnlyDuringSetup();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void list_of_few_beers_sorting_method_change_once() {
        when(mockedBeers.list())
                .thenReturn(nonCompletingJust(beers));
        when(mockedView.sortingMethodNumber()).thenReturn(
                Observable.just(0));
//        Mockito.when(mockedView.sortingAscending()).thenReturn(
//                nonCompletingJust(true));

        createBeersPresenter();

        verify(mockedView, times(1))
                .add((List<Beer>) argThat(hasSize(this.beers.size())));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void list_of_few_beers_sorting_method_change_few_times() {
        when(mockedBeers.list())
                .thenReturn(nonCompletingJust(beers));
        final List<Integer> sortingMethods = Arrays.asList(0, 1, 0);
        when(mockedView.sortingMethodNumber()).thenReturn(
                nonCompletingFrom(sortingMethods));
//        Mockito.when(mockedView.sortingAscending()).thenReturn(
//                nonCompletingJust(true));

        createBeersPresenter();

        verify(mockedView, times(sortingMethods.size()))
                .add((List<Beer>) argThat(hasSize(beers.size())));
    }

    private void verifyInteractionsWithViewOnlyDuringSetup() {
        verify(mockedView, times(1)).showLoading(true);
        verify(mockedView, times(1)).sortingMethodNumber();
        Mockito.verifyNoMoreInteractions(mockedView);
    }
}
