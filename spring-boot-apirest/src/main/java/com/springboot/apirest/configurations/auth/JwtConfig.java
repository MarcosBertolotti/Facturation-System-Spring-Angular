package com.springboot.apirest.configurations.auth;

public class JwtConfig {

    public static final String SECRET_KEY = "alguna.clave.secreta.1231512423";

    public static final String RSA_PRIVATE= "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAr//2NwOxWdZ+myspgkZttpPtnFC5uNP+E0k8hFOtRl9KQA3h\n" +
            "OyHxMxMQs7izb87FrB1P1lFXjNCEIQgRcX44/rS+8KUXLOEsLefk7ZJZx5wNZAiG\n" +
            "EsK+4jnckcm30qLk8t+E/ioKYGBYVIcB7ojOYgUNPJLKL4W6v7bjVbL+e1RTbgh8\n" +
            "9EqV/Y+l0TckWlbynUJ8GJ53A6TxoxlGxT9mfIA3TCfpQj6uhGtOIyix+yMFoIBd\n" +
            "dNvxXUZk07kzXlYrRv1GKGJuLPbm6bpsd2AzLyJSkOOEXYneN9ZNDDV6nVeyt+PI\n" +
            "bgpSzSY4lGpIRDqeW4NZooOBJoQDQlmoqGEHUwIDAQABAoIBAHY6szKHx4a7IXep\n" +
            "Sgwata71tqPQUsLHniFkjEO0Vm00rBNVf50Skw4lW43voJKWS5HwauZk5DRTCr+J\n" +
            "xPYBvdXUo4PS0zux09TOt6mkK618tj4hv7cwnbwo9HP3FwZnqPdCkfuPUEMn1Y6K\n" +
            "1xBfVIziKd8sGKhN5ILPH+48/NjpUdFP9rNi/P8SJ1nNYRK5LAFaQfn/q4Ta8o/d\n" +
            "L1RXXmRCay5mYpvUoP9al3CPAP+NR1HKZgTJvYjg39UbBAfsIPdGKU5YDTKyREGI\n" +
            "TNlpcdDaN75cQwI7kSwto0j78XENBI416835ONPtqPOaUHi4gx1rphhVXTyjG7Zf\n" +
            "tOQm2DECgYEA5STVSwjdCQ8YINzMUpjwr/bWwaEEhLGmis4p4gGTueHub1DeOV8T\n" +
            "AZeoLz1mlpjZKhEHbbMR0xVsYqELnawWHAN6MrY1+rPcwss+7oAAqHKuz2X2gtAW\n" +
            "Vu8Y6+/esmfxG7008R49q9s5tEjmsftu+CHlNwvSDVj5Tqv/40Qc3CsCgYEAxKCc\n" +
            "SYaobIZcu8uNR2+164FZdMTBVWKjmBi1gWBUu1LKrVy0/qkINif0R+c0HDfSIslW\n" +
            "gH/17/SsVxhlTJkKs/THH8VFoFeVG8yVGTsk1uRRXSt+4bsMV1GV1eGnZyKTR4X+\n" +
            "OEl7+8WGQ0n3OKWtOJSC7hwGkTVvKgQWK+ugZXkCgYArvI0NpmJyITRsyVgih9ig\n" +
            "7Y9uI+q13Sa5pcIuxjhJKYuWKaxQ7Qm2qxjJ84FKS2Bf/CIb2SsUzCkE5lJORRNv\n" +
            "Q2ammUrSUFRZxet8IGh/vmwJYB/eNhOdgkOSfRVXnilSLxoUz73buJtaAvQtkp1K\n" +
            "tUWH7TSpoYrsbj4IRe7wQwKBgQCgPkxgV4+3MiEtIwx2ZYIa9gWTdi4higvNDoYN\n" +
            "dBZZu3VVp7IRwGvixtj8KF59iLiGhRMsEhy6e+pf6I/VxvvEAP6u07SUogvw5d+0\n" +
            "+lBhmRz+E06KZEgAeYrlDcvB75mbtoWiFbEnVKjA+eyyi8MJ026I4uovOR7pXP88\n" +
            "uAC18QKBgFWGQLz9knvHlLgunQGU1VDDmlOaa5cZtt1Cy0Qj6YoOottRFeqYMN6q\n" +
            "x/2A8GUYOEjK4bd51Cz/8gT7GvNogEwGHG0q5umHg5V+Szhc0C+qjx1HJ3t/DGm5\n" +
            "fnhC5JQgjOlAm/W7Lmlv7dHJC0jpKEjB3+ptrtwb08BhL3EVgznM\n" +
            "-----END RSA PRIVATE KEY-----\n";

    public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr//2NwOxWdZ+myspgkZt\n" +
            "tpPtnFC5uNP+E0k8hFOtRl9KQA3hOyHxMxMQs7izb87FrB1P1lFXjNCEIQgRcX44\n" +
            "/rS+8KUXLOEsLefk7ZJZx5wNZAiGEsK+4jnckcm30qLk8t+E/ioKYGBYVIcB7ojO\n" +
            "YgUNPJLKL4W6v7bjVbL+e1RTbgh89EqV/Y+l0TckWlbynUJ8GJ53A6TxoxlGxT9m\n" +
            "fIA3TCfpQj6uhGtOIyix+yMFoIBddNvxXUZk07kzXlYrRv1GKGJuLPbm6bpsd2Az\n" +
            "LyJSkOOEXYneN9ZNDDV6nVeyt+PIbgpSzSY4lGpIRDqeW4NZooOBJoQDQlmoqGEH\n" +
            "UwIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";
}
