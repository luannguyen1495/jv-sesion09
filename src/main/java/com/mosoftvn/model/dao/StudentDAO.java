package com.mosoftvn.model.dao;

import com.mosoftvn.dto.StudentDTO;
import com.mosoftvn.model.entity.ClassRoom;
import com.mosoftvn.model.entity.Student;
import com.mosoftvn.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IGenericDAO<StudentDTO,Integer> {
    @Override
    public List<StudentDTO> findAll() {
        Connection connection = null;
        List<StudentDTO> list = new ArrayList<>();

        connection = ConnectionDB.openConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_STUDENT()}");
            ResultSet rs =  callableStatement.executeQuery();
            while (rs.next()){
                StudentDTO student = new StudentDTO();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setBirthday(rs.getDate("birthday"));
                student.setClassName(rs.getString("class_name"));
                list.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Boolean create(StudentDTO student) {
        Connection connection = null;

        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_INSERT_STUDENT(?,?,?)}");
            callableStatement.setString(1,student.getName());
            callableStatement.setDate(2,student.getBirthday());
            callableStatement.setInt(3,student.getClassId());
            int check = callableStatement.executeUpdate();
            if(check >0)
                return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Boolean update(StudentDTO student, Integer integer) {
        return null;
    }

    @Override
    public StudentDTO findId(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
