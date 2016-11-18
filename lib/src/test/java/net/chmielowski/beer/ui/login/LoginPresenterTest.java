package net.chmielowski.beer.ui.login;

import net.chmielowski.beer.login.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class LoginPresenterTest {

    private final String correctPassword = "passwd";
    private final String wrongPassword = "wrong_passwd";
    private final PublishSubject<Void> loginButtonClicked =
            PublishSubject.create();
    @Mock
    LoginView mockedView;
    @Mock
    User mockedUser;
    @Mock
    Action1<Void> mockedOnLoginAction;
    private String email = "piotrek@domain.com";

    @Before
    public void setUp() {
        when(mockedView.email()).thenReturn(email);
        when(mockedUser.login(email, correctPassword))
                .thenReturn(Observable.just(true));
        when(mockedUser.login(email, wrongPassword))
                .thenReturn(Observable.just(false));
        when(mockedView.loginButtonClicked()).thenReturn(
                loginButtonClicked);
        when(mockedView.registerLinkClicked()).thenReturn(
                Observable.<Void>never());
    }

    @Test
    public void login_ok() throws Exception {
        when(mockedView.password()).thenReturn(correctPassword);
        new LoginPresenter(mockedView, mockedUser,
                           mockedOnLoginAction
        );

        loginButtonClicked.onNext(null);

        verify(mockedView, never()).showError();
        verify(mockedOnLoginAction).call(null);
    }

    @Test
    public void login_failed() throws Exception {
        when(mockedView.password()).thenReturn(wrongPassword);
        new LoginPresenter(mockedView, mockedUser,
                           mockedOnLoginAction
        );

        loginButtonClicked.onNext(null);

        verify(mockedOnLoginAction, never()).call(any(Void.class));
        verify(mockedView).showError();
    }

}
