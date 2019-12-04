package controller;

import entity.Productinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import serviceImpl.ProductinfoServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ShopCarController {
    @Autowired
    JedisPool jp;
    @Autowired
    ProductinfoServiceImpl psi;
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

    }





