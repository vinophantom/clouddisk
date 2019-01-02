package com.phantom.controller;

import com.phantom.controller.reqbody.UploadReqBody;
import com.phantom.entity.OriginFile;
import com.phantom.entity.UserFile;
import com.phantom.service.DownloadService;
import com.phantom.service.UploadService;
import com.phantom.service.UserService;
import com.phantom.service.impl.FileService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/12
 * @Package: com.phantom.controller
 * @Description:
 * @ModifiedBy:
 */
@RestController
@MultipartConfig
//@RequestMapping("/file/")
//@RequestMapping("/file/")
public class FileAction {

    @Resource
    private UserService userService;
    @Resource
    private UploadService uploadService;
    @Resource
    private DownloadService downloadService;
    @Resource
    private ModelMapper modelMapper;

    private static final String TMP_FILE_PATH = "e:\\test\\";

    private static Log logger = LogFactory.getLog(FileAction.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(method = {RequestMethod.POST}, value = {"/webUploader"})
    @ResponseBody
    public void webUploader(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            String lsh = "";
            if (isMultipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                // 得到所有的表单域，它们目前都被当作FileItem
                List<FileItem> fileItems = upload.parseRequest(request);
                String id = "";
                String fileName = "";
                // 如果大于1说明是分片处理
                int chunks = 1;
                int chunk = 0;
                FileItem tempFileItem = null;
                for (FileItem fileItem : fileItems) {
                    if (fileItem.getFieldName().equals("id")) {
                        id = fileItem.getString();
                    } else if (fileItem.getFieldName().equals("name")) {
                        fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("chunks")) {
                        chunks = NumberUtils.toInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("chunk")) {
                        chunk = NumberUtils.toInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("multiFile")) {
                        tempFileItem = fileItem;
                    } else if (fileItem.getFieldName().equals("lsh")) {
                        lsh = fileItem.getString();
                    }
                }
                //session中的参数设置获取是我自己的原因,文件名你们可以直接使用fileName,这个是原来的文件名
                //String fileSysName = request.getSession().getAttribute("fileSysName").toString();
                //String realname = fileName.substring(fileName.lastIndexOf("."));//转化后的文件名
                String realname = fileName;//转化后的文件名
                session.setAttribute("realname",realname);
                String filePath = TMP_FILE_PATH + "\\" + lsh + "\\";//文件上传路径
                // 临时目录用来存放所有分片文件
                String tempFileDir = filePath + id;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
                File tempPartFile = new File(parentFileDir, realname + "_" + chunk + ".part");
                System.out.println("parentFileDir============" + parentFileDir);
                FileUtils.copyInputStreamToFile(tempFileItem.getInputStream(), tempPartFile);
                // 是否全部上传完成
                // 所有分片都存在才说明整个文件上传完成
                boolean uploadDone = true;
                for (int i = 0; i < chunks; i++) {
                    File partFile = new File(parentFileDir, realname + "_" + i + ".part");
                    if (!partFile.exists()) {
                        uploadDone = false;
                        continue;
                    }
                }
                // 所有分片文件都上传完成
                // 将所有分片文件合并到一个文件中
                if (uploadDone) {
                    // 得到 destTempFile 就是最终的文件
                    String destFilePath = filePath;
                    File destFolder = new File(destFilePath);
                    if (!destFolder.exists()) {
                        destFolder.mkdirs();
                    }
                    File destTempFile = new File(destFilePath, realname);
                    System.out.println("destTempFile========" + destTempFile);
                    for (int i = 0; i < chunks; i++) {
                        File partFile = new File(parentFileDir, realname + "_" + i + ".part");
                        FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                        //遍历"所有分片文件"到"最终文件"中
                        FileUtils.copyFile(partFile, destTempfos);
                        destTempfos.close();
                    }
                    // 删除临时目录中的分片文件
                    FileUtils.deleteDirectory(parentFileDir);
                } else {
                    // 临时文件创建失败
                    if (chunk == chunks -1) {
                        FileUtils.deleteDirectory(parentFileDir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping("uploadPicture")
    @ResponseBody
    public void uploads(@RequestParam("file")MultipartFile[] files, String destDir,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            fileService.uploads(files, destDir, request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分块文件的大小，与前端保持一致
     */
    private static final long MAX_CHUNK_SIZE = 5 * 1024 * 1024;

    /**
     * 功能：接收分块上传的文件块
     * 示例：POST users/admin/disk/files
     */
    @RequestMapping(value = "/users/{username}/disk/files", method = RequestMethod.POST)
    @ResponseBody
    //public Map<String, Object> upload(HttpServletRequest req, @RequestPart("files[]") Part part, @Valid UploadReqBody reqbody) throws IOException, ServletException {
    public Map<String, Object> upload(HttpServletRequest req, @Valid UploadReqBody reqbody) throws IOException, ServletException {
        // TODO 参数校验

        Part part = req.getPart("files[]");
        System.out.println("part============ " + part);
        System.out.println(req.getHeader("content-range"));
        String contentRange = req.getHeader("content-range");
        // 检查云盘存储空间是否足够
        long size;
        if (contentRange != null) {
            size = Long.parseLong(contentRange.split("/")[1]);
        } else {
            size = part.getSize();
        }
        if (!userService.isUserStorageEnough(reqbody.getUserId(), size)) {
            throw new IllegalArgumentException("Not enough storage to upload this file");
        }

        // 处理没有Content-Range请求头的小文件
        if (contentRange == null && part.getSize() <= MAX_CHUNK_SIZE) {
            UserFile userFile = modelMapper.map(reqbody, UserFile.class);
            return uploadService.serveSmallFile(part, reqbody.getFileMd5(), userFile);
        }

        if (isFirstPart(contentRange)) {
            UserFile localFile = modelMapper.map(reqbody, UserFile.class);
            return uploadService.serveFirstPart(part, reqbody.getFileMd5(), localFile);
        } else if (isLastPart(contentRange)) {
            OriginFile file = new OriginFile();
            file.setFileMd5(reqbody.getFileMd5());
            file.setFileType(part.getHeader("content-type"));
            file.setFileSize(size);
            UserFile userFile = modelMapper.map(reqbody, UserFile.class);
            uploadService.savePart(part, reqbody.getFileMd5());
            return uploadService.serveLastPart(userFile, file);
        } else {
            uploadService.savePart(part, reqbody.getFileMd5());
            Map<String, Object> result = new HashMap<>();
            result.put("test", "test");
            return result;
        }
    }

    /**
     * 功能：取消上传
     * 示例：DELETE users/admin/disk/files?cancel=abcd，取消上传md5值为“abcd”的文件
     */
    @RequestMapping(value = "/users/{username}/disk/files", method = RequestMethod.DELETE, params = "cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelUpload(@RequestParam String cancel) throws InterruptedException {
        // TODO 参数校验
        uploadService.cancel(cancel);
    }

    /**
     * 功能：获取上传到一半的文件断点
     * 示例：GET users/admin/disk/files?resume=abcd，获取md5值为“abcd”的文件的断点
     */
    @RequestMapping(value = "/users/{username}/disk/files", method = RequestMethod.GET, params = "resume")
    public Long resumeUpload(@RequestParam String resume) {
        // TODO 参数校验
        return uploadService.resume(resume);
    }

    /**
     * 功能：下载
     * 示例：GET users/admin/disk/files?files=1,2&folders=3,4，打包下载ID=1,2的文件和ID=3,4的文件夹
     */
    @RequestMapping(value = "/users/{username}/disk/files", method = RequestMethod.GET, params = {"files", "folders"})
    public void download(@PathVariable String username, @RequestParam List<Integer> files, @RequestParam List<Integer> folders, HttpServletResponse response) throws IOException {
        int userId = userService.getUser(username).getId();
        // TODO 参数校验
        if (files.size() + folders.size() == 0) {
            throw new IllegalArgumentException("params must contain at least one file or folder");
        }

        String filename = downloadService.generateZipFileName(files, folders);
        filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);

        downloadService.download(userId, files, folders, response.getOutputStream());
    }


    /**
     * 根据Content-Range请求头(如：bytes 0-9999/312329)，判断文件块是否是首个块（chunk）
     */
    private boolean isFirstPart(String contentRange) {
        return contentRange.charAt(contentRange.indexOf(' ') + 1) == '0';
    }

    /**
     * 根据Content-Range请求头(如：bytes 310000-312328/312329)，判断文件块是否是最后一个块（chunk）
     */
    private boolean isLastPart(String contentRange) {
        String[] validPart = contentRange.substring(contentRange.indexOf('-') + 1).split("/");
        int current = Integer.parseInt(validPart[0]);
        int size = Integer.parseInt(validPart[1]);
        return size - 1 == current;
    }


}
