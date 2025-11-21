package com.result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResultController {

    @PostMapping("/checkResult")
    public ModelAndView result(@RequestParam("stuid") String id) {
        ModelAndView mv = new ModelAndView("result");

        try (Connection con = new DBConnection().getConnection()) {

            String query = "SELECT * FROM student WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(id));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                mv.addObject("id", rs.getInt("id"));
                mv.addObject("name", rs.getString("name"));
                mv.addObject("email", rs.getString("email"));
                mv.addObject("core", rs.getInt("core_marks"));
                mv.addObject("adv", rs.getInt("adv_marks"));
                mv.addObject("spring", rs.getInt("spring_marks"));
                mv.addObject("react", rs.getInt("react_marks"));
                mv.addObject("oracle", rs.getInt("oracle_marks"));

                boolean pass = rs.getInt("core_marks") >= 33 &&
                               rs.getInt("adv_marks") >= 33 &&
                               rs.getInt("spring_marks") >= 33 &&
                               rs.getInt("react_marks") >= 33 &&
                               rs.getInt("oracle_marks") >= 33;

                mv.addObject("status", pass ? "Pass" : "Fail");

            } else {
                mv.addObject("status", "Student not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Failed to fetch result: " + e.getMessage());
        }

        return mv;
    }
}
