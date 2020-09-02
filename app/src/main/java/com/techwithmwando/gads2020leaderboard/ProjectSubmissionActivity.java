package com.techwithmwando.gads2020leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectSubmissionActivity extends AppCompatActivity {
    private TextInputEditText inputFirstName;
    private TextInputEditText inputLastName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputGithubLink;
    private MaterialButton buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputEmail = findViewById(R.id.input_email);
        inputGithubLink = findViewById(R.id.input_github_link);
        buttonSubmit = findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProjectDetails();
            }
        });


    }

    private void postProjectDetails() {
        String cannotBeBlank = "Input cannot be blank";
        String mustBeValidEmail = "Enter a valid email";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        final String firstName = Objects.requireNonNull(inputFirstName.getText()).toString();
        final String lastName = Objects.requireNonNull(inputLastName.getText()).toString();
        final String emailAddress = Objects.requireNonNull(inputEmail.getText()).toString();
        final String githubLink = Objects.requireNonNull(inputGithubLink.getText()).toString();
        if (firstName.isEmpty()) {
            inputFirstName.setError(cannotBeBlank);
            return;
        }
        if (lastName.isEmpty()) {
            inputLastName.setError(cannotBeBlank);
        }
        if (emailAddress.isEmpty()) {
            inputEmail.setError(cannotBeBlank);
        }
        if (githubLink.isEmpty()) {
            inputGithubLink.setError(cannotBeBlank);
        }
        if (!(emailAddress.matches((emailPattern)) || emailAddress.matches(emailPattern2))) {
            inputEmail.setError(mustBeValidEmail);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(ProjectSubmissionActivity.this);
        String postUrl = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        successDialog();
                        Log.d("success", "==" + response);
                        Toast.makeText(ProjectSubmissionActivity.this,
                                "Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                errorDialog();
                Log.d("Error", "==" + error);
                Toast.makeText(ProjectSubmissionActivity.this, "Error \n" + error,
                        Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("entry.1824927963", emailAddress);
                params.put("entry.1877115667", firstName);
                params.put("entry.2006916086", lastName);
                params.put("entry.284483984", githubLink);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return header;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}