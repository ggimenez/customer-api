package com.gabri.coding.customerapi.security;

public enum Roles {

    ADMIN("admin");

    final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
