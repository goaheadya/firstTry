package com.ideaprojects.xqf.practice.cache;

import com.ideaprojects.xqf.practice.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js", "php", "css", "html", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "c#", "shell"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "koa", "yii", "flask", "express"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "ubuntu", "tomcat"));
        tagDTOS.add(server);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql", "redis", "sql", "oracle", "sqlserver"));
        tagDTOS.add(database);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "vim", "svn", "maven", "github"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String fliterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        //flatMap将二维数组拍平成一维数组循环输出
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //filter split中不存在于tagList中的标签
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}
