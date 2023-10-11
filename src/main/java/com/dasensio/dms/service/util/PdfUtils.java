package com.dasensio.dms.service.util;

public class PdfUtils {

    private PdfUtils() {
        throw new IllegalAccessError("utility class");
    }

    public static boolean isPdf(byte[] data) {
        return hasPdfHeader(data) && (isVersion13(data) || isVersion14plus(data));
    }

    private static boolean hasPdfHeader(byte[] data) {
        //%PDF-
        return data != null && data.length > 4 && data[0] == 0x25 && data[1] == 0x50 && data[2] == 0x44 && data[3] == 0x46 && data[4] == 0x2D;
    }

    private static boolean isVersion14plus(byte[] data) {
        //... %%EOF<EOL>
        return data != null && data.length > 7 && data[data.length - 6] == 0x25 && data[data.length - 5] == 0x25 && data[data.length - 4] == 0x45
            && data[data.length - 3] == 0x4F && data[data.length - 2] == 0x46 && data[data.length - 1] == 0x0A;
    }

    private static boolean isVersion13(byte[] data) {
        //... %%EOF<SPACE><EOL>
        return data != null && data.length > 7 && data[data.length - 7] == 0x25 && data[data.length - 6] == 0x25 && data[data.length - 5] == 0x45
            && data[data.length - 4] == 0x4F && data[data.length - 3] == 0x46 && data[data.length - 2] == 0x20 && data[data.length - 1] == 0x0A;
    }

}
