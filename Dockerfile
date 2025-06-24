

# 1. Sử dụng image Tomcat chính thức làm base image
FROM tomcat:10.1-jdk17
LABEL authors="TO NHAT tominh.nhat03@gmail.com"
# 3. Copy file WAR vào thư mục webapps của Tomcat
COPY target/PhuongLinh-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# 4. Expose port để container có thể lắng nghe
EXPOSE 8080

# 5. Lệnh khởi chạy Tomcat (mặc định)
CMD ["catalina.sh", "run"]
