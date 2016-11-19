package net.chmielowski.beer.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public final class FbUser implements User {

    private final FirebaseAuth mAuth;

    public FbUser(final FirebaseAuth auth) {
        this.mAuth = auth;
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
                .map(new Func1<AuthResult, Boolean>() {
                    @Override
                    public Boolean call(final AuthResult authResult) {
                        final boolean success = authResult.getUser() != null;
                        return success;
                    }
                });
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
}
