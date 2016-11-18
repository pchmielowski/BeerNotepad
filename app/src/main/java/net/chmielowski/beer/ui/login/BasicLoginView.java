package net.chmielowski.beer.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import net.chmielowski.beer.BuildConfig;
import net.chmielowski.beer.R;
import net.chmielowski.beer.ui.regiser.RegisterActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

final class BasicLoginView implements LoginView {
    private final LoginActivity mActivity;

    @BindView(R.id.login_et_email)
    EditText mEmail;
    @BindView(R.id.login_et_password)
    EditText mPassword;
    @BindString(R.string.login_failed)
    String mLoginFailed;
    @BindView(R.id.login_bt_login)
    Button mButtonLogin;
    @BindView(R.id.login_pb_wait)
    ProgressBar mProgress;
    @BindView(R.id.login_tv_register)
    TextView mRegister;

    BasicLoginView(final LoginActivity activity) {
        mActivity = activity;
        ButterKnife.bind(this, mActivity);
        if (BuildConfig.DEBUG) {
            mEmail.setText("piotrek@domain.com");
            mPassword.setText("passwd");
        }
        mRegister.setClickable(true);
    }

    @Override
    public Observable<Void> loginButtonClicked() {
        return RxView.clicks(mButtonLogin);
    }

    @Override
    public Observable<Void> registerLinkClicked() {
        return RxView.clicks(mRegister);
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
    public void showError() {
        mProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(mActivity.getApplicationContext(), mLoginFailed,
                       Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }

    // TODO: do the same what with BeersActivity
    @Override
    public void startRegisterActivity() {
        mActivity.startActivity(new Intent(
                mActivity.getApplicationContext(),
                RegisterActivity.class
        ));
    }
}
