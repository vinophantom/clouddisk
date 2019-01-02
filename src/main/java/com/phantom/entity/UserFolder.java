package com.phantom.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.entity
 * @Description:
 * @ModifiedBy:
 */
@Entity
@Table(name = "user_folder", schema = "clouddisk", catalog = "")
public class UserFolder {
    private Integer folderId;
    private int userId;
    private int parentId;
    private String folderName;
    private Date createTime;
    private Date modifyTime;
    private Date deleteTime;

    @Id
    @Column(name = "folder_id")
    public Integer getFolderId() {
        return folderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFolder that = (UserFolder) o;

        if (userId != that.userId) return false;
        if (parentId != that.parentId) return false;
        if (folderId != null ? !folderId.equals(that.folderId) : that.folderId != null) return false;
        if (folderName != null ? !folderName.equals(that.folderName) : that.folderName != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        return deleteTime != null ? deleteTime.equals(that.deleteTime) : that.deleteTime == null;
    }

    @Override
    public int hashCode() {
        int result = folderId != null ? folderId.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + parentId;
        result = 31 * result + (folderName != null ? folderName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        return result;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;

    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "parent_id")
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "folder_name")
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

}
