package com.example.repository;

import com.example.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER
            = new BeanPropertyRowMapper<>(Employee.class);

    /**
     * 従業員一覧情報を入社日順（降順）で取得する.
     *
     * @return　従業員一覧情報（従業員が存在しない場合はサイズ0件の従業員一覧を返す）。
     */
    public List<Employee> findAll() {
        String sql = """
                SELECT
                 id,name,image,gender,hire_date,mail_address,zip_code,address
                 ,telephone,salary,characteristics,dependents_count
                
                 FROM
                  employees
                
                 ORDER BY
                  hire_code DESC
                """;

        List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

        if (employeeList.isEmpty()) {
            return null;
        }

        return employeeList;

    }

    /**
     * 主キーから従業員情報を取得する.
     *
     * @param id
     * @return　従業員情報（従業員が存在しない場合はSpringが自動的に例外を発生します）。
     */
    public Employee findById(Integer id) {
        String sql = """
                SELECT
                 id,name,image,gender,hire_date,mail_address,zip_code,address
                 ,telephone,salary,characteristics,dependents_count
                
                 FROM
                  employees
                
                 WHERE
                  id = :id
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
    }

    /**
     * 従業員情報を変更する
     *
     * @param employee
     */
    public void update(Employee employee) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

        String sql = """
                UPDATE
                 employees
                
                 SET
                  name = :name
                 ,image = :image
                 ,gender = :gender
                 ,hire_date = :hireDate
                 ,mail_address = :mailAddress
                 ,zip_code,address = :zipCode
                 ,telephone = :telephone
                 ,salary = :salary
                 ,characteristics = :characteristics
                 ,dependents_count = :dependentsCount
                
                 WHERE
                  id = :id
                """;

        template.update(sql, param);

    }
}
