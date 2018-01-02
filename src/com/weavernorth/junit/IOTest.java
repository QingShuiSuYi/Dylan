package com.weavernorth.junit;

import com.weavernorth.file.IOUtil;
import org.junit.Test;

/**
 * 测试IOUTIL工具类
 */
public class IOTest {

    @Test
    public void testLine(){
        IOUtil.writeFileByLines("F:/a.txt","你好\n我很好\nlala\n,\n.");
    }
}
