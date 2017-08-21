package io.github.rsaestrela.waffle.compiler;


import io.github.rsaestrela.waffle.exception.WaffleClassCompilerException;
import io.github.rsaestrela.waffle.writer.Resource;

import javax.tools.JavaCompiler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassCompiler {

    private final JavaCompiler javaCompiler;

    public ClassCompiler(JavaCompiler javaCompiler) {
        this.javaCompiler = javaCompiler;
    }

    public List<Resource> compile(List<Resource> toCompileResources) throws WaffleClassCompilerException {

        List<Resource> allResources = new ArrayList<>();
        allResources.addAll(toCompileResources);

        for (Resource toCompileResource: toCompileResources) {
            try {
                javaCompiler.run(null, null, null, toCompileResource.getFile().getPath());
                Resource compiledResource = new Resource();
                compiledResource.setFile(new File(toCompileResource.getFile().getPath().replace(".java", ".class")));
                allResources.add(compiledResource);
            } catch (Exception e) {
                throw new WaffleClassCompilerException(String.format("WAFFLE Error compiling %s",
                        toCompileResource.getFile().getPath()));
            }
        }
        return allResources;

    }
}
