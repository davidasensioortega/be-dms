package com.dasensio.dms.service.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class PdfUtilsTest {

    @Test
    void expectExceptionWhenInstantiatingClass() throws NoSuchMethodException {
        final Constructor<PdfUtils> constructor = PdfUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void isPdf_whenPdfv13_returnsTrue() {
        Assertions.assertTrue(PdfUtils.isPdf("%PDF-1.3 CONTENT %%EOF \n".getBytes()));
    }

    @Test
    void isPdf_whenPdfv14_returnsTrue() {
        Assertions.assertTrue(PdfUtils.isPdf("%PDF-1.4 CONTENT %%EOF\n".getBytes()));
    }

    @Test
    void isPdf_whenPdfFile_returnsTrue() {
        Assertions.assertTrue(PdfUtils.isPdf(getContentFromFile("test.pdf")));
    }

    private byte[] getContentFromFile(String filename) {
        try {
            return Files.readAllBytes(new ClassPathResource(filename).getFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isPdf_whenInvalidContent_returnsFalse() {
        Assertions.assertFalse(PdfUtils.isPdf("Hello World".getBytes()));
    }

}