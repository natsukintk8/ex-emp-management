package com.example.controller;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者登録画面を表示する処理を記述するコントローラーです.<br>
 *
 */

@Controller
@RequestMapping("/")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    /**
     * 従業員登録時のリクエストパラメータを格納します.<br>
     *
     * @param form
     * @return　フォワードするhtmlファイル
     */
    @GetMapping("/to-insert")
    public String toInsert(InsertAdministratorForm form){
        return "administrator/insert";
    }

    /**
     * 管理者情報を登録する.<br>
     *
     * @param form
     * @return ログイン画面へフォワードする。
     */
    @PostMapping("/insert")
    public String insert(InsertAdministratorForm form){
        Administrator administrator = new Administrator();
        administrator.setName(form.getName());
        administrator.setMailAddress(form.getMailAddress());
        administrator.setPassword(form.getPassword());
        administratorService.insert(administrator);
        return "/";

    }
}
