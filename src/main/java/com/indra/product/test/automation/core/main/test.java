package com.indra.product.test.automation.core.main;

import java.net.MalformedURLException;

import com.indra.product.test.automation.core.api.TestLink;

import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class test {
	public static void main(String[] args) throws TestLinkAPIException, MalformedURLException {

		TestLink tl = new TestLink();
		tl.gerarFeaturesDeUmTestPlan();
	}
}
