package net.chmielowski.beer.ui.regiser;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public final class RegisterActivityTest {
    final String email = "email";
    final String passwd = "passwd";
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule =
            new ActivityTestRule<>(RegisterActivity.class, true, false);

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
        Mockito.when(mockedUser.register(email, passwd))
               .thenReturn(Observable.just(true));
    }

    @Test
    public void registersCorrectly() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.register_et_email))
                .perform(replaceText(email));
        onView(withId(R.id.register_et_password))
                .perform(replaceText(passwd));
        onView(withId(R.id.register_bt_register))
                .perform(click());

        onView(withId(R.id.beers_rv_list))
                .check(matches(isDisplayed()));
    }
}
