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

    @View(basePath = "/site/")
    interface BackendNavigation extends Navigation {}

    @View
    class BinRange implements BackendNavigation {}
}
