package com.clark.deduplistings.controller;

import com.clark.deduplistings.service.TableAService;
import com.clark.deduplistings.service.TableBService;
import com.clark.deduplistings.service.impl.RequestUrlServiceImpl;
import com.clark.deduplistings.vo.ResultVO;
import com.clark.deduplistings.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/listing")
public class ListingController {

    @GetMapping("/test")
    public String test(){
        return "OK testing";
    }

    @Autowired
    private TableAService tableAService;

    @Autowired
    private TableBService tableBService;

    @Autowired
    private RequestUrlServiceImpl requestUrlService;

    @GetMapping("/tableA")
    public ResultVO listTableA(){
        System.out.println("/tableA is called");
        List<TableVO> items = tableAService.findAll();
        return items.isEmpty()?
                ResultVO.error("Items Not Found"):
                ResultVO.success("Items return success",items);
    }

    @GetMapping("/tableB")
    public ResultVO listTableB(){
        System.out.println("/tableB is called");
        List<TableVO> items = tableBService.findAll();
        return items.isEmpty()?
                ResultVO.error("Items Not Found"):
                ResultVO.success("Items return success",items);
    }

    @GetMapping("/dedup")
    public ResultVO dedup(){
        System.out.println("/dedup is called");
        List<TableVO> items = requestUrlService.doDedup();
        return items.isEmpty()?
                ResultVO.error("Items Not Found"):
                ResultVO.success("Items return success",items);
    }

}
