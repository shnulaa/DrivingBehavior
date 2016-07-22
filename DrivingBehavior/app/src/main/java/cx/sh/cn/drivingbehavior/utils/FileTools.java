package cx.sh.cn.drivingbehavior.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * Created by ZengChao on 2016/6/28.
 */
public class FileTools {

        /**
         * mkFolder:生成文件夹
         *
         * @param folder
         *            文件夹
         *
         * @return true 成功 false 失败
         */
        public static boolean mkFolder(String folder) {
            try {
                FileUtils.forceMkdir(new File(folder));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * rmFolder:删除文件夹
         *
         * @param folder
         *            文件夹
         *
         * @return true 成功 false 失败
         */
        public static boolean rmFolder(String folder) {
            try {
                FileUtils.deleteDirectory(new File(folder));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * tarFile:压缩文件
         *
         * @param tarFileFullPath
         *            压缩文件路径
         * @param folderFullPath
         *            存放目录路径
         *
         * @return true 删除成功 false 删除失败
         */
        public static boolean tgzFile(String tarFileFullPath, String folderFullPath) {

            File srcFolder = new File(folderFullPath);
            if (!srcFolder.exists()) {
                return false;
            }

            try {
                GzipCompressorOutputStream gzOutStream = new GzipCompressorOutputStream(new BufferedOutputStream(
                        new FileOutputStream(tarFileFullPath)));
                TarArchiveOutputStream tarOutStream = new TarArchiveOutputStream(gzOutStream);
                Iterator<File> iter = FileUtils.iterateFiles(srcFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
                int offsetLength = srcFolder.getParent().length() + 1;
                while (iter.hasNext()) {
                    File fp = iter.next();
                    tarOutStream.putArchiveEntry(new TarArchiveEntry(fp, fp.getAbsolutePath().substring(offsetLength)));
                    if (fp.isFile()) {
                        tarOutStream.write(FileUtils.readFileToByteArray(fp));
                    }
                    tarOutStream.closeArchiveEntry();
                }
                tarOutStream.flush();
                tarOutStream.close();
                return true;
            } catch (Exception e) {
//                LogDog.e("压缩文件", e);
                return false;
            } finally {
                FileTools.chmod777(tarFileFullPath);
            }
        }

        public static void chmod777(String filename) {
            File fp = new File(filename);
            if (fp.exists()) {
                if (fp.isDirectory()) {
                    Iterator<File> iter = FileUtils.iterateFiles(fp, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
                    while (iter.hasNext()) {
                        File fpTmp = iter.next();
                        fpTmp.setReadable(true, false);
                        fpTmp.setWritable(true, false);
                        fpTmp.setExecutable(true, false);
                    }
                } else if (fp.isFile()) {
                    fp.setReadable(true, false);
                    fp.setWritable(true, false);
                    fp.setExecutable(true, false);
                }
            }
        }

}
