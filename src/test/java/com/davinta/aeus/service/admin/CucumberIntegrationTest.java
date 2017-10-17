package com.davinta.aeus.service.admin;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "json:target/cucumber.json", "pretty" }, features = "src/test/resources/")
public class CucumberIntegrationTest {
}
