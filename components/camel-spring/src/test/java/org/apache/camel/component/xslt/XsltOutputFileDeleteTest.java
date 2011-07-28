/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.xslt;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import static org.apache.camel.spring.processor.SpringTestHelper.createSpringCamelContext;

/**
 * @version 
 */
public class XsltOutputFileDeleteTest extends ContextTestSupport {

    protected CamelContext createCamelContext() throws Exception {
        return createSpringCamelContext(this, "org/apache/camel/component/xslt/XsltOutputFileDeleteTest.xml");
    }

    public void testXsltOutputDeleteFile() throws Exception {
        createDirectory("target/xslt");

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived("<?xml version=\"1.0\" encoding=\"UTF-8\"?><goodbye>world!</goodbye>");
        template.sendBodyAndHeader("direct:start", "<hello>world!</hello>", Exchange.XSLT_FILE_NAME, "target/xslt/xsltme.xml");

        assertMockEndpointsSatisfied();
        oneExchangeDone.matchesMockWaitTime();

        // assert file deleted
        File file = new File("target/xslt/xsltme.xml").getAbsoluteFile();
        assertFalse("File should be deleted", file.exists());
    }

}