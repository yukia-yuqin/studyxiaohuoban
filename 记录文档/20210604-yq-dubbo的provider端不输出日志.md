- ```
  layout: post
  title: dubbo的provider端控制台不输出日志
  tags: java
  categories: java
  ```

  ### 背景：dubbo的provider端控制台不输出日志

  三个现象：binding过多，log4j没有appender，dubbo打印不出日志

  ### 原因

  - binding过多，有logback-classic,logback-core,log4j,log4j-to-slf4j,jboss-logging,slf4j-log4j12,spring-boot-starter-logging,jul-to-slf4j,slf4j-api

  - dubbo默认使用log4j。

  #### 解决步骤

  - 去掉log4j日志，效果：不提示log4j的warn，dubbo打印日志
  - 进一步去掉slf4j-log4j12：不提示Binding过多。

  #### 原理

  dubbo的loggerFactory源码，查找日志适配器部分
  // 查找常用的日志框架

  ```
  static {
  	    String logger = System.getProperty("dubbo.application.logger");
  	    if ("slf4j".equals(logger)) {
      		setLoggerAdapter(new Slf4jLoggerAdapter());
      	} else if ("jcl".equals(logger)) {
      		setLoggerAdapter(new JclLoggerAdapter());
      	} else if ("log4j".equals(logger)) {
      		setLoggerAdapter(new Log4jLoggerAdapter());
      	} else if ("jdk".equals(logger)) {
      		setLoggerAdapter(new JdkLoggerAdapter());
      	} else {
      		try {
      			setLoggerAdapter(new Log4jLoggerAdapter());
              } catch (Throwable e1) {
                  try {
                  	setLoggerAdapter(new Slf4jLoggerAdapter());
                  } catch (Throwable e2) {
                      try {
                      	setLoggerAdapter(new JclLoggerAdapter());
                      } catch (Throwable e3) {
                          setLoggerAdapter(new JdkLoggerAdapter());
                      }
                  }
              }
      	}
  	}
  ```

  ​	

  ### slf4j源码解读

  - Binding是一个什么动作？  找到SLF4JServiceProvider的实现
  - SLF4J是如何整合不同的日志框架的?  增加一个第三方的通用接口。
  - Class Path中为什么只能有且仅有一种日志框架的binding?

  #### 几个重要模块：

  slf4j-api 是 SLF4J 的核心模块，所有的日志抽象接口都放在这里面；slf4j-simple 是一种 SLF4J 日志规范的实现，类似于 logback、log4j； slf4j-log4j12 和 slf4j-jdk14 是通过适配器的方式使得 log4j 和 JDK logger 满足 SLF4J 日志规范，而 logback 是天生支持 SLF4J 规范，所以不再需要适配器。

  - slf4j-api   LoggerFactory.java

  ``` 
  public static Logger getLogger(String name) {
       ILoggerFactory iLoggerFactory = getILoggerFactory();
       return iLoggerFactory.getLogger(name);
   }
  
  static SLF4JServiceProvider getProvider() {
          if (INITIALIZATION_STATE == UNINITIALIZED) {
              synchronized (LoggerFactory.class) {
                  if (INITIALIZATION_STATE == UNINITIALIZED) {
                      INITIALIZATION_STATE = ONGOING_INITIALIZATION;
                      performInitialization();
                  }
              }
          }
          switch (INITIALIZATION_STATE) {
          case SUCCESSFUL_INITIALIZATION:
              return PROVIDER;
          case NOP_FALLBACK_INITIALIZATION:
              return NOP_FALLBACK_FACTORY;
          case FAILED_INITIALIZATION:
              throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
          case ONGOING_INITIALIZATION:
              // support re-entrant behavior.
              // See also http://jira.qos.ch/browse/SLF4J-97
              return SUBST_PROVIDER;
          }
          throw new IllegalStateException("Unreachable code");
      }
  
  private final static void bind() {
      try {
          List<SLF4JServiceProvider> providersList = findServiceProviders();
          reportMultipleBindingAmbiguity(providersList);
          if (providersList != null && !providersList.isEmpty()) {
             PROVIDER = providersList.get(0);
             // SLF4JServiceProvider.initialize() is intended to be called here and nowhere else.
             PROVIDER.initialize();
             INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
              reportActualBinding(providersList);
          } else {
              INITIALIZATION_STATE = NOP_FALLBACK_INITIALIZATION;
              Util.report("No SLF4J providers were found.");
              Util.report("Defaulting to no-operation (NOP) logger implementation");
              Util.report("See " + NO_PROVIDERS_URL + " for further details.");
  		}
      }
  
  
   private static List<SLF4JServiceProvider> findServiceProviders() {
          ServiceLoader<SLF4JServiceProvider> serviceLoader = ServiceLoader.load(SLF4JServiceProvider.class);  // 一个基于java.util.serviceLoader范式实现的接口
          List<SLF4JServiceProvider> providerList = new ArrayList<SLF4JServiceProvider>();
          for (SLF4JServiceProvider provider : serviceLoader) {
              providerList.add(provider);
          }
          return providerList;
      }
  
  private static void reportMultipleBindingAmbiguity(List<SLF4JServiceProvider> providerList) {
      if (isAmbiguousProviderList(providerList)) {
          Util.report("Class path contains multiple SLF4J providers.");
          for (SLF4JServiceProvider provider : providerList) {
              Util.report("Found provider [" + provider + "]");
          }
          Util.report("See " + MULTIPLE_BINDINGS_URL + " for an explanation.");
      }
  }
  ```

  

  #### SLF4J适配层

  ![image-20210627145939601](./img/image-20210627145939601.png)

  **slf4j默认实现是logback，如果要使用 log4j 和 java.util.logging 作为 slf4j 的实现，就需要一个适配层，在 slf4j 的项目中，slf4j-log4j12 和 slf4j-jdk14 就是该适配层。其实该适配层的核心思想就是设计模式中的适配器模式。本节以 slf4j-log4j12 模块来说明。**
   final transient org.apache.log4j.Logger logger;  实际代理了org.apache.log4j.Logger类,所以对Log4jLoggerAdapter的所有方法调用,最终调用的都是org.apache.log4j.Logger对应的方法

  

  ### 解决方法

  - 方法一：强制dubbo使用slf4j，以下任意一种配置均可
    方法1.1  <dubbo:application name="knowledge-analysis" logger="slf4j"/>
    方法1.2 -Ddubbo.application.logger=slf4j
    方法1.3  System.setProperty("dubbo.application.logger","slf4j");
    观察到：
    c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.log4j.Log4jLoggerAdapter  ×
    变为：
    c.a.d.common.logger.LoggerFactory - using logger: com.alibaba.dubbo.common.logger.slf4j.Slf4jLoggerAdapter  √

  ### 拓展

  ##### 关于Log4j2

  在日志框架中，以Log4j + Slf4j的使用组合最为常见，但是我们知道Log4j目前已经停止更新了。Apache推出了新的Log4j2来代替Log4j，Log4j2是对Log4j的升级，与其前身Log4j相比有了显着的改进，并提供了许多Logback可用的改进，同时解决了Logback体系结构中的一些固有问题。因此，Log4j2 + Slf4j应该是未来的大势所趋。

  #### 代理和适配的区别

  适配器模式是因为新旧接口不一致导致出现了客户端无法得到满足的问题，但是，由于旧的接口是不能被完全重构掉的，因为我们还想使用实现了这个接口的一些服务。那么为了使用以前实现旧接口的服务，我们就应该把新的接口转换成旧接口，实现这个转换的类就是抽象意义的转换器。相比于适配器的应用场景，代理就不一样了，虽然代理也同样是增加了一层，但是，代理提供的接口和原本的接口是一样的，代理模式的作用是不把实现直接暴露给client，而是通过代理这个层，代理能够做一些处理。