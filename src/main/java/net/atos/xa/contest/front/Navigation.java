package net.atos.xa.contest.front;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;

/**
 */
@View(basePath = "/", extension = "xhtml", navigation = View.NavigationMode.REDIRECT)
public interface Navigation extends ViewConfig{

    @View
    class Index implements Navigation {}

    @View
    class Login implements Navigation {}

    @View(basePath = "/cards/")
    interface CardsNavigation extends Navigation {}
}
