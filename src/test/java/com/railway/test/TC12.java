package com.railway.test;

import org.testng.annotations.Test;
import pageObjects.Railway.ForgotPassword;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;

public class TC12 extends PreparationCommonTest {

    @Test
    public void TC12() {
        System.out.println("The password reset token is invalid.");
        HomePage homePage = new HomePage().open();
        LoginPage loginPage = homePage.gotoLoginPage();
        ForgotPassword forgotPassword = loginPage.goToForgotPasswordPage();
        forgotPassword.forgotPassword("thuthao7904@gmail.com");
    }
}
