package pt.gdglx.gdglxfirebaseauthentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.gdglx.gdglxfirebaseauthentication.utils.NavigationManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getCanonicalName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.login_email)
    EditText email;

    @BindView(R.id.login_password) EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.mAuth = FirebaseAuth.getInstance();

        this.mAuthListener = this.firebaseAuthenticationStateListener;
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

    @OnClick(R.id.login_button)
    public void loginClicked()
    {
        login(email.getText().toString(), password.getText().toString());
    }

    private void login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, firebaseLoginCompleteListener);
    }

    private void loginSuccessful() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            NavigationManager.navigateSuccess(this, user.getEmail());
        }
        else
        {
            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void userSignIn(String email)
    {
        NavigationManager.navigateSuccess(this, email);
    }

    /* ************************************************************
                        FIREBASE WORKSHOP
    ************************************************************ */

    private FirebaseAuth.AuthStateListener firebaseAuthenticationStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                userSignIn(user.getEmail());

            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        }
    };

    private OnCompleteListener<AuthResult> firebaseLoginCompleteListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

            if (!task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show();
            }

            loginSuccessful();
        }
    };
}
