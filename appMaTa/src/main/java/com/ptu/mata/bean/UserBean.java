package com.ptu.mata.bean;

/**
 * Created by SinMin on 2016/10/29
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by level on 2016/10/20.
 */
@DatabaseTable(tableName = "t_user")
public class UserBean {
    @DatabaseField(columnName = "id", generatedId = true)
    long _id;
    @DatabaseField(columnName = "name", unique = true)
    String name;
    @DatabaseField(columnName = "pwd", width = 13)
    String pwd;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DatabaseField(columnName = "url",  unique = true)
    String url;
    @DatabaseField(columnName = "islogin")
   boolean islogin;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean islogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", url='" + url + '\'' +
                ", islogin=" + islogin +
                '}';
    }
}

