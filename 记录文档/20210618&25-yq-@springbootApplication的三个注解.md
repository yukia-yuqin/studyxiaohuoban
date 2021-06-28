# 为什么要用springBoot?

>  一般配置的内容：数据源配置、myBatis配置、事务管理配置、redis配置、

> 为什么要用springBoot? 自动装配。

- SpringBoot自动装配装配了什么？
  - 将标注了@configuration的自动装配类（在autoconfig.jar包的spring.factories文件中带有的类，一般是@ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean））的bean定义加入到bdmap中。自动 = 使用类似SPI方式（其实是springboot自己实现的一套通过ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类的方式）读取spring.factories文件。

### @Configuration

这里的@Configuration对我们来说不陌生，它就是JavaConfig形式的Spring Ioc容器的配置类使用的那个@Configuration，SpringBoot社区推荐使用基于JavaConfig的配置形式，所以，这里的启动类标注了@Configuration之后，本身其实也是一个IoC容器的配置类【自：这里逻辑反了**，应该是启动类在源码中就是IoC容器的一个配置类，而且是一个LiteConfiguration的配置类，标@Configuration只是为了显示表明**】。任何一个标注了@Configuration的Java类定义都是一个JavaConfig配置类。[自：Javaconfig就是用java语言编写的配置，而不是xml等其他语言编写的配置。启动类标注了@Configuration之后，本身其实也是一个IoC容器的配置类：为什么要把启动类作为一个IoC容器的配置类？有什么好处。自：估计是为了通过配置的后置处理引入ComponentScan和EnableAutoConfiguration等注解]

### @ComponentScan

@ComponentScan这个注解在Spring中很重要，它对应XML配置中的元素，@ComponentScan的功能其实就是自动扫描并加载符合条件的组件（比如@Component和@Repository等）或者bean定义，最终将这些bean定义加载到IoC容器中。

我们可以通过basePackages等属性来细粒度的定制@ComponentScan自动扫描的范围，如果不指定，则**默认Spring框架实现会从声明@ComponentScan所在类的package进行扫描。**

> 注：所以SpringBoot的启动类最好是放在root package下，因为默认不指定basePackages。
>
> **因为SpringApplication.run()方法的第一个参数在源码中就被视为一个主配置类，通过这个配置类上面的componentScan去扫描其他的configuration，以及@EnableAutoConfiguration这个注解去进行自动装配。**https://baijiahao.baidu.com/s?id=1671721474930539766&wfr=spider&for=pc

### @EnableAutoConfiguration

这厮才是springboot的核心！

大家是否还熟悉Spring框架提供的各种名字为@Enable开头的Annotation定义？比如@EnableScheduling、@EnableCaching、@EnableMBeanExport等，@EnableAutoConfiguration的理念和做事方式其实一脉相承，简单概括一下就是，借助@Import的支持，收集和注册特定场景相关的bean定义。

而@EnableAutoConfiguration也是借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器，仅此而已！

@EnableAutoConfiguration自动配置的魔法骑士就变成了：**从classpath中搜寻所有的META-INF/spring.factories配置文件，并将其中org.springframework.boot.autoconfigure.EnableAutoConfiguration对应的配置项通过反射（Java Refletion）实例化为对应的标注了@Configuration的JavaConfig形式的IoC容器配置类，然后汇总为一个并加载到IoC容器。**

@import 读取

使用@excludeName注解也可以。如下，@SpringBootApplication(excludeName = {"com.xxx.xxx.People"})

终极方案，不管是 Spring Boot 还是 Spring Cloud 都可以搞定，在配置文件中指定参数 `spring.autoconfigure.exclude` 进行排除：

```
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
```

或者还可以这样写：

```xml
spring.autoconfigure.exclude[0]=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
spring.autoconfigure.exclude[1]=org.springframework.boot.autoconfigure.mail.MailSenderAu
```

[Integrating Spring Boot with HSQLDB] https://www.baeldung.com/spring-boot-hsqldb

##### 自己如何使用spring 的 自动装配

- 写好配置包
- 写好spring.factories文件
- 打成普通的jar包
- 起另一个starter工程，在pom中引入刚刚的jar包
- 然后在应用所在的工程中，引入starter的jar包。。即可。。

### @Enablexxx的注解

在spring中有关于@Enablexxx的注解是开启某一项功能的注解，比如@EnableScheduling表示开启spring的定时任务。其原理是借助@Import的帮助，将所有符合自动配置条件的bean定义加载到Ioc容器。

SpringFactoriesLoader中加载配置,SpringFactoriesLoader属于Spring框架私有的一种扩展方案，其主要功能就是从指定的配置文件META-INF/spring.factories加载配置,即根据@EnableAutoConfiguration的完整类名org.springframework.boot.autoconfigure.EnableAutoConfiguration作为查找的Key,获取对应的一组@Configuration类。

### 附：Introduction to Spring Data JPA

官方文档建议使用hsqldb试用springboot的自动装配功能，这里需要导入jpa-starter和hsqldb两个包。jpa是一个接口规范，starter让springboot使用相应的数据库配置，并实例化一个hsqldb。https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa
