package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void testStringSchema() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        stringSchema.minLength(5).contains("test").required();

        assertFalse(stringSchema.isValid(null));
        assertFalse(stringSchema.isValid(""));

        assertTrue(stringSchema.isValid("this is a test string"));
        assertFalse(stringSchema.isValid("this string does not contain the keyword"));
        assertTrue(stringSchema.isValid("this string contains test"));

        assertFalse(stringSchema.isValid("Hexlet"));
        assertFalse(stringSchema.isValid("Hex"));
    }

    @Test
    public void testNumberValidator() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();

        assertTrue(numberSchema.isValid(null));

        numberSchema.required();
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(10));
        assertFalse(numberSchema.positive().isValid(-10));
        assertFalse(numberSchema.positive().isValid(0));

        numberSchema.range(5, 10);
        assertTrue(numberSchema.isValid(5));
        assertFalse(numberSchema.isValid(4));
        assertFalse(numberSchema.isValid(11));
        assertTrue(numberSchema.isValid(10));
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
}
