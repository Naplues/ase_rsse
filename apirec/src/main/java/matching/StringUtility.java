package matching;

public final class StringUtility {

	public static double calculateSimilarity(String a, String b) {
		if (a.equals(b)) {
			return 1.0;
		}
		String prefixA = getPrefix(a);

		switch (prefixA) {
		case "CSharp.DelegateTypeName":
			return 

		}

	}

	public static String getPrefix(String s) {
		return s.split(":")[0];
	}
}
