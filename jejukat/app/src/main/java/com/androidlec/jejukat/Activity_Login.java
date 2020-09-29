package com.androidlec.jejukat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class Activity_Login extends AppCompatActivity {

    private CallbackManager fb_CallbackManager;
    private LoginCallback_fb fb_LoginCallback;
    private LoginButton fbLogin;

    private OAuthLoginButton naverLogin;
    private static OAuthLogin naverLoginInstance;

    Session kko_LoginCallback;
    GlobalApplication k_globalApplication = GlobalApplication.getGlobalApplicationContext();
    private LoginCallback_kko k_sessionCallback = new LoginCallback_kko();

    LinearLayout kakao,fb,naver,google;

    Intent mainActivity;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mainActivity = new Intent(Activity_Login.this, Activity_Main.class);

        kakao = findViewById(R.id.layout_kakao_login);
        fb = findViewById(R.id.layout_fb_login);
        naver = findViewById(R.id.layout_naver_login);
        google = findViewById(R.id.layout_google_login);
        fbLogin = findViewById(R.id.btn_fb_login);
        naverLogin = findViewById(R.id.btn_naver_login);

        kakao.setOnClickListener(click);
        fb.setOnClickListener(click);
        naver.setOnClickListener(click);
        google.setOnClickListener(click);

        fb_CallbackManager = CallbackManager.Factory.create();
        fb_LoginCallback = new LoginCallback_fb();
        fbLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        fbLogin.registerCallback(fb_CallbackManager, fb_LoginCallback);

        kko_LoginCallback = Session.getCurrentSession();
        kko_LoginCallback.addCallback(k_sessionCallback);

        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this, STATICDATA.NAVER_CLIENT_ID, STATICDATA.NAVER_CLIENT_SECRET, STATICDATA.NAVER_CLIENT_NAME);

        OAuthLoginHandler naverLoginHandler  = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    new RequestApiTask().execute();
                } else {//로그인 실패
                    String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                    String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                }
            }

        };

        naverLogin.setOAuthLoginHandler(naverLoginHandler);

        //로그인후 메인으로 line 121
        back2Main();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fb_CallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.v("data",data.toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.layout_kakao_login:
                    STATICDATA.LOGIN_METHOD = 1;
                    kko_LoginCallback.open(AuthType.KAKAO_LOGIN_ALL, Activity_Login.this);
                    break;

                case R.id.btn_fb_login:
                    STATICDATA.LOGIN_METHOD = 2;
                    break;

                case R.id.btn_naver_login:
                    STATICDATA.LOGIN_METHOD=3;

                case R.id.layout_google_login:
                    STATICDATA.LOGIN_METHOD=4;
                    break;

                //가짜버튼;
                case R.id.layout_fb_login:
                    fbLogin.performClick();
                    break;

                case R.id.layout_naver_login:
                    naverLogin.performClick();
                    break;

            }
        }
    };

    public void back2Main (){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (STATICDATA.LOGIN_STATUS){
                    case 0:
                        Log.v("repeat","login_status: 0");
                        back2Main();
                        break;
                    case 1:
                        //jsp
                        String url = "http://119.207.169.213:8080//jejukat/jejukat_query_login.jsp?email="+STATICDATA.TEMP_EMAIL+"&nickname="+STATICDATA.TEMP_NICKNAME+"&method="+STATICDATA.LOGIN_METHOD+"&picture="+STATICDATA.TEMP_PROFILEPIC;
                        Log.v("url",url);
                        NetworkTask_Select_Userdata networktask = new NetworkTask_Select_Userdata(Activity_Login.this,url);
                        networktask.execute();
                        //전체 로그아웃
                        logOutALLAPI();

                        //sharedPreference 등록;
                        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("method",Integer.toString(STATICDATA.LOGIN_METHOD));
                        editor.putString("email",STATICDATA.TEMP_EMAIL);
                        editor.commit();


                        //메인엑티비티로 돌아가기
                        startActivity(mainActivity);

                        finish();
                        break;
                    case 2:
                        Log.v("repeat","login_status: 2");
                        back2Main();
                        Toast.makeText(Activity_Login.this, "로그인 중입니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },1000);

    }
    public void logOutALLAPI(){
        //kko
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.i("KAKAO_API", "로그아웃 성공");
            }
        });

        //fb
        LoginManager.getInstance().logOut();

        //naver
        naverLoginInstance.logout(context);
    }

   public void getUserData(){


   }
    private class RequestApiTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = naverLoginInstance.getAccessToken(context);
            return naverLoginInstance.requestApi(context, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {

                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                STATICDATA.TEMP_EMAIL = response.getString("email");
                STATICDATA.TEMP_NICKNAME = response.getString("nickname"); //닉네임
                STATICDATA.TEMP_PROFILEPIC = response.getString("profile_image");// 프로필사진추가
                STATICDATA.LOGIN_METHOD = 3;
                STATICDATA.LOGIN_STATUS = 1;

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
