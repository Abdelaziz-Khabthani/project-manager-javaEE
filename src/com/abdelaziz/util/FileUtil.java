package com.abdelaziz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

public final class FileUtil {

	private FileUtil() {
	}

	public static void copyUploadedFile(String fileName, String destination,
			UploadedFile file) {
		try {
			InputStream inputStream = file.getInputstream();
			OutputStream outputStream = new FileOutputStream(new File(
					destination + fileName));
			IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SerialBlob uploadedFileToSerialBlob(UploadedFile file) {
		try {
			SerialBlob tmp = new SerialBlob(IOUtils.toByteArray(file
					.getInputstream()));
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] uploadedFileToBytes(UploadedFile file) {
		try {
			return IOUtils.toByteArray(file.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}