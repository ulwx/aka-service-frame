package com.github.ulwx.aka.frame;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ConfigurationProperties(AkaFrameProperties.FRAME_PROPERTIES_PREFX)
public class AkaFrameProperties implements InitializingBean {
    public final static String  FRAME_PROPERTIES_PREFX="aka.frame";
    @NestedConfigurationProperty
    private List<Protocol> protocols=new ArrayList<>();
    private String globalErrorClass;
    @NestedConfigurationProperty
    private Request request=new Request();
    @NestedConfigurationProperty
    private NotifyRequest notifyRequest=new NotifyRequest();
    @NestedConfigurationProperty
    private Storage storage=new Storage();


    public List<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<Protocol> protocols) {
        this.protocols = protocols;
    }

    public String getGlobalErrorClass() {
        return globalErrorClass;
    }

    public void setGlobalErrorClass(String globalErrorClass) {
        this.globalErrorClass = globalErrorClass;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public NotifyRequest getNotifyRequest() {
        return notifyRequest;
    }

    public void setNotifyRequest(NotifyRequest notifyRequest) {
        this.notifyRequest = notifyRequest;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void afterPropertiesSet()  {
    }
    public static class Database{

        private String ds="";

        public String getDs() {
            return ds;
        }

        public void setDs(String ds) {
            this.ds = ds;
        }
    }
    public static class Storage{
        @NestedConfigurationProperty
        private InsertInterlog insertInterlog=new InsertInterlog();
        @NestedConfigurationProperty
        private InsertNotifylog insertNotifylog=new InsertNotifylog();
        @NestedConfigurationProperty
        private Database database=new Database();

        public InsertInterlog getInsertInterlog() {
            return insertInterlog;
        }

        public void setInsertInterlog(InsertInterlog insertInterlog) {
            this.insertInterlog = insertInterlog;
        }

        public InsertNotifylog getInsertNotifylog() {
            return insertNotifylog;
        }

        public void setInsertNotifylog(InsertNotifylog insertNotifylog) {
            this.insertNotifylog = insertNotifylog;
        }

        public Database getDatabse() {
            return database;
        }

        public void setDatabase(Database databse) {
            this.database = databse;
        }
    }
    public static class InsertInterlog{
        private List<String> excludeProtocol=new ArrayList<>();
        public List<String> getExcludeProtocol() {
            return excludeProtocol;
        }
        public void setExcludeProtocol(List<String> excludeProtocol) {
            this.excludeProtocol = excludeProtocol;
        }
    }
    public static class InsertNotifylog{
        private List<String> excludeProtocol=new ArrayList<>();
        public List<String> getExcludeProtocol() {
            return excludeProtocol;
        }
        public void setExcludeProtocol(List<String> excludeProtocol) {
            this.excludeProtocol = excludeProtocol;
        }
    }
    public static class NotifyRequest{
        private String reqcodeArgname="";
        private String callbackUrl="";
        private Map<String,String > parsers=new HashMap<>();

        public String getReqcodeArgname() {
            return reqcodeArgname;
        }

        public void setReqcodeArgname(String reqcodeArgname) {
            this.reqcodeArgname = reqcodeArgname;
        }

        public Map<String, String> getParsers() {
            return parsers;
        }

        public void setParsers(Map<String, String> parsers) {
            this.parsers = parsers;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public void setCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
        }
    }
    public static class Request{
        private  Sign sign=new Sign();
        private JwtVerify jwtVerify=new JwtVerify();
        private Debug debug=null;
        private List<String> processors=new ArrayList<>();

        public Sign getSign() {
            return sign;
        }

        public void setSign(Sign sign) {
            this.sign = sign;
        }

        public JwtVerify getJwtVerify() {
            return jwtVerify;
        }

        public void setJwtVerify(JwtVerify jwtVerify) {
            this.jwtVerify = jwtVerify;
        }

        public Debug getDebug() {
            return debug;
        }

        public void setDebug(Debug debug) {
            this.debug = debug;
        }

        public List<String> getProcessors() {
            return processors;
        }

        public void setProcessors(List<String> processors) {
            this.processors = processors;
        }
    }
    public static class Debug{
        private String ndjh="";
        private List<String> ipAccessWhitelist=new ArrayList<>();

        public String getNdjh() {
            return ndjh;
        }

        public void setNdjh(String ndjh) {
            this.ndjh = ndjh;
        }

        public List<String> getIpAccessWhitelist() {
            return ipAccessWhitelist;
        }

        public void setIpAccessWhitelist(List<String> ipAccessWhitelist) {
            this.ipAccessWhitelist = ipAccessWhitelist;
        }

    }
    public  static class JwtVerify{
        private Boolean enable=true;
        private String verifyPluginClass="";
        private List<String> excludeProtocol=new ArrayList<>();
        private String secret="";

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getVerifyPluginClass() {
            return verifyPluginClass;
        }

        public void setVerifyPluginClass(String verifyPluginClass) {
            this.verifyPluginClass = verifyPluginClass;
        }

        public List<String> getExcludeProtocol() {
            return excludeProtocol;
        }

        public void setExcludeProtocol(List<String> excludeProtocol) {
            this.excludeProtocol = excludeProtocol;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class Sign{
        private Boolean enable=true;
        private List<String> excludeProtocol=new ArrayList<>();
        private String requestSignKey="";

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public List<String> getExcludeProtocol() {
            return excludeProtocol;
        }

        public void setExcludeProtocol(List<String> excludeProtocol) {
            this.excludeProtocol = excludeProtocol;
        }

        public String getRequestSignKey() {
            return requestSignKey;
        }

        public void setRequestSignKey(String requestSignKey) {
            this.requestSignKey = requestSignKey;
        }
    }
    public static class Protocol{
        private String  packageName="";
        private String namesapce="";
        private String errorClass="";

        public String getNamesapce() {
            return namesapce;
        }

        public void setNamesapce(String namesapce) {
            this.namesapce = namesapce;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getErrorClass() {
            return errorClass;
        }

        public void setErrorClass(String errorClass) {
            this.errorClass = errorClass;
        }
    }
}
