package com.chauncy.common.util.huanxin;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import net.sf.json.JSONObject;

/**
 * @Author cheng
 * @create 2019-12-21 21:32
 */

public class HuanXinUtil {

    //  private   String targetURL = "https://a1.easemob.com/1177180507177107/bit/chatrooms";
    private String uri = "https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/";
    private String hx_client_id = "";
    private String hx_client_Secret = "";
    private String hx_access_token = "";
    private long hx_token_time = 0;
    private static String profilepath = "Im.properties";


    public HuanXinUtil(){

        String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""));  //项目路径
        filePath = filePath.replaceAll("file:/", "");
        filePath = filePath.replaceAll("%20", " ");
        filePath = filePath.trim()+profilepath;
//        filePath = "D://hx_key.properties";

        Properties properties = new Properties();

        try {
            InputStream in = HuanXinUtil.class.getClassLoader().getResourceAsStream("Im.properties");
            String filePaths = String.valueOf(in);
//            InputStream ins = new BufferedInputStream(new FileInputStream(filePaths));
            properties.load(in);
            hx_client_id = properties.getProperty("CLIENT_ID");
            hx_client_Secret = properties.getProperty("CLIENT_SECRET");
            hx_access_token = properties.getProperty("ACCESS_TOKEN");
            hx_token_time = Long.parseLong(properties.getProperty("TOKEN_TIME"));
            in.close();
            Date date = new Date(hx_token_time);
            System.out.println(date.before(new Date()));
            if(date.before(new Date())){
                hx_access_token=this.getAccessToken(hx_client_id, hx_client_Secret);
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                now.set(Calendar.DATE, now.get(Calendar.DATE) + 5);
                hx_token_time =now.getTimeInMillis();
//                FileOutputStream fileOutputStream = new FileOutputStream(filePaths);
                properties.setProperty("TOKEN_TIME", Long.toString(hx_token_time));
                properties.setProperty("ACCESS_TOKEN", hx_access_token);
//                properties.store(fileOutputStream, "update access_token");
//                fileOutputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public String getchatroomID(String roomname){
        String id ="";
        try {
            URL url = new URL("https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/chatrooms");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+hx_access_token);
            String input = "{\"name\":\""+roomname+"\",\"description\":\"chatroom\",\"owner\":\"tdd123\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {

            }else{
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    System.out.println("------");
                    sb.append(output);
                }
                JSONObject json = JSONObject.fromObject(sb.toString());
                id =  JSONObject.fromObject(json.getString("data")).getString("id");

            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;

    }



    public int createUser(String username,String password,String nickname){
        int  i =0;
        try {

            URL url = new URL("https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+hx_access_token);
            String input = "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"nickname\":\""+nickname+"\"}";
            System.out.println(input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            System.out.println(conn.getResponseCode());
            i =conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public JSONObject changeUserNickname(String username,String nickname){
        JSONObject json  =null;
        try {
            URL url = new URL("https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/users/"+username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+hx_access_token);
            String input = "{\"nickname\":\""+nickname+"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            if (conn.getResponseCode() != 200) {

            }else{
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                json = JSONObject.fromObject(sb.toString());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }


    public JSONObject getHXUserInfo(String username){
        JSONObject json  =null;
        try {

            URL url = new URL("https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/users/"+username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+hx_access_token);

            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {

            }else{
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                json = JSONObject.fromObject(sb.toString());
            }
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return json;

    }



    public String getAccessToken(String client_id,String client_secret){
        String token="";
        try {
            URL url = new URL("https://a1-vip5.easemob.com/1416181129068746/kefuchannelapp61142/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"grant_type\":\"client_credentials\"," +
                    "\"client_id\":\""+client_id+"\"," +
                    "\"client_secret\":\""+client_secret+"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {

            }else{

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                JSONObject json = JSONObject.fromObject(sb.toString());
                token =  json.getString("access_token");

            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;

    }


    /**
     * 创建聊天室
     * @param name  聊天室的名称(俱乐部id)
     * @param description   聊天室的描述
     * @return
     */
    public String createChatRooms(String name , String description) {
        String id = "";
        try {
            URL url = new URL(uri + "chatrooms");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "
                    + hx_access_token);
            String input = "{\"name\":\""+name+"\",\"description\":\""+description+"\"" +
                    ",\"maxusers\":\"500\",\"owner\":\"88888888\"}";
            System.out.println(input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                System.out.println(sb.toString());
                JSONObject json = JSONObject.fromObject(sb.toString());
                id =  JSONObject.fromObject(json.getString("data")).getString("id");
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void addChatRooms(String clubID , String username) {
        try {
            URL url = new URL(uri + "chatrooms/"+clubID+"/users/"+username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "
                    + hx_access_token);
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                System.out.println(sb.toString());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param uid  俱乐部id/接收者
     * @param msg   会员id/文本内容
     * @param form  发送者
     * @return
     */
    public int sendMessage(String uid, String msg, String form) {
        int i = 0;
        try {
            URL url = new URL(uri + "messages");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "
                    + hx_access_token);
            // String input = "{\"username\":\"" + username +
            // "\",\"password\":\"" + password + "\",\"nickname\":\"" + nickname
            // + "\"}";
            String input = "{\"target_type\":\"users\",\"target\":[\"" + uid
                    + "\"],\"msg\":{\"type\":\"txt\"," + "\"msg\":\"" + msg
                    + "\"},\"form\":\"" + form + "\"}";
            System.out.println(input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("utf-8"));
            os.flush();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                StringBuffer sb = new StringBuffer();
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                System.out.println(sb.toString());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static void main(String[] args) {
//      System.out.println(new HuanXinUtil().createChatRooms("f78761ea7067493fbc7725d384d2c52d","云南当康休闲健身"));
//      new HuanXinUtil().createUser("123456789012", "1234567", "云南当康休闲健身");
        System.out.println((new HuanXinUtil().getHXUserInfo("1234567890123 ")));
//      new HuanXinUtil().sendMessage("1", "87f332cbea614da1abd4406cf159857b", "app_system");
//        System.out.println(new HuanXinUtil().getAccessToken("YXA6YqU2kPOoEei5783rT3IOAA", "YXA6xBk2Rg0Fwr1NzLyJv5vBb5dI1bk"));
    }
}


