package com.phantom.controller;

import com.phantom.constant.MenuConsts;
import com.phantom.controller.reqbody.MoveReqBody;
import com.phantom.controller.reqbody.RenameFileReqBody;
import com.phantom.controller.reqbody.RenameFolderReqBody;
import com.phantom.controller.reqbody.ShredReqBody;
import com.phantom.entity.SysUser;
import com.phantom.entity.UserFolder;
import com.phantom.service.DiskService;
import com.phantom.service.UserService;
import com.phantom.service.dto.UserDTO;
import com.phantom.service.dto.UserFileDTO;
import com.phantom.service.dto.UserFolderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.controller
 * @Description:
 * @ModifiedBy:
 */
@RestController
public class HomeAction {

    @Autowired
    private UserService userService;
    @Autowired
    private DiskService diskService;


    /**
     * 功能：获取用户不同菜单下的文件（及文件夹）列表
     * 示例：GET users/admin/photo，获取admin用户的所有照片
     */
    @RequestMapping(value = "/users/{username}/{menu}", method = RequestMethod.GET)
    public Map<String, Object> getMenu(@PathVariable String username, @PathVariable String menu) {
        // 检查menu参数是否合法
        if (!MenuConsts.getConsts().contains(menu)) {
            throw new IllegalArgumentException("Illegal argument: " + menu);
        }

        int userId = ((SysUser)userService.getUser(username)).getId();
        return diskService.getMenuContents(userId, menu);
    }

    /**
     * 功能：获取某个文件夹的内容
     * 示例：GET users/admin/disk/folders/55?sort=1，获取admin用户网盘内folderID=55的文件夹内容，以最后修改时间排序
     *
     * @param sort sort=1按时间排序，sort=其它值 按中文拼音排序
     */
    @RequestMapping(value = "/users/{username}/disk/folders/{folderId}", method = RequestMethod.GET)
    public Map<String, Object> getFolderContents(
            @PathVariable String username, @PathVariable Integer folderId, @RequestParam(defaultValue = "0") int sort) {
        // TODO 参数校验
        int userId = userService.getUser(username).getId();
        return diskService.getFolderContents(userId, folderId, sort);
    }

    /**
     * 功能：搜索用户网盘内的文件
     * 示例：GET users/admin/disk/search?input=txt，搜索admin用户网盘内文件名含“txt”的文件及文件夹
     */
    @RequestMapping(value = "/users/{username}/disk/search", method = RequestMethod.GET)
    public Map<String, Object> search(@PathVariable String username, @RequestParam("input") String input) {
        int userId = userService.getUser(username).getId();
        Map<String, Object> result = diskService.search(userId, input);
        return result;
    }

    /**
     * 功能：批量移动文件及文件夹
     * 示例：PATCH users/admin/disk/move，请求体：{"files":[1,2],"folders":[3,4],"dest":5}，
     * 将ID=1,2的文件及ID=3,4的文件夹移动到ID=5的文件夹中
     */
    @RequestMapping(value = "/users/{username}/disk/move", method = RequestMethod.PATCH)
    public Map<String, Object> move(@PathVariable String username, @RequestBody @Valid MoveReqBody reqBody) {
        // int userId = userService.getUser(username).getId();
        // TODO 参数校验
        return diskService.move(reqBody.getFolders(), reqBody.getFiles(), reqBody.getDest());
    }

    /**
     * 功能：重命名文件夹
     * 示例：PATCH users/admin/disk/folders/123，请求体：{"folderName":"rename_test"}，
     * 把ID=123的文件夹重命名为rename_test
     */
    @RequestMapping(value = "/users/{username}/disk/folders/{folderId}", method = RequestMethod.PATCH)
    public UserFolderDTO renameFolder(@PathVariable String username, @PathVariable int folderId
            , @RequestBody @Valid RenameFolderReqBody reqBody) {
        // int userId = userService.getUser(username).getId();
        // TODO 参数校验
        return diskService.renameFolder(folderId, reqBody.getFolderName());
    }

    /**
     * 功能：重命名文件
     * 示例：PATCH users/admin/disk/files/123，请求体：{"fileName":"rename","fileType":"jpg"}，
     * 把ID=123的文件重命名为rename.jpg
     */
    @RequestMapping(value = "/users/{username}/disk/files/{fileId}", method = RequestMethod.PATCH)
    public UserFileDTO renameFile(@PathVariable String username, @PathVariable int fileId
            , @RequestBody @Valid RenameFileReqBody reqBody) {
        // int userId = userService.getUser(username).getId();
        // TODO 参数校验
        return diskService.renameFile(fileId, reqBody.getFileName(), reqBody.getFileType());
    }

    /**
     * 功能：新建文件夹
     * 示例：POST users/admin/disk/folders，请求体：{"folderName":"新建文件夹","parentId":5}，
     * 在ID=5的文件夹下新建一个名字为“新建文件夹”的文件夹
     */
    @RequestMapping(value = "/users/{username}/disk/folders", method = RequestMethod.POST)
    public UserFolderDTO newFolder(@PathVariable String username, @RequestBody @Valid UserFolder reqBody) {
        // TODO 参数校验
        int userId = userService.getUser(username).getId();
        reqBody.setUserId(userId);

        return diskService.newFolder(reqBody);
    }

    /**
     * 功能：彻底删除回收站的文件
     * 示例：DELETE users/admin/recycle，请求体：{"files":[11,22],"folders":[33,44]}，
     * 删除ID=11,22的文件和ID=33,44的文件夹，并更新用户已用空间
     */
    @RequestMapping(value = "/users/{username}/recycle", method = RequestMethod.DELETE)
    public UserDTO shred(@PathVariable String username
            , @RequestBody @Valid ShredReqBody reqBody) {
        int userId = userService.getUser(username).getId();
        // TODO 参数校验
        return diskService.shred(reqBody.getFolders(), reqBody.getFiles(), userId);
    }
}
