package net.atos.xa.contest.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pouic
 * Date: 23/11/13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(AuthorizationService.class));
    }
}