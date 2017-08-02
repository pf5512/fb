
package com.footballer.filter;

import com.footballer.filter.context.ApplicationContext;

/**
 * Created by ian on 7/28/14.
 */
public class AppContextHolder {

    private static final ThreadLocal<ApplicationContext> contextHolder = new ThreadLocal<>();
    
    public static ApplicationContext getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }

    public static void setContext(ApplicationContext appContext) {
        contextHolder.set(appContext);
    }

}
