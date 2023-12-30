package com.cgvsu.objreader;

import com.cgvsu.math.*;
import com.cgvsu.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader { // класс чтения модели из файла

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public static Model readModel(final String fileContent) throws ObjReaderException {
		Model result = new Model();
		int lineIndex = 0;
		Scanner scanner = new Scanner(fileContent);

		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String token = wordsInLine.get(0);
			wordsInLine.remove(0);
			lineIndex++;

			switch (token) {
				case OBJ_VERTEX_TOKEN -> result.addVertex(parseVertex(wordsInLine, lineIndex));
				case OBJ_TEXTURE_TOKEN -> result.addTextureVertex(parseTextureVertex(wordsInLine, lineIndex));
				case OBJ_NORMAL_TOKEN -> result.addNormal(parseNormal(wordsInLine, lineIndex));
				case OBJ_FACE_TOKEN -> result.addPolygon(parsePolygon(wordsInLine, lineIndex));
				default -> {
				}
			}
		}

		int maxVertexIndex = -1, maxTextureVertexIndex = -1, maxNormalIndex = -1;
		int polygonIndex = 0;

		for (Polygon polygon : result.getPolygons()) {
			polygonIndex++;

			for (int vertexIndex : polygon.getVertexIndices()) {
				if (vertexIndex > maxVertexIndex) {
					maxVertexIndex = vertexIndex;
				}
			}
			if (maxVertexIndex > result.getVertices().size()) {
				throw new ObjReaderException("index of vertex is out of bounds in polygon " + polygonIndex + ".");
			}

			for (int textureVertexIndex : polygon.getTextureVertexIndices()) {
				if (textureVertexIndex > maxTextureVertexIndex) {
					maxTextureVertexIndex = textureVertexIndex;
				}
			}
			if (maxTextureVertexIndex > result.getTextureVertices().size()) {
				throw new ObjReaderException("index of texture vertex is out of bounds in polygon " + polygonIndex + ".");
			}

			for (int normalIndex : polygon.getNormalIndices()) {
				if (normalIndex > maxNormalIndex) {
					maxNormalIndex = normalIndex;
				}
			}
			if (maxNormalIndex > result.getNormals().size()) {
				throw new ObjReaderException("index of normal is out of bounds in polygon " + polygonIndex + ".");
			}
		}

		if (result.equalsEmptyModel()) {
			throw new ObjReaderException("incorrect .obj file");
		}

		return result;
	}

	protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, final int lineIndex) {
		if (wordsInLineWithoutToken.size() > 4) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		}

		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch (NumberFormatException exception) {
			throw new ObjReaderException("Failed to parse float value.", lineIndex);

		} catch (IndexOutOfBoundsException exception) {
			throw new ObjReaderException("Too few vertex arguments.", lineIndex);
		}
	}

	protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, final int lineIndex) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		}

		try {
			return new Vector2f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)));

		} catch (NumberFormatException exception) {
			throw new ObjReaderException("Failed to parse float value.", lineIndex);

		} catch (IndexOutOfBoundsException exception) {
			throw new ObjReaderException("Too few texture vertex arguments.", lineIndex);
		}
	}

	protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, final int lineIndex) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		}

		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch (NumberFormatException exception) {
			throw new ObjReaderException("Failed to parse float value.", lineIndex);

		} catch (IndexOutOfBoundsException exception) {
			throw new ObjReaderException("Too few normal arguments.", lineIndex);
		}
	}

	protected static Polygon parsePolygon(final ArrayList<String> wordsInLineWithoutToken, final int lineIndex) {
		if (wordsInLineWithoutToken.size() < 3) {
			throw new ObjReaderException("Too few polygon arguments.", lineIndex);
		}

		Polygon result = new Polygon();
		for (String s : wordsInLineWithoutToken) {
			parsePolygonWord(s, lineIndex, result);
		}

		return result;
	}

	protected static void parsePolygonWord(final String wordInLine, final int lineIndex, Polygon outPolygon) {
		try {
			String[] wordIndices = wordInLine.split("/");

			switch (wordIndices.length) {
				case 1 -> {
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);

					if (Integer.parseInt(wordIndices[0]) - 1 < 0) {
						throw new ObjReaderException("Negative index of point.", lineIndex);
					}
				}
				case 2 -> {
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);
					outPolygon.addTextureVertexIndex(Integer.parseInt(wordIndices[1]) - 1);

					if ((Integer.parseInt(wordIndices[0]) - 1 < 0) || (Integer.parseInt(wordIndices[1]) - 1 < 0)) {
						throw new ObjReaderException("Negative index of point.", lineIndex);
					}
				}
				case 3 -> {
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);
					outPolygon.addNormalIndex(Integer.parseInt(wordIndices[2]) - 1);

					if ((Integer.parseInt(wordIndices[0]) - 1 < 0) || (Integer.parseInt(wordIndices[2]) - 1 < 0)) {
						throw new ObjReaderException("Negative index of point.", lineIndex);
					}

					if (!wordIndices[1].equals("")) {
						outPolygon.addTextureVertexIndex(Integer.parseInt(wordIndices[1]) - 1);

						if (Integer.parseInt(wordIndices[1]) - 1 < 0) {
							throw new ObjReaderException("Negative index of point.", lineIndex);
						}
					}
				}
				default -> throw new ObjReaderException("Invalid element size.", lineIndex);
			}

		} catch (NumberFormatException exception) {
			throw new ObjReaderException("Failed to parse int value.", lineIndex);

		} catch (IndexOutOfBoundsException exception) {
			throw new ObjReaderException("Too few arguments.", lineIndex);
		}
	}
}