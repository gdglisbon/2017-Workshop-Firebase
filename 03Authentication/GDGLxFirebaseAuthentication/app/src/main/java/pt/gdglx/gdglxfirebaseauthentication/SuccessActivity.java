package pt.gdglx.gdglxfirebaseauthentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessActivity extends AppCompatActivity {

    private static final String ARG_USER = "user";

    @BindView(R.id.success_email)
    TextView email;

    public static Intent calling(Context context, String user)
    {
        Intent intent = new Intent(context, SuccessActivity.class );

        Bundle args = new Bundle();
        args.putString(ARG_USER, user);

        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.success);
        ButterKnife.bind(this);

        setData();
    }

    private void setData() {

        Bundle args = getIntent().getExtras();

        if( args.containsKey(ARG_USER) )
        {
            email.setText( args.getString(ARG_USER) );
        }

    }
}
