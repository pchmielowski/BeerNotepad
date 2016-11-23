package net.chmielowski.beer.ui.beers;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.TestComponent;
import net.chmielowski.beer.model.Beer;
import net.chmielowski.beer.model.Beers;
import net.chmielowski.beer.model.Photo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.subjects.ReplaySubject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions
        .isAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public final class BeersActivityTest {

    @Rule
    public ActivityTestRule<BeersActivity> mActivityTestRule =
            new ActivityTestRule<>(BeersActivity.class, true, false);
    @Inject
    Beers mockedBeers;
    Photo mockedPhoto;

    @Before
    public void setUp() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();
        BeerApplication app = (BeerApplication) instrumentation
                .getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.cachedComponent();
        component.inject(this);
        mockedPhoto = Mockito.mock(Photo.class);
    }

    @Test
    public void sorted_by_rating_ascending() {
        ReplaySubject<List<Beer>> subject = ReplaySubject.create();
        subject.onNext(Arrays.asList(
                new Beer("bbb", "c1", "s1", 0.0f, mockedPhoto),
                new Beer("aaa", "c2", "s2", 1.0f, mockedPhoto)
        ));
        Mockito.when(mockedBeers.list())
               .thenReturn(subject);

        mActivityTestRule.launchActivity(new Intent());

        onView(withText("bbb"))
                .check(isAbove(withText("aaa")));
    }

    @Test
    public void sorted_by_country_descending() {
        ReplaySubject<List<Beer>> subject = ReplaySubject.create();
        subject.onNext(Arrays.asList(
                new Beer("from bbb", "Czechy", "s1", 0.0f, mockedPhoto),
                new Beer("from aaa", "Austria", "s2", 1.0f, mockedPhoto)
        ));
        Mockito.when(mockedBeers.list())
               .thenReturn(subject);

        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.beers_sp_sort))
                .perform(click());
        onView(withText("Country"))
                .perform(click());

        onView(withText("from aaa"))
                .check(isAbove(withText("from bbb")));
    }

    @Test
    public void sorted_by_country_order_change() {
        ReplaySubject<List<Beer>> subject = ReplaySubject.create();
        subject.onNext(Arrays.asList(
                new Beer("from bbb", "Czechy", "s1", 0.0f, mockedPhoto),
                new Beer("from aaa", "Austria", "s2", 1.0f, mockedPhoto)
        ));
        Mockito.when(mockedBeers.list())
               .thenReturn(subject);

        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.beers_sp_sort))
                .perform(click());
        onView(withText("Country"))
                .perform(click());
        onView(withId(R.id.beers_switch_sort))
                .perform(click());

        onView(withText("from bbb"))
                .check(isAbove(withText("from aaa")));
    }

    @Test
    public void starts_add_beer_activity() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.beers_fab_add))
                .perform(click());

        onView(withId(R.id.add_et_country))
                .check(matches(isDisplayed()));
    }

    @Test
    public void starts_add_beer_activity_and_goes_back() {
        mActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.beers_fab_add))
                .perform(click());

        pressBack();

        onView(withId(R.id.beers_fab_add))
                .check(matches(isDisplayed()));
    }
}
