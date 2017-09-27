/**
* MIT License
* 
* Copyright (c) 2016 Donato Rimenti
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package co.aurasphere.jandex;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;

import co.aurasphere.jandex.dto.DetectLanguageResponse;
import co.aurasphere.jandex.dto.SupportedLanguageResponse;
import co.aurasphere.jandex.dto.TranslateTextResponse;

public class Jandex {

	public final static String BASE_ENDPOINT = "https://translate.yandex.net/api/v1.5/tr.json/";

	private Client client;

	private WebTarget target;

	private String apiKey;

	public Jandex(String apiKey) {
		this.client = ClientBuilder.newClient();
		this.apiKey = apiKey;
	}

	public Jandex(String apiKey, boolean loggingEnabled) {
		this.client = ClientBuilder.newClient();
		if (loggingEnabled) {
			this.client.register(LoggingFilter.class);
		}
		this.apiKey = apiKey;
	}

	public DetectLanguageResponse detectLanguage(String text) {
		target = client.target(BASE_ENDPOINT + "detect")
				.queryParam("key", apiKey).queryParam("text", text);
		Response response = target.request().get();
		DetectLanguageResponse responseDto = response
				.readEntity(DetectLanguageResponse.class);
		return responseDto;
	}

	public TranslateTextResponse translateText(String text,
			String targetLanguage) {
		target = client.target(BASE_ENDPOINT + "translate")
				.queryParam("key", apiKey).queryParam("text", text)
				.queryParam("lang", targetLanguage);
		Response response = target.request().get();
		TranslateTextResponse responseDto = response
				.readEntity(TranslateTextResponse.class);
		return responseDto;
	}

	public SupportedLanguageResponse supportedLanguages() {
		target = client.target(BASE_ENDPOINT + "getLangs")
				.queryParam("key", apiKey).queryParam("ui", "en");
		Response response = target.request().get();
		SupportedLanguageResponse responseDto = response
				.readEntity(SupportedLanguageResponse.class);
		return responseDto;
	}
}
