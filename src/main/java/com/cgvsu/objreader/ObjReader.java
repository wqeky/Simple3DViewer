package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {

	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";

	public ObjReader() {
	}

	public static Model readModel(String fileContent) throws ObjReaderException {
		Model result = new Model();
		int lineIndex = 0;
		Scanner scanner = new Scanner(fileContent);

		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList(Arrays.asList(line.split("\\s+")));
			if (!wordsInLine.isEmpty()) {
				String token = (String)wordsInLine.get(0);
				wordsInLine.remove(0);
				++lineIndex;
			switch (token) {
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd));
				default -> {}
			}
		}

		return result;
	}

		int maxVertexIndex = -1;
		int maxTextureVertexIndex = -1;
		int maxNormalIndex = -1;
		int polygonIndex = 0;
		Iterator var15 = result.getPolygons().iterator();

		do {
			if (!var15.hasNext()) {
				if (result.equalsEmptyModel()) {
					throw new ObjReaderException("incorrect .obj file");
				}

				return result;
			}

			Polygon polygon = (Polygon)var15.next();
			++polygonIndex;
			Iterator var10 = polygon.getVertexIndices().iterator();

			int normalIndex;
			while(var10.hasNext()) {
				normalIndex = (Integer)var10.next();
				if (normalIndex > maxVertexIndex) {
					maxVertexIndex = normalIndex;
				}
			}

			if (maxVertexIndex > result.getVertices().size()) {
				throw new ObjReaderException("index of vertex is out of bounds in polygon " + polygonIndex + ".");
			}

			var10 = polygon.getTextureVertexIndices().iterator();

			while(var10.hasNext()) {
				normalIndex = (Integer)var10.next();
				if (normalIndex > maxTextureVertexIndex) {
					maxTextureVertexIndex = normalIndex;
				}
			}

			if (maxTextureVertexIndex > result.getTextureVertices().size()) {
				throw new ObjReaderException("index of texture vertex is out of bounds in polygon " + polygonIndex + ".");
			}

			var10 = polygon.getNormalIndices().iterator();

			while(var10.hasNext()) {
				normalIndex = (Integer)var10.next();
				if (normalIndex > maxNormalIndex) {
					maxNormalIndex = normalIndex;
				}
			}
		} while(maxNormalIndex <= result.getNormals().size());

		throw new ObjReaderException("index of normal is out of bounds in polygon " + polygonIndex + ".");
	}

	protected static Vector3f parseVertex(ArrayList<String> wordsInLineWithoutToken, int lineIndex) {
		if (wordsInLineWithoutToken.size() > 4) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		} else {
			try {
				return new Vector3f(Float.parseFloat((String)wordsInLineWithoutToken.get(0)), Float.parseFloat((String)wordsInLineWithoutToken.get(1)), Float.parseFloat((String)wordsInLineWithoutToken.get(2)));
			} catch (NumberFormatException var3) {
				throw new ObjReaderException("Failed to parse float value.", lineIndex);
			} catch (IndexOutOfBoundsException var4) {
				throw new ObjReaderException("Too few vertex arguments.", lineIndex);
			}
		}
	}

	protected static Vector2f parseTextureVertex(ArrayList<String> wordsInLineWithoutToken, int lineIndex) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		} else {
			try {
				return new Vector2f(Float.parseFloat((String)wordsInLineWithoutToken.get(0)), Float.parseFloat((String)wordsInLineWithoutToken.get(1)));
			} catch (NumberFormatException var3) {
				throw new ObjReaderException("Failed to parse float value.", lineIndex);
			} catch (IndexOutOfBoundsException var4) {
				throw new ObjReaderException("Too few texture vertex arguments.", lineIndex);
			}
		}
	}

	protected static Vector3f parseNormal(ArrayList<String> wordsInLineWithoutToken, int lineIndex) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Invalid element size.", lineIndex);
		} else {
			try {
				return new Vector3f(Float.parseFloat((String)wordsInLineWithoutToken.get(0)), Float.parseFloat((String)wordsInLineWithoutToken.get(1)), Float.parseFloat((String)wordsInLineWithoutToken.get(2)));
			} catch (NumberFormatException var3) {
				throw new ObjReaderException("Failed to parse float value.", lineIndex);
			} catch (IndexOutOfBoundsException var4) {
				throw new ObjReaderException("Too few normal arguments.", lineIndex);
			}
		}
	}

	protected static Polygon parsePolygon(ArrayList<String> wordsInLineWithoutToken, int lineIndex) {
		if (wordsInLineWithoutToken.size() < 3) {
			throw new ObjReaderException("Too few polygon arguments.", lineIndex);
		} else {
			Polygon result = new Polygon();
			Iterator var3 = wordsInLineWithoutToken.iterator();

			while(var3.hasNext()) {
				String s = (String)var3.next();
				parsePolygonWord(s, lineIndex, result);
			}

			return result;
		}
	}

	protected static void parsePolygonWord(String wordInLine, int lineIndex, Polygon outPolygon) {
		try {
			String[] wordIndices = wordInLine.split("/");
			switch (wordIndices.length) {
				case 1:
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);
					if (Integer.parseInt(wordIndices[0]) - 1 < 0) {
						throw new ObjReaderException("Negative index of point.", lineIndex);
					}
					break;
				case 2:
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);
					outPolygon.addTextureVertexIndex(Integer.parseInt(wordIndices[1]) - 1);
					if (Integer.parseInt(wordIndices[0]) - 1 >= 0 && Integer.parseInt(wordIndices[1]) - 1 >= 0) {
						break;
					}

					throw new ObjReaderException("Negative index of point.", lineIndex);
				case 3:
					outPolygon.addVertexIndex(Integer.parseInt(wordIndices[0]) - 1);
					outPolygon.addNormalIndex(Integer.parseInt(wordIndices[2]) - 1);
					if (Integer.parseInt(wordIndices[0]) - 1 >= 0 && Integer.parseInt(wordIndices[2]) - 1 >= 0) {
						if (!wordIndices[1].equals("")) {
							outPolygon.addTextureVertexIndex(Integer.parseInt(wordIndices[1]) - 1);
							if (Integer.parseInt(wordIndices[1]) - 1 < 0) {
								throw new ObjReaderException("Negative index of point.", lineIndex);
							}
						}
						break;
					}

					throw new ObjReaderException("Negative index of point.", lineIndex);
				default:
					throw new ObjReaderException("Invalid element size.", lineIndex);
			}

		} catch (NumberFormatException var4) {
			throw new ObjReaderException("Failed to parse int value.", lineIndex);
		} catch (IndexOutOfBoundsException var5) {
			throw new ObjReaderException("Too few arguments.", lineIndex);
		}
	}
}
