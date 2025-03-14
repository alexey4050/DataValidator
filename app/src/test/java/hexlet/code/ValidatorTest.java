package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void testStringSchema() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        assertTrue(stringSchema.isValid(""));
        assertTrue(stringSchema.isValid(null));

        stringSchema.required();
        stringSchema.minLength(5).contains("fox");
        assertTrue(stringSchema.isValid("what does the fox say"));
        assertFalse(stringSchema.isValid("what does the dog say"));
        assertFalse(stringSchema.isValid("what"));
        assertFalse(stringSchema.isValid(null));
        assertFalse(stringSchema.isValid(""));

        assertTrue(stringSchema.contains("wh").isValid("what does the fox say"));
        assertTrue(stringSchema.contains("what").isValid("what does the fox say"));
        assertFalse(stringSchema.contains("whatthe").isValid("what does the fox say"));

        assertFalse(stringSchema.isValid("Hexlet"));
        assertFalse(stringSchema.isValid("Hex"));
        assertFalse(stringSchema.minLength(4).isValid("Hexlet"));

        var schema1 = validator.string();
        assertTrue(schema1.required().minLength(10).minLength(4).isValid("hexlet"));
    }

    @Test
    public void testNumberValidator() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();

        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.positive().isValid(null));

        numberSchema.required();
        assertFalse(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(10));
        assertFalse(numberSchema.positive().isValid(-10));
        assertFalse(numberSchema.positive().isValid(0));

        numberSchema.range(5, 10);
        assertTrue(numberSchema.isValid(5));
        assertFalse(numberSchema.isValid(4));
        assertFalse(numberSchema.isValid(11));
        assertTrue(numberSchema.isValid(10));

        numberSchema.required().range(1, 10);
        assertTrue(numberSchema.isValid(5));
        assertFalse(numberSchema.isValid(0));
        assertFalse(numberSchema.isValid(11));
        assertFalse(numberSchema.isValid(null));
    }

    @Test
    public void testMapSchema() {
        Validator validator = new Validator();
        MapSchema mapSchema = validator.map();

        assertTrue((mapSchema.isValid(null)));

        mapSchema.required();
        assertFalse(mapSchema.isValid(null));

        assertTrue(mapSchema.isValid(new HashMap<>()));
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(mapSchema.isValid(data));

        mapSchema.sizeof(2);
        assertFalse(mapSchema.isValid(data));

        data.put("key2", "value2");
        assertTrue(mapSchema.isValid(data));

        data.put("key3", "value3");
        assertFalse(mapSchema.isValid(data));
    }

    @Test
    public void testShapeWithAdditional() {
        Validator validator = new Validator();
        var schema = validator.map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required().contains("oh"));
        schemas.put("lastName", validator.string().required().minLength(2).contains("it"));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.shape(schemas).isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.shape(schemas).isValid(human3));

        Map<String, String> human4 = new HashMap<>();
        human4.put("firstName", "Alica");
        human4.put("lastName", "Cooper");
        assertFalse(schema.isValid(human4));

        Map<String, String> human5 = new HashMap<>();
        human5.put("firstName", "Johnson");
        assertFalse(schema.isValid(human5));
    }
}
