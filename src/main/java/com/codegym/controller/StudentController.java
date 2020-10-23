package com.codegym.controller;

import com.codegym.model.Student;
import com.codegym.service.student.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping()
    public String showListStudent(Model model){
        model.addAttribute("students",studentService.findAll());
        return "list";
    }
    @GetMapping("/create")
    public String showFormCreate(Model model){
        model.addAttribute("student",new Student());
        return "create";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute("student") Student student,Model model){
        studentService.save(student);
        model.addAttribute("message","create is successful");
        return "/create";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable Long id,Model model,@RequestBody Student student){
        Optional<Student> optionalStudent = studentService.findById(id);
        student.setId(id);
        if(optionalStudent.isPresent()) {
            model.addAttribute("student", student);
            return "edit";
        }
        return "redirect:/students";
    }
    @PostMapping("/edit")
    public String updateStudent(@RequestBody Student student,Model model){
        studentService.save(student);
        model.addAttribute("message","update is successful");
        return "/edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.remove(id);
        return "redirect:/students";
    }
}
