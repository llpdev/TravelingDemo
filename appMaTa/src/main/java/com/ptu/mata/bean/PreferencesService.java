package com.ptu.mata.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.name;

/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class PreferencesService {
   private Context context;

    public PreferencesService(Context context) {
        this.context = context;
    }
    public void saveUser(String editnum,String editpwd){
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("usernum", editnum);
        editor.putString("userpwd", editpwd);
        editor.commit();
    }
    public Map<String, String> getUser() {
        Map<String, String> params = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        params.put("usernum", preferences.getString("usernum",""));
        params.put("userpwd", preferences.getString("userpwd",""));
        return params;
    }


}
