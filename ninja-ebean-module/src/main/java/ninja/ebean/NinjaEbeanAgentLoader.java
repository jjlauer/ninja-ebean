package ninja.ebean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;

public class NinjaEbeanAgentLoader {

    static public void tryLoadAgent(Logger logger, String params) {
        try {
            // try to load the agent loader
            Class<?> agentLoaderClass = Class.forName("org.avaje.agentloader.AgentLoader", true, NinjaEbeanAgentLoader.class.getClassLoader());
            
            // find the matching method
            Method loadAgentMethod = agentLoaderClass.getMethod("loadAgentFromClasspath", String.class, String.class);
            
            // try to load the agent into the running JVM process
            boolean loaded = (boolean)loadAgentMethod.invoke(null, "avaje-ebeanorm-agent", params);
            if (!loaded) {
                logger.error("avaje-ebeanorm-agent not found on classpath (not dynamically loaded)");
            } else {
                logger.info("avaje-ebeanorm-agent successfully loaded");
            }
        } catch (ClassNotFoundException e) {
            logger.error("avaje-agentloader not found on classpath (unable to try loading agent)");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("Unable to find/invoke loadAgentFromClasspath method", e);
        }
    }
    
}
