# Spring boot 项目构建

参考：https://spring.io/quickstart

## 问题

### 问题一：

```bash
[ERROR] The goal you specified requires a project to execute but there is no POM in this directory (E:\软件工程\Base Project). Please verify you inv Maven from the correct directory. -> [Help 1]
```

解决：

需要在包含pom.xml的文件夹中运行：

```bash
mvn spring-boot:run
```



### 问题二：

```bash
Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:3.3.4:run (default-cli) on project demo: Process terminated with exit code: 1 -> [Help 1]
#找不到或无法加载主类
```



解决：

不用mvn spring-boot:run了，直接运行主类： locate the `DemoApplication.java` file in the `src/main/java/com/example/demo` folder

成功



### 问题三：

直接运行主类控制台出现：

```bash
2024-09-29T17:26:41.735+08:00 ERROR 18596 --- [demo] [           main] o.a.catalina.core.AprLifecycleListener   : An incompatible version [1.2.21] of the Apache Tomcat Native library is installed, while Tomcat requires version [1.2.34]
```

，但是打开http://localhost:8080/hello正确出现”hello world！“



## 构建

项目结构：

com
 +- example
     +- myapplication
         +- MyApplication.java
         |
         +- customer
         |   +- Customer.java
         |   +- CustomerController.java
         |   +- CustomerService.java
         |   +- CustomerRepository.java
         |
         +- order
             +- Order.java
             +- OrderController.java
             +- OrderService.java
             +- OrderRepository.java

解释：

感谢您的澄清！您提供的结构确实非常符合典型的 **Spring Boot** 项目结构，通常会涉及到控制层（Controller）、服务层（Service）、以及持久层（Repository）。我将结合 **Spring Boot** 的相关概念，进一步解释这个项目结构。

### 项目结构解释：

```
com
 └── example
     └── myapplication
         ├── MyApplication.java
         ├── customer
         │   ├── Customer.java
         │   ├── CustomerController.java
         │   ├── CustomerService.java
         │   └── CustomerRepository.java
         └── order
             ├── Order.java
             ├── OrderController.java
             ├── OrderService.java
             └── OrderRepository.java
```

#### 1. `MyApplication.java`

这是整个 **Spring Boot** 应用的启动类，通常包含 `@SpringBootApplication` 注解，并且包括 `main` 方法来启动整个应用。Spring Boot 通过这个类来启动嵌入式的应用服务器（如 Tomcat）并运行整个项目。

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

#### 2. `customer` 包

这个包处理与“客户”相关的逻辑，包含了 MVC 架构的各个层次。

- **`Customer.java`**: 这是客户的实体类（Entity），通常会使用 **JPA** 的注解来映射数据库表中的字段。例如：

  ```java
  @Entity
  public class Customer {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String name;
      private String email;
      // getters, setters, constructors
  }
  ```

- **`CustomerController.java`**: 这是控制器类，用于处理与客户相关的 HTTP 请求。典型的注解是 `@RestController`，并且会通过 `@RequestMapping` 或 `@GetMapping` 等注解来映射 URL 路径。

  ```java
  @RestController
  @RequestMapping("/customers")
  public class CustomerController {
      private final CustomerService customerService;
  
      public CustomerController(CustomerService customerService) {
          this.customerService = customerService;
      }
  
      @GetMapping
      public List<Customer> getAllCustomers() {
          return customerService.getAllCustomers();
      }
  
      // Other endpoints like creating, updating customers
  }
  ```

- **`CustomerService.java`**: 服务类，包含业务逻辑。通常会使用 `@Service` 注解。它会与持久层（Repository）进行交互，进行数据库操作。

  ```java
  @Service
  public class CustomerService {
      private final CustomerRepository customerRepository;
  
      public CustomerService(CustomerRepository customerRepository) {
          this.customerRepository = customerRepository;
      }
  
      public List<Customer> getAllCustomers() {
          return customerRepository.findAll();
      }
  
      // Other business logic methods
  }
  ```

- **`CustomerRepository.java`**: 持久层接口，通常继承自 **JPA** 的 `JpaRepository` 或 `CrudRepository`，负责与数据库交互，执行增删改查操作。

  ```java
  public interface CustomerRepository extends JpaRepository<Customer, Long> {
      // Custom database query methods if needed
  }
  ```

#### 3. `order` 包

与 `customer` 包类似，`order` 包处理与“订单”相关的逻辑，包含了对应的实体类、控制器、服务类和持久层接口。

- **`Order.java`**: 订单实体类。

  ```java
  @Entity
  public class Order {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String orderNumber;
      private Double amount;
      // getters, setters, constructors
  }
  ```

- **`OrderController.java`**: 处理订单相关的 HTTP 请求的控制器类。

  ```java
  @RestController
  @RequestMapping("/orders")
  public class OrderController {
      private final OrderService orderService;
  
      public OrderController(OrderService orderService) {
          this.orderService = orderService;
      }
  
      @GetMapping
      public List<Order> getAllOrders() {
          return orderService.getAllOrders();
      }
  
      // Other endpoints
  }
  ```

- **`OrderService.java`**: 订单相关的业务逻辑类。

  ```java
  @Service
  public class OrderService {
      private final OrderRepository orderRepository;
  
      public OrderService(OrderRepository orderRepository) {
          this.orderRepository = orderRepository;
      }
  
      public List<Order> getAllOrders() {
          return orderRepository.findAll();
      }
  
      // Other business logic
  }
  ```

- **`OrderRepository.java`**: 订单持久层接口，通常继承自 `JpaRepository`。

  ```java
  public interface OrderRepository extends JpaRepository<Order, Long> {
      // Additional query methods if necessary
  }
  ```

### 总结

