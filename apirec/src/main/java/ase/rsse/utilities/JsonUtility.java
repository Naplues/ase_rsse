package ase.rsse.utilities;

import com.google.gson.Gson;

import ase.rsse.apirec.transactions.Transaction;

public final class JsonUtility {

	private static Gson _gson;

	private static Gson getGson() {
		if (_gson == null) {
			_gson = new Gson();
		}
		return _gson;
	}

	public static String toJson(Object obj) {
		return getGson().toJsonTree(obj).toString();
	}

	public static Transaction fromJson(String json) {
		Transaction ts = getGson().fromJson(json, Transaction.class);
		return ts;
	}
}
