# Tuyển Dụng và Gợi Ý Kỹ Năng

## Mô tả Dự Án
Dự án này nhằm tạo ra một hệ thống tuyển dụng trực tuyến giữa các công ty và ứng viên, cung cấp gợi ý công việc dựa trên kỹ năng của ứng viên và giúp công ty tìm kiếm ứng viên có kỹ năng phù hợp sau đó thực hiện việc gửi email mời. Thực hiện các CRUD như ( Add Candidates, add job, add jobSkil, addSkill,...)

### 1. Entities và Tạo Bảng
Các entities được thiết kế để lưu trữ thông tin về công việc, ứng viên, kỹ năng, kinh nghiệm làm việc, v.v. Khi thực thi, hệ thống sẽ tự động tạo các bảng tương ứng trong cơ sở dữ liệu. Ví dụ:

- `Job`: Lưu trữ thông tin về công việc.
- `Candidate`: Lưu trữ thông tin về ứng viên.
- `Skill`: Lưu trữ danh sách các kỹ năng.
- `Experience`: Lưu trữ thông tin về kinh nghiệm làm việc của ứng viên.

### 2. Repositories
Các repositories sẽ cung cấp các phương thức để truy vấn cơ sở dữ liệu. Ví dụ:

- `JobRepository`: Thao tác với thông tin công việc.
- `CandidateRepository`: Thao tác với thông tin ứng viên.
- `SkillRepository`: Thao tác với danh sách kỹ năng.
- `ExperienceRepository`: Thao tác với thông tin kinh nghiệm làm việc.

### 3. Services
Các services chứa logic xử lý nghiệp vụ, kết nối giữa controllers và repositories. Ví dụ:

- `JobService`: Xử lý logic liên quan đến công việc.
- `CandidateService`: Xử lý logic liên quan đến ứng viên.
- `SkillService`: Xử lý logic liên quan đến kỹ năng.
- `ExperienceService`: Xử lý logic liên quan đến kinh nghiệm làm việc.

### 4. Trang Web

#### Công Ty Đăng Tin Tuyển Dụng
- `JobController`: Hiển thị form đăng tin tuyển dụng và xử lý thông tin khi công ty đăng tin.
- `JobView`: Hiển thị danh sách công việc và chi tiết công việc.

#### Ứng Viên Đăng Nhập
- `CandidateController`: Gợi ý công việc dựa trên kỹ năng của ứng viên và đề xuất các kỹ năng để học.
- `CandidateView`: Hiển thị thông tin ứng viên và công việc được gợi ý.

#### Gửi Mail Mời
- `EmailService`: Gửi mail mời ứng viên sau khi công ty tìm kiếm và chọn ứng viên phù hợp.

## Hướng Dẫn Cài Đặt và Chạy
1. Clone repository về máy tính của bạn.
2. Cấu hình cơ sở dữ liệu trong file `application.properties`.
3. Chạy ứng dụng Spring Boot.

## Yêu Cầu Hệ Thống
- Java 11 trở lên.
- Maven để quản lý các dependency.
- Cơ sở dữ liệu: MySQL hoặc PostgreSQL.

## Đóng Góp
Mọi đóng góp và phản hồi đều được hoan nghênh. Hãy tạo một issue nếu bạn gặp vấn đề hoặc có gợi ý mới.

## Tác Giả
[Your Name]
