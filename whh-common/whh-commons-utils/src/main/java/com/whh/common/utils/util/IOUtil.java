package com.whh.common.utils.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Properties;


/**
 * 文件操作工具类
 *
 * @author Huang Zhigang
 */
public class IOUtil {
    private static String preContextPath;

    /**
     * 当前项目路径
     */
    private static String contextPath;

    /**
     * The default buffer size
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private static final int EOF = -1;
    private static char SKIP_CHAR_BUFFER[];
    private static byte SKIP_BYTE_BUFFER[];
    public static String CHARSET_UTF8 = "UTF-8";


    public static String read(String fileName) throws IOException {
        return read(fileName, CHARSET_UTF8);
    }

    public static String read(String fileName, String encoding) throws IOException {
        File file = new File(fileName);
        return read(file, encoding);
    }

    public static String read(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return toString(in, encoding);
        } finally {
            WebUtils.closeQuietly(in);
        }
    }

    public static String toString(InputStream input, String encoding)
            throws IOException {
        return IOUtils.toString(input, encoding);
    }
    
    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static void writeFile(String fileName, String data, String encoding, boolean append) throws IOException {
        File file = new File(fileName);
        writeFile(file, data, encoding, append);
    }

    public static void writeFile(File file, String data, String encoding, boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            write(data, out, encoding);
        } finally {
            WebUtils.closeQuietly(out);
        }
    }

	public static void write(String data, OutputStream output)
			throws IOException {
		write(data, output, null);
	}
 
	public static void write(String data, OutputStream output, String encoding)
			throws IOException {
		byte[] bs = null;
		if (data != null) {
			if (encoding == null) {
				bs = data.getBytes();
			} else {
				bs = data.getBytes(encoding);
			}
		}
		output.write(bs);
	}

	public static void write(byte[] datas, OutputStream output)
			throws IOException {
		if (datas != null) {
			output.write(datas);
		}
	}

    public static void copy(InputStream input, Writer output, String encoding)
            throws IOException {
        if (encoding == null) {
            copy(input, output);
        } else {
            InputStreamReader in = new InputStreamReader(input, encoding);
            copy(in, output);
        }
    }

    public static void copy(InputStream input, Writer output)
            throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy(in, output);
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L)
			return -1;
		else
			return (int) count;
	}

	public static long copy(InputStream input, OutputStream output,
			int bufferSize) throws IOException {
		return copyLarge(input, output, new byte[bufferSize]);
	}

	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		return copy(input, output, 4096);
	}

	public static long copyLarge(InputStream input, OutputStream output,
			byte buffer[]) throws IOException {
		long count;
		int n;
		for (count = 0L; -1 != (n = input.read(buffer)); count += n)
			output.write(buffer, 0, n);

		return count;
	}

	public static long copyLarge(InputStream input, OutputStream output,
			long inputOffset, long length) throws IOException {
		return copyLarge(input, output, inputOffset, length, new byte[4096]);
	}

	public static long copyLarge(InputStream input, OutputStream output,
			long inputOffset, long length, byte buffer[]) throws IOException {
		if (inputOffset > 0L)
			skipFully(input, inputOffset);
		if (length == 0L)
			return 0L;
		int bufferLength = buffer.length;
		int bytesToRead = bufferLength;
		if (length > 0L && length < (long) bufferLength)
			bytesToRead = (int) length;
		long totalRead = 0L;
		do {
			int read;
			if (bytesToRead <= 0
					|| -1 == (read = input.read(buffer, 0, bytesToRead)))
				break;
			output.write(buffer, 0, read);
			totalRead += read;
			if (length > 0L)
				bytesToRead = (int) Math.min(length - totalRead, bufferLength);
		} while (true);
		return totalRead;
	}

	public static long skip(InputStream input, long toSkip) throws IOException {
		if (toSkip < 0L)
			throw new IllegalArgumentException((new StringBuilder())
					.append("Skip count must be non-negative, actual: ")
					.append(toSkip).toString());
		if (SKIP_BYTE_BUFFER == null)
			SKIP_BYTE_BUFFER = new byte[2048];
		long remain = toSkip;
		do {
			if (remain <= 0L)
				break;
			long n = input.read(SKIP_BYTE_BUFFER, 0,
					(int) Math.min(remain, 2048L));
			if (n < 0L)
				break;
			remain -= n;
		} while (true);
		return toSkip - remain;
	}

    public static long skip(Reader input, long toSkip) throws IOException {
		if (toSkip < 0L)
			throw new IllegalArgumentException((new StringBuilder())
					.append("Skip count must be non-negative, actual: ")
					.append(toSkip).toString());
		if (SKIP_CHAR_BUFFER == null)
			SKIP_CHAR_BUFFER = new char[2048];
		long remain = toSkip;
		do {
			if (remain <= 0L)
				break;
			long n = input.read(SKIP_CHAR_BUFFER, 0,
					(int) Math.min(remain, 2048L));
			if (n < 0L)
				break;
			remain -= n;
		} while (true);
		return toSkip - remain;
	}

	public static void skipFully(InputStream input, long toSkip)
			throws IOException {
		if (toSkip < 0L)
			throw new IllegalArgumentException((new StringBuilder())
					.append("Bytes to skip must not be negative: ")
					.append(toSkip).toString());
		long skipped = skip(input, toSkip);
		if (skipped != toSkip)
			throw new EOFException((new StringBuilder())
					.append("Bytes to skip: ").append(toSkip)
					.append(" actual: ").append(skipped).toString());
		else
			return;
	}

	public static void skipFully(Reader input, long toSkip) throws IOException {
		long skipped = skip(input, toSkip);
		if (skipped != toSkip)
			throw new EOFException((new StringBuilder())
					.append("Chars to skip: ").append(toSkip)
					.append(" actual: ").append(skipped).toString());
		else
			return;
	}

    /**
     * 解析出文件的后辍名
     * @param filename
     * @return
     */
    public static String getFileExtName(String filename) {
        if (filename == null || filename.length() == 0) {
            return "";
        }
        int slash = filename.lastIndexOf('/');
        if (slash > -1) {
            filename = filename.substring(slash + 1);
        }
        slash = filename.lastIndexOf('?');
        if (slash > -1) {
            filename = filename.substring(0, slash);
        }
        int dot = filename.lastIndexOf(".");
        if (dot > -1 && dot < filename.length() - 1) {
            return filename.substring(dot + 1);
        }
        return "";
    }

    public static String getResource(String resource) {
        InputStream is = openInputStream(resource);

        try {
            return IOUtils.toString(is, CHARSET_UTF8);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Properties loadProperties(String resource) {
        Properties properties = new Properties();
        try {
            InputStream is = openInputStream(resource);
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("couldn't load properties file '" + resource + "' ", e);
        }
        return properties;
    }

    /**
     * 获取资源文件的输入流
     * @param resource
     * @return
     */
    public static InputStream openInputStream(String resource) {
        return IOUtil.class.getResourceAsStream(resource);
    }
}
