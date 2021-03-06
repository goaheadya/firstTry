## springboot项目实战

## 资料
[Spring 文档](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es](https://elasticsearch.cn/explore)  
[Github deploy key](https://docs.github.com/en/developers/overview/managing-deploy-keys#deploy-keys)  
[BootStrap](https://v3.bootcss.com/getting-started)  
[Github apps](https://docs.github.com/en/developers/apps)  
[Github oauth apps](https://docs.github.com/en/developers/apps/building-oauth-apps)  
[Github creating oauth apps](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app)  
[授权OAuth应用程序](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps)  
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#bot-features-embedded-database-support)  
[菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)  
[BootStrap](https://v3.bootcss.com/css)  
[Lombok](https://www.projectlombok.org)    
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)   
[Spring MVC](https://docs.spring.io/spring-framework/docs/5.0.3.RELEASE/spring-framework-reference/web.html)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)

## 工具
[Git下载](https://git-scm.com/download)  
[Visual Paradigm](https://www.visual-paradigm.com)  
[Flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven)    
[POSTMAN](https://web.postman.co/)  
[Markdown开源工具](https://pandao.github.io/editor.md/#download)  

## 脚本
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    BIO          VARCHAR(256),
    constraint USER_PK
        primary key (ID)
);
```

```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
mvn dependency:resolve -Dclassifier=sources   //下载源码
```


