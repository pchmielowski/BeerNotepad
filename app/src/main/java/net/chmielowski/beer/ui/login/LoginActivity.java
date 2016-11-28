package net.chmielowski.beer.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;

import javax.inject.Inject;

import rx.functions.Func1;

public final class LoginActivity extends Activity {
    @Inject
    User user;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ((BeerApplication) getApplication()).cachedComponent().inject(this);
        new LoginPresenter(
                new BasicLoginView(this),
                user,
                new StartBeersActivityAction(this)
        );
        if (isLoggedIn()) {
            new StartBeersActivityAction(this);
        }
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(
                R.id.login_btn_fblogin);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(
                mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(final FacebookException exception) {
                        throw new Error(exception);
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        user.login(FacebookAuthProvider.getCredential(
                token.getToken()))
            .map(new Func1<Boolean, Void>() {
                @Override
                public Void call(final Boolean aBoolean) {
                    return null;
                }
            })
            .subscribe(new StartBeersActivityAction(this));
    }
}

