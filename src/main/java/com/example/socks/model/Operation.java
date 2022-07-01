package com.example.socks.model;

public enum Operation {

    MORE_THAN("moreThan"),
    LESS_THAN("lessThan"),
    EQUAL("equal");

    private final String name;

    Operation(String operation) {
        this.name = operation;
    }

    public String getName() {
        return name;
    }

    public static Operation enumFromString(String value) throws IllegalArgumentException {
        if (value != null) {
            for (Operation operation : Operation.values()) {
                if (value.equalsIgnoreCase(operation.getName())) {
                    return operation;
                }
            }
        }
        throw new IllegalArgumentException("Incorrect operation value! You can use: "
                + MORE_THAN.getName() + ", "
                + LESS_THAN.getName() + ", "
                + EQUAL.getName() + "; "
                + "but not: " + value);
    }
}
