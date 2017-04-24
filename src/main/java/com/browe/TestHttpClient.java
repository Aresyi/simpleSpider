package com.browe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {

  // 创建CookieStore实例
  static CookieStore cookieStore = null;
  static HttpClientContext context = null;
  String loginUrl = "https://sec.1688.com/query.htm?smApp=kylin&smPolicy=kylin-index-anti_Spider-seo-html-checkcode&smCharset=GBK&smTag=NTguMjQ3LjExMi44Miw0NDg2OTQ3MDQsZDk1NjI2ZjliNWI1NDk1OWEzOThmZDdlM2VmM2MyZmU%3D&smReturn=https%3A%2F%2Ftradingking1842.1688.com%2F&smSign=aueKpRrMZZhYDtqN79t7tA%3D%3D";
  String testUrl = "http://127.0.0.1:8080/CwlProClient/account/querySubAccount.action";
  String loginErrorUrl = "http://127.0.0.1:8080/CwlProClient/login/login.jsp";

  public void testLogin(String checkcode) throws Exception {
    System.out.println("----testLogin");

    // // 创建HttpClientBuilder
    // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    // // HttpClient
    // CloseableHttpClient client = httpClientBuilder.build();
    // 直接创建client
    CloseableHttpClient client = HttpClients.createDefault();

    HttpPost httpPost = new HttpPost(loginUrl);
    Map<String,String> parameterMap = new HashMap<String,String>();
    parameterMap.put("action", "QueryAction");
    parameterMap.put("event_submit_do_query", "ok");
    parameterMap.put("smPolicy", "kylin-index-anti_Spider-seo-html-checkcode");
    parameterMap.put("smReturn", "https://shop1467133708554.1688.com/");
    parameterMap.put("smApp", "kylin");
    parameterMap.put("smCharset", "GBK");
    parameterMap.put("smTag", "MTQwLjIwNi4xNzYuMjM4LDQ0ODY5NDcwNCxkMzVmYWU1M2JiNGE0NTU4OGI1YWY3N2YzN2MzYjM2ZA");
    parameterMap.put("smSign", "LZS03+qHLfX0Msf8QaiNRw==");
    parameterMap.put("identity", "sm-kylin");
    parameterMap.put("captcha", "");
    parameterMap.put("checkcode", checkcode);
    
    

    UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
        getParam(parameterMap), "UTF-8");
    httpPost.setEntity(postEntity);
    
//    httpPost.getParams().setParameter(arg0, arg1)
    
    httpPost.addHeader("HOST", "sec.1688.com");
    httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
    httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    httpPost.addHeader("Cookie", "cna=aATkEMsbBQ4CAYzOsO4Va2Po; alicnweb=touch_tb_at%3D1492868605943%7Chp_newbuyerguide%3Dtrue%7Clastlogonid%3Dyidej; l=AuzsOSh81NkwecUBTV5we4vSvMQebJBP; isg=AmJi2FEyTkEptFIRoYA2CbYosugopYNXCTXhsqz7R1WAfwD5lkep3Py52TJQ; ali_beacon_id=58.247.112.82.1492155919756.578154.9; UM_distinctid=15b6b72e8129-0994a8e6af019e8-1262694a-1fa400-15b6b72e813429; ali_ab=58.247.112.82.1492156345558.0; ali_apache_track=\"c_ms=1|c_mid=b2b-448694704|c_lid=yidej\"; _cn_slid_=oR9YnG3Kbf; last_mid=b2b-448694704; __last_loginid__=yidej; ad_prefer=\"2017/04/21 22:05:23\"; h_keys=\"%u670d%u9970%u914d%u4ef6%u3001%u9970%u54c1#%u4e1d%u889c#%u5973%u978b#%u6bdb%u8863#%u886c%u886b#%u4f11%u95f2%u88e4#%u725b%u4ed4%u88e4#%u5973%u88e4#%u6253%u5e95%u886b#%u9488%u7ec7%u886b\"; JSESSIONID=8L78AGZv1-SqVXbNfrwZ3AdwfNn5-bx0okGQ-LRn1; _csrf_token=1492867677442; _tmp_ck_0=\"1bobU%2FQQBaymrsbF%2Fc8lkr06zjPvwcYNWqMho33QfoDUX5TxpZUPF%2BjW%2BKMiGb%2BbNT9svRwyPR2lVicYOMNwHIzC7OSSReuXEX7dJu44jBj%2FZrwahSmFDGFg4i8cs8RgcStIQW9bx%2Fn%2FZobZJwg9b2xC0zSwV2Cr2S91q9%2Fr1Sb4A6xQKxMUzcF2D43wk7kcTMZQR2u96ryLbRhg0%2BJOL6t4muYzmlxzDoTJ52dCLFUI5ISYgCfrhpW9e%2Fm4wvJQrMzSH5OL8F0H2Btgi%2F6Yyblo%2BKGzcZvSxxoSJ2nowM7z4U4ruOB5yB7%2Flu1mjAWjYlI%2BVSWhnK209%2BQwVFB5195INjK0C51O1nVtMU%2BkZaxZjmwwVH4RKsYjj7%2BLkdBLK6IS9zaqGKTJY7Zu%2BGKXrYhL0vk4fKnhRzUMme0aneQN4VLPgLDHoUH501vY7UDdgRvogGS%2BuDc3ECFnPM8E8iT3iOD4wXMeWfGXzXE5hOKQx2PVlwLAoYM4jHSVPc3DU2w9y1Fk%2BHEJJANzf%2FwWScZcTBfapF6zyetkSYxXNgsobLxn5to2CQ%3D%3D\"; _ITBU_IS_FIRST_VISITED_=ali0351%3Apm0iehjk88%7C*xC-i2FHSvmHGvF8LOmgT%3Apm0ig230d4; ali_apache_tracktmp=\"c_w_signed=Y\"; _nk_=\"ZoRzR58gc8I%3D\"; tbsnid=B4d%2BT7kAG8At8v5hgyvhptaK%2FNxiPm3mw4VtbOs2kkg6sOlEpJKl9g%3D%3D; LoginUmid=\"apNr1331iL4EI1CFfmVNrmyRikk0a2vWto2w79h%2BMDt0ZvS19%2BaAOg%3D%3D\"; userID=\"RjxqNVNTkQ6cwrfA0X%2BjY0iLyDfg7Rak2PZEg0Y4jRg6sOlEpJKl9g%3D%3D\"; userIDNum=\"3JO27xBdmfIDBPJBu4sTUQ%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; __cn_logon__=true; __cn_logon_id__=yidej; unb=448694704; cn_tmp=\"Z28mC+GqtZ0Ldvy+h3uXO4uwk+LCnw9n15V6pHYItoNGv+JDzDL6UaJuOmzoiEESU2etvy4NuGvRwxc/V7oMi6mO43E7zpoC+wd/6kyVAbwIgpUae/KaaUAkyPiKhXQQ/oMJpWeoSSasvH7k4TG9QlvVVLCVKHE1qRx29P8lFNPIG0IAXBskPfbvoZyLdCFZmqnWhObLHdfykwLbhdBceBlGL5zVFgRDAA/gTpmhLaQ=\"");
    
    System.out.println("request line:" + httpPost.getRequestLine());
    try {
      // 执行post请求
      HttpResponse httpResponse = client.execute(httpPost);
//      String location = httpResponse.getFirstHeader("Location")
//          .getValue();
//      if (location != null && location.startsWith(loginErrorUrl)) {
//        System.out.println("----loginError");
//      }
      printResponse(httpResponse);

//      // 执行get请求
//      System.out.println("----the same client");
//      HttpGet httpGet = new HttpGet(testUrl);
//      System.out.println("request line:" + httpGet.getRequestLine());
//      HttpResponse httpResponse1 = client.execute(httpGet);
//      printResponse(httpResponse1);


      // cookie store
//      setCookieStore(httpResponse);
      
      // context
//      setContext();
      
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void testContext() throws Exception {
    System.out.println("----testContext");
    // 使用context方式
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(testUrl);
    System.out.println("request line:" + httpGet.getRequestLine());
    try {
      // 执行get请求
      HttpResponse httpResponse = client.execute(httpGet, context);
      System.out.println("context cookies:"
          + context.getCookieStore().getCookies());
      printResponse(httpResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void testCookieStore() throws Exception {
    System.out.println("----testCookieStore");
    // 使用cookieStore方式
    CloseableHttpClient client = HttpClients.custom()
        .setDefaultCookieStore(cookieStore).build();
    HttpGet httpGet = new HttpGet(testUrl);
    System.out.println("request line:" + httpGet.getRequestLine());
    try {
      // 执行get请求
      HttpResponse httpResponse = client.execute(httpGet);
      System.out.println("cookie store:" + cookieStore.getCookies());
      printResponse(httpResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭流并释放资源
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void printResponse(HttpResponse httpResponse)
      throws ParseException, IOException {
    // 获取响应消息实体
    HttpEntity entity = httpResponse.getEntity();
    // 响应状态
    System.out.println("status:" + httpResponse.getStatusLine());
    System.out.println("headers:");
    HeaderIterator iterator = httpResponse.headerIterator();
    while (iterator.hasNext()) {
      System.out.println("\t" + iterator.next());
    }
    // 判断响应实体是否为空
    if (entity != null) {
      String responseString = EntityUtils.toString(entity);
      System.out.println("response length:" + responseString.length());
      System.out.println("response content:"
          + responseString.replace("\r\n", ""));
    }
  }

  public static void setContext() {
    System.out.println("----setContext");
    context = HttpClientContext.create();
    Registry<CookieSpecProvider> registry = RegistryBuilder
        .<CookieSpecProvider> create()
        .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
        .register(CookieSpecs.BROWSER_COMPATIBILITY,
            new BrowserCompatSpecFactory()).build();
    context.setCookieSpecRegistry(registry);
    context.setCookieStore(cookieStore);
  }

  public static void setCookieStore(HttpResponse httpResponse) {
    System.out.println("----setCookieStore");
    cookieStore = new BasicCookieStore();
    // JSESSIONID
    String setCookie = httpResponse.getFirstHeader("Set-Cookie")
        .getValue();
    String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
        setCookie.indexOf(";"));
    System.out.println("JSESSIONID:" + JSESSIONID);
    // 新建一个Cookie
    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
        JSESSIONID);
    cookie.setVersion(0);
    cookie.setDomain("127.0.0.1");
    cookie.setPath("/CwlProClient");
    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
    cookieStore.addCookie(cookie);
  }

  public static List<NameValuePair> getParam(Map parameterMap) {
    List<NameValuePair> param = new ArrayList<NameValuePair>();
    Iterator it = parameterMap.entrySet().iterator();
    while (it.hasNext()) {
      Entry parmEntry = (Entry) it.next();
      param.add(new BasicNameValuePair((String) parmEntry.getKey(),
          (String) parmEntry.getValue()));
    }
    return param;
  }
  
  
  public static void main(String[] args) throws Exception {
	TestHttpClient obj = new TestHttpClient();
	obj.testLogin("gfkx");
}
}

