package com.senzec.alfa.socket_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;
import com.senzec.alfa.R;
import com.senzec.alfa.to_json.group_name.SocketChatWithMenu;
import com.senzec.alfa.utils.SharedPrefClass;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A login screen that offers login via username.
 */
public class ChatLoginActivity extends Activity {

    private EditText mUsernameView;

    private String mUsername;

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);

        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();

        // Set up the login form.
       /* mUsernameView = (EditText) findViewById(R.id.username_input);
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });*/
        attemptLogin();

        Intent intent = new Intent();
        intent.putExtra("username", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());
        //intent.putExtra("username", mUsername);
        intent.putExtra("numUsers", 1);
        setResult(RESULT_OK, intent);
        finish();

        //     mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //     mSocket.off("login", onLogin);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        //      mUsernameView.setError(null);

        // Store values at the time of the login attempt.
       /* String username = mUsernameView.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }

        mUsername = username;*/

        // perform the user login attempt.
        //mSocket.emit("add user", username);
        //mSocket.emit("initChat", new SharedPrefClass(LoginActivity.this).getLoginInfo());
        // mSocket.emit("initChat", "{userId:\"59d8714e458ac81a370d29b9\"}");
        //  mSocket.emit("initChat", convertToJSON());


   /*     JSONObject obj = new JSONObject();
        try {
            obj.put("latitudData", lat);
            obj.put("longitudData", lon);
            obj.put("name", nombreUsuario);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("coordenada", obj);*/

        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());

        }catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("initChat", obj);

    }

    public String convertToJSON(){
        String sender_id = new SharedPrefClass(ChatLoginActivity.this).getLoginInfo();

        SocketChatWithMenu profileMenu = new SocketChatWithMenu(sender_id);

        Gson gson = new Gson();
        String profileJson = gson.toJson(profileMenu);
        System.out.print(profileJson);
        return  profileJson;
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("username", new SharedPrefClass(ChatLoginActivity.this).getLoginInfo());
            //intent.putExtra("username", mUsername);
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}
