package com.github.ulwx.aka.frame;

import com.ulwx.tool.HttpUtils;
import com.ulwx.tool.MD5;
import com.ulwx.tool.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;

public class UlFrameSignatures {
    private static final Logger log = LoggerFactory.getLogger(UlFrameSignatures.class);

    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return text;
    }

    public static String signature(String signKey,String message) {

        try {
            String sMessagekey=message+"&key="+signKey;
            log.debug("signMessage="+sMessagekey);

            return MD5.MD5generator(sMessagekey,"utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static  String signature(String signKey,TreeMap<String,String> parmMap) {
        String signMessage="";
        for(String key:parmMap.keySet()){
            String val=parmMap.get(key);
            if(val!=null){
                String str=key+"="+val;
                signMessage=signMessage+"&"+str;
            }


        }
        signMessage=StringUtils.trimLeadingString(signMessage, "&");

        return signature(signKey,signMessage);
    }

    public static void main(String[] args) throws Exception {

        //System.out.println(ByteUtils.toHexAscii("agg0xx".getBytes("utf-8"), ""));
        String url="http://10.29.216.148:5080/jydTradeService/intf/v1/trade/UserBindCardRegist";

        TreeMap<String,String> parmMap=new TreeMap<>();
        parmMap.put("userId", "567890");
        parmMap.put("mobile", "18565574709");
        parmMap.put("realname", "孙超进");
        parmMap.put("idCardNo", "42900119801116801X");
        parmMap.put("userType", "1");
        String sign=UlFrameSignatures.signature("xwgarw",parmMap);
        parmMap.put("sign", sign);
        String ret=HttpUtils.post(url, parmMap, "utf-8", "utf-8", 100000);
        System.out.println(ret);



    }
}
