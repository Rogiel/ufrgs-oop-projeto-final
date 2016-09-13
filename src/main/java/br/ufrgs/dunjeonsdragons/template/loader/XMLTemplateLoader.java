package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.Template;

import java.io.File;

/**
 * Created by Rogiel on 9/13/16.
 */
public class XMLTemplateLoader implements TemplateLoader {

    private File path;

    /**
     * Creates a new XMLTemplateLoader from the given file
     *
     * @param path the file to use as the base for loading templates
     */
    XMLTemplateLoader(File path) {
        this.path = path;
    }

    @Override
    public Template load(String identifier) {
        return null;
    }

}
