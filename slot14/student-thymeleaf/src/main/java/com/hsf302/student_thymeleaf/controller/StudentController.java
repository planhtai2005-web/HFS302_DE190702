package com.hsf302.student_thymeleaf.controller;

import com.hsf302.student_thymeleaf.entity.Student;
import com.hsf302.student_thymeleaf.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("majors")
    public Map<String, String> majors() {
        Map<String, String> majors = new LinkedHashMap<>();
        majors.put("SE", "Kỹ thuật phần mềm");
        majors.put("AI", "Trí tuệ nhân tạo");
        majors.put("IA", "An toàn thông tin");
        majors.put("GD", "Thiết kế đồ họa");
        return majors;
    }

    // TODO 4
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students = studentService.search(keyword);
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("avgGpa", studentService.averageGpa(students));
        model.addAttribute("activeMenu", "list");
        return "students/list";
    }

    // TODO 5
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return studentService.findById(id).map(s -> {
            model.addAttribute("student", s);
            model.addAttribute("activeMenu", "list");
            return "students/detail";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có id = " + id);
            return "redirect:/students";
        });
    }

    // TODO 6
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("activeMenu", "new");
        return "students/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return studentService.findById(id).map(s -> {
            model.addAttribute("student", s);
            model.addAttribute("activeMenu", "list");
            return "students/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy sinh viên có id = " + id);
            return "redirect:/students";
        });
    }

    // TODO 7
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result, Model model, RedirectAttributes ra) {

        if (studentService.isStudentCodeDuplicated(student.getStudentCode(), student.getId())) {
            result.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại");
        }
        if (result.hasErrors()) {
            model.addAttribute("activeMenu", student.getId() == null ? "new" : "list");
            return "students/form";
        }

        boolean isNew = student.getId() == null;
        studentService.save(student);
        ra.addFlashAttribute("successMessage",
                (isNew ? "Thêm" : "Cập nhật") + " sinh viên " + student.getFullName() + " thành công!");
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        studentService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Đã xóa sinh viên có id = " + id);
        return "redirect:/students";
    }
}