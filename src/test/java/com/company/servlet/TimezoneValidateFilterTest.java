package com.company.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class TimezoneValidateFilterTest {

    private TimezoneValidateFilter filter;

    @BeforeEach
    private void init() {
        filter = new TimezoneValidateFilter();
    }

    @Test
    public void testThatTimezoneValidateFilterHandledCorrectly() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(httpServletRequest.getParameter(anyString())).thenReturn("UTC-1");

        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testThatTimezoneValidateFilterHandledIncorrectly() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(httpServletRequest.getParameter(anyString())).thenReturn("UTC-17");
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(httpServletResponse).setStatus(400);
        verify(printWriter).write("\"Error 400\": \"Invalid timezone\"");
        verify(printWriter, times(1)).close();
    }

}