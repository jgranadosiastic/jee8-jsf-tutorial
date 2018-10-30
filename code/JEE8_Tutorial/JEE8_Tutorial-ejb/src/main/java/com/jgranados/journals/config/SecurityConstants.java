package com.jgranados.journals.config;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 29.10.2018
 * @Title: SecurityConstants
 * @Description: description
 *
 * Changes History
 */
public class SecurityConstants {

    public static final String DATASOURCE_LOOKUP = "${'jdbc/__JEE8Tutorial'}";
    public static final String CALLER_QUERY = "select user_password from User where user_name = ?";
    public static final String GROUPS_QUERY = "select user_role from User where user_name = ?";
    public static final String PBKDF_ITERATIONS = "Pbkdf2PasswordHash.Iterations=3072";
    public static final String PBKDF_ALGORITHM = "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256";
    public static final String PBKDF_SALT_SIZE = "Pbkdf2PasswordHash.SaltSizeBytes=64";
}
