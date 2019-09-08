# Spring MongoDB

## 1. MongoDB là gì?

MongoDB là cơ sở dữ liệu hướng tài liệu mã nguồn mở và cơ sở dữ liệu hàng đầu của NoQuery, dữ liệu trong MongoDB lưu dưới định dạng JSON.

MongoDB là một cơ sở dữ liệu NoSQL hỗ trợ đa nền tảng. MongoDB hỗ trợ hầu hết các ngôn ngữ lập trình phổ biến như C#, Java, PHP, Javascript...

## 2. Lợi thế của MongoDB

* MongoDB sử dụng lưu trữ dữ liệu dưới dạng Document JSON, không có sự ràng buộc lẫn nhau nên linh hoạt trong việc lưu trữ dữ liệu.
* MongoDB dễ mở rộng.
* MongoDB hiệu năng cao.

## 3. Khái niệm MongoDB cơ bản

### 3.1 Database

Database là một Ô chứa dữ liệu ở mức vật lý (physical), mỗi database sẽ có nhiều Collection và được thiết lập lưu trữ ở một nơi trong máy chủ máy tính.

### 3.2 Collection

Collection là một nhóm các dữ liệu thuộc cùng loại (do người dùng quy định) và chúng sẽ không có ràng buộc, quan hệ với Collection khác. Và chúng cũng sẽ không cố định về số trường gọi là Field ( như rows trong tables )

### 3.3 Document

Document trong MongoDB có cấu trúc tương tự như kiểu dữ liệu JSON (key-value).

### 3.4 Primary Key

Primary Key mặc định trong MongoDB được gọi là _id và đại diện cho một giá trị duy nhất được hiểu là khóa chính. Nếu bạn thêm mới một document thì MongoDB sẽ tự động sinh ra một _id đại diện.


## 4. Spring Boot + MongoDB

Tích hợp MongoDB trong tập tin pom.xml:

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Tích hợp MongoDB trong tập tin application.properties: 

```
#mongodb
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=dbexam
```

Tạo lớp Employee:

```
package com.techfinally.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Tech Finally
 */
@Document(collection = "employee")
public class Employee {

    @Id
    @Field(value = "empNo")
    private String empNo;

    @Field(value = "empName")
    private String empName;

    @Field(value = "empMail")
    private String empMail;

    @Field(value = "empPhone")
    private String empPhone;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMail() {
        return empMail;
    }

    public void setEmpMail(String empMail) {
        this.empMail = empMail;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

}
```

Tạo lớp EmployeeRepository

```
package com.techfinally.example.repository;

import com.techfinally.example.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tech Finally
 */
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>{
    
}
```

Tạo lớp EmployeeController

```
package com.techfinally.example.controller;

import com.techfinally.example.bean.ResponseData;
import com.techfinally.example.model.Employee;
import com.techfinally.example.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tech Finally
 */
@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> findByAll() {
        List<Employee> list = employeeRepository.findAll();
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> findById(@PathVariable String id) {
        Optional<Employee> list = employeeRepository.findById(id);
        Employee employee = null;
        if (list != null && list.isPresent()) {
            employee = list.get();
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> create(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> update(@RequestBody Employee employee) {
        Optional<Employee> list = employeeRepository.findById(employee.getEmpNo());
        if (list != null && list.isPresent()) {
            employeeRepository.save(list.get());
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> delete(@PathVariable String id) {
        Optional<Employee> list = employeeRepository.findById(id);
        if (list != null && list.isPresent()) {
            employeeRepository.delete(list.get());
        }
        ResponseData response = new ResponseData();
        response.setCode(200);
        response.setData(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
```

## 5. Testing

### 5.1 GET

http://localhost:8080/api/v1/employees/

```
{
	"code": 200,
	"data": [
		{
			"empNo": "20200010",
			"empName": "Truong Duong",
			"empMail": "truongduong@techfinally.com",
			"empPhone": "+84.886.987.987"
			},
			  {
			"empNo": "20701210",
			"empName": "Minh Nguyen",
			"empMail": "minhnguyen@techfinally.com",
			"empPhone": "+84.356.123.321"
		}
	],
}
```

http://localhost:8080/api/v1/employees/20200010/

```
{
	"code": 200,
	"data": {
		"empNo": "20200010",
		"empName": "Truong Duong",
		"empMail": "truongduong@techfinally.com",
		"empPhone": "+84.886.987.987"
	}
}
```

### 5.2 POST

http://localhost:8080/api/v1/employees/

```
{
	"empNo":"30701008",
	"empName":"Truong Nguyen",
	"empMail":"truongnguyen@techfinally.com",
	"empPhone":"+84.336.123.123"
}
```

### 5.3 PUT

http://localhost:8080/api/v1/employees/

```
{
	"empNo":"20200010",
	"empName":"Truong Duong",
	"empMail":"truongduong@techfinally.com",
	"empPhone":"+84.888.234.234"
}
```

### 5.4 DELETE

http://localhost:8080/api/v1/employees/30701008/