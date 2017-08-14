package io.github.rsaestrela.waffle.writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.rsaestrela.waffle.exception.WaffleClassWriterException;
import io.github.rsaestrela.waffle.exception.WaffleRuntimeException;
import io.github.rsaestrela.waffle.processor.OutputClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public abstract class ClassWriter<T extends OutputClass> {

    private static final String TEMPLATE_FOLDER = "templates/";
    private static final String ENCODING = "UTF-8";
    private final String classPackage;
    private Configuration freemarkerConfig;

    public ClassWriter(String classPackage)  {
        this.classPackage = classPackage;
        configureFreemarker();
    }

    private void configureFreemarker() {
        freemarkerConfig = new Configuration();
        freemarkerConfig.setDefaultEncoding(ENCODING);
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(
                    new File(Thread.currentThread().getContextClassLoader().getResource(TEMPLATE_FOLDER).getFile()));
        } catch (IOException e) {
            throw new WaffleRuntimeException("WAFFLE-DEV wrong freemarker configuration");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void write(String className, String classTemplate, T clazz) throws WaffleClassWriterException {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("clazz", clazz);
        try {
            String packageDir = clazz.getNamespace().replace(".","/");
            new File(String.format("target/classes/%s/%s", packageDir, classPackage)).mkdirs();
            File file = new File(String.format("target/classes/%s/%s/%s.java", packageDir, classPackage, className));
            Writer fileWriter = new FileWriter(file);
            getTemplate(classTemplate).process(varMap, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            throw new WaffleClassWriterException(String.format("WAFFLE Error writing %s class", className));
        }
    }

    private Template getTemplate(String classTemplate) throws WaffleClassWriterException, IOException {
        return freemarkerConfig.getTemplate(classTemplate);
    }

    public void rollback() {
        //todo
    }
}
