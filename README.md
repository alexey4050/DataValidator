### Hexlet tests and linter status:
[![Actions Status](https://github.com/alexey4050/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/alexey4050/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/db6a1b2df7e54c0fd7ec/maintainability)](https://codeclimate.com/github/alexey4050/DataValidator/maintainability)
[![CI](https://github.com/alexey4050/DataValidator/actions/workflows/ci.yml/badge.svg)](https://github.com/alexey4050/DataValidator/actions/workflows/ci.yml)
[![Test Coverage](https://api.codeclimate.com/v1/badges/db6a1b2df7e54c0fd7ec/test_coverage)](https://codeclimate.com/github/alexey4050/DataValidator/test_coverage)

## Проект: Validator Data

---
### Описание
*Validator Data* - это проект для валидации данных на языке *Java*, предназначенный для проверки
корректности входных данных различных типов. В проекте поддерживается валидация строк, чисел и объектов Map, обеспечивая разработчикам эффективный способ управления данными и повышения их качества в приложении.

### Зачем нужен этот проект?

1. ___Защита от некорректных данных:___ Валидация помогает избежать ошибок, которые могут возникнуть из-за неправильного формата или значения данных.
2. ___Простота использования:___ В проекте представлен интуитивно понятный интерфейс для определения правил валидации, что облегчает интеграцию в другие проекты.
3. ___Улучшение качества кода:___ Использование валидации делает код более предсказуемым, упрощает отладку и улучшает читаемость.
4. ___Гибкость:___ Пользователи могут настраивать правила валидации под специфические требования своего приложения.


### Как пользоваться?

***1. Добавление классов в проект:***

Создайте три класса в пакете Вашего проекта: *StringSchema*, *NumberSchema*, *MapSchema*.

***StringSchema.java***

```java
package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addValidation("required", value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addValidation("minLength", value -> value == null
                || value.length() >= length);
        return this;
    }

    public StringSchema contains(String subString) {
        addValidation("contains", value -> value == null
                || value.contains(subString));
        return this;
    }
}
```
***NumberSchema.java***

```java
package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public BaseSchema<Integer> required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidation("positive", value -> value == null || value > 0);
        return this;
    }

    public NumberSchema range(int startWithNumber, int endWithNumber) {
        addValidation("range", value -> value == null || value >= startWithNumber 
                && value <= endWithNumber);
        return this;
    }
}
```
***MapSchema.java***

```java
import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    @Override
    public BaseSchema<Map<?, ?>> required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> value ==  null || value.size() == size);
        return this;
    }
    @SuppressWarnings("unchecked")
    public <K, V> MapSchema shape(Map<K, BaseSchema<V>> schemas) {
        addValidation("shape", map -> map != null && schemas.entrySet().stream()
                .allMatch(schema -> schema.getValue().isValid((V) map.get(schema.getKey()))));
        return this;
    }
}
```
***2. Примеры использования***

```java

package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Validator validator = new Validator();
        // Пример валидации строки
        StringSchema stringValidator = new StringSchema().required().minLength(4);
        System.out.println(stringValidator.isValid("")); // false
        System.out.println(stringValidator.isValid("Test")); // true
        System.out.println(stringValidator.isValid("Te")); // false

        // Пример валидации числа
        NumberSchema numberValidator = validator.number().positive();
        System.out.println(numberValidator.isValid(50)); // true
        System.out.println(numberValidator.required().isValid(null)); // false
        System.out.println(numberValidator.isValid(-5)); // false

        // Пример валидации Map
        MapSchema mapValidator = validator.map();
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("key1", "value1");
        validMap.put("key2", 2);
        System.out.println(mapValidator.isValid(validMap)); // true

        Map<String, Object> invalidMap = new HashMap<>();
        invalidMap.put("key1", "value1");
        System.out.println(mapValidator.required().isValid(invalidMap)); // true

        // Пример валидации специальной схемы с дополнительными полями
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required().contains("oh"));
        schemas.put("lastName", validator.string().required().minLength(2).contains("it"));

        human1.put("firstName", "John");
        System.out.println(mapValidator.isValid(human1)); // true

        human1.put("lastName", "Smith");
        System.out.println(mapValidator.isValid(human1)); // true
    }
}
```
