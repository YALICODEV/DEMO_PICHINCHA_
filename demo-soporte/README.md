# API de Cambio de Divisas

## Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3
- **Programación Reactiva:** Spring WebFlux
- **Seguridad:** Spring Security (JWT)
- **Persistencia:** R2DBC con H2 Database (In-Memory)
- **Documentación:** Swagger
- **Cliente de Pruebas:** Postman

## ⚙️ Requisitos Previos
- Tener **Java 17** instalado
- Tener **Maven** instalado (`mvn -v` para verificar)

## 🚀 Cómo ejecutar el proyecto
Para ejecutar el proyecto, usa los siguientes comandos:

```bash
mvn clean package
mvn spring-boot:run
```
## Swagger
Para acceder a la documentación de la API, puedes ingresar a la siguiente URL:
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Endpoints
## 🛠 **Endpoints**

| **Método** | **Endpoint**                                       | **Descripción**                               | **Cuerpo (Request Body)**               | **Respuesta (Response Body)**        |
|------------|----------------------------------------------------|-----------------------------------------------|-----------------------------------------|--------------------------------------|
| `POST`     | `/exchange-rate/transaction`                       | Procesa una transacción de cambio de divisas. | `ExchangeRateTransactionRequest` (JSON) | `ExchangeTransactionResponse` (JSON) |
| `POST`     | `/exchange-rate`                                   | Crea una nueva tasa de cambio.                | `ExchangeRateRequest` (JSON)            | `ExchangeRateResponse` (JSON)        |
| `PUT`      | `/exchange-rate`                                   | Actualiza una tasa de cambio existente.       | `ExchangeRateRequest` (JSON)            | `ExchangeRateResponse` (JSON)        |
| `GET`      | `/exchange-rate/{sourceCurrency}/{targetCurrency}` | Obtiene la tasa de cambio entre dos monedas.  | N/A                                     | `ExchangeRateResponse` (JSON)        |
