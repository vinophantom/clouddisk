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
@Table(name = "origin_file", schema = "clouddisk", catalog = "")
public class OriginFile {
    private Integer originFileId;
    private String fileMd5;
    private long fileSize;
    private String fileType;
    private String fileUrl;
    private Byte storageMode;
    private String hdfsPath;
    private String hbaseRowkey;
    private Byte status;
    private Integer fileCount;
    private Byte fileStatus;
    private Date createTime;
    private Date modifyTime;

    @Id
    @Column(name = "origin_file_id")
    public Integer getOriginFileId() {
        return originFileId;
    }

    public void setOriginFileId(Integer originFileId) {
        this.originFileId = originFileId;
    }

    @Basic
    @Column(name = "file_md5")
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Basic
    @Column(name = "file_size")
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
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
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "storage_mode")
    public Byte getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(Byte storageMode) {
        this.storageMode = storageMode;
    }

    @Basic
    @Column(name = "hdfs_path")
    public String getHdfsPath() {
        return hdfsPath;
    }

    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath;
    }

    @Basic
    @Column(name = "hbase_rowkey")
    public String getHbaseRowkey() {
        return hbaseRowkey;
    }

    public void setHbaseRowkey(String hbaseRowkey) {
        this.hbaseRowkey = hbaseRowkey;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "file_count")
    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OriginFile that = (OriginFile) o;

        if (originFileId != that.originFileId) return false;
        if (fileSize != that.fileSize) return false;
        if (fileMd5 != null ? !fileMd5.equals(that.fileMd5) : that.fileMd5 != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;
        if (storageMode != null ? !storageMode.equals(that.storageMode) : that.storageMode != null) return false;
        if (hdfsPath != null ? !hdfsPath.equals(that.hdfsPath) : that.hdfsPath != null) return false;
        if (hbaseRowkey != null ? !hbaseRowkey.equals(that.hbaseRowkey) : that.hbaseRowkey != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (fileCount != null ? !fileCount.equals(that.fileCount) : that.fileCount != null) return false;
        if (fileStatus != null ? !fileStatus.equals(that.fileStatus) : that.fileStatus != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originFileId;
        result = 31 * result + (fileMd5 != null ? fileMd5.hashCode() : 0);
        result = 31 * result + (int) (fileSize ^ (fileSize >>> 32));
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        result = 31 * result + (storageMode != null ? storageMode.hashCode() : 0);
        result = 31 * result + (hdfsPath != null ? hdfsPath.hashCode() : 0);
        result = 31 * result + (hbaseRowkey != null ? hbaseRowkey.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (fileCount != null ? fileCount.hashCode() : 0);
        result = 31 * result + (fileStatus != null ? fileStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }
}
