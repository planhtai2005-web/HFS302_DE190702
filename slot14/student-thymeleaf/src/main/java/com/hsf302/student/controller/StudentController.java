
package com.hsf302.student.controller;

import com.hsf302.student.entity.Student;
import com.hsf302.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // TODO 4: Danh sách và tìm kiếm sinh viên
    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Student> students = studentService.search(keyword);

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("avgGpa", studentService.averageGpa(students));
        model.addAttribute("activeMenu", "list");

        return "students/list";
    }

    // TODO 5: Xem chi tiết sinh viên
    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            RedirectAttributes ra) {

        Optional<Student> opt = studentService.findById(id);

        if (opt.isEmpty()) {
            ra.addFlashAttribute(
                    "errorMessage",
                    "Không tìm thấy sinh viên có id = " + id
            );
            return "redirect:/students";
        }

        model.addAttribute("student", opt.get());
        model.addAttribute("activeMenu", "list");

        return "students/detail";
    }

    // TODO 6: Hiển thị form thêm sinh viên
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("activeMenu", "new");

        return "students/form";
    }

    // TODO 6: Hiển thị form sửa sinh viên
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model,
            RedirectAttributes ra) {

        Optional<Student> opt = studentService.findById(id);

        if (opt.isEmpty()) {
            ra.addFlashAttribute(
                    "errorMessage",
                    "Không tìm thấy sinh viên có id = " + id
            );
            return "redirect:/students";
        }

        model.addAttribute("student", opt.get());
        model.addAttribute("activeMenu", "list");

        return "students/form";
    }

    // TODO 7: Lưu sinh viên mới hoặc cập nhật sinh viên
    @PostMapping("/save")
    public String save(
            @ModelAttribute("student") @Valid Student student,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {

        // Kiểm tra mã sinh viên có bị trùng hay không
        if (studentService.isStudentCodeDuplicated(
                student.getStudentCode(), student.getId())) {

            result.rejectValue(
                    "studentCode",
                    "duplicate",
                    "Mã sinh viên đã tồn tại"
            );
        }

        // Có lỗi validation: giữ nguyên dữ liệu và quay lại form
        if (result.hasErrors()) {
            model.addAttribute(
                    "activeMenu",
                    student.getId() == null ? "new" : "list"
            );
            return "students/form";
        }

        boolean isNew = student.getId() == null;
        String fullName = student.getFullName();

        studentService.save(student);

        String action = isNew ? "Thêm" : "Cập nhật";

        ra.addFlashAttribute(
                "successMessage",
                action + " sinh viên " + fullName + " thành công!"
        );

        return "redirect:/students";
    }

    // Cho sẵn: Xóa sinh viên
    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes ra) {

        studentService.deleteById(id);

        ra.addFlashAttribute(
                "successMessage",
                "Đã xóa sinh viên có id = " + id
        );

        return "redirect:/students";
    }
}
