package quitessence.www.test;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class SignIn extends AppCompatActivity {

    EditText Username_et, Password_et;
    Button LogIn ;
    String PasswordHolder, UsernameHolder;
    String finalResult ;
    String HttpURL = "https://quitessence.com/familymApp/UserLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Username_et = (EditText)findViewById(R.id.etUsername);
        Password_et = (EditText)findViewById(R.id.etPassword);
        LogIn = (Button)findViewById(R.id.btnSignIn);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    UserLoginFunction(UsernameHolder, PasswordHolder);
                } else {
                    Toast.makeText(SignIn.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){
        UsernameHolder = Username_et.getText().toString();
        PasswordHolder = Password_et.getText().toString();
        if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {
            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String username, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignIn.this,"Loading Data",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {

                /** REPLACED "email" with "username"*/

                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){
                    finish();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    intent.putExtra(UserUsername,username);
                    startActivity(intent);
                } else{
                    Toast.makeText(SignIn.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }
            }

        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(username,password);

    }

}