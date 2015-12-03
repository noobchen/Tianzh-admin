package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.common.util.json.JsonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
@Component
public class UmengServiceImpl implements UserService {
    String authUrl = "http://api.umeng.com/authorize";
    String channelsUrl = "http://api.umeng.com/channels";
    private String appListUrl = "http://api.umeng.com/apps";
    private String appBaseUrl = "http://api.umeng.com/base_data";
    @Autowired
    HttpRequestService httpRequestService;

    @Override
    public Object obtainNewUsers(String productIdKey, String specifyDate) throws Exception {
        String respAuthTokenStr = obtainAuthToken(productIdKey);

        if (respAuthTokenStr != null) {
            AuthToken authToken = JsonUtils.jsonToObject(respAuthTokenStr, AuthToken.class, false);

            if (authToken != null && authToken.getCode() == 200) {
                //获取Umeng Appkey
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("appkey", CacheContainer.getUmengKey(productIdKey));
                params.put("per_page", "500");//默认100
                params.put("date", specifyDate);

                String authTokenStr = authToken.getAuth_token();
                params.put("auth_token", authTokenStr);


                String channelUsersStr = httpRequestService.get(channelsUrl, params);

                ArrayList<ChannelUsers> channelUserses = JsonUtils.jsonToObject(channelUsersStr, new TypeReference<ArrayList<ChannelUsers>>() {
                });

                return channelUserses;
            }
        }

        return null;
    }

    @Override
    public App obtainAppNewUsers(String productIdKey, String appKey, String specifyDate) throws Exception {
        String respAuthTokenStr = obtainAuthToken(productIdKey);

        if (respAuthTokenStr != null) {
            AuthToken authToken = JsonUtils.jsonToObject(respAuthTokenStr, AuthToken.class, false);

            if (authToken != null && authToken.getCode() == 200) {
                HashMap<String, String> params = new HashMap<String, String>();

                String authTokenStr = authToken.getAuth_token();
                params.put("auth_token", authTokenStr);
                params.put("appkey", appKey);
                params.put("date", specifyDate);

                String appStr = httpRequestService.get(appBaseUrl, params);

                App apps = JsonUtils.jsonToObject(appStr, new TypeReference<App>() {
                });

                return apps;
            }
        }
        return null;
    }


    private String obtainAuthToken(String productIdKey) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        //从缓存容器获取友盟账号和密码
        String umengAccountPwd = CacheContainer.getUmengAccountPwd(productIdKey);
        if (umengAccountPwd != null) {
            String email = umengAccountPwd.split("\\|")[0];
            String pwd = umengAccountPwd.split("\\|")[1];

            params.put("email", email);
            params.put("password", pwd);

            return httpRequestService.post(authUrl, params);
        } else {
            return null;
        }
    }


    public static class AuthToken {
        //{"code":200,"success":"ok","auth_token":"zydYUNSz86D2d14pmyxr"}
        int code;
        String success;
        String auth_token;


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("code", code)
                    .append("success", success)
                    .append("auth_token", auth_token)
                    .toString();
        }
    }


    public static class ChannelUsers {
        String date;
        String id;
        String channel;
        int total_install;
        float total_install_rate;
        int install;
        int active_user;
        int launch;
        String duration;
        float install_rate;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getTotal_install() {
            return total_install;
        }

        public void setTotal_install(int total_install) {
            this.total_install = total_install;
        }

        public float getTotal_install_rate() {
            return total_install_rate;
        }

        public void setTotal_install_rate(float total_install_rate) {
            this.total_install_rate = total_install_rate;
        }

        public int getInstall() {
            return install;
        }

        public void setInstall(int install) {
            this.install = install;
        }

        public int getActive_user() {
            return active_user;
        }

        public void setActive_user(int active_user) {
            this.active_user = active_user;
        }

        public int getLaunch() {
            return launch;
        }

        public void setLaunch(int launch) {
            this.launch = launch;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public float getInstall_rate() {
            return install_rate;
        }

        public void setInstall_rate(float install_rate) {
            this.install_rate = install_rate;
        }


        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("date", date)
                    .append("id", id)
                    .append("channel", channel)
                    .append("total_install", total_install)
                    .append("total_install_rate", total_install_rate)
                    .append("install", install)
                    .append("active_user", active_user)
                    .append("launch", launch)
                    .append("duration", duration)
                    .append("install_rate", install_rate)
                    .toString();
        }
    }

    public static class App {
        private String name;
        private String appkey;
        private int installations;
        private int new_users;
        private int active_users;
        private int launches;
        private String date;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public int getInstallations() {
            return installations;
        }

        public void setInstallations(int installations) {
            this.installations = installations;
        }

        public int getNew_users() {
            return new_users;
        }

        public void setNew_users(int new_users) {
            this.new_users = new_users;
        }

        public int getActive_users() {
            return active_users;
        }

        public void setActive_users(int active_users) {
            this.active_users = active_users;
        }

        public int getLaunches() {
            return launches;
        }

        public void setLaunches(int launches) {
            this.launches = launches;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("App{");
            sb.append("name='").append(name).append('\'');
            sb.append(", appkey='").append(appkey).append('\'');
            sb.append(", installations=").append(installations);
            sb.append(", new_users=").append(new_users);
            sb.append(", active_users=").append(active_users);
            sb.append(", launches=").append(launches);
            sb.append(", date=").append(date);
            sb.append('}');
            return sb.toString();
        }
    }
}
