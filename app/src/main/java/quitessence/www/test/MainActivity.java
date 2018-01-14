package quitessence.www.test;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button LogOut;
    TextView UserFamily_IDShow;
    String Family_IDHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogOut = (Button)findViewById(R.id.button);
        UserFamily_IDShow = (TextView)findViewById(R.id.Family_IDShow);

        Intent intent = getIntent();
        Family_IDHolder = intent.getStringExtra(SignIn.UserFamily_ID);
        UserFamily_IDShow.setText(Family_IDHolder);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Log Out Successfully", Toast.LENGTH_LONG).show();

            }
        });
    }
}