package com.bbt.user.domain;

import java.io.Serializable;

public class UserDO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.account
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.pwd
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    private String pwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.account
     *
     * @return the value of user.account
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.account
     *
     * @param account the value for user.account
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.pwd
     *
     * @return the value of user.pwd
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.pwd
     *
     * @param pwd the value for user.pwd
     *
     * @mbg.generated Fri Nov 09 16:54:47 CST 2018
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }
}