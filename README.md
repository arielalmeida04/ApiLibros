# ApiLibros — Backend

API REST desarrollada con **Spring Boot** para la gestión de un sistema de **libros y categorías**. Este repositorio contiene la capa **Backend** (servidor, base de datos, seguridad y pruebas). La interfaz de usuario se encuentra en el repositorio del frontend.

**Frontend:** [FrontApiBooks](https://github.com/arielalmeida04/FrontApiBooks)

---

## Sobre el proyecto

Proyecto **full stack** en desarrollo que implementa un **CRUD completo** para administrar **categorías** y **libros**. La arquitectura separa responsabilidades entre cliente (Angular) y servidor (Spring Boot), comunicándose mediante HTTP/JSON.

### Estado actual

| Módulo     | Backend (API)                              | Frontend                          |
|------------|--------------------------------------------|-----------------------------------|
| Categorías | CRUD completo implementado                 | Listado (GET) implementado        |
| Libros     | CRUD completo implementado                 | Pendiente                         |
| Seguridad  | JWT + Spring Security + roles              | Basic Auth (en desarrollo)      |
| Testing    | JUnit 5 + Mockito + JaCoCo                 | Karma + Jasmine                   |

El backend expone endpoints REST versionados bajo `/v1`, persiste datos en **MySQL** y protege los recursos con autenticación **JWT** y control de acceso por **roles** (`EMPLEADO`, `JEFE`).

---

### Seguridad — JWT (JSON Web Tokens)

Implementación completa de autenticación **stateless** con la librería **jjwt 0.12.5**:

| Componente        | Archivo                  | Responsabilidad                                      |
|-------------------|--------------------------|------------------------------------------------------|
| `JwtService`      | `service/JwtService.java`| Generación, validación y extracción de claims        |
| `JwtReqFilter`    | `filter/JwtReqFilter.java`| Filtro que intercepta requests con header `Bearer`  |
| `TokenController` | `controllers/TokenController.java` | Endpoint `POST /v1/authenticate`         |
| `ConfigSecurity`  | `config/ConfigSecurity.java` | Reglas de acceso por rol y rutas públicas     |

**Flujo de autenticación:**
1. El cliente envía credenciales a `POST /v1/authenticate`
2. El servidor valida usuario/contraseña contra la base de datos (Spring Security + JDBC)
3. Se genera un token JWT con validez de **1 hora**
4. Las peticiones protegidas incluyen el header `Authorization: Bearer <token>`

### Testing — JUnit 5 + Mockito

Pruebas unitarias con **JUnit Jupiter** y **Mockito** (incluidos en `spring-boot-starter-test`):

| Test                          | Qué valida                                           |
|-------------------------------|------------------------------------------------------|
| `CategoriaIMPLTest`           | Lógica del servicio: mock del DAO, verificación de llamadas |
| `CategoriaControllerTest`     | Controlador REST: respuesta HTTP al crear categoría  |
| `PrimeraApiRestBooksApplicationTests` | Contexto de Spring Boot carga correctamente  |
| Tests en `example/junit/`     | Ejercicios de assertions JUnit (`assertEquals`, `@BeforeEach`, etc.) |

**Patrón de testing aplicado:**
```java
@Mock ICategoriaDAO categoriaDAO;       // Simula la capa de persistencia
@InjectMocks CategoriaServiceIMPL service;  // Inyecta mocks en el servicio
when(categoriaDAO.findAll()).thenReturn(listCategorias);  // Mockito stub
verify(categoriaDAO, times(1)).findAll();  // Verifica interacción
```

### Cobertura de código — JaCoCo

El proyecto incluye el plugin **JaCoCo** configurado en `pom.xml` para generar reportes de cobertura al ejecutar `mvn test`.

---

## Stack tecnológico completo

### Backend

| Tecnología              | Versión   | Uso                                           |
|-------------------------|-----------|-----------------------------------------------|
| Java                    | 17        | Lenguaje principal                            |
| Spring Boot             | 3.5       | Framework backend                             |
| Spring Data JPA         | —         | ORM y repositorios (`ICategoriaDAO`, `ILibroDAO`) |
| Spring Security         | —         | Autenticación, autorización y roles           |
| **JWT (jjwt)**          | 0.12.5    | Tokens de autenticación stateless             |
| MySQL                   | —         | Base de datos relacional                      |
| SpringDoc OpenAPI       | 2.6       | Documentación Swagger UI                        |
| Spring Validation       | —         | Validación de datos de entrada                |
| **JUnit 5**             | —         | Framework de pruebas unitarias                |
| **Mockito**             | —         | Mocking de dependencias en tests              |
| **JaCoCo**              | 0.8.8     | Reportes de cobertura de código               |
| Maven                   | —         | Gestión de dependencias y build               |
| SLF4J                   | —         | Logging estructurado en servicios             |

---

## Buenas prácticas aplicadas

### Arquitectura en capas

```
Controller  →  Service (Interface + Impl)  →  DAO (Repository)  →  MySQL
   REST           Lógica de negocio              Persistencia
```

- **Separación de responsabilidades:** cada capa tiene un rol definido
- **Interfaces de servicio:** `ICategoriaService`, `ILibroService` desacoplan contrato de implementación
- **Inyección de dependencias:** constructor injection en servicios y configuración de seguridad

### Seguridad

- Autenticación **JWT stateless** (sin sesiones en servidor)
- Autorización por **roles** (`EMPLEADO` puede leer, `JEFE` puede crear/editar/eliminar)
- Filtro personalizado (`JwtReqFilter`) integrado en la cadena de Spring Security
- Endpoint de autenticación público; resto de operaciones protegidas

### Persistencia y transacciones

- Entidades JPA con relaciones (`Libro` → `@ManyToOne` con `Categoria`)
- `@Transactional(readOnly = true)` en consultas
- `@Transactional` en operaciones de escritura (create, update, delete)
- `FetchType.LAZY` para optimizar carga de relaciones

### API REST

- Respuestas estandarizadas con objetos `ResponseRest` (metadata + datos)
- Códigos HTTP semánticos (`200`, `404`, `400`, `500`)
- CORS habilitado para `http://localhost:4200` (Angular)
- Documentación interactiva con **Swagger UI**

### Testing

- Tests unitarios aislados con **Mockito** (sin dependencia de base de datos)
- Verificación de interacciones con `verify()` y `times()`
- Datos de prueba preparados en `@BeforeEach`
- Cobertura medida con **JaCoCo**

### Logging

- Logger SLF4J en servicios y controladores
- Registro de inicio/fin de operaciones y errores con contexto

---

## Endpoints principales

| Método   | Ruta                    | Descripción              | Rol requerido      |
|----------|-------------------------|--------------------------|--------------------|
| POST     | `/v1/authenticate`      | Obtener token JWT        | Público            |
| GET      | `/v1/categorias`        | Listar categorías        | Público            |
| GET      | `/v1/categorias/{id}`   | Buscar por ID            | EMPLEADO / JEFE    |
| POST     | `/v1/categorias`        | Crear categoría          | JEFE               |
| PUT      | `/v1/categorias/{id}`   | Actualizar categoría     | JEFE               |
| DELETE   | `/v1/categorias/{id}`   | Eliminar categoría       | JEFE               |
| GET      | `/v1/libros`            | Listar libros            | EMPLEADO / JEFE    |
| GET      | `/v1/libros/{id}`       | Buscar libro por ID      | EMPLEADO / JEFE    |
| POST     | `/v1/libros`            | Crear libro              | JEFE               |
| PUT      | `/v1/libros/{id}`       | Actualizar libro         | JEFE               |
| DELETE   | `/v1/libros/{id}`       | Eliminar libro           | JEFE               |

**Documentación Swagger:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Requisitos previos

- [Java JDK 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (o usar el wrapper incluido `mvnw`)
- [MySQL](https://www.mysql.com/) en ejecución
- Base de datos `db_books` creada

---

## Cómo iniciar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/arielalmeida04/ApiLibros.git
cd ApiLibros
```

### 2. Configurar la base de datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE db_books;
```

Verificar la configuración en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost/db_books?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
```

### 3. Ejecutar la aplicación

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

La API quedará disponible en **http://localhost:8080**

### 4. Probar la API

**Obtener token JWT:**

```bash
curl -X POST http://localhost:8080/v1/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"user\": \"admin\", \"password\": \"admin\"}"
```

**Listar categorías (público):**

```bash
curl http://localhost:8080/v1/categorias
```

**Consultar con JWT:**

```bash
curl http://localhost:8080/v1/categorias/1 \
  -H "Authorization: Bearer <tu-token-jwt>"
```

### 5. Ejecutar las pruebas

```bash
mvnw test
```

Para ver el reporte de cobertura JaCoCo, revisar `target/site/jacoco/index.html` después de ejecutar los tests.


---

## Estructura del proyecto

```
src/
├── main/java/com/company/books/backend/
│   ├── config/              # Seguridad (ConfigSecurity, AuthConfig)
│   ├── controllers/         # REST Controllers (Categoria, Libro, Token)
│   ├── filter/              # JwtReqFilter
│   ├── model/               # Entidades JPA (Categoria, Libro)
│   │   └── dao/             # Repositorios Spring Data
│   ├── request/             # DTOs de entrada (AuthRequest)
│   ├── response/            # DTOs de salida estandarizados
│   └── service/             # Interfaces + implementaciones + JwtService
└── test/java/               # Tests JUnit 5 + Mockito
    ├── controllers/         # CategoriaControllerTest
    ├── service/             # CategoriaIMPLTest
    └── example/junit/       # Ejercicios de assertions JUnit
```

---

## Próximos pasos

- Integrar JWT en el frontend Angular (reemplazar Basic Auth)
- Completar el CRUD de categorías y libros en la interfaz
- Ampliar cobertura de tests (servicio de libros, filtros JWT)
- Agregar validaciones con Bean Validation en los DTOs
- Externalizar secretos JWT en variables de entorno

---

## Autor

**Ariel Almeida** — Proyecto de demostración full stack (Spring Boot + Angular + MySQL)
