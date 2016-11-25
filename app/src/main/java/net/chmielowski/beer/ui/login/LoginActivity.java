package net.chmielowski.beer.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.chmielowski.beer.BeerApplication;
import net.chmielowski.beer.R;
import net.chmielowski.beer.login.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import rx.functions.Func1;

public final class LoginActivity extends Activity {
    @Inject
    User user;
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
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

// CHECKSTYLE:OFF
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "net.chmielowski.beer",
                    PackageManager.GET_SIGNATURES
            );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(
                        "KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT)
                );
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        // CHECKSTYLE:ON


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser usr = firebaseAuth.getCurrentUser();
//                if (usr == null) {
//                    // User is signed out
//                } else {
//                    // User is signed in
//                }
                // ...
            }
        };


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
                        exception.printStackTrace();
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

