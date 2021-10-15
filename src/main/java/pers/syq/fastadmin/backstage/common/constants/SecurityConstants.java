package pers.syq.fastadmin.backstage.common.constants;


public final class SecurityConstants {

    /**
     * 角色的key
     **/
    public static final String PERMISSION_CLAIMS = "permission";
    /**
     * Token过期时间(s)
     */
    public static final long TOKEN_EXPIRATION = 2 * 60 * 60;

    /**
     * Token刷新时间(s)
     */
    public static final long TOKEN_REFRESH_TIME = 60 * 60;

    public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";


    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static final String JWT_SECRET_KEY = "example";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String REDIS_TOKEN_PREFIX = "token";

    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            //knife4j
            "/doc.html"
    };


    // System WHITELIST
    public static final String[] SYSTEM_WHITELIST = {
            "/sys/user/login",
            "/sys/user/logout",
            "/sys/captcha"
    };

    public static final String[] STATIC_WHITELIST = {
            "/index.html",
            "/static/**",
            "/"
    };

    private SecurityConstants() {
    }

}
