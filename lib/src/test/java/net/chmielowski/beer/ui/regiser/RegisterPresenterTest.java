package net.chmielowski.beer.ui.regiser;

import net.chmielowski.beer.login.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.functions.Action1;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {
    @Mock
    RegisterView mockedView;
    @Mock
    User mockedUser;
    @Mock
    Action1<Void> mockedAction;

    @Test
    public void passwords_differ() {
        when(mockedView.registerButtonClicked()).thenReturn(
                Observable.<Void>just(null));
        when(mockedView.password()).thenReturn("passwd");
        when(mockedView.passwordRepeated()).thenReturn("different passwd");

        new RegisterPresenter(mockedView, mockedUser, mockedAction);

        verify(mockedView).showError("Passwords do not match");
    }

    @Test
    public void registration_error() {
        when(mockedView.registerButtonClicked()).thenReturn(
                Observable.<Void>just(null));
        when(mockedView.password()).thenReturn("passwd");
        when(mockedView.passwordRepeated()).thenReturn("passwd");
        when(mockedView.email()).thenReturn("email");
        when(mockedUser.register("email", "passwd"))
                .thenReturn(
                        Observable.<Boolean>error(new Throwable("message")));

        new RegisterPresenter(mockedView, mockedUser, mockedAction);

        verify(mockedView).showError("message");
    }

    @Test
    public void registration_correct() {
        when(mockedView.registerButtonClicked()).thenReturn(
                Observable.<Void>just(null));
        when(mockedView.password()).thenReturn("passwd");
        when(mockedView.passwordRepeated()).thenReturn("passwd");
        when(mockedView.email()).thenReturn("email");
        when(mockedUser.register("email", "passwd"))
                .thenReturn(
                        Observable.<Boolean>just(null));

        new RegisterPresenter(mockedView, mockedUser, mockedAction);

        verify(mockedAction).call(ArgumentMatchers.<Void>any());
    }
}
