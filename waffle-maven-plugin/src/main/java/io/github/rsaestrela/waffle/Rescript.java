package io.github.rsaestrela.waffle;

import io.github.rsaestrela.waffle.exception.WaffleException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "rescript", defaultPhase = LifecyclePhase.INSTALL)
public class Rescript extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException {
        try {
            Waffle.rescript(new String[]{});
        } catch (WaffleException e) {
            e.printStackTrace();
        }
    }
}