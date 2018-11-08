package com.bbt.authorization.constants;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 15:14
 */
public enum TokenClearType {

    LOGOUT_PASSIVE(1, "已在其他设备登录"), // 被动退出，即在其他设备登录
    MANUAL_FREEZE(2, "该账户已被冻结"), // 人为冻结（管理员冻结）
    FREQUENT_OPT(3, "该账户操作频繁，已被冻结"); // 操作频繁，冻结

    private int val;
    private String message;

    TokenClearType(int val, String message) {
        this.val = val;
        this.message = message;
    }

    public int getVal() {
        return val;
    }

    public String getMessage() {
        return message;
    }

    public static TokenClearType getEnum(int val) {
        TokenClearType[] values = TokenClearType.values();
        for (TokenClearType tokenClearType : values) {
            if (tokenClearType.val == val) {
                return tokenClearType;
            }
        }
        return null;
    }
}
