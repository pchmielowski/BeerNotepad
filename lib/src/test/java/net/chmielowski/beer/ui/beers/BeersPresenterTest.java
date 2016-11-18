package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.SortBeerFunction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.inOrder;
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
    @Mock
    Action1<Func1<List<Beer>, List<Beer>>> mockedAction;

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
        return new BeersPresenter(mockedView, new ChangeDataObserverAction(
                mockedBeers.list(),
                new ShowBeersAction(mockedView)
        ));
    }

    // TODO: 3 legacy tests - refactor or remove
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
                nonCompletingJust(0));
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

    // This test is ok
    @Test
    public void sorting_method_set_few_times() {
        // TODO: this test depends on implementation of
        //       MethodNumberToSortingFunction.
        // Please, create a new ctor in BeerPresenter and inject the fction
        final List<Integer> sortingMethods = Arrays.asList(0, 1);
        when(mockedView.sortingMethodNumber()).thenReturn(
                nonCompletingFrom(sortingMethods));

        new BeersPresenter(mockedView, mockedAction);

        InOrder order = inOrder(mockedAction);
        order.verify(mockedAction, times(1)).call(
                new SortBeerFunction(new Beer.CompareByRating()));
        order.verify(mockedAction, times(1)).call(
                new SortBeerFunction(new Beer.CompareByCountry()));
    }

    private void verifyInteractionsWithViewOnlyDuringSetup() {
        verify(mockedView, times(1)).showLoading(true);
        verify(mockedView, times(1)).sortingMethodNumber();
        Mockito.verifyNoMoreInteractions(mockedView);
    }
}
