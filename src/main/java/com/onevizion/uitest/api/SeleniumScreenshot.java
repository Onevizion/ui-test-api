package com.onevizion.uitest.api;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

@Component
public class SeleniumScreenshot {

    @Autowired
    private SeleniumSettings seleniumSettings;

    @Autowired
    private SeleniumLogger seleniumLogger;

    public void getScreenshot() {
        getScreenshot(false);
    }

    void getScreenshot(boolean saveScreenshotToExternalSystem) {
        try {
            if (seleniumSettings.getWebDriver() instanceof TakesScreenshot) {
                if (saveScreenshotToExternalSystem) {
                    String screenshotBase64 = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BASE64);
                    seleniumSettings.setTestFailScreenshot(screenshotBase64);
                }

                byte[] screen = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.BYTES);

                saveScreenshot("", screen);
            } else {
                seleniumLogger.error("Current web browser dont't supports getting screenshots");
            }
        } catch (Exception e) {
            seleniumLogger.error("getScreenshot Unexpected exception: " + e.getMessage());
        }
    }

    private void saveScreenshot(String screenshotFileNameSuffix, byte[] screenshot) {
        String screensDirectory = seleniumSettings.getScreenshotsPath();
        String ciAddress = seleniumSettings.getCiAddr();
        Boolean isRemoteWebDriver = seleniumSettings.getRemoteWebDriver();

        String browserName;
        if (isRemoteWebDriver) {
            browserName = seleniumSettings.getBrowser();
        } else {
            browserName = seleniumSettings.getWebDriver().getClass().getName();
            browserName = browserName.substring(browserName.lastIndexOf('.') + 1);
        }
        browserName = browserName.replaceAll(" ", "_");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        Date date = new Date();

        String screenshotFileName = String.format("%s_%s_%s%s.jpg", browserName, seleniumSettings.getTestName(), dateFormat.format(date), screenshotFileNameSuffix);

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(String.format("%s/%s", screensDirectory, screenshotFileName))))) {
            out.write(screenshot);
            String screenAddr;
            if (isRemoteWebDriver) {
                screenAddr = ciAddress + screenshotFileName;
            } else {
                screenAddr = screensDirectory + screenshotFileName;
            }
            seleniumLogger.error(screenAddr);
        } catch (IOException e) {
            seleniumLogger.error("Can't save screenshot because of: " + e.getMessage());
        }
    }

    public void compareScreenshot(WebElement webElement, String fileName) {
        int imageWidth = webElement.getSize().getWidth();
        int imageHeight = webElement.getSize().getHeight();
        int xCoord = webElement.getLocation().getX();
        int yCoord = webElement.getLocation().getY();

        File screen = ((TakesScreenshot) seleniumSettings.getWebDriver()).getScreenshotAs(OutputType.FILE);

        BufferedImage actualImage = null;
        try {
            BufferedImage img = ImageIO.read(screen);
            actualImage = img.getSubimage(xCoord, yCoord, imageWidth, imageHeight);
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't read actual image", e);
        }

        boolean expectedImageExist;
        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read((new ClassPathResource("com\\onevizion\\guitest\\" + fileName)).getInputStream());
            expectedImageExist = true;
        } catch (FileNotFoundException e) {
            expectedImageExist = false;
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't read expected image", e);
        }

        if (!expectedImageExist) {
            byte[] actualScreen = convertBufferedImageToByteArray(actualImage);
            saveScreenshot("_actual", actualScreen);
            throw new SeleniumUnexpectedException("Expected image not exist");
        }

        boolean isSizeEqual = bufferedImagesSizeEqual(actualImage, expectedImage);
        if (!isSizeEqual) {
            byte[] actualScreen = convertBufferedImageToByteArray(actualImage);
            saveScreenshot("_actual", actualScreen);
            byte[] expectedScreen = convertBufferedImageToByteArray(expectedImage);
            saveScreenshot("_expected", expectedScreen);
            throw new SeleniumUnexpectedException("Size of images not equal. Actual[" + actualImage.getWidth() + "x" + actualImage.getHeight() + "] Expected[" + expectedImage.getWidth() + "x" + expectedImage.getHeight() + "]");
        }

        boolean isEqual = bufferedImagesEqual(actualImage, expectedImage);
        if (!isEqual) {
            byte[] actualScreen = convertBufferedImageToByteArray(actualImage);
            saveScreenshot("_actual", actualScreen);
            byte[] expectedScreen = convertBufferedImageToByteArray(expectedImage);
            saveScreenshot("_expected", expectedScreen);
            BufferedImage differentImage = getDifferentImage(actualImage, expectedImage);
            byte[] differentScreen = convertBufferedImageToByteArray(differentImage);
            saveScreenshot("_different", differentScreen);
            throw new SeleniumUnexpectedException("Images not equal.");
        }
    }

    private boolean bufferedImagesSizeEqual(BufferedImage actualImage, BufferedImage expectedImage) {
        return actualImage.getWidth() == expectedImage.getWidth() && actualImage.getHeight() == expectedImage.getHeight();
    }

    private boolean bufferedImagesEqual(BufferedImage actualImage, BufferedImage expectedImage) {
        for (int x = 0; x < actualImage.getWidth(); x++) {
            for (int y = 0; y < actualImage.getHeight(); y++) {
                if (actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    private BufferedImage getDifferentImage(BufferedImage actualImage, BufferedImage expectedImage) {
        BufferedImage differentImage = new BufferedImage(actualImage.getWidth(), actualImage.getHeight(), actualImage.getType());

        for (int x = 0; x < actualImage.getWidth(); x++) {
            for(int y = 0; y < actualImage.getHeight(); y++) {
                if (actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)) {
                    differentImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    differentImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        return differentImage;
    }

    private byte[] convertBufferedImageToByteArray(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", baos);
            baos.flush();
            byte[] byteArray = baos.toByteArray();
            baos.close();
            return byteArray;
        } catch (IOException e) {
            throw new SeleniumUnexpectedException("Can't convert BufferedImage to ByteArray", e);
        }
    }

}