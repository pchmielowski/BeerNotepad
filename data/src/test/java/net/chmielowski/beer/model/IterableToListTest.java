package net.chmielowski.beer.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IterableToListTest {

    Photos mockedPhotos;
    private StructBeer firstBeerAsStruct;
    private Beer firstBeerAsObject;
    private StructBeer secondBeerAsStruct;
    private Beer secondBeerAsObject;

    @Before
    public void setUp() throws Exception {
        firstBeerAsStruct = new StructBeer();
        firstBeerAsStruct.mCountry = "country";
        firstBeerAsStruct.mName = "name";

        firstBeerAsStruct.mRating = 3.4f;
        firstBeerAsStruct.mStyle = "style";
        firstBeerAsObject = objectBeer(firstBeerAsStruct);

        secondBeerAsStruct = new StructBeer();
        secondBeerAsStruct.mCountry = "country2";
        secondBeerAsStruct.mName = "name2";
        secondBeerAsStruct.mRating = 4.4f;
        secondBeerAsStruct.mStyle = "style2";
        secondBeerAsObject = objectBeer(secondBeerAsStruct);

        mockedPhotos = Mockito.mock(Photos.class);
    }

    @NonNull
    private Beer objectBeer(final StructBeer struct) {
        return new Beer(
                struct.mName,
                struct.mCountry,
                struct.mStyle,
                struct.mRating,
                Mockito.mock(Photo.class)
        );
    }

    @Test
    public void one_beer() throws Exception {
        final List<Beer> result = new FbReadBeers.IterableToList(mockedPhotos)
                .call(
                        Collections.singletonList(
                                mockedSnapshot(firstBeerAsStruct)));

        assertThat(
                result,
                is(Collections.singletonList(
                        firstBeerAsObject))
        );
    }

    @Test
    public void few_beers() throws Exception {
        final List<Beer> result = new FbReadBeers.IterableToList(mockedPhotos)
                .call(
                        Arrays.asList(
                                mockedSnapshot(firstBeerAsStruct),
                                mockedSnapshot(secondBeerAsStruct)
                        ));

        assertThat(
                result,
                is(Arrays.asList(
                        firstBeerAsObject, secondBeerAsObject))
        );
    }

    @NonNull
    private DataSnapshot mockedSnapshot(
            final StructBeer structBeer) {
        final DataSnapshot mock = Mockito.mock(
                DataSnapshot.class);
        Mockito.when(mock.getValue(
                (Class<StructBeer>) Mockito.any()))
               .thenReturn(structBeer);
        return mock;
    }
}
