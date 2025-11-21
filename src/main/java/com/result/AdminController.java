package com.result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @PostMapping("/addStu")
    public ModelAndView addStu(@RequestParam("stuid") String id, 
                               @RequestParam("stuname") String name, 
                               @RequestParam("stuemail") String email) {
        ModelAndView mv = new ModelAndView("welcome-admin");

        try (Connection con = new DBConnection().getConnection()) {

            String query = "INSERT INTO student (id, name, email, core_marks, adv_marks, spring_marks, react_marks, oracle_marks) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);

            ps.executeUpdate();
            System.out.println("Data has been added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Failed to add student: " + e.getMessage());
        }

        return mv;
    }

    @PostMapping("/updateMarks")
    public ModelAndView updateMarks(@RequestParam("id") String id,
                                    @RequestParam("core") String core,
                                    @RequestParam("adv") String adv,
                                    @RequestParam("spring") String spring,
                                    @RequestParam("react") String react,
                                    @RequestParam("oracle") String oracle) {
        ModelAndView mv = new ModelAndView("welcome-admin");

        try (Connection con = new DBConnection().getConnection()) {

            String query = "UPDATE student SET core_marks = ?, adv_marks = ?, spring_marks = ?, react_marks = ?, oracle_marks = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(core));
            ps.setInt(2, Integer.parseInt(adv));
            ps.setInt(3, Integer.parseInt(spring));
            ps.setInt(4, Integer.parseInt(react));
            ps.setInt(5, Integer.parseInt(oracle));
            ps.setInt(6, Integer.parseInt(id));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                mv.addObject("error", "Student ID not found");
            } else {
                System.out.println("Marks updated successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Failed to update marks: " + e.getMessage());
        }

        return mv;
    }

    @PostMapping("/deleteStu")
    public ModelAndView deleteStudent(@RequestParam("stuid") String id) {
        ModelAndView mv = new ModelAndView("welcome-admin");

        try (Connection con = new DBConnection().getConnection()) {

            String query = "DELETE FROM student WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(id));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                mv.addObject("error", "Student ID not found");
            } else {
                System.out.println("Student deleted successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Failed to delete student: " + e.getMessage());
        }

        return mv;
    }
}
