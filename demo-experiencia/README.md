# API de Experiencia
## Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3
- **Programación Reactiva:** Spring WebFlux
- **Seguridad:** Spring Security (JWT)
- **Persistencia:** R2DBC con H2 Database (In-Memory)
- **Utilidades:** Lombok, Swagger
- **Cliente de Pruebas:** Postman

## ⚙️ Requisitos Previos
- Tener **Java 17** instalado
- Tener **Maven** instalado (`mvn -v` para verificar)

##  🚀  Cómo ejecutar el proyecto
~~~ bash
    mvn clean package
    mvn spring-boot:run
~~~

## Swagger
- [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Autenticación con JWT
~~~ http request
POST /auth/login
Content-Type: application/json

{
    "email": "admin@google.com",
    "password": "123456"
}
~~~

Luego de obtener el token, se debe agregar en el header de las peticiones:
~~~ http request
Authorization: Bearer <token>
~~~

## 🛠 **Endpoints**

| **Método** | **Endpoint**           | **Descripción**                            | **Cuerpo (Request Body)**               | **Respuesta (Response Body)**        |
|------------|------------------------|--------------------------------------------|-----------------------------------------|--------------------------------------|
| `POST`     | `/experience/{userId}` | Procesa una transacción de tipo de cambio. | `ExchangeRateTransactionRequest` (JSON) | `ExchangeTransactionResponse` (JSON) |

### Request Body

~~~ http request
POST /experience/{userId}
Content-Type: application/json
Authorization: Bearer <token>

{
    "amount": 100.0,
    "source_currency": "USD",
    "target_currency": "EUR",
    "exchange_rate": 0.85
}
~~~

### Response Body
~~~ http request
{
    "id": 1,
    "username": "admin",
    "initial_amount": 100.0,
    "final_amount": 85.0,
    "exchange_rate": 0.85,
    "from_currency": "USD",
    "to_currency": "EUR",
    "transaction_date": "2025-02-15 14:30:00"
}

~~~