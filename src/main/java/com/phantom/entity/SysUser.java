package com.phantom.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/2
 * @Package: com.phantom.entity
 * @Description:
 * @ModifiedBy:
 */
@Entity
@Table(name = "sys_user", schema = "clouddisk", catalog = "")
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private int valid;
    private Date createdTime;
    private Date modifiedTime;
    private Integer createdUser;
    private Integer modifiedUser;
    private long memorySize;
    private long usedSize;

    public SysUser(int id, String username, String password, String salt, String email, String mobile, int valid, Date createdTime, Date modifiedTime, Integer createdUser, Integer modifiedUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.mobile = mobile;
        this.valid = valid;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.createdUser = createdUser;
        this.modifiedUser = modifiedUser;
    }

    public SysUser() {
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", valid=" + valid +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", createdUser='" + createdUser + '\'' +
                ", modifiedUser='" + modifiedUser + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUser sysUser = (SysUser) o;

        if (valid != sysUser.valid) return false;
        if (memorySize != sysUser.memorySize) return false;
        if (usedSize != sysUser.usedSize) return false;
        if (id != null ? !id.equals(sysUser.id) : sysUser.id != null) return false;
        if (username != null ? !username.equals(sysUser.username) : sysUser.username != null) return false;
        if (password != null ? !password.equals(sysUser.password) : sysUser.password != null) return false;
        if (salt != null ? !salt.equals(sysUser.salt) : sysUser.salt != null) return false;
        if (email != null ? !email.equals(sysUser.email) : sysUser.email != null) return false;
        if (mobile != null ? !mobile.equals(sysUser.mobile) : sysUser.mobile != null) return false;
        if (createdTime != null ? !createdTime.equals(sysUser.createdTime) : sysUser.createdTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(sysUser.modifiedTime) : sysUser.modifiedTime != null)
            return false;
        if (createdUser != null ? !createdUser.equals(sysUser.createdUser) : sysUser.createdUser != null) return false;
        return modifiedUser != null ? modifiedUser.equals(sysUser.modifiedUser) : sysUser.modifiedUser == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + valid;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + (createdUser != null ? createdUser.hashCode() : 0);
        result = 31 * result + (modifiedUser != null ? modifiedUser.hashCode() : 0);
        result = 31 * result + (int) (memorySize ^ (memorySize >>> 32));
        result = 31 * result + (int) (usedSize ^ (usedSize >>> 32));
        return result;
    }

    @Id
    @Column(name = "id")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "valid")
    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Basic
    @Column(name = "createdTime")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "modifiedTime")
    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Basic
    @Column(name = "createdUser")
    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "modifiedUser")
    public Integer getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Basic
    @Column(name = "memory_size")
    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    @Basic
    @Column(name = "used_size")
    public long getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(long usedSize) {
        this.usedSize = usedSize;
    }
}
