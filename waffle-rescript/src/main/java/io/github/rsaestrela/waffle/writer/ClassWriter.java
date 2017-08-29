package io.github.rsaestrela.waffle.writer;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
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
    private static final String TARGET_CLASSES = "target/generated-sources/java/waffle";
    private static final String ENCODING = "UTF-8";
    private final String classPackage;
    private final Configuration freemarkerConfig;

    public ClassWriter(String classPackage)  {
        this.classPackage = classPackage;
        this.freemarkerConfig = new Configuration();
        configureFreemarker();
    }

    private void configureFreemarker() {
        freemarkerConfig.setDefaultEncoding(ENCODING);
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(
                    new File(Thread.currentThread().getContextClassLoader().getResource(TEMPLATE_FOLDER).getFile()));
        } catch (IOException e) {
            throw new WaffleRuntimeException("WAFFLE-DEV wrong freemarker configuration");
        }
    }

    protected Resource write(String className, String classTemplate, T clazz) throws WaffleClassWriterException {
        String packageDir = clazz.getNamespace().replace(".","/");
        createDir(packageDir);
        return processTemplate(classTemplate, clazz, packageDir, className);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDir(String packageDir) throws WaffleClassWriterException {
        String directory = String.format("%s/%s/%s", TARGET_CLASSES, packageDir, classPackage);
        File dir = new File(directory);
        try {
            dir.mkdirs();
        } catch (Exception e) {
            throw new WaffleClassWriterException(String.format("WAFFLE Error writing %s directory", directory));
        }
    }

    private Resource processTemplate(String classTemplate, T clazz, String packageDir, String className)
            throws WaffleClassWriterException {
        String file = String.format("%s/%s/%s/%s.java", TARGET_CLASSES, packageDir, classPackage, className);
        File javaFile = new File(file);
        try {
            Writer fileWriter = new FileWriter(javaFile);
            Map<String, Object> varMap = new HashMap<>();
            varMap.put("clazz", clazz);
            freemarkerConfig.getTemplate(classTemplate).process(varMap, fileWriter);
            fileWriter.close();
            Resource resource = new Resource();
            resource.setFile(javaFile);
            return resource;
        } catch (IOException e) {
            throw new WaffleClassWriterException(String.format("WAFFLE Error writing %s", file));
        } catch (TemplateException e) {
            throw new WaffleClassWriterException(
                    String.format("WAFFLE Error processing template %s for %s", classTemplate, className)
            );
        }
    }

}
