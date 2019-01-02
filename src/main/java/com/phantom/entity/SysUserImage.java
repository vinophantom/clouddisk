package com.phantom.entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.entity
 * @Description:
 * @ModifiedBy:
 */
@Entity
@Table(name = "sys_user_image", schema = "clouddisk", catalog = "")
public class SysUserImage {
    private Integer id;
    private Integer userid;
    private byte[] content;
    private String md5;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUserImage image = (SysUserImage) o;

        if (id != null ? !id.equals(image.id) : image.id != null) return false;
        if (userid != null ? !userid.equals(image.userid) : image.userid != null) return false;
        if (!Arrays.equals(content, image.content)) return false;
        return md5 != null ? md5.equals(image.md5) : image.md5 == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        return result;
    }

    public void setId(Integer id) {
        this.id = id;

    }

    @Basic
    @Column(name = "userid")
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "content")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "md5")
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}
