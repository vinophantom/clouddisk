package com.phantom.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/13
 * @Package: com.phantom.service.impl
 * @Description:
 * @ModifiedBy:
 */
@Service
public class FileService {
    public void uploads(MultipartFile[] files, String destDir,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        //User user = (User)request.getSession().getAttribute("user");
        String path = request.getContextPath();
        String[] fileNames;
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + path;
        // 获取文件上传的真实路径
        //String uploadPath = request.getSession().getServletContext().getRealPath("/");
        String uploadPath = "e:\\test\\";
        List<String> picPaths = new ArrayList<String>();
        try {
            fileNames = new String[files.length];
            int index = 0;
            //上传文件过程
            for (MultipartFile file : files) {
                String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//                int length = getAllowSuffix().indexOf(suffix);
//                if (length == -1) {
//                    throw new Exception("请上传允许格式的文件");
//                }
                destDir = "staticResource/user/picture/";
                File destFile = new File(uploadPath + destDir);
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew() + "." + suffix;//
                File f = new File(destFile.getAbsoluteFile() + File.separator + fileNameNew);
                //如果当前文件已经存在了，就跳过。
                if(f.exists()){
                    continue;
                }
                file.transferTo(f);
                f.createNewFile();
                fileNames[index++] = basePath + destDir + fileNameNew;
            }
//            //个人作品展示
//            if(user.getUserType() == 0){
//                Resume resume = resumeDao.findResumeByUserId(user.getId());
//                String resumeRank = resume.getResumeRank();
//                //若不存在，添加上“作品展示”，并更新
//                if(resumeRank.indexOf(":zpzs") == -1){
//                    resumeRank = resumeRank+":zpzs";
//                    resume.setResumeRank(resumeRank);
//                    resumeDao.updateResume(resume);
//                }
//                request.getSession().setAttribute("user", user);
//            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     * @return string
     */
    private String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }
}
