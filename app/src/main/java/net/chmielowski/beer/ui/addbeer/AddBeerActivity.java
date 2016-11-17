package net.chmielowski.beer.ui.addbeer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import net.chmielowski.beer.R;
import net.chmielowski.beer.model.FbBeers;

public final class AddBeerActivity extends AppCompatActivity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        new AddBeerPresenter(new BasicAddBeerView(this),
                             new FbBeers(FirebaseDatabase.getInstance()),
                             new Finishable() {
                                 @Override
                                 public void finish() {
                                     finish();
                                 }
                             });
    }

}
