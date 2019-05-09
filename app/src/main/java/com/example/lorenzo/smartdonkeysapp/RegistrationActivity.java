package com.example.lorenzo.smartdonkeysapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lorenzo.smartdonkeysapp.model.MESSAGE_TYPE;
import com.example.lorenzo.smartdonkeysapp.model.Message;
import com.example.lorenzo.smartdonkeysapp.model.UserWelcomeMessage;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends AppCompatActivity{

    private RegistrationActivity.UserRegistrationTask mAuthTask = null;
    private View mLoginFormView;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private Button mConfirmRegistrationButton;
    private EditText mUsernameView;
    private View mProgressView;
    private ImageButton takePhotoButton;

    private Bitmap profileImage;

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);

        mConfirmRegistrationButton = findViewById(R.id.confirm_button);

        mUsernameView = (EditText) findViewById(R.id.username);

        mLoginFormView = findViewById(R.id.login_form);

        mProgressView = findViewById(R.id.login_progress);

        takePhotoButton = findViewById(R.id.takePhotoButton);

        mConfirmRegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegistration();
            }
        });

        takePhotoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void doRegistration() {
        connection = ConnectionHandler.getConnection();
        if(connection == null) {
            connection = new Connection();
            ConnectionHandler.setConnection(connection);
        }

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirm_password = mConfirmPasswordView.getText().toString();
        String username = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (!password.equals(confirm_password)) {
            mConfirmPasswordView.setError(getString(R.string.not_same_values));
            focusView = mConfirmPasswordView;
            cancel = true;
        }
        if(!validUsername(username)){
            mUsernameView.setError(getString(R.string.not_valid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            if(profileImage != null) //se ha messo l'immagine del profilo
                mAuthTask = new UserRegistrationTask(email, password, username, BitMapToString(profileImage));
            else
                mAuthTask = new UserRegistrationTask(email, password, username, "");
            try {
                mAuthTask.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void loadMenu(){
        AlertDialog loginDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        loginDialog.setMessage(getString(R.string.success_login));
        loginDialog.show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isEmailValid(String email) {
        if(email.contains("\""))
            return false;
        if(email.contains("\'"))
            return false;
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        if(password.contains("\""))
            return false;
        if(password.contains("\'"))
            return false;
        if(password.length() >= 7)
            return true;
        return false;
    }

    private boolean validUsername(String username){
        if(username.length() < 4)
            return false;
        if(username.contains("\""))
            return false;
        if(username.contains("\'"))
            return false;
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    public class UserRegistrationTask extends AsyncTask<Void, Void, Message> {

        private final String mEmail;
        private final String mPassword;
        private final String mUsername;
        private final String mPhoto;


        UserRegistrationTask(String email, String password, String username, String photo) {
            mEmail = email;
            mPassword = password;
            mUsername = username;
            mPhoto = photo;
        }

        @Override
        protected Message doInBackground(Void... params) {
            try {
                String hashedpassword = get_SHA_512_SecurePassword(mPassword, getSalt());
                return connection.doRequestRegistration(mEmail, hashedpassword, mUsername, mPhoto);
            }catch(NoSuchAlgorithmException e){

            }
            return new Message(MESSAGE_TYPE.ERROR_MESSAGE, "errore nella cifratura della password");
        }

        @Override
        protected void onPostExecute(final Message message) {
            mAuthTask = null;
            showProgress(false);
            if(message.getMessageCode() == MESSAGE_TYPE.OK) {
                loadMenu();
            }
            else if(message.getMessageCode() == MESSAGE_TYPE.ERROR_MESSAGE){
                AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                alertDialog.setMessage(message.getServiceMessage());
                alertDialog.show();
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                alertDialog.setMessage("risposta inattesa dal server, ti invitiamo a riprovare");
                alertDialog.show();
            }
            //finish();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            profileImage = (Bitmap) extras.get("data");
            takePhotoButton.setImageBitmap(profileImage);
        }
    }

    public class UploadProfileImage extends AsyncTask<Void, Void, Void> {

        private final String photo;
        private final String endPoint;


        UploadProfileImage(String photo, String endPoint) {
            this.photo = photo;
            this.endPoint = endPoint;
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

    }

}