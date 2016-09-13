package br.ufrgs.dunjeonsdragons.template.loader;

import br.ufrgs.dunjeonsdragons.template.Template;

/**
 * Created by Rogiel on 9/13/16.
 */
public interface TemplateLoader {

    /**
     * Loads a template given by the identifier
     *
     * @param identifier the template identifier to load
     * @return the loaded template or NULL if the template was not found
     */
    Template load(String identifier);

}
