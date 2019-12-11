package controller;

import com.sun.deploy.net.HttpResponse;
import entity.Productinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import serviceImpl.ProductinfoServiceImpl;
import serviceImpl.UserServiceImpl;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
public class ShopCarController {
    @Autowired
    JedisPool jp;
    @Autowired
    ProductinfoServiceImpl psi;
    @Autowired
    UserServiceImpl usi;
    @RequestMapping("/addCar")
    public String addCar(@RequestParam String pid,@RequestParam String username){
        //购物车hmset,key:username
        //value是一个map,map的key是pid,,value是商品数量
        //Productinfo p = psi.selectByPrimaryKey(pid);
        //获取jedis连接
        Jedis jedis = jp.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        //判断用户购物车里有没有商品
//        if(map.isEmpty()){//是否为空, map.put("","")会空指针异常
////             map = new HashMap<String,String>();
//             map.put(pid,"1");
//             jedis.hmset(username,map);
//        }else{//有商品
            //此商品之前加入过购物车了,map里有id了
            if(map.containsKey(pid)){
                map.put(pid,String.valueOf(Integer.parseInt(map.get(pid))+1));
                jedis.hmset(username,map);
            }else{//之前没加过
                map.put(pid,"1");
                jedis.hmset(username,map);
//            }
        }
        return "";
    }


    @RequestMapping("/getCar")
    public List<Productinfo> getCar(@RequestParam String username){
        Jedis jedis = jp.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        List<Productinfo> list  = new ArrayList<>();
        //Productinfo有库存pNum,将其修改成用户点击的
        //map转成list集合
        if(!map.isEmpty()){
            for(Map.Entry<String,String> entry  : map.entrySet()) {
                Productinfo pi = psi.selectByPrimaryKey(Integer.parseInt(entry.getKey()));
                //将库存改为用户选择的
                pi.setpNum(Integer.parseInt(entry.getValue()));
               list.add(pi);
            }
        }
        return list;
    }
    @RequestMapping("/removeItems")
    public void  removeItems(@RequestParam String pid,@RequestParam String username){
        Jedis jedis = jp.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        //不用做非空判断
        map.remove(pid);
        //一定要先删除后塞值,重要!!!
        // 因为redis中的散列表在进行存储值时,只会将客户端上送的hashmap中存在的key在redis中查找对应的key值进行覆盖重写,
        // 至于通过Java代码对该HashMap进行的remove操作在redis中并不会感知到,所以在通过HMSET函数进行操作时,
        // redis只会找到key覆写,不会执行del操作,实际针对redis中的hashmap key的删除只能通过HDEL函数

        jedis.del(username);
        jedis.hmset(username,map);
    }


    @RequestMapping("/reduceItemsNum")
    public void reduceItemsNum(@RequestParam String pnum,@RequestParam String username,@RequestParam String pid){
        Jedis jedis = jp.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        String num = map.get(pid);
        if(Integer.parseInt(num)>1) {
            map.put(pid, String.valueOf(Integer.parseInt(num)-1));
            jedis.hmset(username,map);
        }else{
            jedis.hdel(username,pid);
        }
    }

    @RequestMapping("/addItemsNum")
    public void addItemsNum(@RequestParam String pnum,@RequestParam String username,@RequestParam String pid){
        Jedis jedis = jp.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        String num = map.get(pid);
        map.put(pid, String.valueOf(Integer.parseInt(num)+1));
        jedis.hmset(username,map);
        }

        @RequestMapping("/xieItemsNum")
    public  void xieItemsNum(@RequestParam String pnum,@RequestParam String username,@RequestParam String pid){
            Jedis jedis = jp.getResource();
            Map<String, String> map = jedis.hgetAll(username);
            if(Integer.parseInt(pnum)>0){
            map.put(pid,pnum);
            jedis.hmset(username,map);
            }
        }


//   @RequestMapping("/sendEmail")
    public  void sendEmail(String emailCount, int randomNum, HttpServletRequest req,
                           HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String from="1241215936@qq.com";//你自己的邮箱
        String host="smtp.qq.com";//本机地址
        //Properties可以确定系统的属性,就是为了寻找我们的host
        Properties properties=System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "25");
        properties.put("mail.smtp.auth","true");//开启代理

        Authenticator aut=new Authenticator() {//创建Authenticator内部类来填入代理的用户名前缀和密码shiro
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1241215936","csgomahuuxrobabf");//填用户名和代理密码
            }
        };

        //创建Session会话,Session是java.mail API最高入口
        Session session=Session.getDefaultInstance(properties,aut);
        //MimeMessage获取session对象,就可以创建要发送的邮箱内容和标题
        MimeMessage message=new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));//设置你自己的邮箱
            message.addRecipients(Message.RecipientType.TO, emailCount);//设置接受邮箱
            message.setSubject("验证码");//设置邮箱标题
            message.setText("您本次的验证码是:"+randomNum);//邮箱内容
            Transport.send(message);//发送邮箱

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/selectEmailByUsername")
    public String selectEmailByUsername(@RequestParam String username,
                                        HttpServletRequest httpServletRequest, HttpServletResponse resp) throws IOException {
        String email = usi.selectEmailByUsername(username);
        int randNum=(int)((Math.random()*9+1)*100000);
        sendEmail(email,randNum,httpServletRequest,resp);
        return String.valueOf(randNum);

    }
   //删除库存,生成订单需要用户名
    @RequestMapping("/deleteProductNum")
    public synchronized String deleteProductNum(@RequestParam String username,@RequestParam Integer pid,
                                                @RequestParam Integer pnum){
        Productinfo p = psi.selectByPrimaryKey(pid);
        System.out.println("...."+p.getpNum()+","+pnum);
        if(p.getpNum()>pnum) {
            p.setpNum(p.getpNum() - pnum);
            psi.updateByPrimaryKey(p);
            return "yes";
        }else if(p.getpNum()==pnum) {
            p.setpNum(0);
            p.setStatus(1);
            psi.updateByPrimaryKey(p);
            return "yes";
        }
        return "no";
    }
    }





