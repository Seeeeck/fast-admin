package pers.syq.fastadmin.backstage.constants;

public class WebConstants {
    public static final String CONTENT_TYPE_JSON = "text/json;charset=utf-8";
    /**
     * Captcha expiration time(MINUTES)
     */
    public static final Integer CAPTCHA_EXPIRATION = 5;

    public static final String DEFAULT_AVATAR = "http://img.72qq.com/file/202102/22/a99922af3c.jpg";

    private WebConstants(){}

    public enum ScheduleStatus{
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
