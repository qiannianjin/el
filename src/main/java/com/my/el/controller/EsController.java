package com.my.el.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EsController {




    @RequestMapping("/index")
    public String index(){


    return "index.html";



}
  @PostMapping("/abc")
  @ResponseBody
   public JSONObject data(){


     JSONObject jsonObject= new JSONObject();

     jsonObject.put("abc","suibian");


     return jsonObject;

    }




}
