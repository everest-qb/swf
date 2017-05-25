/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.sys;

import javax.servlet.http.*;
import java.util.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author andy.dh.chen
 */
public class SwfCookies {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public void initial(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void setCookies(String cookieName, String cookieValue, int age) {
        try {
            Cookie cookie1 = new Cookie(cookieName, URLEncoder.encode(cookieValue, "UTF-8"));
            cookie1.setMaxAge(age);
            response.addCookie(cookie1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setCookiesValue(String cookiesName, String cookiesValue, int age) {
        Cookie[] cook = request.getCookies();
        try {
            if (cook != null) {
                if (cook.length > 0) {
                    for (int i = 0; i < cook.length; i++) {
                        if (cook[i].getName().equals(cookiesName)) {
                            cook[i].setValue(URLEncoder.encode(cookiesValue, "UTF-8"));
                            cook[i].setMaxAge(age);
                            response.addCookie(cook[i]);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCookies(String cookiesName) {
        String value = "";
        try {
            Cookie[] cook = request.getCookies();
            if (cook != null) {
                if (cook.length > 0) {
                    for (int i = 0; i < cook.length; i++) {
                        if (cook[i].getName().equals(cookiesName)) {
                            value = URLDecoder.decode(cook[i].getValue(), "UTF-8");
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }

    public void deleteCookies(String cookiesName) {
        Cookie cook = new Cookie(cookiesName, null);
        cook.setMaxAge(0);
        response.addCookie(cook);
    }

    public Map getCookieItem(String cookiesItemName) {
        Map value = new HashMap();
        try {
            Cookie[] cook = request.getCookies();
            if (cook != null) {
                if (cook.length > 0) {
                    for (int i = 0; i < cook.length; i++) {
                        if (cook[i].getName().indexOf(cookiesItemName) >= 0) {
                            value.put(cook[i].getName(), URLDecoder.decode(cook[i].getValue(), "UTF-8"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }

    /*將Map 以Value排序*/
    public Map<String, Integer> sortMapByValues(Map<String, Integer> aMap) {
        Set<Map.Entry<String, Integer>> mapEntries = aMap.entrySet();

        List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
        Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> ele1, Map.Entry<String, Integer> ele2) {
                return (ele2.getValue() - ele1.getValue());
            }
        });
        Map<String, Integer> aMap2 = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }

        return aMap2;
    }

}
