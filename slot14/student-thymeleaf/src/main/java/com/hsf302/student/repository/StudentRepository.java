
package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 19.1: Tìm theo mã sinh viên hoặc họ tên,
    // không phân biệt chữ hoa/chữ thường, sắp xếp id tăng dần
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(
            String code, String name
    );

    // TODO 19.2: Kiểm tra mã sinh viên đã tồn tại khi thêm mới
    boolean existsByStudentCodeIgnoreCase(String studentCode);

    // TODO 19.3: Kiểm tra mã sinh viên đã tồn tại ở sinh viên khác khi cập nhật
    boolean existsByStudentCodeIgnoreCaseAndIdNot(String studentCode, Long id);
}
