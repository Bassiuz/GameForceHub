package com.bassiuz.gameforcehub.viewbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseViewBuilderTest {

    @Test
    void wrapPage() {
        String testpage = "testPageadfasdfadsf";
        assertEquals(BaseViewBuilder.wrapPageTop + testpage + BaseViewBuilder.wrapPageBottom,
                        BaseViewBuilder.wrapPage(testpage));
    }

    @Test
    void addHeader() {
        String testpage = "testPageadfasdfadsf";
        assertEquals(BaseViewBuilder.headerTop + testpage + BaseViewBuilder.headerBottom,
                BaseViewBuilder.addHeader(testpage));
    }
}