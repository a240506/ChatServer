package com.example.chatserver.serve;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.User;
import com.example.chatserver.common.R;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.LoginParam;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*
文件创建于  2022/09/17  17：01
*/
@RestController
public class LoginServe {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendliesServiceImpl friendliesService;
    //登录
    @RequestMapping( value = "/login",method = RequestMethod.POST)
    public R login(HttpServletRequest request,@RequestBody LoginParam params){
        String  captcha=(String)request.getSession().getAttribute("captcha");
        //判断验证码是否正确
        if(!params.getVcCode().equals(captcha)){
            return Tool.result(null,0,"登录失败,验证码错误");
        }
        //Map<String,Object> meta = new HashMap<String,Object>();
        User user=null;
        //用户登录
        if(params.getType().equals("user")){
            user= userService.userLogin(params.getUserName(),params.getPassword());
        //    管理员登录
        }else if(params.getType().equals("admin")){
            user= userService.adminLogin(params.getUserName(),params.getPassword());
        }

        if(user!=null){
            //保存名字
            request.getSession().setAttribute("userName",params.getUserName());
            String  userName=(String)request.getSession().getAttribute("userName");
        //    登录成功
            return Tool.result(user,200,"登录成功");
        }
        return Tool.result(null,0,"登录失败,账号或者密码错误");
    }

    @RequestMapping( value = "/register",method = RequestMethod.POST)
    // 这里注册也用 LoginParam 都一样
    //Map<String, Object> params
    public R register(HttpServletRequest request,@RequestBody Map<String, Object> params) throws IOException {
        String  captcha=(String)request.getSession().getAttribute("captcha");
        //判断验证码是否正确
        //if(!params.getVcCode().equals(captcha)){
        //    return Tool.result(null,0,"登录失败,验证码错误");
        //}
        ////这里判断用户名是否重复
        //if(userService.loadByName(params.getUserName())!=null){
        //    return Tool.result(null,0,"用户名重复");
        //}
        //User user=new User();
        //user.setUserName(params.getUserName());
        //user.setPassword(params.getPassword());
        //user.setType(params.getType());
        //user.setAvatarUrl();
        //TODO 头像地址设置，目前没有搞
        //user.setAvatarUrl("@/assets/image/logo.png");


        //long l= userService.userRegister(user);
        //if(l==1){
        //    return Tool.result(null,200,"注册成功");
        //}

        //System.out.println(params);



        System.out.println((String)params.get("avatarUrl"));
        //图片下载
        Tool.downloadFile((String)params.get("avatarUrl"),"D:\\迅雷下载\\my-chat项目图片文件夹\\images","a.jpg");



        return Tool.result(null,0,"注册失败");
    }
    //判断是否登录过了
    @RequestMapping( value = "/isLoggedIn",method = RequestMethod.GET)
    public R isLoggedIn(HttpServletRequest request){
        String  userName=(String)request.getSession().getAttribute("userName");
        if(userName==null){
            return Tool.result(false,200,"没有登录");
        }else{
            return Tool.result(true,200,userName+"登录了");
        }
    }
    //获取验证码
    //链接后的数字是随机数，保证不会被浏览器缓存，没有其他用途
    @RequestMapping("/captcha/{random}")
    public void  captcha(HttpServletRequest request, HttpServletResponse response, @PathVariable String random) throws IOException {
        //算术类型
        //ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        //中文类型验证吗
        //ChineseCaptcha captcha = new ChineseCaptcha();
        // 英文与数字验证码
        // SpecCaptcha captcha = new SpecCaptcha();
        //英文与数字动态验证码
        //GifCaptcha captcha = new GifCaptcha();
        //中文动态验证码
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha();
        //几位数运算   默认是两位
        //captcha.setLen(2);

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(80, 30);
        String text = captcha.text();// 获取验证码的字符
        //保存验证码到服务器session中
        request.getSession().setAttribute("captcha",text);
        System.out.println("验证码："+text);
        // 输出验证码
        captcha.out(response.getOutputStream());

    }

