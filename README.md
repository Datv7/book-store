## book-store
1. Tạo 1 db có tên "bookstore"
2. KHởi chạy mysql server
3. Path bookstore\bookstore\src\main\resources\application.properties  
Mục database sửa thông tin: url, username, password(account trên máy)  
Mục email điền username và password (email bản thân dùng test tương đương email chủ shop). Email phải đạt yêu cầu: mở xác thực 2 bước(đk1), truy cập tràn quản lý tài khoản google, tìm kiếm "mật khẩu ứng dung" tạo password(đk2), pass bỏ dấu cách đi. 

4. Thông tin api: localhost:8080/swagger-ui/index.html# (hiệu lực khi chạy project)
Chuyển về postman (https://www.youtube.com/watch?v=c4RVkOOy-Cs&list=PLqMvN7uBIdIAq6PzDyXtqtKaRGbpLy9Mm&index=16&t=817s) bắt đầu từ 24p 40s

5. Chỉ test những cái ghi trong commit "/cus"(customer)và "/admin"(admin)