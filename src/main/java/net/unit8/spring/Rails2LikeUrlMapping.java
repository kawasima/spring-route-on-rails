package net.unit8.spring;

import net.unit8.http.router.Options;
import net.unit8.http.router.Routes;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Rails2LikeUrlMapping extends AbstractHandlerMapping {
    public Rails2LikeUrlMapping(String path) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            Routes.load(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        Options options = Routes.recognizePath(request.getRequestURI(), request.getMethod());
        String handler = options.getString("controller");
        String action = options.getString("action");
        final AutowireCapableBeanFactory beanFactory = obtainApplicationContext().getAutowireCapableBeanFactory();
        final Class<?> clazz = beanFactory.getType(handler);
        Optional<Method> method = Stream.of(clazz.getMethods())
                .filter(m -> Objects.equals(m.getName(), action))
                .findFirst();

        Options params = options.except("controller", "action");
        Map<String, String> variables = (Map<String, String>)Optional.ofNullable(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                .orElse(new HashMap<String, String>());

        for (String key : params.keySet()) {
            variables.put(key, params.getString(key));
        }
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, variables);

        return method.map(m -> new HandlerMethod(beanFactory.getBean(handler), m))
                .orElseThrow();
    }
}
