package com.sca.sca_application;

import com.sca.sca_application.ScaRules.ScaRulesDecorators.DuringCommentBlockDecorator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaRulesDecoratorsTests {
    @Autowired
    DuringCommentBlockDecorator duringCommentBlockDecorator;

    @Test
    public void duringCommentBlockDecoratorTest(){
        Assert.assertFalse(duringCommentBlockDecorator.isDuringCommentBlock());

        duringCommentBlockDecorator.setDuringCommentBlock(true);
        Assert.assertTrue(duringCommentBlockDecorator.isDuringCommentBlock());

        duringCommentBlockDecorator.setDuringCommentBlock(false);
        Assert.assertFalse(duringCommentBlockDecorator.isDuringCommentBlock());
    }
}
