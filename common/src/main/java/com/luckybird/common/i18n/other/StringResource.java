package com.luckybird.common.i18n.other;

import com.luckybird.common.context.utils.ContextUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 字符串资源类
 *
 * @author Mir
 * @modify 新云鸟
 */
public class StringResource implements MessageSource {

    public static final String RESOURCE_BUNDLE_NAME = "stringResource";

    public static final String RESOURCE_BUNDLE_SUFFIX = "properties";

    @Override
    public String getMessage(@NonNull String code, @Nullable Object[] args, String defaultMessage, @NonNull Locale locale) {
        return getMessage(code, args);
    }

    @NonNull
    @Override
    public String getMessage(@NonNull String code, @Nullable Object[] args, @NonNull Locale locale) throws NoSuchMessageException {
        return getMessage(code, args);
    }

    @NonNull
    @Override
    public String getMessage(MessageSourceResolvable resolvable, @NonNull Locale locale) throws NoSuchMessageException {
        return getMessage(Objects.requireNonNull(resolvable.getCodes())[0], resolvable.getArguments());
    }

    public String get(String key) {
        if (!StringUtils.hasText(key)) {
            return "";
        }
        return getResourceBundle().getString(key);
    }

    private String getMessage(String key, Object... args) {
        return String.format(get(key), args);
    }

    private ResourceBundle getResourceBundle() {
        Locale locale = ContextUtils.getLocale();
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale, new MultiControl());
    }

    private static class MultiControl extends ResourceBundle.Control {

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, RESOURCE_BUNDLE_SUFFIX);
            List<URL> resources = Collections.list(loader.getResources(resourceName));
            Collections.reverse(resources);
            return new MultiResourcePropertyResourceBundle(resources);
        }
    }

    private static class MultiResourcePropertyResourceBundle extends ResourceBundle {

        private final HashMap<String, Object> lookup;

        public MultiResourcePropertyResourceBundle(List<URL> urls) throws IOException {
            lookup = new HashMap<>();
            for (URL url : urls) {
                try (InputStream is = url.openConnection().getInputStream();
                     Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                    Properties properties = new Properties();
                    properties.load(reader);
                    for (Map.Entry<Object, Object> e : properties.entrySet()) {
                        lookup.put((String) e.getKey(), e.getValue());
                    }
                }
            }
        }

        @Override
        protected Object handleGetObject(@NonNull String key) {
            return lookup.get(key);
        }

        @NonNull
        @Override
        public Enumeration<String> getKeys() {
            return new ResourceBundleEnumeration(lookup.keySet(), (parent != null) ? parent.getKeys() : null);
        }

        @NonNull
        @Override
        protected Set<String> handleKeySet() {
            return lookup.keySet();
        }

        private static class ResourceBundleEnumeration implements Enumeration<String> {

            private final Set<String> set;

            private final Iterator<String> iterator;

            private final Enumeration<String> enumeration;

            private String next = null;

            public ResourceBundleEnumeration(Set<String> set, Enumeration<String> enumeration) {
                this.set = set;
                this.iterator = set.iterator();
                this.enumeration = enumeration;
            }

            @Override
            public boolean hasMoreElements() {
                if (next != null) {
                    return true;
                }
                if (iterator.hasNext()) {
                    next = iterator.next();
                } else if (enumeration != null) {
                    while (next == null && enumeration.hasMoreElements()) {
                        next = enumeration.nextElement();
                        if (set.contains(next)) {
                            next = null;
                        }
                    }
                }
                return next != null;
            }

            @Override
            public String nextElement() {
                if (!hasMoreElements()) {
                    throw new NoSuchElementException();
                }
                String ret = next;
                next = null;
                return ret;
            }
        }
    }
}
