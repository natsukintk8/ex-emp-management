package com.example.controller;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員関連機能の処理の制御を行うコントローラです.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 従業員情報を検索する.
     *
     * @param model モデル
     * @return 全従業員一覧
     */
    @GetMapping("/show-list")
    public String showList(Model model){
        model.addAttribute("employeeList",employeeService.showList());
        return "employee/list";
    }

    /**
     * 従業員情報を検索する.
     *
     * @param id ID
     * @param model モデル
     * @param form フォーム
     * @return 従業員情報
     */
    @GetMapping("/show-detail")
    public String showDetail(String id, Model model, UpdateEmployeeForm form){
        Employee employee = new Employee();
        BeanUtils.copyProperties(form,employee);
        employee = employeeService.showDetail(Integer.parseInt(id));
        model.addAttribute("employee",employee);
        return "employee/detail";
    }
}
