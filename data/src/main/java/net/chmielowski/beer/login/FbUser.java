package net.chmielowski.beer.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import rx.Observable;
import rx.subjects.PublishSubject;

public final class FbUser implements User {

    private final FirebaseAuth mAuth;

    public FbUser(final FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    public Observable<Boolean> login(final String name, final String passwd) {
        final PublishSubject<Boolean> status = PublishSubject.create();
        mAuth.signInWithEmailAndPassword(name, passwd).addOnCompleteListener(
                new ResultObservable(status));
        return status.asObservable();
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
