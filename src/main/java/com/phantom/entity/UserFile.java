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
@Table(name = "user_file", schema = "clouddisk", catalog = "")
public class UserFile {
    private Integer fileId;
    private int userId;
    private int parentId;
    private int originId;
    private String fileName;
    private String fileType;
    private Byte fileStatus;
    private Date createTime;
    private Date modifyTime;
    private Date deleteTime;

    @Override
    public String toString() {
        return "UserFile{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", originId=" + originId +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileStatus=" + fileStatus +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", deleteTime=" + deleteTime +
                '}';
    }

    @Id
    @Column(name = "file_id")
    public Integer getFileId() {
        return fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFile userFile = (UserFile) o;

        if (userId != userFile.userId) return false;
        if (parentId != userFile.parentId) return false;
        if (originId != userFile.originId) return false;
        if (fileId != null ? !fileId.equals(userFile.fileId) : userFile.fileId != null) return false;
        if (fileName != null ? !fileName.equals(userFile.fileName) : userFile.fileName != null) return false;
        if (fileType != null ? !fileType.equals(userFile.fileType) : userFile.fileType != null) return false;
        if (fileStatus != null ? !fileStatus.equals(userFile.fileStatus) : userFile.fileStatus != null) return false;
        if (createTime != null ? !createTime.equals(userFile.createTime) : userFile.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(userFile.modifyTime) : userFile.modifyTime != null) return false;
        return deleteTime != null ? deleteTime.equals(userFile.deleteTime) : userFile.deleteTime == null;
    }

    @Override
    public int hashCode() {
        int result = fileId != null ? fileId.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + parentId;
        result = 31 * result + originId;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (fileStatus != null ? fileStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (deleteTime != null ? deleteTime.hashCode() : 0);
        return result;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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
    @Column(name = "origin_id")
    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "file_status")
    public Byte getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Byte fileStatus) {
        this.fileStatus = fileStatus;
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
