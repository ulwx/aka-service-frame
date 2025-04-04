package com.github.ulwx.aka.frame;

import com.ulwx.tool.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.*;


@ConfigurationProperties(AkaFrameProperties.FRAME_PROPERTIES_PREFX)
public class AkaFrameProperties implements InitializingBean {
    public final static String  FRAME_PROPERTIES_PREFX="aka.frame";
    @NestedConfigurationProperty
    private Map<String,ProtocolProperties> protocols=new LinkedHashMap<>();
    private Map<String, Handler> handlers=new HashMap<>();
    private String dsName;


    public Map<String, ProtocolProperties> getProtocols() {
        return protocols;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public void setProtocols(Map<String, ProtocolProperties> protocols) {
        this.protocols = protocols;
    }

    public Map<String, Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void afterPropertiesSet()  {
    }
    public static class Database{
        private Boolean enbale=null;
        public Boolean getEnbale() {
            if(enbale==null ){
                return true;
            }
            return enbale;
        }

        public void setEnbale(Boolean enbale) {
            this.enbale = enbale;
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

    public static class Handler {
        private SignVerify signVerify=new SignVerify();
        private JwtVerify jwtVerify=new JwtVerify();
        private Debug debug=null;
        private List<String> processors=new ArrayList<>();
        @NestedConfigurationProperty
        private Storage storage=new Storage();

        public SignVerify getSignVerify() {
            return signVerify;
        }

        public void setSignVerify(SignVerify signVerify) {
            this.signVerify = signVerify;
        }

        public Storage getStorage() {
            return storage;
        }

        public void setStorage(Storage storage) {
            this.storage = storage;
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
        private String paramIn="";
        private String paramName="";


        public String getParamIn() {
            return paramIn;
        }

        public void setParamIn(String paramIn) {
            this.paramIn = paramIn;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

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

    public static class SignVerify {
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
        private String namesapce="";
        private  String handler="";
        private String protocolBuilderClass="";

        public String getProtocolBuilderClass() {
            return protocolBuilderClass;
        }

        public void setProtocolBuilderClass(String protocolBuilderClass) {
            this.protocolBuilderClass = protocolBuilderClass;
        }

        public String getNamesapce() {
            return namesapce;
        }

        public void setNamesapce(String namesapce) {
            this.namesapce = namesapce;
        }



        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }
    }
}
