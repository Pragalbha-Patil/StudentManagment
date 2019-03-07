package psp.studentmanagement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class activity_register extends AppCompatActivity{

    EditText name,email,password,confirm_password,mobno;
    Button registerBtn;
    ProgressBar loadingBar;
    String URL_register = "http://10.10.8.233/Studentapp/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set values from activity_main.xml */
        name = findViewById(R.id.ed_name);
        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_password);
        confirm_password = findViewById(R.id.ed_confirm_password);
        mobno = findViewById(R.id.ed_mobNo);
        registerBtn = findViewById(R.id.ed_registerBtn);
        loadingBar = findViewById(R.id.ed_progressBar);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Variables getting values */
                final String userName = name.getText().toString().trim();
                final String userEmail = email.getText().toString().trim();
                final String userPassword = password.getText().toString().trim();
                final String userConfirmPassword = confirm_password.getText().toString().trim();
                final String userMobile = mobno.getText().toString().trim();
                if(userName.isEmpty())
                    name.setError("Name is necessary");
                else if(userEmail.isEmpty())
                    email.setError("Email is necessary");
                else if(userPassword.isEmpty())
                    password.setError("Password is necessary");
                else if(userConfirmPassword.isEmpty())
                    confirm_password.setError("Confirm password is necessary");
                else if(!userPassword.equals(userConfirmPassword))
                    confirm_password.setError("Password doesn't match!");
                else if(userMobile.isEmpty())
                    mobno.setError("Mobile Number is necessary!");
                else{

                    Register(userName,userEmail,userPassword,userMobile);
                }
            }
        });
    }

    private void Register(final String userName, final String userEmail, final String userPassword, final String userMobile /*the variables are made final for Map method to take this values*/) {
        loadingBar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.GONE);
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //rock on!!
                        try{
                            JSONObject jsonobj = new JSONObject(response);
                            String succmsg = jsonobj.getString("success");

                            if (succmsg.equals("1"))
                                Toast.makeText(activity_register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(activity_register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity_register.this,"Error: "+e, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(activity_register.this,"Registered Successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //rock on!!
                    }
                }
        ) /*HashMap here before ;*/{

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put( "name",userName /*Using Register() method variables*/ );
                params.put("email",userEmail /*Using Register() method variables*/ );
                params.put("password",userPassword /*Using Register() method variables*/ );
                params.put("mobno",userMobile);
                return params;
            }
        } ;

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(strReq); /*String request object added to String Queue object*/
    }
}