    /**
     * 获取当前用户的信息
     * @param request
     * @return
     */
    @RequestMapping( value = "/getUserInfo",method = RequestMethod.GET)
    public R grtUserInfo(HttpServletRequest request){
        String  userName=(String)request.getSession().getAttribute("userName");
        User user= userService.loadByName(userName);
        if(user==null){
            return Tool.result(null,0,"获取用户信息失败");
        }
        return Tool.result(user,200,"获取用户信息成功");
    }

    @ResponseBody
    @RequestMapping("/file/upload")
    protected  Map<String,Object> loginDeal(@RequestParam("file") MultipartFile fileUpload){
        //TODO 这里应该判断文件大小的
        //String type = fileUpload.getOriginalFilename().substring(fileUpload.getOriginalFilename().lastIndexOf("."));
        //设置随机的文件名字
        String fileName=Tool.getTimeString()+"_"+(new Random()).nextInt(1000000)+"_"+fileUpload.getOriginalFilename();
        //文件先保存到临时文件夹中
        //TODO 临时文件应该定时删除，一个文件60分钟
        String tmpFilePath =  "D:\\迅雷下载\\my-chat项目图片文件夹\\temp"  ;

        //没有路径就创建路径
        File tmp = new File(tmpFilePath);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        String resourcesPath = tmpFilePath + "\\" + fileName;

        File upFile = new File(resourcesPath);
        try {
            fileUpload.transferTo(upFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("imageUrl","http://localhost:19091/static/temp/"+fileName);
        map.put("physicalPath",resourcesPath);
        map.put("originalFileName",fileUpload.getOriginalFilename());
        return map;
    }

    @RequestMapping("/user/update")
    protected Boolean userUpdate(@RequestBody User user){
        if(1L==userService.update(user)){
            return true;
        }
        return false;
    }

    /**
     * 用户搜索，根据用户民
     * @param params
     * @return
     */
    @RequestMapping("/user/search/userName")
    protected List<User> userSearchUserName(HttpServletRequest request,@RequestBody Map<String, Object> params){
        //TODO 应该把密码隐藏，感觉还有sql注入的漏洞
        List<User> list=userService.loadLikeName("%"+(String) params.get("search")+"%");

        //要排除掉自己，和已经添加好友的用户
        User user= userService.loadByName((String)request.getSession().getAttribute("userName"));
        List<Friendlies> friendlies= friendliesService.loadFriendByUserYAndType(Integer.parseInt(user.getUserId().toString()));
        Iterator<User> iterator=list.iterator();
        while (iterator.hasNext()){
            User u=iterator.next();
            //删除自己
            if(u.getUserId().equals(user.getUserId())){
                iterator.remove();
            }

            //删除好友
            for(Friendlies item:friendlies){
                if(item.getUserX()== Integer.parseInt(user.getUserId().toString())){
                    if(item.getUserY() ==Integer.parseInt(u.getUserId().toString())){
                        iterator.remove();
                        break;
                    }
                }
                if(item.getUserY()== Integer.parseInt(user.getUserId().toString())){
                    if(item.getUserX() ==Integer.parseInt(u.getUserId().toString())){
                        iterator.remove();
                        break;
                    }
                }

            }
        }
        return list;
    }

    /**
     * 获取头像名
     * @return
     */
    @RequestMapping("/avatars")
    public List<String>  getAvatars() {
        File file=new File("D:\\迅雷下载\\my-chat项目图片文件夹\\avatars");
        List<File> list=Tool.getAllFile(file);
        List<String> avatars=new ArrayList<>();
        for(File item :list){
            avatars.add(item.getName());
        }

        return avatars;
    }

    @RequestMapping("/hello")
    public String  hello(HttpServletRequest request, HttpServletResponse response) {

        //String  captcha=(String)request.getSession().getAttribute("userName");
        //System.out.println(captcha);

        return userService.loadByName("test2").toString();
    }








}
