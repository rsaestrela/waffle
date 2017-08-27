package io.github.rsaestrela.waffle.compiler;


import io.github.rsaestrela.waffle.exception.WaffleClassCompilerException;

import javax.tools.JavaCompiler;

public class ClassCompiler {

    private final JavaCompiler javaCompiler;

    public ClassCompiler(JavaCompiler javaCompiler) {
        this.javaCompiler = javaCompiler;
    }

    public int compile(String[] files) throws WaffleClassCompilerException {
        try {
            //https://stackoverflow.com/questions/12173294/compile-code-fully-in-memory-with-javax-tools-javacompiler
            return javaCompiler.run(null, null, null, files);
        } catch (Exception e) {
            throw new WaffleClassCompilerException(String.format("WAFFLE Error compiling %s", files));
        }
    }
}
