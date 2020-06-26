package com.ywy.condition;

import com.ywy.pojo.Blue;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


//自定义逻辑返回需要导入的组件的全类名
public class MyImportSelector implements ImportSelector {

    //返回值就是要导入到容器中的组件的全类名
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.ywy.pojo.Blue","com.ywy.pojo.Yellow"};
    }
}
