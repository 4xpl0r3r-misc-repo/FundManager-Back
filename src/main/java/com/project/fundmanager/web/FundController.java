package com.project.fundmanager.web;

import com.project.fundmanager.entity.Fund;
import com.project.fundmanager.service.FundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fund")
public class FundController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FundService fundService;

    @GetMapping(value = "/getById")
    public Map<String, Object> getFundById(@RequestParam(defaultValue= "1") long id) {
        Fund res = fundService.getFundById(id);
        if (res != null){
            return Map.of("message","success","fund",res );
        }else {
            return Map.of("error", "Not Found", "message", "The fund of this ID doesn't exist.");
        }

    }
}
