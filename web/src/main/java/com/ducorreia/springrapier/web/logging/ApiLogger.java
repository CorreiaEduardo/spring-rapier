package com.ducorreia.springrapier.web.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiLogger {
    private final Logger logger;

    public ApiLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public Logger getLogger() {
        return logger;
    }
}
