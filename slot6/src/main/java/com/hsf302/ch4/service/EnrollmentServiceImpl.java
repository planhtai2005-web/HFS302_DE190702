package com.hsf302.ch4.service;
import com.hsf302.ch4.dto.EnrollmentView;
import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.CourseRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hsf302.ch4.dto.StudentCreditDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCoursesOfStudent(String studentCode) {
        Student student = studentRepository
                .findByStudentCode(studentCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found: " + studentCode
                        )
                );

        return List.copyOf(student.getCourses());
    }

    @Override
    public List<Student> getStudentsOfCourse(String courseCode) {
        Course course = courseRepository
                .findByCode(courseCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Course not found: " + courseCode
                        )
                );

        return List.copyOf(course.getStudents());
    }
    // ===== TODO 9 =====

    @Override
    public List<Student> findStudentsInCourse(String courseCode) {
        return studentRepository.findByCourses_CodeOrderByFullNameAsc(courseCode);
    }

    @Override
    public long countStudentsInCourse(String courseCode) {
        return studentRepository.countByCourses_Code(courseCode);
    }

    @Override
    public List<Student> findActiveStudentsInCourse(String courseCode) {
        return studentRepository.findByCourses_CodeAndActiveTrueOrderByFullNameAsc(courseCode);
    }

    // ===== TODO 11 =====
    @Override
    public List<Student> findStudentsWithoutCourses() {
        return studentRepository.findByCoursesIsEmptyOrderByFullNameAsc();
    }

    @Override
    public boolean isEnrolled(String studentCode, String courseCode) {
        return studentRepository.existsByStudentCodeAndCourses_Code(studentCode, courseCode);
    }

    // ===== TODO 12 =====
    @Override
    public List<Student> findGoodStudentsInCourse(String courseCode, double minGpa) {
        if (minGpa < 0 || minGpa > 4) {
            throw new IllegalArgumentException("minGpa must be in [0, 4]");
        }

        return studentRepository.findGoodStudentsInCourse(courseCode, minGpa);
    }
    // ===== TODO 14 =====

    @Override
    public List<StudentCreditDTO> getCreditSummary(int minCredits) {
        if (minCredits < 0) {
            throw new IllegalArgumentException("minCredits must be >= 0");
        }
        return studentRepository.getCreditSummary(minCredits);
    }
    // ===== TODO 15 =====

    @Override
    public List<Student> findStudentsWithMoreThan(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        return studentRepository.findStudentsWithMoreThanNCourses(n);
    }

    // ===== TODO 16 =====
    @Override
    public Student getStudentWithCourses(String studentCode) {
        return studentRepository.findByStudentCodeWithCourses(studentCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found: " + studentCode));
    }

    // TODO 18
    @Override
    public List<EnrollmentView> getEnrollmentsOfDepartment(String deptCode) {
        return studentRepository.findEnrollmentsOfDepartment(deptCode);
    }

    // TODO 19
    @Override
    public Page<Student> findStudentsInCoursePage(String courseCode, int pageIndex, int size) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex must be >= 0 and size must be > 0");
        }

        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by("fullName"));
        return studentRepository.findPageByCourseCode(courseCode, pageable);
    }
    // ===== TODO 20 =====
    @Override
    @Transactional
    public void enroll(String studentCode, String courseCode) {
        Student s = getStudent(studentCode);
        Course c = getCourse(courseCode);
        checkAndEnroll(s, c);
    }

    /** Kiểm tra quy tắc nghiệp vụ rồi mới đăng ký. Dùng lại ở TODO 22. */
    private void checkAndEnroll(Student s, Course c) {
        if (!s.isActive()) {
            throw new IllegalStateException(
                    "Student " + s.getStudentCode() + " is inactive");
        }

        if (s.getCourses().contains(c)) {
            throw new IllegalStateException(
                    "Student " + s.getStudentCode()
                            + " already enrolled in " + c.getCode());
        }

        int enrolled = c.getStudents().size();

        if (enrolled >= c.getCapacity()) {
            throw new IllegalStateException(
                    "Course " + c.getCode()
                            + " is full (" + enrolled
                            + "/" + c.getCapacity() + ")");
        }

        s.enroll(c);
    }

    // ===== Helper for TODO 20 =====
    private Student getStudent(String studentCode) {
        return studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found: " + studentCode));
    }

    private Course getCourse(String courseCode) {
        return courseRepository.findByCode(courseCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Course not found: " + courseCode));
    }
    // ===== TODO 21 =====
    @Override
    @Transactional
    public void unenroll(String studentCode, String courseCode) {
        Student s = getStudent(studentCode);
        Course c = getCourse(courseCode);

        if (!s.getCourses().contains(c)) {
            throw new IllegalStateException(
                    "Student " + studentCode
                            + " is not enrolled in " + courseCode);
        }

        s.unenroll(c);
    }
    // ===== TODO 22 =====
    @Override
    @Transactional
    public void switchCourse(String studentCode, String fromCode, String toCode) {
        if (fromCode == null || fromCode.equals(toCode)) {
            throw new IllegalArgumentException(
                    "fromCode and toCode must be different");
        }

        Student s = getStudent(studentCode);
        Course from = getCourse(fromCode);
        Course to = getCourse(toCode);

        if (!s.getCourses().contains(from)) {
            throw new IllegalStateException(
                    "Student " + studentCode
                            + " is not enrolled in " + fromCode);
        }

        s.unenroll(from);
        checkAndEnroll(s, to);
    }
}
