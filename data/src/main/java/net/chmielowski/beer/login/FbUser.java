package net.chmielowski.beer.login;

import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public final class FbUser implements User {

    private final FirebaseAuth mAuth;
    private final LoginManager mFacebook;

    public FbUser(final FirebaseAuth auth, final LoginManager facebook) {
        this.mAuth = auth;
        this.mFacebook = facebook;
    }

    @Override
    public String uid() {
        return mAuth.getCurrentUser().getUid();
    }

    @Override
    public Observable<Boolean> login(final String name, final String passwd) {
        final PublishSubject<Boolean> status = PublishSubject.create();
        mAuth.signInWithEmailAndPassword(name, passwd).addOnCompleteListener(
                new ResultObservable(status));
        return status.asObservable();
    }

    @Override
    public Observable<Boolean> register(final String email,
            final String password) {
        return RxFirebaseAuth
                .createUserWithEmailAndPassword(mAuth, email, password)
                .map(new AuthResultBooleanFunc());
    }

    @Override
    public Observable<Boolean> login(final Object credential) {
        if (!(credential instanceof AuthCredential)) {
            throw new Error("credential is not instance of AuthCredential");
        }
        return RxFirebaseAuth
                .signInWithCredential(mAuth, (AuthCredential) credential)
                .map(new AuthResultBooleanFunc());
    }

    @Override
    public void logout() {
        mAuth.signOut();
        mFacebook.logOut();
    }

    private static class ResultObservable
            implements OnCompleteListener<AuthResult> {
        private final PublishSubject<Boolean> mStatus;

        ResultObservable(final PublishSubject<Boolean> status) {
            this.mStatus = status;
        }

        @Override
        public void onComplete(@NonNull final Task<AuthResult> task) {
            mStatus.onNext(task.isSuccessful());
        }
    }

    private static class AuthResultBooleanFunc
            implements Func1<AuthResult, Boolean> {
        @Override
        public Boolean call(final AuthResult authResult) {
            return authResult.getUser() != null;
        }
    }
}
