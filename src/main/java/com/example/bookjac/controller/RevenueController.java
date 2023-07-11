package com.example.bookjac.controller;

import com.example.bookjac.domain.Settlement;
import com.example.bookjac.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("Revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("daily")
    public void daily(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "selectWay", required = false) Integer selectWay,
            @RequestParam(value = "month", required = false) String yearMonth,
            Model model) {

        //정산 전체 리스트 조회
        Map<String, Object> info = revenueService.selectSettlement(startDate, endDate, selectWay, yearMonth);
        System.out.println(startDate + ", " + endDate + ", " + selectWay + ", " + yearMonth);

        model.addAllAttributes(info);
    }

    @PostMapping("addDaily")
    public String addDaily(Settlement settlement) {
        //일일 정산 입력 과정
        boolean ok = revenueService.insertRevenue(settlement);

        return "redirect:/Revenue/daily";
    }

    @DeleteMapping("deleteDaily")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteDaily(
            @RequestParam("settlementId") Integer settlementId) {
        //일일 정산 삭제 과정
        Map<String, Object> res = revenueService.deleteRevenue(settlementId);
        System.out.println(res.get("message"));

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("getDailyInfo")
    @ResponseBody
    public Settlement getDailyInfo(@RequestParam("settlementId") Integer settlementId) {
        //시간 측정
        long beforTime = System.currentTimeMillis();

        // 일일 정산 수정 시 기존 정보 조회
        Settlement settlement = revenueService.selectSettlementById(settlementId);

        //시간 측정
        long afterTime = System.currentTimeMillis();
        long result = (afterTime - beforTime);
        System.out.println("수정 실행 시간 : " + result);

        return settlement;
    }

    @PutMapping("modifyDaily")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> modifyDaily(
            @RequestBody Settlement settlement) {
        // 정산 수정 프로세스 
        Map<String, Object> res = revenueService.modifyDaily(settlement);

        System.out.println(res.get("message"));
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("monthly")
    public void monthly(Model model) {
        // 일일 정산 입력창 forward

        //월별 정보 조회
        //Map<String, Object> info =  revenueService.selectSettlementForMonth();
    }

    @GetMapping("dailyDetail")
    public void dailyDetail(
            @RequestParam("settlementId") Integer settlementId,
            Model model) {
        //Map<String, Object> info = new HashMap<>();

        //일일 정산 상세 내역 조회
        Map<String, Object> info = revenueService.selectDailyDetailBySettlementId(settlementId);

        model.addAllAttributes(info);
    }
}
