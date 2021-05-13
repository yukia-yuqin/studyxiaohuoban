

## [@Autowired，@Resource和@Inject区别与实现原理](https://my.oschina.net/u/4396523/blog/3501419)

- @Autowired是spring框架提供的实现依赖注入的注解，主要支持在set方法，field，构造函数中完成bean注入，注入方式为通过类型查找bean，即byType的，如果存在多个同一类型的bean，则使用@Qualifier来指定注入哪个beanName的bean。
- 与JDK的@Resource的区别：@Resource是基于bean的名字，即beanName，来从spring的IOC容器查找bean注入的，而@Autowried是基于类型byType来查找bean注入的。
- 与JDK的@Inject的区别：@Inject也是基于类型来查找bean注入的，如果需要指定名称beanName，则可以结合使用@Named注解，而@Autowired是结合@Qualifier注解来指定名称beanName。

## **注解处理器**

- 在spring框架内部实现当中，注解实现注入主要是通过bean后置处理器BeanPostProcessor接口的实现类来生效的。BeanPostProcessor后置处理器是在spring容器启动时，创建bean对象实例后，马上执行的，对bean对象实例进行加工处理。
- @Autowired 源码分析，AutowiredAnnotationBeanPostProcessor