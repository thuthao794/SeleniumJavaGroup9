package com.railway.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.RegisterPage;

public class TC07 extends PreparationCommonTest{
    @Test
    public void TC07() {
        System.out.println("User can create new account");

        HomePage homePage = new HomePage().open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        String actualMsg =  registerPage.register("thuthao@gmail.com","123456789","123456789","123456789").getWelcomeRegisterMsg();
        String expectedMsg =  "Thank you for registering your account";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
}
