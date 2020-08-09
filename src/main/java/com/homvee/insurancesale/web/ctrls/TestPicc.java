package com.homvee.insurancesale.web.ctrls;

import com.homvee.insurancesale.utils.HttpUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestPicc{

    public static void main(String[] args) throws Exception {


        String addr = "http://157.122.153.67:9000/khyx/qth/vehicle/JSPlatQueryCar.do?licenseNo=%E5%B7%9DAH33H2&vinNo=LGXCG6DF3E0121657&licenseType=02";


        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID","93PMZClnO4w_R0aCgz7rqbjSs1OIjRj1K0wyiHydbpFVYZWX55xv!-167023395");
        cookie.setDomain("157.122.153.67");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);


        String content = HttpUtils.postForm(addr ,cookieStore);
        System.out.println(content);

        /*URL url = new URL(addr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Cookie", "JSESSIONID=93PMZClnO4w_R0aCgz7rqbjSs1OIjRj1K0wyiHydbpFVYZWX55xv!-167023395");
        try (InputStream in = connection.getInputStream();) {

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }

            System.out.println(new String(output.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
