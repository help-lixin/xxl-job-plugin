package help.lixin.xxl.job.properties;

import help.lixin.xxl.job.plugin.IAppNameProcess;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@RefreshScope
@ConfigurationProperties(prefix = XxlJobProperties.XXL_JOB_PREFIX)
public class XxlJobProperties implements ApplicationContextAware {

    public static final String XXL_JOB_PREFIX = "xxl.job.info";

    public static final Boolean ENABLED = Boolean.TRUE;
    /**
     * 执行器生效情况 默认为true
     */
    @Value("${xxl.job.enabled:false}")
    private Boolean enabled = XxlJobProperties.ENABLED;
    /**
     * 调度中心的部署地址,若调度中心采用集群部署，存在多个地址，则用逗号分隔
     */
    @Value("${xxl.job.admin.addresses:}")
    private String adminAddresses;
    /**
     * xxl-job-admin 账号
     */
    @Value("${xxl.job.admin.username:admin}")
    private String username;
    /**
     * xxl-job-admin 密码
     */
    @Value("${xxl.job.admin.password:123456}")
    private String password;
    /**
     * 执行器通讯TOKEN 非空时启用
     **/
    @Value("${xxl.job.accessToken:}")
    private String accessToken;
    /**
     * 执行器的应用名称,它是执行器心跳注册的分组依据
     */
    @Value("${xxl.job.executor.appName:${spring.application.name}}")
    private String appName;
    /**
     * 执行器名称 中文名
     */
    @Value("${xxl.job.executor.desc:}")
    private String appDesc;
    /**
     * 执行器注册地址：优先使用该配置作为注册地址 如果为空的话，则使用 ip:port
     */
    @Value("${xxl.job.executor.address:#{null}}")
    private String address;
    /**
     * 执行器注册ip
     */
    @Value("${xxl.job.executor.ip:#{null}}")
    private String ip;
    /**
     * 执行器注册端口
     */
    @Value("${xxl.job.executor.port:9977}")
    private int port;
    /**
     * 执行器运行日志文件存储磁盘路径 :需要对该路径拥有读写权限；为空则使用默认路径；
     */
    @Value("${xxl.job.executor.logpath:}")
    private String logPath;
    /**
     * 执行器日志文件保存天数 : 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能
     */
    @Value("${xxl.job.executor.logretentiondays:30}")
    private int logRetentionDays;


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getAdminAddresses() {
        return adminAddresses;
    }

    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appname) {
        this.appName = appname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public int getLogRetentionDays() {
        return logRetentionDays;
    }

    public void setLogRetentionDays(int logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String title) {
        this.appDesc = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "XxlJobProperties{" +
                "enabled=" + enabled +
                ", adminAddresses='" + adminAddresses + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", address='" + address + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", logPath='" + logPath + '\'' +
                ", logRetentionDays=" + logRetentionDays +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;
}
