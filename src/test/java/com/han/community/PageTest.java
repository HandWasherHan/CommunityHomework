package com.han.community;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.han.community.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageTest {

    @Autowired
    PostMapper postMapper;

    @Test
    public void selectPageTest() {
        IPage page = new Page(2, 2);
        postMapper.selectPage(page, null);
        System.out.println(page.getRecords());
    }

}
