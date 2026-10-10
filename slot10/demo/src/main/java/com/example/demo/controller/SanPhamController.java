
package com.example.demo.controller;

import com.example.demo.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {

    // Lưu tạm trong bộ nhớ, mất khi restart ứng dụng.
    private final List<SanPham> danhSach = new CopyOnWriteArrayList<>();

    // GET: Hiển thị form thêm sản phẩm
    @GetMapping("/them")
    public String showForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        return "sanpham/form";
    }

    // POST: Nhận dữ liệu và thêm sản phẩm
    @PostMapping("/them")
    public String xuLyForm(
            @ModelAttribute("sanPham") SanPham sanPham,
            RedirectAttributes ra) {

        danhSach.add(sanPham);
        ra.addFlashAttribute(
                "thongBao",
                "Thêm sản phẩm thành công!"
        );

        return "redirect:/sanpham/ket-qua";
    }

    // GET: Hiển thị trang kết quả
    @GetMapping("/ket-qua")
    public String ketQua(Model model) {
        model.addAttribute("danhSach", danhSach);
        return "sanpham/ket-qua";
    }
}