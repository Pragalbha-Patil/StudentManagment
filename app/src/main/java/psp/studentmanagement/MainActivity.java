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

public class MainActivity extends AppCompatActivity {

    EditText name,email,phone,password;
    Button register1;
    ProgressBar pb_loading;
    private static String URL_register="https://medicaljava.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.ed_name);
        email = findViewById(R.id.ed_email);

        password = findViewById(R.id.ed_password);
        register1 = findViewById(R.id.ed_register);
        pb_loading = findViewById(R.id.ed_progress);

        register1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final String username = name.getText().toString().trim();
                final String mail = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                if (username.isEmpty()) name.setError("Enter the name!");
                else if (mail.isEmpty()) email.setError("Enter the email!");
                else if (pass.isEmpty()) password.setError("Enter the password!");
                else register(username,mail,pass);
            }
        });
    }

    public void register(final String username,final String mail,final String pass) {
        //pb_loading.setVisibility(View.VISIBLE);
        //register1.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("success");

                    if(msg.equals("1")){
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Error in Registration!"+e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error in Registration !"+error.toString(), Toast.LENGTH_SHORT).show();
                        pb_loading.setVisibility(View.GONE);
                        register1.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Send Data To Server
                Map<String,String> params = new HashMap<>();
                params.put("name",username);
                params.put("email",mail);
                params.put("mobno","12345");
                params.put("password",pass);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
