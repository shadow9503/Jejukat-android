package com.androidlec.jejukat;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginCallback_fb implements FacebookCallback<LoginResult> {

    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.v("Callback :: ", "onSuccess");
        requestMe(AccessToken.getCurrentAccessToken());
    }
    // 로그인 창을 닫을 경우, 호출됩니다.
    @Override
    public void onCancel() {
        Log.v("Callback :: ", "onCancel");
    }
    // 로그인 실패 시에 호출됩니다.
    @Override
    public void onError(FacebookException error) {
        Log.v("Callback :: ", "onError : " + error.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe(AccessToken token) {
        STATICDATA.LOGIN_STATUS=2;
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                        parser(object.toString());
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
    public void parser(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            STATICDATA.TEMP_EMAIL = jsonObject.getString("email");
            STATICDATA.TEMP_NICKNAME = jsonObject.getString("name");
            STATICDATA.LOGIN_METHOD =2;
            STATICDATA.LOGIN_STATUS=1;
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
