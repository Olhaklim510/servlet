package com.company.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TimeServletTest {
    private final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'x");
    private TimeServlet timeServlet;

    @BeforeEach
    private void init() {
        timeServlet = new TimeServlet();
    }

    @Test
    public void testThatDoGetHandledCorrectly() throws IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        PrintWriter printWriter = mock(PrintWriter.class);

        when(httpServletRequest
                .getParameter(anyString()))
                .thenReturn(OffsetDateTime.now()
                        .atZoneSameInstant(ZoneId.of("UTC"))
                                .toOffsetDateTime().format(FORMAT));
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        timeServlet.doGet(httpServletRequest, httpServletResponse);

        verify(httpServletResponse.getWriter()).write(anyString());
        verify(httpServletResponse.getWriter()).close();
    }

}