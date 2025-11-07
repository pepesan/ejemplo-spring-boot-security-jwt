## Documentación del proyecto

## Login
POST http://localhost:8080/api/auth/login

Usuario admin:
```json
{
    "usernameOrEmail": "admin",
    "password":"admin"
}
```

Usuario pepesan:
```json
{
    "usernameOrEmail": "pepesan",
    "password":"password"
}
```

Devolverá un objeto similar a este:
```json
{
  "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJwZXBlc2FuIiwiaWF0IjoxNzQ4NTQwMzA5LCJleHAiOjE3NDg2MjY3MDl9.l2pbT1gtthbtdtQyRk13L7-pqZrgQsAw4-c4wzQc6fYVSI2X-kB7jgdmysfvu-rM",
  "tokenType": "Bearer"
}
```

## Peticiones Protegidas
(Sólo admin)

GET http://localhost:8080/api/admin

Authorization: Bearer TOKEN

(Sólo user)

GET http://localhost:8080/api/user

Authorization: Bearer TOKEN



