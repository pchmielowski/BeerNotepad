package net.chmielowski.beer.ui.regiser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;
import net.chmielowski.beer.ui.login.StartBeersActivityAction;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public final class RegisterActivity extends Activity {
    @Inject
    User user;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        new RegisterPresenter(
                new BasicRegisterView(this),
                user,
                new StartBeersActivityAction(this)
        );
    }

    static class BasicRegisterView implements RegisterView {
        private final RegisterActivity mActivity;
        @BindView(R.id.register_bt_register)
        Button mRegisterButton;
        @BindView(R.id.register_et_email)
        EditText mEmail;
        @BindView(R.id.register_et_password)
        EditText mPassword;

        BasicRegisterView(
                final RegisterActivity activity) {
            ButterKnife.bind(this, activity);
            mActivity = activity;
        }

        @Override
        public Observable<Void> registerButtonClicked() {
            return RxView.clicks(mRegisterButton);
        }

        @Override
        public String email() {
            return String.valueOf(mEmail.getText());
        }

        @Override
        public String password() {
            return String.valueOf(mPassword.getText());
        }

        @Override
        public void showError(final String message) {
            Toast.makeText(
                    mActivity.getApplicationContext(), message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