这个结构非常典型地遵循了 **Spring Boot** 应用的三层架构：

1. **控制层**：`Controller` 处理用户请求。
2. **服务层**：`Service` 处理业务逻辑。
3. **持久层**：`Repository` 负责与数据库交互。

每个实体（如 `Customer` 和 `Order`）都有对应的控制器、服务和持久层，确保了代码的模块化和清晰的职责分离。



## mysql和idea连接

好的，下面是使用 MySQL 数据库的配置步骤。

### 1. **MySQL 安装与设置**

首先，确保你的系统上已经安装并运行了 MySQL。你可以使用以下命令来检查 MySQL 是否运行：

```bash
mysql -u root -p
```

如果 MySQL 还没有安装，可以根据你的操作系统选择安装方式，例如：

- **Windows/MacOS**：可以使用 MySQL Installer 或 Homebrew 安装。
- **Linux**：可以使用 `apt-get` 或 `yum` 等包管理工具。

### 2. **创建数据库**

登录 MySQL 后，创建一个数据库供 Spring Boot 项目使用。例如，创建一个名为 `myappdb` 的数据库：

```sql
CREATE DATABASE myappdb;
```

还可以为这个数据库创建一个用户（假设用户名为 `user`，密码为 `password`）：

```sql
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON myappdb.* TO 'user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. **添加 MySQL 依赖**

在 Spring Boot 项目的 `pom.xml` 中，添加 MySQL 依赖：

```xml
<dependencies>
    <!-- MySQL 驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- 其他依赖 -->
    <!-- 例如Spring Web  -->
</dependencies>
```

然后，刷新 Maven 项目以下载新的依赖。

### 4. **配置 MySQL 数据库连接**

打开 `src/main/resources/application.properties` 文件，添加以下 MySQL 连接配置：

```properties
# MySQL 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/myappdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

> **说明**：
>
> - `spring.datasource.url` 中的 `localhost` 可以替换为你的 MySQL 服务器的 IP 地址，
>
>   ```bash
>   mysql> SHOW VARIABLES WHERE Variable_name = 'port';
>   #用于查看端口号
>   ```
>
>   
>
>   `myappdb` 为前面创建的数据库名称。
>
> - `spring.datasource.username` 和 `spring.datasource.password` 为你设置的 MySQL 用户名和密码。
>
> - `spring.jpa.hibernate.ddl-auto=update` 会根据模型自动更新数据库表结构。

### 5. **创建实体类和 Repository**

创建一个简单的实体类来代表数据库中的表。例如，创建 `User` 实体：

```java
package com.example.myapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // getters and setters
}
```

接着，创建 `UserRepository` 接口来访问数据库：

```java
package com.example.myapp.repository;

import com.example.myapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

### 6. **创建服务层与控制器**

你可以创建一个服务层和控制器来使用这个实体和数据库交互。例如：

#### Service 类：

```java
package com.example.myapp.service;

import com.example.myapp.entity.User;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
```

#### Controller 类：

```java
package com.example.myapp.controller;

import com.example.myapp.entity.User;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
```

### 7. **运行项目并测试**

运行项目后，可以通过 POST 请求向 `http://localhost:8080/users` 发送一个 JSON 数据来创建用户，例如：

```json
{
  "name": "John",
  "email": "john@example.com"
}
```

然后使用 GET 请求访问 `http://localhost:8080/users` 来获取所有用户。

### 8. **总结**

你现在已经将 Spring Boot 应用程序连接到 MySQL 数据库，并创建了一个简单的 REST API，可以存储和检索用户数据。接下来你可以：

- 扩展实体类，添加更多的业务逻辑。
- 使用数据库进行更复杂的查询。
- 根据需要引入更多的 Spring 功能，例如 Spring Security、异步处理等。

如果你遇到任何问题或有进一步的需求，随时告诉我。



## 我的项目：基线需求

| buyers：           |           |           |                                            |               |                                                 |
| ------------------ | --------- | --------- | ------------------------------------------ | ------------- | ----------------------------------------------- |
| id                 | name      | phone     | trade_time                                 | trade_address | stauts(认为有成功、失败、待交易、候补四种状态） |
| int auto_increment | varchar20 | varchar15 | datetime                                   | varchar30     | varchar10                                       |
| good：             |           |           |                                            |               |                                                 |
| id                 | name      | price     | status（在售、冻结、已售出（直接到在售）） |               |                                                 |
| int auto_increment | varchar20 | int       | varchar10                                  |               |                                                 |
| sellers：          |           |           |                                            |               |                                                 |
| account            | password  |           |                                            |               |                                                 |
| varchar20          | varchar20 |           |                                            |               |                                                 |

1. 先连接mysql：File/new/data source/mysql
2. 再自动生成entity：https://blog.csdn.net/u014636209/article/details/118067380
3. 再手动生成repository



### 推送项目到陈悦的github仓库

1. 陈悦把我拉进colaborators
2. 本地新建目录，git clone 陈悦的仓库.git
3. 将项目文件全拉进新建的目录
4. 在IDEA中，Git\new branch,Git\Push
5. 成功

### 问题：

1. 运行main方法，出现报错：

   ```bash
   Unable to resolve name [org.hibernate.dialect.MySQL5Dialect ] as strategy [org.hibernate.dialect.Dialect]
   ```

   解决：

   是application.properties中jpa的问题，将下面这行注释掉就成功了，虽然我也不知道为什么

   ```bash
   spring.jpa.properties.hibernate.dialect="org.hibernate.dialect.MySQL5Dialect"
   ```







## after chenyue

只运行陈悦的项目，再修改完jdk之后还是报错：

```bash

```

解决：

rebuild然后invalidate cashes就好了

