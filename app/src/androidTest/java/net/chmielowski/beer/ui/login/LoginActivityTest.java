package net.chmielowski.beer.ui.login;


import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.TestComponent;
import net.chmielowski.beer.login.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyString;

@RunWith(AndroidJUnit4.class)
public final class LoginActivityTest {

    final String email = "bad email";
    final String passwd = "bad passwd";
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);
    @Inject
    User mockedUser;

    @Before
    public void setUp() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();
        BeerApplication app = (BeerApplication) instrumentation
                .getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.cachedComponent();
        component.inject(this);
    }

    @Test
    public void loginsCorrectly() {
        Mockito.when(mockedUser.login(anyString(), anyString()))
               .thenReturn(Observable.just(true));

        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.login_et_email))
                .perform(replaceText(email));
        onView(withId(R.id.login_et_password))
                .perform(replaceText(passwd));
        onView(withId(R.id.login_bt_login))
                .perform(click());

        // TODO: add assertion
    }

}

