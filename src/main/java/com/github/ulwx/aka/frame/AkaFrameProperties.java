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
    private List<ProtocolProperties> protocols=new ArrayList<>();
    private String globalErrorClass="";
    private Map<String, RequestHandler> handlers=new HashMap<>();
    public List<ProtocolProperties> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolProperties> protocols) {
        this.protocols = protocols;
    }

    public String getGlobalErrorClass() {
        return globalErrorClass;
    }

    public void setGlobalErrorClass(String globalErrorClass) {
        this.globalErrorClass = globalErrorClass;
    }


    public Map<String, RequestHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, RequestHandler> handlers) {
        this.handlers = handlers;
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
        private LogConfig logConfig=new LogConfig();
        @NestedConfigurationProperty
        private Database database=new Database();

        public LogConfig getLogConfig() {
            return logConfig;
        }

        public void setLogConfig(LogConfig logConfig) {
            this.logConfig = logConfig;
        }

        public Database getDatabase() {
            return database;
        }

        public Database getDatabse() {
            return database;
        }

        public void setDatabase(Database databse) {
            this.database = databse;
        }
    }
    public static class LogConfig{
        private List<String> excludeProtocol=new ArrayList<>();
        public List<String> getExcludeProtocol() {
            return excludeProtocol;
        }
        public void setExcludeProtocol(List<String> excludeProtocol) {
            this.excludeProtocol = excludeProtocol;
        }
    }

    public static class RequestHandler {
        private  Sign sign=new Sign();
        private JwtVerify jwtVerify=new JwtVerify();
        private Debug debug=null;
        private List<String> processors=new ArrayList<>();
        @NestedConfigurationProperty
        private Storage storage=new Storage();

        public Sign getSign() {
            return sign;
        }

        public Storage getStorage() {
            return storage;
        }

        public void setStorage(Storage storage) {
            this.storage = storage;
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
        private Boolean enable=false;

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

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
        private Boolean enable=false;
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
        private Boolean enable=false;
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
    public static class ProtocolProperties {
        private String  packageName="";
        private String namesapce="";
        private String errorClass="";
        private  String handler="";

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

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }
    }
}
