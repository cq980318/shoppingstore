package controller;

import entity.Productinfo;
import entity.WebInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import serviceImpl.ProductinfoServiceImpl;
import serviceImpl.WebInfoServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class NavigatorAndShowProductsController {
    @Autowired
    ProductinfoServiceImpl psi;
    @Autowired
    WebInfoServiceImpl wsi;

    @RequestMapping("/selectAllP_type")
    public List<String> selectAllP_type(){
        return psi.selectAllP_type();
    }

    @RequestMapping("/selectAllProductsByP_type")
    public List<Productinfo> selectAllProductsByP_type(@RequestParam String p_type,@RequestParam Integer page){
        return psi.selectAllProductsByP_type(p_type,page);
    }

    @RequestMapping("/selectAllProductsP_type")
    public List<Productinfo> selectAllProductsP_type(@RequestParam String p_type){
        return psi.selectAllProductsP_type(p_type);
    }

    @RequestMapping("/selectTotal")
    public int selectTotal(@RequestParam String p_type){
        return psi.selectTotal(p_type);
    }

    @RequestMapping("/getproductinfo")
    public  Productinfo getproductinfo(@RequestParam Integer pId){
        return psi.selectByPrimaryKey(pId);
    }

    @RequestMapping("/getWebData")
    public List<WebInfo> getWebData(){
        return wsi.selectAll();
    }

    @RequestMapping("/selectAll")
    public List<Productinfo> selectAll(){
        return psi.selectAll();
    }

    @RequestMapping("/down")
    public void down(@RequestParam Integer pId){
        Productinfo pi = psi.selectByPrimaryKey(pId);
        pi.setStatus(1);
        psi.updateByPrimaryKey(pi);
    }

    @RequestMapping("/up")
    public void up(@RequestParam Integer pId){
        Productinfo pi = psi.selectByPrimaryKey(pId);
        pi.setStatus(0);
        psi.updateByPrimaryKey(pi);
    }


}
