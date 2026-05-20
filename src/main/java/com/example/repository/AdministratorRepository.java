package com.example.repository;

import com.example.domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理者情報を操作するリポジトリクラスです.
 */
@Repository
public class AdministratorRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER
            = (rs, i) -> {
        Administrator administrator = new Administrator();
        administrator.setId(rs.getInt("id"));
        administrator.setName(rs.getString("name"));
        administrator.setMailAddress(rs.getString("mail_address"));
        administrator.setPassword(rs.getString("password"));
        return administrator;
    };

    /**
     * 管理者情報を挿入する.
     *
     * @param administrator 管理者情報
     */
    public void insert(Administrator administrator) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
        if (administrator.getId() == null) {
            String sql = """
                    INSERT INTO
                     administrators
                     (name,mail_address,password)
                    
                     VALUES
                     (:name,:mailAddress,:password)
                    """;

            template.update(sql, param);

        } else {
            String sql = """
                    UPDATE
                     administrators
                    
                     SET
                     name = :name
                     ,mail_address = :mailAddress
                     ,password = :password
                    
                     WHERE
                     id = :id
                    """;

            template.update(sql, param);
        }
    }

    /**
     * メールアドレスとパスワードから管理者情報を取得する.
     *
     * @param mailAddress メールアドレス
     * @param password パスワード
     * @return 管理者情報（1件も存在しない場合はnullを返す）
     */
    public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
        String sql = """
                SELECT
                 id,name,mail_address,password
                
                 FROM
                 administrators
                
                 WHERE
                 mail_address = :mailAddress
                 AND
                 password = :password
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("mailAddress", mailAddress)
                .addValue("password", password);

        List<Administrator> administratorList
                = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);

        if (administratorList.isEmpty()) {
            return null;
        }
        return administratorList.getFirst();
    }

}
