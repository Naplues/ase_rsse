package ase.rsse.utilities;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import ase.rsse.apirec.transactions.ITransactionConstants;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;

public final class IoUtility {


	public static List<Context> readContext(String pathToContext) {
		LinkedList<Context> res = Lists.newLinkedList();
		try {
			IReadingArchive ra = new ReadingArchive(new File(pathToContext));
			while (ra.hasNext()) {
				res.add(ra.getNext(Context.class));
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static List<IIDEEvent> readEvent(String pathToEvent) {
		LinkedList<IIDEEvent> res = Lists.newLinkedList();
		try {
			IReadingArchive ra = new ReadingArchive(new File(pathToEvent));
			while (ra.hasNext()) {
				res.add(ra.getNext(IIDEEvent.class));
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void writeTransactionToFile(String fileName, String json) throws IOException {
		// create file within Transactions folder
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, fileName);
		FileUtils.writeStringToFile(file, json);
	}

	public static String readContent(String parentDir, String fileName) throws IOException {
		File file = new File(parentDir, fileName);
		return FileUtils.readFileToString(file);
	}
	
	public static String readContentOfTransaction(String fileName) throws IOException {
		return readContent(ITransactionConstants.TRANSACTION_DIRECTORY, fileName);
	}
}
