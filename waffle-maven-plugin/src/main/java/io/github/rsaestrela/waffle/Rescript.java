package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.exception.WaffleException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "rescript", defaultPhase = LifecyclePhase.INSTALL)
public class Rescript extends AbstractMojo {

    @Parameter
    private String[] definitions;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            Waffle.rescript(definitions);
        } catch (WaffleException e) {
            e.printStackTrace();
        }
    }

    public String[] getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String[] definitions) {
        this.definitions = definitions;
    }
}