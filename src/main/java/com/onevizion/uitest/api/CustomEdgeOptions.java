package com.onevizion.uitest.api;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

/**
 * Class to manage options specific to {@link EdgeDriver}.
 *
 * <p>Example usage:
 * <pre><code>
 * EdgeOptions options = new EdgeOptions()
 * options.addExtensions(new File("/path/to/extension.crx"))
 * options.setBinary(new File("/path/to/edge"));
 *
 * // For use with EdgeDriver:
 * EdgeDriver driver = new EdgeDriver(options);
 *
 * // For use with RemoteWebDriver:
 * RemoteWebDriver driver = new RemoteWebDriver(
 *     new URL("http://localhost:4444/wd/hub"),
 *     new EdgeOptions());
 * </code></pre>
 *
 */
public class CustomEdgeOptions extends ChromiumOptions<CustomEdgeOptions> {

  /**
   * Key used to store a set of ChromeOptions in a {@link Capabilities}
   * object.
   */
  public static final String CAPABILITY = "ms:edgeOptions";

  public CustomEdgeOptions() {
    super(CapabilityType.BROWSER_NAME, BrowserType.EDGE, CAPABILITY);
  }
}
