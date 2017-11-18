package me.drfish.ioc.common.io;

import me.drfish.ioc.beans.BeanDefinition;
import me.drfish.ioc.beans.DefaultBeanDefinition;
import me.drfish.ioc.common.utils.StringUtils;
import me.drfish.ioc.exception.XmlConfigurationErrorException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public class XmlParser {
    private static Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private static List<String> ComponentPackageNames = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    public static Map<String, BeanDefinition> parser(Document doc) throws Exception {
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        for (Element elements : list) {
            BeanDefinition beanDefinition = new DefaultBeanDefinition();
            String packageName = elements.getAttributeValue("packageName");
            if (packageName != null) {
                ComponentPackageNames.add(packageName);
            }
            String beanDefinitionName = elements.getAttributeValue("id");
            if (beanDefinitionName == null) {
                continue;
            }
            String classpath = elements.getAttributeValue("class");
            Class<?> beanClass;
            if (classpath != null) {
                beanClass = Class.forName(classpath);
                beanDefinition.setBeanClass(beanClass);
            }
            List<Element> eles = elements.getChildren("property");
            if (eles != null && eles.size() >= 1) {
                // 进行遍历
                for (Element e : eles) {
                    // 属性名
                    String proName = e.getAttributeValue("name");
                    // bean
                    String beanDepend = e.getAttributeValue("ref");
                    // 需要注入的基本类型值
                    String value = e.getAttributeValue("value");
                    // 需要注入的基本类型的类型
                    String type = e.getAttributeValue("type");
                    // 注入基本类型属性
                    if (beanDepend == null && value != null) {
                        if (StringUtils.isNotBlank(proName) && StringUtils.isNotBlank(type)) {
                            beanDefinition.addDependOn("." + proName + "+" + type + "+" + value);
                        }

                    }
                    // 需要注入bean
                    if (beanDepend != null && value == null) {
                        beanDefinition.addDependOn(beanDepend);
                    }
                    if ((beanDepend == null && value == null) || (beanDepend != null && value != null)) {
                        logger.error("请检查property元素配置的正确性，ref和value不能同时为空或者同时有值");
                        throw new XmlConfigurationErrorException("At XmlParser,请删除property元素或添加可用的属性值");
                    }
                }
            }
            beanDefinitions.put(beanDefinitionName, beanDefinition);
        }
        return beanDefinitions;
    }

    public static BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    // 获取配置文件中的包名
    public static List<String> getComponentPackageNames() {
        return ComponentPackageNames;
    }
}
