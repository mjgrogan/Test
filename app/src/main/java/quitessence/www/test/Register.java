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

public class Register extends AppCompatActivity {

    Button register;
    EditText Username_et, FamilyName_et, Password_et, ConfirmPassword_et, Mobile_et ;
    String UsernameHolder, FamilyNameHolder, PasswordHolder, ConfirmPasswordHolder, MobileHolder;
    String finalResult ;
    String HttpURL = "https://quitessence.com/familymApp/userregistration.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assign Id'S
        Username_et = (EditText)findViewById(R.id.etUsername);
        FamilyName_et = (EditText)findViewById(R.id.etFamilyName);
        Password_et = (EditText)findViewById(R.id.etPassword);
        ConfirmPassword_et = (EditText)findViewById(R.id.etConfirmPassword);
        Mobile_et = (EditText)findViewById(R.id.etMobile);
        register = (Button)findViewById(R.id.Submit);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    UserRegisterFunction(UsernameHolder, FamilyNameHolder, PasswordHolder, MobileHolder);
                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(Register.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    public void CheckEditTextIsEmptyOrNot(){

        UsernameHolder = Username_et.getText().toString();
        FamilyNameHolder = FamilyName_et.getText().toString();
        PasswordHolder = Password_et.getText().toString();
        ConfirmPasswordHolder = ConfirmPassword_et.getText().toString();
        MobileHolder = Mobile_et.getText().toString();


        if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(FamilyNameHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(MobileHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String username, final String familyname, final String password, final String mobile){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Register.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);

                hashMap.put("familyname",params[1]);

                hashMap.put("password",params[2]);

                hashMap.put("mobile",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(username,familyname,password,mobile);
    }

}