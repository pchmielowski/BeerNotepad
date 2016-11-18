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
import static android.support.test.espresso.assertion.ViewAssertions
        .doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public final class LoginActivityTest {

    final String email = "email";
    final String passwd = "passwd";
    final String wrongPasswd = "wrong passwd";
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
        Mockito.when(mockedUser.login(email, passwd))
               .thenReturn(Observable.just(true));
        Mockito.when(mockedUser.login(email, wrongPasswd))
               .thenReturn(Observable.just(false));
    }

    @Test
    public void loginsCorrectly() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.login_et_email))
                .perform(replaceText(email));
        onView(withId(R.id.login_et_password))
                .perform(replaceText(passwd));
        onView(withId(R.id.login_bt_login))
                .perform(click());

        onView(withId(R.id.beers_rv_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginFails() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.login_et_email))
                .perform(replaceText(email));
        onView(withId(R.id.login_et_password))
                .perform(replaceText(wrongPasswd));
        onView(withId(R.id.login_bt_login))
                .perform(click());

        onView(withId(R.id.beers_rv_list))
                .check(doesNotExist());
    }

    @Test
    public void opensRegistrationForm() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.login_tv_register))
                .perform(click());

        onView(withId(R.id.register_et_email))
                .check(matches(isDisplayed()));
    }

}

