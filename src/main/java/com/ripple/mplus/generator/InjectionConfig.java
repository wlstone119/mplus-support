package com.ripple.mplus.generator;

import java.util.List;
import java.util.Map;

import com.ripple.mplus.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.ripple.mplus.generator.config.IFileCreate;
import com.ripple.mplus.generator.config.builder.ConfigBuilder;

/**
 * 抽象的对外接口
 *
 * @author hubin
 * @since 2016-12-07
 */
public abstract class InjectionConfig {

    /**
     * 全局配置
     */
    private ConfigBuilder config;

    /**
     * 自定义返回配置 Map 对象
     */
    private Map<String, Object> map;

    /**
     * 自定义输出文件
     */
    private List<FileOutConfig> fileOutConfigList;

    /**
     * 自定义判断是否创建文件
     */
    private IFileCreate fileCreate;

    /**
     * 注入自定义 Map 对象
     */
    public abstract void initMap();

    public ConfigBuilder getConfig() {
        return config;
    }

    public void setConfig(ConfigBuilder config) {
        this.config = config;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<FileOutConfig> getFileOutConfigList() {
        return fileOutConfigList;
    }

    public void setFileOutConfigList(List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList = fileOutConfigList;
    }

    public IFileCreate getFileCreate() {
        return fileCreate;
    }

    public void setFileCreate(IFileCreate fileCreate) {
        this.fileCreate = fileCreate;
    }
}
