package net.chmielowski.beer.ui.beers;

import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Photo;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import rx.functions.Func1;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import static org.mockito.Mockito.times;

public class ChangeDataObserverActionTest {

    Subject<List<Beer>, List<Beer>>
            mockedEventSource = ReplaySubject.create();
    @Mock
    Photo mockedPhoto;
    final List<Beer> secondFunctionReturn = Arrays.asList(
            new Beer("d", "d", "d", 4.0f, mockedPhoto));
    final List<Beer> firstFunctionReturn = Arrays.asList(
            new Beer("c", "c", "c", 3.0f, mockedPhoto));
    final List<Beer> functionInput = Arrays.asList(
            new Beer("a", "a", "a", 1.0f, mockedPhoto),
            new Beer("b", "b", "b", 2.0f,
                     mockedPhoto
            )
    );

    @Test
    public void call() throws Exception {
        ShowBeersAction mockedAction = Mockito.mock(ShowBeersAction.class);
        Func1<List<Beer>, List<Beer>> firstMockedFunction = Mockito.mock(
                Func1.class);
        Func1<List<Beer>, List<Beer>> secondMockedFunction = Mockito.mock(
                Func1.class);

        Mockito.when(firstMockedFunction.call(functionInput)).thenReturn(
                firstFunctionReturn);
        Mockito.when(secondMockedFunction.call(functionInput)).thenReturn(
                secondFunctionReturn);
        final ChangeDataObserverAction observer = new ChangeDataObserverAction(
                mockedEventSource, mockedAction);

        mockedEventSource.onNext(functionInput);
        observer.call(firstMockedFunction);
        Mockito.verify(mockedAction, times(1)).onNext(firstFunctionReturn);

        Mockito.reset(mockedAction);
        observer.call(secondMockedFunction);
        Mockito.verify(mockedAction, times(1)).onNext(secondFunctionReturn);
    }
}
