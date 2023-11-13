package com.mosoftvn.controller;

import com.mosoftvn.dto.StudentDTO;
import com.mosoftvn.model.entity.ClassRoom;
import com.mosoftvn.model.entity.Student;
import com.mosoftvn.model.service.ClassRoomService;
import com.mosoftvn.model.service.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/StudentServlet")
public class StudentServlet extends HttpServlet {
    private StudentService studentService = null;
    private ClassRoomService classRoomService = null;

    @Override
    public void init() throws ServletException {
        studentService = new StudentService();
        classRoomService = new ClassRoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null){
            getAll(request,response);
        }
        if(action.equals("add")){
            List<ClassRoom> list = classRoomService.findAll();
            request.setAttribute("list",list);
            request.getRequestDispatcher("views/add-student.jsp").forward(request,response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String dateStr = request.getParameter("birthday");
        Integer classId = Integer.parseInt(request.getParameter("classId"));
        // convert tu string => sql.date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        Date birthday = null;
        try {
            birthday = formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        StudentDTO student = new StudentDTO();
        student.setName(name);
        student.setClassId(classId);
        student.setBirthday(new java.sql.Date(birthday.getTime()));
        studentService.create(student);

        getAll(request,response);

    }


    public void getAll(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        List<StudentDTO> list = studentService.findAll();
        request.setAttribute("list",list);
        request.getRequestDispatcher("views/student.jsp").forward(request,response);
    }
}