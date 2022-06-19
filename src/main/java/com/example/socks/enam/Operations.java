package com.example.socks.enam;

import org.springframework.web.bind.MethodArgumentNotValidException;

public enum Operations {

    MORE_THAN("moreThan"),
    LESS_THAN("lessThan"),
    EQUAL("equal");

    private final String name;

    Operations(String operation) {
        this.name = operation;
    }

    public String getName() {
        return name;
    }

    public static Operations enumFromString(String value) throws MethodArgumentNotValidException {
        if (value != null) {
            for (Operations operations : Operations.values()) {
                if (value.equalsIgnoreCase(operations.getName())) {
                    return operations;
                }
            }
        }
        throw new MethodArgumentNotValidException(null, null);
    }
}
