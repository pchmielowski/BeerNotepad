package net.chmielowski.beer.ui.beers;

import org.junit.Test;
import org.mockito.Mockito;

import rx.Observable;
import rx.functions.Action1;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FabPresenterTest {

    @Test
    public void starts_add_beer_activity() {
        BeersView mockedView = Mockito.mock(BeersView.class);
        Action1<Void> mockedAction = Mockito.mock(Action1.class);
        when(mockedView.fabClicked()).thenReturn(
                Observable.<Void>just(null));

        new FabPresenter(mockedView, mockedAction);

        verify(mockedAction).call(null);
    }
}
