/**
 * Copyright 2005 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package support.community.lucene.parse.rtf;

import java.io.*;

import support.community.lucene.index.*;

import com.etranslate.tm.processing.rtf.*;

/**
 * 
 * 
 */
public class RtfExtractor implements TextExtractor 
{

    public String extractText(InputStream is) throws Exception
    {
        RTFParserDelegateImpl delegate = new RTFParserDelegateImpl();
        Reader reader = new InputStreamReader(is); ////charset????????
        RTFParser rtfParser = RTFParser.createParser(reader);
        rtfParser.setNewLine("\n");
        rtfParser.setDelegate(delegate);
    
        rtfParser.parse();
    
        return delegate.getText();
    }
}
