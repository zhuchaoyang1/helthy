package config;

/**
 * heathy-server-redis模块所有状态枚举
 */
public enum HttpStatus {

    REDIS_NO_VALUE_BY_KEY(-1, "No Value for hash key");

    HttpStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;
    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
