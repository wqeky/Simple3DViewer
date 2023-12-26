package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0", "5.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.02f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "ba"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(List.of("1.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParsePolygon01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3"));
        Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 5);

        ArrayList<Integer> vertexIndices = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> textureVertexIndices = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> normalIndices = new ArrayList<>(Arrays.asList(0, 1, 2));

        Polygon expectedResult = new Polygon();
        expectedResult.setVertexIndices(vertexIndices);
        expectedResult.setTextureVertexIndices(textureVertexIndices);
        expectedResult.setNormalIndices(normalIndices);

        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParsePolygon02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3"));
        Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 5);

        ArrayList<Integer> vertexIndices = new ArrayList<>(Arrays.asList(10, 11, 12));
        ArrayList<Integer> textureVertexIndices = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> normalIndices = new ArrayList<>(Arrays.asList(0, 1, 2));

        Polygon expectedResult = new Polygon();
        expectedResult.setVertexIndices(vertexIndices);
        expectedResult.setTextureVertexIndices(textureVertexIndices);
        expectedResult.setNormalIndices(normalIndices);

        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParsePolygon03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParsePolygon04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2"));
        try {
            Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few polygon arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParsePolygon05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1/1", "2/2/2/2", "3/3/3/3"));
        try {
            Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParsePolygon06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/-3"));
        try {
            Polygon result = ObjReader.parsePolygon(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Negative index of point.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}