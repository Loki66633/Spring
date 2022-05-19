package hr.tvz.ivanovic.hardwareapp;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Repository
public class JdbcHardwareRepository implements HardwareRepository{

    private static final String SELECT_ALL = "SELECT * FROM hardware";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;

    public JdbcHardwareRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("hardware")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    public List<Hardware> findAll() {
        return List.copyOf(jdbc.query(SELECT_ALL,this::mapRowToHardware));
    }

    @Override
    public Optional<Hardware> findByCode(String code) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE article_Code = ?", this::mapRowToHardware, code)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Hardware> save(Hardware hardware) {
        try {
            saveHardwareDetails(hardware);
            return Optional.of(hardware);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(String code) {
        if(jdbc.update("DELETE FROM hardware WHERE article_Code = ?", code)==1){
            return true;
        }
        return false;
    }




    private Hardware mapRowToHardware(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("name");
        String articleCode = rs.getString("article_Code");
        String typeString = rs.getString("article_Type");
        typeString = typeString.toUpperCase(Locale.ROOT);
        int stock = rs.getInt("stock");
        double price = rs.getDouble("price");
        Hardware.Type articleType = switch (typeString) {
            case "GPU" -> Hardware.Type.GPU;
            case "CPU" -> Hardware.Type.CPU;
            case "MBO" -> Hardware.Type.MBO;
            case "RAM" -> Hardware.Type.RAM;
            case "STORAGE" -> Hardware.Type.STORAGE;
            default -> Hardware.Type.OTHER;
        };
        System.out.println(name+articleCode+articleType+price+stock);
        return new Hardware(name,articleCode,articleType,stock,price);
    }


    private long saveHardwareDetails(Hardware hardware) {
        Map<String, Object> values = new HashMap<>();

        values.put("name", hardware.getName());
        values.put("article_Code", hardware.getArticleCode());
        values.put("article_Type", hardware.getArticleType());
        values.put("stock", hardware.getStock());
        values.put("price", hardware.getPrice());

        return inserter.executeAndReturnKey(values).longValue();
    }
}





















/*    @Override
    public Optional<Student> update(String JMBAG, Student updatedStudent) {
        int executed = jdbc.update("UPDATE student set " +
                        "first_name = ?, " +
                        "last_name = ?, " +
                        "ects_points = ?, " +
                        "date_of_birth = ? " +
                            "WHERE jmbag = ?",
                        updatedStudent.getFirstName(),
                        updatedStudent.getLastName(),
                        updatedStudent.getNumberOfECTS(),
                        updatedStudent.getDateOfBirth(),
                        updatedStudent.getJmbag()
                );

        if(executed > 0){
            return Optional.of(updatedStudent);
        } else {
            return Optional.empty();
        }
    }*/