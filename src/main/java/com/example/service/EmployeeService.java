package com.example.service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 従業員関連機能の業務処理を行うサービスです.
 */
@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     *従業員情報一覧を全件検索する業務処理を行う.
     *
     * @return 全従業員情報一覧
     */
    public List<Employee> showList(){
        return employeeRepository.findAll();
    }
}
