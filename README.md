# ui-test-api
High level, Selenium based Java API to programmatically access web UI of OneVizion platform.

Release cycle is syncronized with OneVizion platform releases. API version used should match one of OneVizion.

API hides UI implementation details which may change between versions by providing high level methods to UI elements. For example:
```java
MainMenu.selectMenuItem(String item)
Filter.openFilterForm(Long gridIdx)
Export.waitExportDone()
```
